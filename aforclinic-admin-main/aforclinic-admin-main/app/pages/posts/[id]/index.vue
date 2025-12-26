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

async function remove() {
  const confirmed = await $dialog.confirm(`게시글을 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/posts/${route.params.id}`, {
        method: `patch`,
        body: {
          status: `DELETED`,
        },
      });
      navigateTo(`/posts`, { replace: true });
    }
    catch {
      $dialog.alert(`게시글 삭제 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex flex-col gap-4 border-b">
        <span class="text-lg font-bold">
          {{ data.title }}
        </span>
        <div class="flex items-center gap-4">
          <div>
            {{ data.createdBy }}
          </div>
          <div>
            {{ data.createdAt }}
          </div>
        </div>
      </QCardSection>
      <QCardSection>
        <div
          class="prose min-h-80 max-w-none"
          v-html="data.content"
        />
      </QCardSection>
      <QCardSection
        v-if="data.attachments"
        class="border-t"
      >
        <UiAttachmentList
          v-model="data.attachments"
          readonly
        />
      </QCardSection>
      <QCardActions class="justify-between border-t p-4">
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
          label="수정"
          :to="`/posts/${route.params.id}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
