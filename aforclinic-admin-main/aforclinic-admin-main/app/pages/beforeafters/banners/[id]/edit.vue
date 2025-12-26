<script setup lang="ts">
const { $api, $config, $dialog } = useNuxtApp();

const { onChange: onThumbnailChange, open } = useFileUpload();
const route = useRoute();

const imageId = ref<string | undefined>(``);

const mediaType = ref(`IMAGE`);
const mediaImagePath = ref(``);
const mediaVideoLink = ref(``);

const { data, error } = await useAsyncData<Partial<BeforeAfterBanner>>(async () => {
  const data = await $api<ApiResponse<BeforeAfterBanner>>(`/beforeafters/banners/${route.params.id}`);

  mediaType.value = data.data.mediaType;
  if (mediaType.value === 'IMAGE') {
    mediaImagePath.value = data.data.mediaContent;
  }
  else {
    mediaVideoLink.value = data.data.mediaContent;
  }
  return data.data;
}, {
  default: () => ({}),
});

const title = ref(data.value.title);
const description = ref(data.value.description);

watch(imageId, () => {
  if (imageId.value) { mediaImagePath.value = `${$config.public.apiBase}/files/${imageId.value}`; }
  else {
    mediaImagePath.value = ``;
  }
});

onThumbnailChange((item) => {
  imageId.value = item.id;
});

async function remove() {
  const confirmed = await $dialog.confirm(`전후사진 배너를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/beforeafters/banners/${route.params.id}`, {
        method: `patch`,
        body: {},
      });
      navigateTo(`/beforeafters/banners`, { replace: true });
    }
    catch (error) {
      $dialog.error(error, `전후사진 배너 삭제 중 오류가 발생했습니다.`);
    }
  }
}

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }
    let mediaContent = '';
    if (mediaType.value === 'IMAGE') {
      if (!mediaImagePath.value) {
        $dialog.alert(`이미지가 없습니다.`);
        return;
      }
      mediaContent = mediaImagePath.value;
    }
    else {
      if (!mediaVideoLink.value) {
        $dialog.alert(`영상이 없습니다.`);
        return;
      }
      mediaContent = mediaVideoLink.value;
    }

    await $api(`/beforeafters/banners/${route.params.id}`, {
      method: `put`,
      body: {
        title: title.value,
        description: description.value,
        mediaType: mediaType.value,
        mediaContent,
      },
    });// satisfies CreateEvent | CreateEventWithPost,    );
    navigateTo(`/beforeafters/banners`);
  }
  catch (error) {
    $dialog.error(error, `전후사진 배너 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-lg">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            전후사진 배너 수정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <div class="flex gap-4">
            <div class="flex-1 space-y-4">
              <QInput
                v-model="title"
                label="제목"
                autofocus
                clearable
              />
              <QInput
                v-model="description"
                type="textarea"
                label="설명"
                autogrow
                clearable
              />
            </div>
          </div>
        </QForm>
      </QCardSection>
      <QSeparator />

      <QCardSection>
        <div class="flex items-center gap-6">
          <QBtnToggle
            v-model="mediaType"
            toggle-color="primary"
            :options="[
              { label: '이미지', value: 'IMAGE' },
              { label: '영상', value: 'VIDEO' },
            ]"
          />
          <div
            v-if="mediaType === 'IMAGE'"
            class="flex flex-1 gap-4"
          >
            <QInput
              v-model="mediaImagePath"
              label="이미지 경로 (이미지는 9:16(1080*1920) 비율의 10MB 미만으로 선택하세요.)"
              dense
              class="flex-1 text-lg"
            />
            <QBtn
              v-if="imageId"
              color="red"
              label="이미지 삭제"
              class=""
              @click="imageId = undefined"
            />
            <QBtn
              color="blue"
              label="이미지 선택"
              class=""
              @click="open()"
            />
          </div>
          <div
            v-else
            class="flex flex-1 gap-4"
          >
            <QInput
              v-model="mediaVideoLink"
              label="영상 링크 입력 (예시  https://www.youtube.com/watch?v=761ae_KDg_Q)"
              dense
              class="flex-1 text-lg"
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
          to="/beforeafters/banners"
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
