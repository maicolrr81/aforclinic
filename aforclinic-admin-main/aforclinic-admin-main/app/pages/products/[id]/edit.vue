<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<Product>>(async () => {
  const data = await $api<ApiResponse<Product>>(`/products/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const { data: categories } = await useAsyncData<ApiResponse<ProductCategory[]>>(() => $api(`/categories/names`));
const computedCategoryOptions = computed(() => Array.isArray(categories.value?.data)
  ? categories.value?.data.map(category => ({
    text: category.name,
    value: category.id,
  }))
  : []);

const id = ref(data.value.id!);
const categoryId = ref(data.value.categoryList?.[0]?.id);
const name = ref(data.value.name!);
const description = ref(data.value.description);

const sumPrice = ref(data.value.adjustedPrice!);
const price = ref(data.value.discountedPrice!);
const discount = ref(Math.round((1 - price.value / sumPrice.value) * 100));

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
  const confirmed = await $dialog.confirm(`상품을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  try {
    await $api(`/products/${id.value}`, {
      method: `patch`,
    });
    navigateTo(`/products`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `상품 삭제 중 오류가 발생했습니다.`);
  }
}

async function submit() {
  try {
    if (!categoryId.value) {
      $dialog.alert(`카테고리 선택되지 않았습니다.`);
      return;
    }
    if (!name.value) {
      $dialog.alert(`상품명이 입력되지 않았습니다.`);
      return;
    }
    const categoryList: Category[] = [];
    categoryList.push({
      id: categoryId.value,
    });
    await $api(`/products/${id.value}`, {
      method: `put`,
      body: {
        name: name.value,
        description: description.value,
        adjustedPrice: sumPrice.value ?? 0,
        discountedPrice: price.value ?? 0,
        categoryList,
        productBundleList: [],
      } satisfies UpdateProduct,
    });
    navigateTo(`/products`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `상품 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            상품 수정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>
      <QCardSection class="space-y-4">
        <fieldset class="flex gap-4">
          <QSelect
            v-model="categoryId"
            label="카테고리"
            :options="computedCategoryOptions"
            emit-value
            map-options
            option-label="text"
            option-value="value"
            class="w-full md:w-40"
          />
          <QInput
            v-model="name"
            label="상품명"
            autofocus
            clearable
            class="flex-1"
          />
        </fieldset>

        <fieldset>
          <QInput
            v-model="description"
            label="상품 설명"
            type="textarea"
          />
        </fieldset>
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
            label="상품 가격"
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
