<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();

const dayjs = useDayjs();

const title = ref<string>(``);
const description = ref<string>();
const content = ref<string>(``);
const imageId = ref<string>();
const startDate = ref(dayjs().format(`YYYY-MM-DD`));
const endDate = ref(dayjs().format(`YYYY-MM-DD`));

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }
    if (!startDate.value) {
      $dialog.alert(`시작일이 입력되지 않았습니다.`);
      return;
    }
    if (!endDate.value) {
      $dialog.alert(`종료일이 입력되지 않았습니다.`);
      return;
    }
    if (!content.value) {
      $dialog.alert(`내용이 입력되지 않았습니다.`);
      return;
    }

    await $api(`/landings`, {
      method: `post`,
      body: {
        title: title.value,
        description: description.value,
        content: content.value,
        imageId: imageId.value,
        startDate: startDate.value,
        endDate: endDate.value,
      } satisfies CreateLandingPost,
    });
    navigateTo(`/landings`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `랜딩페이지 등록 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            랜딩페이지 등록
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm class="flex flex-col gap-4">
          <QInput
            v-model="title"
            label="제목"
            autofocus
            clearable
          />
          <QInput
            v-model="description"
            label="설명"
            clearable
          />
          <div class="flex gap-4">
            <UiDatePicker
              v-model="startDate"
              class="flex-1"
              clearable
              label="시작일"
            />
            <UiDatePicker
              v-model="endDate"
              class="flex-1"
              clearable
              label="종료일"
            />
          </div>
        </QForm>
      </QCardSection>

      <QCardSection>
        <ClientOnly>
          <TiptapEditor v-model="content" />
        </ClientOnly>
      </QCardSection>

      <QCardActions class="justify-end gap-x-4 p-4">
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="취소"
          to="/landings"
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
