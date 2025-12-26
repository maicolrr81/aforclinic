<script setup lang="ts">
import type { QTableColumn } from 'quasar';
import type { BeforeAfterBanner } from '~~/shared/types/BeforeAfterBanner';

const { $api } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const rowsPerPageOptions = computed<{
  label: string;
  value: number | undefined;
}[]>(() => [
  { label: `전체`, value: undefined },
  { label: `10`, value: 10 },
  { label: `20`, value: 20 },
  { label: `50`, value: 50 },
  { label: `100`, value: 100 },
]);

const title = ref(route.query.title as string || ``);
const page = ref(Number(route.query.page || 1));
const size = ref(false);
// const size = ref<number | undefined>(route.query.size ? Number(route.query.size) : undefined);

const pagination = reactive({
  page, // 현재 페이지
  // rowsPerPage: size,
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => {
  const baseParams = {
    title: title.value,
    page: pagination.page,
  };
  return baseParams;
  // return pagination.rowsPerPage === undefined
  //   ? { ...baseParams, unpaged: 'true' }
  //   : { ...baseParams, size: pagination.rowsPerPage };
});

// 컬럼 정보
const columns = computed<QTableColumn[]>(() => ([
  { field: `sequence`, name: `sequence`, label: `순번`, align: `center`, style: `width: 60px` },
  { field: `title`, name: `title`, label: `제목`, align: `left`, headerClasses: `text-center` },
  { field: `description`, name: `description`, label: `설명`, align: `left`, headerClasses: `text-center` },
  { field: `mediaType`, name: `mediaType`, label: `미디어유형`, align: `center`, style: `width: 100px` },
  { field: `mediaContent`, name: `mediaContent`, label: `미디어정보`, align: `center`, style: `width: 100px` },
  { field: `createdBy`, name: `createdBy`, label: `등록자`, align: `center`, style: `width: 100px` },
  { field: `createdAt`, name: `createdAt`, label: `등록일시`, align: `center`, style: `width: 60px` },
  { field: `modifiedBy`, name: `modifiedBy`, label: `수정자`, align: `center`, style: `width: 100px` },
  { field: `modifiedAt`, name: `modifiedAt`, label: `수정일시`, align: `center`, style: `width: 60px` },
]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  title.value = (newQuery.title) as string || ``;
  page.value = Number(newQuery.page) || 1;
  // size.value = newQuery.size ? Number(newQuery.size) : undefined;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data } = await useAsyncData<ApiPagedResponse<BeforeAfterBanner>>(async () => {
  const data = await $api<ApiPagedResponse<BeforeAfterBanner>>(`/beforeafters/banners`, {
    params: params.value,
  });
  pagination.rowsNumber = data.data.page.totalElements ?? 0;
  return data;
}, {
  watch: [() => route.query],
});

// 데이터
const rows = computed(() => (data.value?.data.content ?? []).map((row) => {
  let mediaType;
  switch (row.mediaType) {
    case `IMAGE`:
      mediaType = `이미지`;
      break;
    case `VIDEO`:
      mediaType = `영상`;
      break;
    default:
      mediaType = ``;
  }
  return {
    ...row,
    mediaType,
  };
}));

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
            전후사진 배너 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/beforeafters/banners/create"
          >
            전후사진 배너 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex justify-start gap-2"
          @submit.prevent="updateQueryParams"
        >
          <QSelect
            v-if="size"
            v-model="size"
            :options="rowsPerPageOptions"
            label="검색개수"
            map-options
            emit-value
            class="w-40"
            dense
            clearable
            @update:model-value="updateQueryParams()"
          />
          <QSpace />
          <div class="ml-auto">
            <QInput
              v-model="title"
              type="text"
              label="검색어"
              clearable
              autofocus
              dense
              @keyup.enter="updateQueryParams"
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
                :to="`/beforeafters/banners/${props.row.id}/edit`"
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
