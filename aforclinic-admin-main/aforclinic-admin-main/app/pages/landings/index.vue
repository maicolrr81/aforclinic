<script setup lang="ts">
import type { QTableColumn } from 'quasar';

const { $api } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const text = ref(route.query.text as string || ``);
const page = ref(Number(route.query.page || 1));

const pagination = reactive({
  page, // 현재 페이지
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => ({
  text: text.value,
  page: pagination.page,
}));

// 컬럼 정보
const columns = computed<QTableColumn[]>(() => ([
  { field: `sequence`, name: `sequence`, label: `순번`, align: `center`, style: `width: 60px;` },
  { field: `title`, name: `title`, label: `제목`, align: `left`, headerClasses: `text-center` },
  { field: `link`, name: `link`, label: ``, align: `center`, style: `width: 60px;` },
  { field: `createdBy`, name: `createdBy`, label: `등록자`, align: `center`, style: `width: 120px;` },
  { field: `createdAt`, name: `createdAt`, label: `등록일시`, align: `center`, style: `width: 160px;` },
]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  text.value = (newQuery.text) as string || ``;
  page.value = Number(newQuery.page) || 1;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data, refresh } = await useAsyncData<ApiPagedResponse<LandingPost>>(async () => {
  const data = await $api<ApiPagedResponse<LandingPost>>(`/landings`, {
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
            랜딩페이지 목록
          </QToolbarTitle>
          <QBtn
            color="blue"
            to="/landings/create"
          >
            랜딩페이지 등록
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex flex-col justify-between gap-4 md:flex-row md:gap-2"
          @submit.prevent="updateQueryParams()"
        >
          <fieldset class="grid flex-1 grid-cols-1 gap-4 md:grid-cols-3 md:gap-2 lg:grid-cols-6" />
          <fieldset class="flex flex-col gap-4 md:flex-row md:gap-2">
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
                :to="`/landings/${props.row.postId}/edit`"
              >
                {{ props.value }}
              </NuxtLink>
            </QTd>
          </template>

          <template #body-cell-link="props">
            <QTd :props>
              <QBtn
                flat
                dense
                :href="`${$config.public.homepage}/landings/${props.row.postId}`"
                target="_blank"
              >
                <QIcon name="open_in_new" />
              </QBtn>
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
