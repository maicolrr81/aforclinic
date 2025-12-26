<script setup lang="ts">
import type { ProductSignatureResponse } from '~~/shared/types/Product';

const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const selectedProduct = ref<string>();
// const selectedBundleProducts = ref<SelectedProductBundle[]>([]);
const selectedSignatures = ref<{ productId: string; productName: string }[]>([]);

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
const { data, error } = await useAsyncData<ProductSignatureResponse[]>(async () => {
  const data = await $api<ApiResponse<ProductSignatureResponse[]>>(`/products/signatures`);
  return data.data;
}, {
  immediate: true,
  default: () => [],
});

watch(data, (newData) => {
  if (!newData || newData.length === 0) { return; }
  newData.forEach((v) => {
    selectedSignatures.value.push({ productId: v.productId, productName: v.product.name });
  });
}, {
  immediate: true,
});

const categoryId = ref<string>(``);

const { data: products } = await useAsyncData<Product[]>(async () => {
  selectedProduct.value = undefined;
  // selectedSignatures.value = [];
  if (!categoryId.value) { return []; }
  const data = await $api<ApiResponse<Product[]>>(`/products/event-product`, {
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
      !selectedSignatures.value.find(v => product.id === v.productId)
      && product.name.toLowerCase().includes(lower),
    )
    ?.map(product => ({
      label: product.name,
      value: product,
    }));
}

function productFilterFn(val: string, update: (cb: () => void) => void, abort: () => void) {
  update(() => {
    updateProductOptions(val || '');
  });
}

// products나 selectedSignatures가 바뀌면 options 다시 계산
watch([products, selectedSignatures], () => {
  updateProductOptions('');
}, {
  deep: true,
});

function selectProduct(newValue: Product) {
  const duplicatedProduct = selectedSignatures.value.find(v => v.productId === newValue.id);
  if (duplicatedProduct) {
    return;
  }
  const foundedProduct = products.value?.find((product) => {
    return (product.id === newValue.id);
  });
  if (foundedProduct) {
    selectedSignatures.value.push({ productId: foundedProduct.id, productName: foundedProduct.name });
    selectedProduct.value = foundedProduct.name;
  }
}

function deleteBundleProduct(productId: string) {
  selectedSignatures.value = selectedSignatures.value.filter(v => v.productId !== productId);
}

async function submit() {
  // 유효성 체크해야함
  if (!categoryId.value) { return; }

  try {
    const productList: string[] = [];
    selectedSignatures.value.forEach((v) => {
      productList.push(v.productId);
    });

    await $api(`/products/signatures`, {
      method: `post`,
      body: {
        productList,
      },
    });
    $dialog.alert(`시그니처로 등록되었습니다.`);
    navigateTo(`/products/signatures`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `시그니처 저장 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between  pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            시그니처 관리
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection class="space-y-4">
        <fieldset class="flex gap-4">
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
            시그니처에 등록할 상품 목록
          </QItemLabel>
          <QItem
            v-for="product of selectedSignatures"
            :key="product.productId"
            class="flex items-center gap-6"
            clickable
          >
            <div>
              {{ product.productName }}
            </div>
            <QSpace />
            <QBtn
              icon="close"
              color="red"
              flat
              dense
              @click="deleteBundleProduct(product.productId)"
            />
          </QItem>
        </QList>
      </QCardSection>

      <QCardActions class="justify-end p-4">
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
