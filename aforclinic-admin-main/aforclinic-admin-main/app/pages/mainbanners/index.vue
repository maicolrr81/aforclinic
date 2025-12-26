<script setup lang="ts">
import { VueDraggableNext } from 'vue-draggable-next';

const { $api, $config, $dialog, $overlay } = useNuxtApp();

// 서버 데이터 불러오기
const { data, refresh } = await useAsyncData<ApiPagedResponse<MainBanner>>(() => $api(`/mainbanners`, {
  params: {
    unpaged: true,
  },
}));

// 데이터
const rows = ref<MainBanner[]>([]);

watchEffect(() => {
  rows.value = data.value?.data.content ?? [];
});

// 저장
async function submit() {
  $overlay.show();
  try {
    await $api(`/mainbanners`, {
      method: `put`,
      body: rows.value.map((row, idx) => ({
        id: row.id,
        displayOrder: idx,
      })) satisfies ReorderCategory[],
    });
    refresh();
  }
  catch {
    $dialog.alert(`메인화면 배너의 순서 변경 중 오류가 발생했습니다.`);
  }
  finally {
    $overlay.hide();
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            메인화면 배너 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/mainbanners/create"
          >
            메인화면 배너 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QList
          bordered
          class="overflow-x-auto transition"
        >
          <div class="min-w-[1450px]">
            <QItem class="grid grid-cols-[20px_50px_180px_150px_1fr_120px_160px_120px_160px] items-center gap-x-2 border-b text-xs font-bold">
              <div />
              <div class="text-center">
                순서
              </div>
              <div class="text-center">
                제목
              </div>
              <div class="text-center">
                이미지
              </div>
              <div class="text-center">
                경로
              </div>
              <div class="text-center">
                등록자
              </div>
              <div class="text-center">
                등록일시
              </div>
              <div class="text-center">
                수정자
              </div>
              <div class="text-center">
                수정일시
              </div>
            </QItem>
            <VueDraggableNext
              v-model="rows"
              animation="200"
              handle=".handle"
              @change="submit()"
            >
              <QItem
                v-for="(row, index) of rows"
                :key="row.id"
                class="grid grid-cols-[20px_50px_180px_150px_1fr_120px_160px_120px_160px] items-center gap-x-2  text-sm"
                :class="{
                  'border-t': index > 0,
                }"
              >
                <div class="handle h-5 cursor-grab active:cursor-grabbing">
                  <NuxtIcon
                    name="mdi:drag-vertical"
                    size="20"
                  />
                </div>
                <div class="px-1 text-center">
                  {{ row.sequence }}
                </div>
                <div class="px-1 text-center">
                  <NuxtLink
                    class="hover:underline"
                    :to="`/mainbanners/${row.id}/edit`"
                  >
                    {{ row.title }}
                  </NuxtLink>
                </div>
                <div class="px-1 text-start">
                  <QImg
                    v-if="row.imageId"
                    class="max-h-14 flex-1 rounded-lg border"
                    :src="`${$config.public.apiBase}/files/${row.imageId}`"
                    fit="contain"
                  />
                </div>
                <div class="px-1 text-start">
                  {{ row.linkUri }}
                </div>
                <div class="px-1 text-center">
                  {{ row.createdBy }}
                </div>
                <div class="px-1 text-center">
                  {{ row.createdAt }}
                </div>
                <div class="px-1 text-center">
                  {{ row.modifiedBy }}
                </div>
                <div class="px-1 text-center">
                  {{ row.modifiedAt }}
                </div>
              </QItem>
            </VueDraggableNext>
          </div>
        </QList>
      </QCardSection>
    </QCard>
  </QPage>
</template>

<style lang="css" scoped>
.transition-move {
  transition: transform 0.3s ease;
}
</style>
