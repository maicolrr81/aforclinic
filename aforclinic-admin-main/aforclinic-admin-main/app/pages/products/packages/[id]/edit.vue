<script setup lang="ts">
import type { ProductResponse } from '~~/shared/types/Product';

const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const sumPrice = ref<number>(0);
const discount = ref(0);
const price = ref(0);

const selectedProduct = ref<string>();
const selectedBundleProducts = ref<SelectedProductBundle[]>([]);

const { data: categories } = await useAsyncData(async () => {
  const data = await $api<ApiPagedResponse<ProductCategory>>(`/categories`, {
    method: `get`,
    params: {
      unpaged: true,
    },
  });
  return data.data.content.map(item => ({ label: item.name, value: item.id }));
});

// 조회
const { data, error } = await useAsyncData<Partial<ProductResponse>>(async () => {
  const data = await $api<ApiResponse<ProductResponse>>(`/products/${route.params.id}`);
  return data.data;
}, {
  immediate: true,
  default: () => ({}),
});
// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

watch(data, (newData) => {
  if (!newData?.productBundleList) { return; }
  newData.productBundleList.forEach((v) => {
    // sumPrice.value = sumPrice.value + (v.productDiscountedPrice * v.quantity);
    selectedBundleProducts.value.push({
      bundleId: '',
      productId: v.productId,
      productName: v.productName,
      productAdjustedPrice: v.productAdjustedPrice,
      productDiscountedPrice: v.productDiscountedPrice,
      quantity: v.quantity,
      categoryId: '',
    });
  });

  sumPrice.value = newData.adjustedPrice!;
  price.value = newData.discountedPrice!;
  discount.value = Math.round((1 - price.value / sumPrice.value) * 100);

  // if (newData.price && sumPrice.value > 0) {
  //   discount.value = Number(((1 - newData.price / sumPrice.value) * 100).toFixed(2));
  // }
}, {
  immediate: true,
});

const categoryId = ref<string>(``);
const name = ref(data.value.name);
const description = ref(data.value.description);
// const price = ref(data.value.discountedPrice);

const { data: products } = await useAsyncData<Product[]>(async () => {
  selectedProduct.value = undefined;
  // selectedBundleProducts.value = [];
  if (!categoryId.value) { return []; }
  const data = await $api<ApiResponse<Product[]>>(`/products/bundle-product`, {
    params: {
      categoryId: categoryId.value,
      searchText: null,
    },
  });
  return data.data;
}, {
  immediate: true,
  watch: [() => categoryId.value],
});

const productOptions = ref<{ label: string; value: Product }[]>();

function updateProductOptions(filter = '') {
  const lower = filter.toLowerCase();
  productOptions.value = products.value
    ?.filter(product =>
      !selectedBundleProducts.value.find(v => (product.id === v.productId))
      && product.name.toLowerCase().includes(lower),
    )
    ?.map(product => ({
      label: product.name,
      value: product,
    }));

  sumPrice.value = 0;
  selectedBundleProducts.value.forEach((v) => {
    sumPrice.value = sumPrice.value + (v.productDiscountedPrice * v.quantity);
  });

  // price.value = Math.round(sumPrice.value * ((100 - discount.value) / 100));
}

function productFilterFn(val: string, update: (cb: () => void) => void, _abort: () => void) {
  update(() => {
    updateProductOptions(val || '');
  });
}

// products나 selectedBundleProducts가 바뀌면 options 다시 계산
watch([products, selectedBundleProducts], () => {
  updateProductOptions('');
}, {
  deep: true,
});

// let skipPriceWatch = false;
// let skipDiscountWatch = false;
// watch(price, (newPrice) => {
//   if (skipPriceWatch) {
//     skipPriceWatch = false;
//     return;
//   }
//   if (newPrice && newPrice > 0) {
//     skipDiscountWatch = true;
//     selectPrice(newPrice);
//   }
// });
// watch(discount, (newDiscount) => {
//   if (skipDiscountWatch) {
//     skipDiscountWatch = false;
//     return;
//   }
//   skipPriceWatch = true;
//   selectDiscount(newDiscount);
// });

function selectProduct(newValue: Product) {
  const duplicatedProduct = selectedBundleProducts.value.find(product => product.productId === newValue.id);
  if (duplicatedProduct) {
    return;
  }
  const foundedProduct = products.value?.find((product) => {
    return (product.id === newValue.id);
  });
  if (foundedProduct) {
    selectedBundleProducts.value.push({
      bundleId: '',
      productId: foundedProduct.id,
      productName: foundedProduct.name,
      productAdjustedPrice: foundedProduct.adjustedPrice,
      productDiscountedPrice: foundedProduct.discountedPrice,
      quantity: 1,
      categoryId: '',
    });
    selectedProduct.value = foundedProduct.name;
  }
}

function deleteBundleProduct(productId: string) {
  selectedBundleProducts.value = selectedBundleProducts.value.filter(v => v.productId !== productId);
}

// function selectDiscount(discount: number) {
//   price.value = Math.round(sumPrice.value * ((100 - discount) / 100));
// }

// function selectPrice(price: number) {
//   discount.value = Math.round((100 - price / sumPrice.value * 100) * 100) / 100;
// }

watch(sumPrice, (newSumPrice) => {
  if (newSumPrice && newSumPrice > 0) {
    discount.value = Math.round((1 - price.value / newSumPrice) * 100);
  }
});

watch(price, (newPrice) => {
  if (newPrice && newPrice > 0) {
    discount.value = Math.round((1 - newPrice / sumPrice.value) * 100);
  }
});

async function remove() {
  const confirmed = await $dialog.confirm(`패키지를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  // $q.loading.show({
  //   message: `처리 중입니다...`,
  // });

  try {
    await $api(`/products/${route.params.id}`, {
      method: `patch`,
    });
    navigateTo(`/products`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `패키지 삭제 중 오류가 발생했습니다.`);
  }
  finally {
    // $q.loading.hide();
  }
}

async function submit() {
  try {
    if (!name.value) {
      $dialog.alert(`패키지명이 입력되지 않았습니다.`);
      return;
    }
    const productBundleList: CreateProductBundle[] = [];
    selectedBundleProducts.value.forEach((v) => {
      productBundleList.push({
        productId: v.productId,
        quantity: v.quantity,
      });
    });

    if (productBundleList.length === 0) {
      $dialog.alert(`패키지에 상품이 등록되지 않았습니다.`);
      return;
    }

    await $api(`/products/${route.params.id}`, {
      method: `put`,
      body: {
        name: name.value,
        description: description.value,
        adjustedPrice: sumPrice.value ?? 0,
        discountedPrice: price.value ?? 0,
        categoryList: [],
        productBundleList,
      } satisfies UpdateProduct,
    });
    navigateTo(`/products`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `패키지 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between  pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            패키지 수정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection class="space-y-4">
        <fieldset class="flex gap-4">
          <QInput
            v-model="name"
            label="패키지명"
            autofocus
            clearable
            class="flex-1"
          />
        </fieldset>

        <fieldset>
          <QInput
            v-model="description"
            label="패키지 설명"
            type="textarea"
          />
        </fieldset>
        <fieldset>
          <QSelect
            v-model="categoryId"
            label="카테고리"
            :options="categories"
            emit-value
            map-options
            class="w-full"
          />
        </fieldset>
        <fieldset>
          <QSelect
            v-model="selectedProduct"
            :options="productOptions"
            emit-value
            map-options
            input-debounce="0"
            label="상품을 검색합니다."
            :readonly="!categoryId"
            use-input
            hide-selected
            @filter="productFilterFn"
            @update:model-value="selectProduct"
          />
        </fieldset>

        <QList
          bordered
          class="rounded"
        >
          <QItemLabel class="p-4 text-base text-primary">
            패키지에 등록할 상품 목록
          </QItemLabel>
          <QItem
            v-for="product of selectedBundleProducts"
            :key="product.productId"
            class="flex items-center gap-6"
            clickable
          >
            <div>
              {{ product.productName }}
            </div>
            <QSpace />
            <div>
              {{ product.productDiscountedPrice }} 원
            </div>
            <QInput
              v-model="product.quantity"
              label="개수"
              type="number"
              step="1"
              min="1"
              dense
              class="w-20"
            />
            <QBtn
              icon="close"
              color="red"
              flat
              dense
              @click="deleteBundleProduct(product.productId)"
            />
          </QItem>
        </QList>

        <div class="w-full">
          <fieldset class="space-y-4">
            <QInput
              v-model="sumPrice"
              label="정가 가격"
            >
              <template #append>
                원
              </template>
            </QInput>
            <QInput
              v-model="price"
              label="패키지 가격"
              type="number"
              step="100"
              min="0"
            >
              <template #append>
                원
              </template>
            </QInput>
            <QInput
              v-model="discount"
              label="할인율"
              type="number"
              max="100"
              min="1"
              readonly
            >
              <template #append>
                %
              </template>
            </QInput>
          </fieldset>
        </div>
      </QCardSection>

      <QCardActions class="justify-end p-4">
        <QBtn
          class="!px-8 !py-2"
          color="red"
          label="삭제"
          @click="remove()"
        />
        <QSpace />
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="목록"
          to="/products"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="저장"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
