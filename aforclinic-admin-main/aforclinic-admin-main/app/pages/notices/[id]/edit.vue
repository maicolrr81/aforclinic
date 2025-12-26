<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<NoticePost>>(async () => {
  const data = await $api<ApiResponse<NoticePost>>(`/notices/${route.params.id}`);
  return data.data;
}, {
  default: () => ({}),
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

const id = ref(data.value.id!);
const title = ref(data.value.title!);
const content = ref(data.value.content!);
const status = ref(data.value.status!);
const attachments = ref(data.value.attachments ?? []);

async function submit() {
  try {
    await $api(`/notices/${id.value}`, {
      method: `put`,
      body: {
        type: `NOTICE`,
        title: title.value,
        content: content.value,
        status: status.value,
        attachments: attachments.value,
      } satisfies UpdateNoticePost,
    });
    navigateTo(`/notices/${id.value}`, { replace: true });
  }
  catch {
    $dialog.alert(`공지사항 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            공지사항 수정
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
          <ClientOnly>
            <TiptapEditor v-model="content" />
          </ClientOnly>
        </QForm>
      </QCardSection>
      <QCardSection>
        <UiAttachmentList
          v-model="attachments"
          bordered
        />
      </QCardSection>
      <QCardActions class="justify-end p-4">
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="목록"
          to="/notices"
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
