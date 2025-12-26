<script setup lang="ts">
const { $api, $dialog } = useNuxtApp();

const title = ref(``);
const content = ref(``);
const attachments = ref<Attachment[]>([]);

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

    await $api(`/posts`, {
      method: `post`,
      body: {
        type: `DEFAULT`,
        title: title.value,
        content: content.value,
        attachments: attachments.value,
      } satisfies CreateDefaultPost,
    });
    navigateTo(`/posts`, { replace: true });
  }
  catch (error) {
    $dialog.error(error, `게시글 등록 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex justify-between pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            게시글 등록
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
      <QCardActions class="justify-end gap-x-4 p-4">
        <QBtn
          class="!px-8 !py-2"
          color="grey"
          label="취소"
          to="/posts"
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
