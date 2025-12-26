<script setup lang="ts">
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

async function remove() {
  const confirmed = await $dialog.confirm(`랜딩페이지를 삭제하시겠습니까?`, {
    title: `⚠️ 주의`,
  });
  if (confirmed) {
    try {
      await $api(`/landings/${route.params.postId}`, {
        method: `patch`,
        body: {
          status: `DELETED`,
        },
      });
      navigateTo(`/landings`, { replace: true });
    }
    catch {
      $dialog.alert(`랜딩페이지 삭제 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="flex flex-col gap-4 border-b">
        <div class="flex items-center justify-between">
          <span class="text-lg font-bold">
            {{ data.title }}
          </span>
          <NuxtLink
            class="hover:underline"
            :to="`${$config.public.homepage}/landings/${route.params.postId}`"
            target="_blank"
          >
            {{ `${$config.public.homepage}/landings/${route.params.postId}` }}
          </NuxtLink>
        </div>

        <div class="flex items-center gap-4">
          <div>
            {{ data.createdBy }}
          </div>
          <div>
            {{ data.createdAt }}
          </div>
          <QSpace />
          <div>
            시작일: {{ data.startDate }}
          </div>
          <div>
            종료일: {{ data.endDate }}
          </div>
        </div>
      </QCardSection>

      <QCardSection>
        <div
          class="prose min-h-80 max-w-none"
          v-html="data.content"
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
          to="/landings"
        />
        <QBtn
          type="submit"
          class="!px-8 !py-2"
          color="blue"
          label="수정"
          :to="`/landings/${route.params.postId}/edit`"
        />
      </QCardActions>
    </QCard>
  </QPage>
</template>
