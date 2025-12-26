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

async function remove() {
  const confirmed = await $dialog.confirm(`상품을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    await $api(`/products/${route.params.id}`, {
      method: `patch`,
    });
    navigateTo(`/products`, { replace: true });
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
        <div class="w-full space-y-4">
          <QInput
            v-model="data.categoryList[0].name"
            label="카테고리"
            autofocus
            readonly
            class="w-full"
          />
          <QInput
            v-model="data.name"
            label="상품명"
            autofocus
            readonly
            class="w-full"
          />
          <QInput
            v-model="data.description"
            label="상품 설명"
            type="textarea"
            autofocus
            readonly
            class="w-full"
          />
          <QInput
            v-model="data.price"
            label="가격"
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
          :to="`/products/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
