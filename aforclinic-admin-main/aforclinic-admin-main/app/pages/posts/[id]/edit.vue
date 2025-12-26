<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<DefaultPost>>(async () => {
  const data = await $api<ApiResponse<DefaultPost>>(`/posts/${route.params.id}`);
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

async function remove() {
  const confirmed = await $dialog.confirm(`게시글을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });

  if (!confirmed) { return; }

  try {
    await $api(`/posts/${id.value}`, {
      method: `patch`,
      body: {
        status: `DELETED`,
      },
    });
    navigateTo(`/posts`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `게시글 삭제 중 오류가 발생했습니다.`);
  }
}

async function submit() {
  try {
    if (!title.value) {
      $dialog.alert(`제목이 입력되지 않았습니다.`);
      return;
    }
    if (!content.value) {
      $dialog.alert(`내용이 입력되지 않았습니다.`);
      return;
    }

    await $api(`/posts/${id.value}`, {
      method: `put`,
      body: {
        type: `DEFAULT`,
        title: title.value,
        content: content.value,
        status: status.value,
        attachments: attachments.value,
      } satisfies UpdateDefaultPost,
    });
    navigateTo(`/posts`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `게시글 수정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            게시글 수정
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
          color="red"
          label="삭제"
          @click="remove()"
        />
        <QSpace />
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="목록"
          to="/posts"
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
