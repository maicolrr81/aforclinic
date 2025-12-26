<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<ProductCategory>>(async () => {
  const data = await $api<ApiResponse<ProductCategory>>(`/categories/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

async function remove() {
  const confirmed = await $dialog.confirm(`상품 카테고리를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/categories/${route.params.id}`, {
        method: `patch`,
      });
      navigateTo(`/products/categories`, { replace: true });
    }
    catch (error) {
      $dialog.error(error, `상품 카테고리 수정 중 오류가 발생했습니다.`);
    }
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

      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <QInput
            v-model.trim="data.name"
            type="text"
            label="상품 카테고리명"
            readonly
          />
          <QInput
            v-model.trim="data.description"
            type="textarea"
            label="상품 카테고리 설명"
            readonly
          />
          <QInput
            v-model.trim="data.displayOrder"
            type="number"
            label="화면 표시 순서"
            readonly
          />
        </QForm>
      </QCardSection>

      <!-- <QCardSection>
        <div class="min-h-80 whitespace-pre-line">
          {{ data.description }}
        </div>
      </QCardSection> -->
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
          to="/products/categories"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="수정"
          :to="`/products/categories/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
