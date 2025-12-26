<script setup lang="ts">
const { $api, $config, $dialog } = useNuxtApp();

const { onChange: onThumbnailChange, open } = useFileUpload();

const title = ref(``);
const linkUri = ref(``);
const imageId = ref<string | undefined>(``);
const displayOrder = ref(999);

onThumbnailChange((item) => {
  imageId.value = item.id;
});

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }
    if (!linkUri.value) {
      $dialog.alert(`경로가 입력되지 않았습니다.`);
      return;
    }
    if (!imageId.value) {
      $dialog.alert(`이미지가 선택되지 않았습니다.`);
      return;
    }

    await $api(`/mainbanners`, {
      method: `post`,
      body: {
        title: title.value,
        linkUri: linkUri.value,
        imageId: imageId.value,
        displayOrder: displayOrder.value,
      } satisfies CreateMainBanner,
    });
    navigateTo(`/mainbanners`);
  }
  catch (error) {
    $dialog.error(error, `메인화면 배너 등록 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto max-w-screen-lg">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            메인화면 배너 등록
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <div class="flex-1 space-y-4">
            <QInput
              v-model="title"
              label="제목"
              autofocus
              clearable
            />
            <QInput
              v-model="linkUri"
              type="text"
              label="경로 (예시 https://www.google.com/)"
              autogrow
              clearable
            />
            <div class="grid items-center justify-center gap-9">
              <div class="text-center">
                <div class="w-100 flex flex-col items-center justify-between gap-4">
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
                      label="배너 이미지 삭제"
                      @click="imageId = undefined"
                    />
                    <QBtn
                      color="blue"
                      label="배너 이미지 선택"
                      @click="open()"
                    />
                  </div>
                  <div class="px-4 text-center text-gray-500">
                    768:300 비율의 10MB 미만으로 선택하세요.
                  </div>
                </div>
              </div>
            </div>
          </div>
        </QForm>
      </QCardSection>
      <QSeparator />

      <QCardActions class="justify-end p-4">
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="취소"
          to="/mainbanners"
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
