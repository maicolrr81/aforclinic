<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();

const name = ref('');
const description = ref(``);
const displayOrder = ref(999);

async function submit() {
  try {
    if (!name.value) {
      $dialog.alert(`상품 카테고리명이 입력되지 않았습니다.`);
      return;
    }
    await $api(`/categories`, {
      method: `post`,
      body: {
        name: name.value,
        description: description.value,
        displayOrder: displayOrder.value,
      } satisfies CreateProductCategory,
    });
    navigateTo(`/products/categories`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `상품 카테고리 등록 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            상품 카테고리 등록
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>
      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <QInput
            v-model.trim="name"
            type="text"
            label="상품 카테고리명"
            clearable
          />
          <QInput
            v-model.trim="description"
            type="textarea"
            label="상품 카테고리 설명"
            clearable
          />
          <!-- <QInput
            v-model.trim="displayOrder"
            type="number"
            label="화면 표시 순서"
          /> -->
        </QForm>
      </QCardSection>
      <QCardActions class="justify-end p-4">
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="취소"
          to="/products/categories"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="등록"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
