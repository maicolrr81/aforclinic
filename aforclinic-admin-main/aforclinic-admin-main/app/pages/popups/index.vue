<script setup lang="ts">
import type { Popup, ReorderPopup } from '~~/shared/types/Popup';
import { VueDraggableNext } from 'vue-draggable-next';

const { $api, $dialog, $overlay } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const title = ref(route.query.title as string || ``);
const progressType = ref(route.query.progressType as string || null);
const page = ref(Number(route.query.page || 1));

const popupTypeOptions = computed<{
  label: string;
  value: string;
}[]>(() => [
  { label: `목록 팝업`, value: 'CAROUSEL' },
  { label: `개별 팝업`, value: 'MODAL' },
]);
const type = ref(route.query.type as string || 'CAROUSEL');

const pagination = reactive({
  page, // 현재 페이지
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => {
  if (progressType.value === null) {
    return ({
      type: type.value,
      unpaged: true,
    });
  }
  else {
    return ({
      type: type.value,
      title: title.value,
      progressType: progressType.value,
      page: pagination.page,
    });
  }
},
);

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  type.value = newQuery.type as string || 'CAROUSEL',
  title.value = (newQuery.title) as string || ``;
  progressType.value = (newQuery.progressType) as string || null;
  page.value = Number(newQuery.page) || 1;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data, refresh } = await useAsyncData<ApiPagedResponse<Popup>>(async () => {
  const data = await $api<ApiPagedResponse<Popup>>(`/popups`, {
    params: params.value,
  });
  pagination.rowsNumber = data.data.page.totalElements ?? 0;
  return data;
}, {
  watch: [() => route.query],
});

// 데이터
const rows = ref<Popup[]>([]);

watchEffect(() => {
  rows.value = data.value?.data.content ?? [];
});
const totalPages = computed(() => data.value?.data.page.totalPages ?? 1);
const totalElements = computed(() => data.value?.data.page.totalElements ?? 0);

// 페이지 변경
function onChangedPage(newPage: number) {
  page.value = newPage;
  updateQueryParams();
}

// 조회
function updateQueryParams() {
  router.push({
    path: route.path,
    query: { ...params.value },
  });
}

// 저장
async function submit() {
  $overlay.show();
  try {
    await $api(`/popups`, {
      method: `put`,
      body: rows.value.map((row, idx) => ({
        id: row.id,
        displayOrder: idx,
      })) satisfies ReorderPopup[],
    });
    refresh();
  }
  catch {
    $dialog.alert(`팝업의 순서 변경 중 오류가 발생했습니다.`);
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
            팝업 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/popups/create"
          >
            팝업 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex flex-col justify-between gap-4 md:flex-row md:gap-2"
          @submit.prevent="updateQueryParams"
        >
          <div class="flex gap-4">
            <QSelect
              v-model="type"
              :options="popupTypeOptions"
              label="유형"
              map-options
              emit-value
              dense
              @update:model-value="updateQueryParams()"
            />
            <QBtnToggle
              v-model="progressType"
              toggle-color="blue"
              spread
              :options="[
                { label: '전체', value: null },
                { label: '종료', value: 'ENDED' },
                { label: '진행중', value: 'ONGOING' },
                { label: '예정', value: 'BEFORE' },
              ]"
              :style="{ whiteSpace: 'nowrap' }"
              @update:model-value="updateQueryParams()"
            />
          </div>
          <div>
            <QInput
              v-if="progressType !== null"
              v-model="title"
              type="text"
              label="검색어"
              clearable
              autofocus
              dense
            >
              <template #append>
                <QIcon
                  name="search"
                  class="cursor-pointer"
                  @click="updateQueryParams"
                />
              </template>
            </QInput>
          </div>
        </QForm>
      </QCardSection>

      <QCardSection>
        <QList
          bordered
          class="overflow-x-auto transition"
        >
          <div class="min-w-[1450px]">
            <QItem
              class="grid items-center gap-x-2 border-b text-xs font-bold"
              :class="{
                'grid-cols-[20px_50px_180px_150px_1fr_120px_120px_120px_160px_120px_160px]': progressType === null,
                'grid-cols-[50px_180px_150px_1fr_120px_120px_120px_160px_120px_160px]': progressType !== null,
              }"
            >
              <div v-if="progressType === null" />
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
                링크
              </div>
              <div class="text-center">
                시작일
              </div>
              <div class="text-center">
                종료일
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
                class="grid items-center gap-x-2  text-sm"
                :class="{
                  'border-t': index > 0,
                  'grid-cols-[20px_50px_180px_150px_1fr_120px_120px_120px_160px_120px_160px]': progressType === null,
                  'grid-cols-[50px_180px_150px_1fr_120px_120px_120px_160px_120px_160px]': progressType !== null,
                }"
              >
                <div
                  v-if="progressType === null"
                  class="handle h-5 cursor-grab active:cursor-grabbing"
                >
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
                    :to="`/popups/${row.id}/edit`"
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
                  {{ row.startDate }}
                </div>
                <div class="px-1 text-center">
                  {{ row.endDate }}
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

        <div
          v-if="progressType !== null"
          class="mt-4 flex items-center justify-between"
        >
          <span>
            전체 개수: {{ totalElements }}
          </span>
          <QPagination
            v-model="pagination.page"
            :max="totalPages"
            color="grey-8"
            @update:model-value="onChangedPage"
          />
          <QPagination
            v-model="pagination.page"
            :max="totalPages"
            color="grey-8"
            input
            input-class="text-xs m-0"
            :boundary-links="false"
            :direction-links="false"
            @update:model-value="onChangedPage"
          />
        </div>
      </QCardSection>
    </QCard>
  </QPage>
</template>

<style module>
.table-header.table-header {
  @apply font-bold border-b;
}
.transition-move {
  transition: transform 0.3s ease;
}
</style>
