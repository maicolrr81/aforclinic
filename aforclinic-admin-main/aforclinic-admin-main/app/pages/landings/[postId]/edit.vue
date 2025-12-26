<script setup lang="ts">
import type { UpdateLandingPost } from '#shared/types/Post';

const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<LandingPost>>(async () => {
  const data = await $api<ApiResponse<LandingPost>>(`/landings/${route.params.postId}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const title = ref(data.value.title!);
const description = ref(data.value.description);
const content = ref(data.value.content!);
const imageId = ref(data.value.imageId);
const startDate = ref(data.value.startDate!);
const endDate = ref(data.value.endDate!);
const status = ref(data.value.status!);

async function remove() {
  const confirmed = await $dialog.confirm(`랜딩페이지를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  try {
    await $api(`/landings/${route.params.postId}`, {
      method: `patch`,
      body: {
        status: `DELETED`,
      },
    });
    navigateTo(`/landings`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `랜딩페이지 삭제 중 오류가 발생했습니다.`);
  }
}

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

    await $api(`/landings/${route.params.postId}`, {
      method: `put`,
      body: {
        title: title.value,
        description: description.value,
        content: content.value,
        imageId: imageId.value,
        startDate: startDate.value,
        endDate: endDate.value,
        status: status.value,
      } satisfies UpdateLandingPost,
    });
    navigateTo(`/landings`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `랜딩페이지 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            랜딩페이지 수정
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
          to="/landings"
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
