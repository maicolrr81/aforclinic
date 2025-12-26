<script setup lang="ts">
const { $api, $config, $dialog } = useNuxtApp();

const { onChange: onThumbnailChange } = useFileUpload();
const route = useRoute();

const { data: categories } = await useAsyncData<ApiResponse<ProductCategory[]>>(() => $api(`/categories/names`), { immediate: true });
const computedCategoryOptions = computed(() => Array.isArray(categories.value?.data)
  ? categories.value?.data.map(category => ({
    text: category.name,
    value: category.id,
  }))
  : []);

const { data } = await useAsyncData<Partial<BeforeAfter>>(async () => {
  const data = await $api<ApiResponse<BeforeAfter>>(`/beforeafters/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
  immediate: true,
});

const categoryId = ref(data.value.beforeAfterCategoriesList?.[0]?.categoryId ?? '');
const title = ref(data.value.title);
const description = ref(data.value.description);

const recommendedTags = ref<string[]>([]);

const imgIdx = ref(``);
const beforeImageId = ref<string | undefined>(data.value.beforeImage);
const afterImageId = ref<string | undefined>(data.value.afterImage);

watchEffect(() => {
  const rawTags = data.value?.beforeAfterTagsList;
  try {
    if (rawTags && rawTags.length > 0) {
      rawTags.forEach((v) => {
        recommendedTags.value.push(v.tag);
      });
    }
  }
  catch {
    recommendedTags.value = [];
  }
});

onThumbnailChange((item) => {
  if (imgIdx.value === `before`) {
    beforeImageId.value = item.id;
  }
  else if (imgIdx.value === `after`) {
    afterImageId.value = item.id;
  }
});

async function remove() {
  const confirmed = await $dialog.confirm(`전후사진을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/beforeafters/${route.params.id}`, {
        method: `patch`,
        body: {},
      });
      navigateTo(`/beforeafters`, { replace: true });
    }
    catch {
      $dialog.alert(`삭제 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-lg">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="pe-0">
          <QToolbarTitle class="text-center">
            {{ title }}
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>
      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <div class="flex gap-4">
            <QSelect
              v-model="categoryId"
              label="카테고리"
              :options="computedCategoryOptions"
              emit-value
              map-options
              option-label="text"
              option-value="value"
              class="w-full md:w-40"
              readonly
            />
            <QInput
              v-model="title"
              label="제목"
              autofocus
              class="flex-1"
              readonly
            />
          </div>
          <div>
            <QInput
              v-model="description"
              type="textarea"
              label="설명"
              autogrow
              readonly
            />
          </div>
        </QForm>
      </QCardSection>

      <QSeparator />

      <QCardSection>
        <h3 class="pb-4 pl-3 text-lg">
          태그
        </h3>
        <div class="mb-3 flex flex-wrap gap-2">
          <QChip
            v-for="(item, index) of recommendedTags"
            :key="index"
            color="blue"
            text-color="white"
          >
            {{ item }}
          </QChip>
        </div>
      </QCardSection>

      <QSeparator />

      <QCardSection class="grid grid-flow-col items-center justify-center gap-9">
        <div class="text-center">
          <div class="flex w-80 flex-col items-center justify-between gap-4">
            <QImg
              v-if="beforeImageId"
              class="max-h-96 w-full flex-1 rounded-lg border"
              :src="`${$config.public.apiBase}/files/${beforeImageId}`"
              fit="contain"
            />
          </div>
        </div>
        <div class="text-center">
          <div class="flex w-80 flex-col items-center justify-between gap-4">
            <QImg
              v-if="afterImageId"
              class="max-h-96 w-full flex-1 rounded-lg border"
              :src="`${$config.public.apiBase}/files/${afterImageId}`"
              fit="contain"
            />
          </div>
        </div>
      </QCardSection>

      <QSeparator />

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
          to="/beforeafters"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="수정"
          :to="`/beforeafters/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
