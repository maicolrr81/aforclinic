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

const id = ref(data.value.id!);
const name = ref(data.value.name!);
const description = ref(data.value.description!);
const displayOrder = ref(data.value.displayOrder ?? 0);

async function remove() {
  const confirmed = await $dialog.confirm(`상품 카테고리를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  try {
    await $api(`/categories/${id.value}`, {
      method: `patch`,
    });
    navigateTo(`/products/categories`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `상품 카테고리 삭제 중 오류가 발생했습니다.`);
  }
}

async function submit() {
  try {
    await $api(`/categories/${id.value}`, {
      method: `put`,
      body: {
        name: name.value,
        description: description.value,
        displayOrder: displayOrder.value,
      } satisfies UpdateProductCategory,
    });
    navigateTo(`/products/categories`);
  }
  catch (error) {
    $dialog.error(error, `상품 카테고리 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-md">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            상품 카테고리 수정
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
          label="저장"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
