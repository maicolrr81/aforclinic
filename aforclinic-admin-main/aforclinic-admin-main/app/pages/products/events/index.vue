<script setup lang="ts">
import type { QTableColumn } from 'quasar';
import type { Event } from '~~/shared/types/Event';

const { $api } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const text = ref(route.query.text as string || ``);
const progressType = ref(route.query.progressType as string || null);
const page = ref(Number(route.query.page || 1));

const pagination = reactive({
  page, // 현재 페이지
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => ({
  text: text.value,
  progressType: progressType.value,
  page: pagination.page,
}));

// 컬럼 정보
const columns = computed<QTableColumn[]>(() => ([
  { field: `sequence`, name: `sequence`, label: `순번`, align: `center`, style: `width: 60px` },
  { field: `title`, name: `title`, label: `제목`, align: `left`, headerClasses: `text-center` },
  { field: `startDate`, name: `startDate`, label: `시작일`, align: `center`, style: `width: 100px` },
  { field: `endDate`, name: `endDate`, label: `종료일`, align: `center`, style: `width: 100px` },
  { field: `createdBy`, name: `createdBy`, label: `등록자`, align: `center`, style: `width: 100px` },
  { field: `createdAt`, name: `createdAt`, label: `등록일시`, align: `center`, style: `width: 60px` },
  { field: `modifiedBy`, name: `modifiedBy`, label: `수정자`, align: `center`, style: `width: 100px` },
  { field: `modifiedAt`, name: `modifiedAt`, label: `수정일시`, align: `center`, style: `width: 60px` },
]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  text.value = (newQuery.text) as string || ``;
  progressType.value = (newQuery.progressType) as string || null;
  page.value = Number(newQuery.page) || 1;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data, refresh } = await useAsyncData<ApiPagedResponse<Event>>(async () => {
  const data = await $api<ApiPagedResponse<Event>>(`/events`, {
    params: params.value,
  });
  pagination.rowsNumber = data.data.page.totalElements ?? 0;
  return data;
}, {
  watch: [() => route.query],
});

// 데이터
const rows = computed(() => (data.value?.data.content ?? []));
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
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            이벤트 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/products/events/create"
          >
            이벤트 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex flex-col justify-between gap-4 md:flex-row md:gap-2"
          @submit.prevent="updateQueryParams()"
        >
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
          <fieldset>
            <QInput
              v-model="text"
              type="text"
              label="검색어"
              clearable
              autofocus
              dense
              @keydown.enter="updateQueryParams()"
            >
              <template #append>
                <QIcon
                  name="search"
                  class="cursor-pointer"
                  @click="refresh()"
                />
              </template>
            </QInput>
          </fieldset>
        </QForm>
      </QCardSection>

      <QCardSection class="pt-0">
        <QTable
          :columns
          :rows
          :pagination
        >
          <template #body-cell-title="props">
            <QTd :props>
              <NuxtLink
                class="hover:underline"
                :to="`/products/events/${props.row.id}/edit`"
              >
                <NuxtIcon
                  v-if="props.row.pinned"
                  name="mdi:pin"
                  class="text-red-500"
                  size="16"
                />
                {{ props.value }}
              </NuxtLink>
            </QTd>
          </template>
        </QTable>
        <div class="mt-4 flex items-center justify-between">
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
