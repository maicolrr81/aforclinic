<script setup lang="ts">
import type { BeforeAfterCategories, BeforeAfterTags } from '~~/shared/types/BeforeAfter';

const { $api, $config, $dialog } = useNuxtApp();

const { onChange: onThumbnailChange, open } = useFileUpload();
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
const title = ref<string>(data.value.title ?? ``);
const description = ref(data.value.description);

const recommendedTag = ref<string>(``);
const recommendedTags = ref<string[]>([]);

const imageId = ref<string | undefined>(data.value.image);
// const imgIdx = ref(``);
// const beforeImageId = ref<string | undefined>(data.value.beforeImage);
// const afterImageId = ref<string | undefined>(data.value.afterImage);

// watchEffect(() => {
//   const rawTags = data.value?.beforeAfterTagsList;
//   try {
//     if (rawTags && rawTags.length > 0) {
//       rawTags.forEach((v) => {
//         recommendedTags.value.push(v.tag);
//       });
//     }
//   }
//   catch {
//     recommendedTags.value = [];
//   }
// });
watch(() => data.value, (newData) => {
  const rawTags = newData?.beforeAfterTagsList;
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
}, {
  immediate: true,
});

function addRecommendedTag() {
  const newValue = recommendedTag.value;
  if (newValue && !recommendedTags.value.includes(newValue)) {
    recommendedTags.value.push(newValue);
  }
  recommendedTag.value = ``;
}

function removeRecommendedTag(keyword: string) {
  recommendedTags.value = recommendedTags.value.filter(recommendedTag => recommendedTag !== keyword);
}

onThumbnailChange((item) => {
  imageId.value = item.id;
  // if (imgIdx.value === `before`) {
  //   beforeImageId.value = item.id;
  // }
  // else if (imgIdx.value === `after`) {
  //   afterImageId.value = item.id;
  // }
});

function imgClick() {
  open();
}

// function beforeImgClick() {
//   imgIdx.value = `before`;
//   open();
// }
// function afterImgClick() {
//   imgIdx.value = `after`;
//   open();
// }

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
    catch (error) {
      $dialog.error(error, `전후사진 삭제 중 오류가 발생했습니다.`);
    }
  }
}

async function submit() {
  try {
    if (!categoryId.value) {
      $dialog.alert(`카테고리 선택이 필요합니다.`);
      return;
    }
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }
    if (!imageId.value) {
      $dialog.alert(`전후사진 선택이 필요합니다.`);
      return;
    }
    // if (!beforeImageId.value) {
    //   $dialog.alert(`전 사진 선택이 필요합니다.`);
    //   return;
    // }
    // if (!afterImageId.value) {
    //   $dialog.alert(`후 사진 선택이 필요합니다.`);
    //   return;
    // }

    const beforeAfterCategoriesList: BeforeAfterCategories[] = [];
    if (categoryId.value) {
      beforeAfterCategoriesList.push({
        beforeAfterId: '',
        categoryId: categoryId.value,
        category: null,
      });
    }
    const beforeAfterTagsList: BeforeAfterTags[] = [];
    if (recommendedTags.value && recommendedTags.value.length > 0) {
      recommendedTags.value.forEach((v) => {
        beforeAfterTagsList.push({
          beforeAfterId: '',
          tag: v,
        });
      });
    }

    await $api(`/beforeafters/${route.params.id}`, {
      method: `put`,
      body: {
        title: title.value,
        description: description.value,
        image: imageId.value,
        // beforeImage: beforeImageId.value,
        // afterImage: afterImageId.value,
        // afterBlurImage: undefined,
        beforeAfterCategoriesList,
        beforeAfterTagsList,
      } satisfies UpdateBeforeAfter,
    });
    navigateTo(`/beforeafters`);
  }
  catch (error) {
    $dialog.error(error, `전후사진 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-lg">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            전후사진 수정
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
            />
            <QInput
              v-model="title"
              label="제목"
              autofocus
              clearable
              class="flex-1"
            />
          </div>
          <div>
            <QInput
              v-model="description"
              type="textarea"
              label="설명"
              autogrow
              clearable
            />
          </div>
        </QForm>
      </QCardSection>

      <QSeparator />

      <QCardSection>
        <h3 class="pb-4 pl-3 text-lg">
          태그
        </h3>
        <QInput
          v-model="recommendedTag"
          label="태그 입력"
          dense
          @keydown.enter.prevent="addRecommendedTag()"
        />
        <div class="mt-3 flex flex-wrap gap-2">
          <QChip
            v-for="(item, index) of recommendedTags"
            :key="index"
            removable
            color="blue"
            text-color="white"
            @remove="removeRecommendedTag(item)"
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
              v-if="imageId"
              class="max-h-96 w-full flex-1 rounded-lg border"
              :src="`${$config.public.apiBase}/files/${imageId}`"
              fit="contain"
            />
            <div class="flex gap-4">
              <QBtn
                v-if="imageId"
                color="red"
                label="전후사진 삭제"
                @click="imageId = undefined"
              />
              <QBtn
                color="blue"
                label="전후사진 선택"
                @click="imgClick"
              />
            </div>
          </div>
        </div>
      </QCardSection>
      <div class="pb-4 pl-4 pr-4 text-center text-gray-500">
        전후사진은 4:5 비율의 10MB 미만으로 선택하세요.
      </div>

      <!-- <QCardSection class="grid grid-flow-col items-center justify-center gap-9">
        <div class="text-center">
          <div class="flex w-80 flex-col items-center justify-between gap-4">
            <QImg
              v-if="beforeImageId"
              class="max-h-96 w-full flex-1 rounded-lg border"
              :src="`${$config.public.apiBase}/files/${beforeImageId}`"
              fit="contain"
            />
            <div class="flex gap-4">
              <QBtn
                v-if="beforeImageId"
                color="red"
                label="전 사진 삭제"
                @click="beforeImageId = undefined"
              />
              <QBtn
                color="blue"
                label="전 사진 선택"
                @click="beforeImgClick"
              />
            </div>
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
            <div class="flex gap-4">
              <QBtn
                v-if="afterImageId"
                color="red"
                label="후 사진 삭제"
                @click="afterImageId = undefined"
              />
              <QBtn
                color="blue"
                label="후 사진 선택"
                @click="afterImgClick"
              />
            </div>
          </div>
        </div>
      </QCardSection> -->

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
          label="저장"
          @click="submit()"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
