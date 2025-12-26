<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const sumPrice = ref(0);
const discount = ref(0);

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
    sumPrice.value = sumPrice.value + (v.productDiscountedPrice * v.quantity);
  });
  if (newData.discountedPrice && sumPrice.value > 0) {
    discount.value = Math.round((1 - newData.discountedPrice / sumPrice.value) * 100);
  }
}, {
  immediate: true,
});

async function remove() {
  const confirmed = await $dialog.confirm(`상품을 삭제하시겠습니까?`, {
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
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="pe-0">
          <QToolbarTitle class="text-center">
            {{ data.name }}
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection class="flex gap-4">
        <div class="flex w-1/3 gap-4">
          <div class="flex w-full flex-col items-center justify-center gap-4">
            <QImg
              v-if="data.imageId"
              class="max-h-96 w-full flex-1"
              :src="`${$config.public.apiBase}/files/${data.imageId}`"
              fit="contain"
            />
            <QSpace v-else />
          </div>
        </div>
        <div class="w-full space-y-4">
          <QInput
            v-model="data.name"
            label="패키지명"
            autofocus
            readonly
            class="w-full"
          />
          <QInput
            v-model="data.description"
            label="패키지 설명"
            type="textarea"
            autofocus
            readonly
            class="w-full"
          />
          <QList
            bordered
            class="rounded"
          >
            <QItemLabel class="p-4 text-base text-primary">
              패키지에 등록된 상품 목록
            </QItemLabel>
            <QItem
              v-for="product of data.productBundleList"
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
                readonly
              />
            </QItem>
          </QList>
          <QInput
            v-model="sumPrice"
            label="정가 가격"
            readonly
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
          <QInput
            v-model="data.discountedPrice"
            label="패키지 가격"
            autofocus
            readonly
            class="w-full"
          >
            <template #append>
              원
            </template>
          </QInput>
        </div>
      </QCardSection>
      <QCardActions class="justify-between border-t p-4">
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
          label="수정"
          :to="`/products/packages/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
