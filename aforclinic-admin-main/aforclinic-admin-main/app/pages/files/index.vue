<script setup lang="ts">
import type { QTableColumn } from 'quasar';

const { $api } = useNuxtApp();
const runtimeConfig = useRuntimeConfig();

const route = useRoute();
const router = useRouter();

// 1. 검색 조건을 설정한다.
const filename = ref(route.query.filename as string);

// 2. 페이지네이션을 정의한다.
const pagination = ref({
  page: Number(route.query.page ?? 1),
  rowsNumber: 0,
});

// 3. 파라미터 정보를 정의한다.
const params = computed<FileMetadataParams>(() => ({
  filename: filename.value,
  page: pagination.value.page,
}));

// 4. 테이블 헤더를 정의한다.
const columns = computed<QTableColumn[]>(() => ([
  { name: `filename`, field: `filename`, label: `파일명`, align: `left` },
  // { name: `path`, field: `path`, label: `파일 경로`, align: `left` },
  { name: `size`, field: `size`, label: `파일 크기`, align: `center`, style: `width: 100px` },
  { name: `createdAt`, field: `createdAt`, label: `생성 일자`, align: `center`, style: `width: 120px` },
  { name: `modifiedAt`, field: `modifiedAt`, label: `수정 일자`, align: `center`, style: `width: 120px` },
]));

// 5. 서버로 데이터를 요청한다.
const { data, refresh } = useAsyncData<ApiPagedResponse<FileMetadata>>(() => $api(`/files`, { params: params.value }));

// 6. 테이블 데이터 정의
const rows = computed(() => (data.value?.data.content ?? []).map(data => ({
  ...data,
  src: `${runtimeConfig.public.apiBase}/files/${data.id}`,
})));

// 7. 페이지네이션 총 페이지 개수 정의
const totalPages = computed(() => data.value?.data.page.totalPages ?? 1);

// 8. 페이지네이션 정보가 변경되면 갱신
watch(() => pagination.value.page, () => {
  router.push({
    query: {
      ...route.query,
      ...params.value,
    },
  });
  refresh();
});
</script>

<template>
  <QPage class="container mx-auto">
    <QCard class="rounded">
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            파일 관리
          </QToolbarTitle>
          <QBtn color="blue">
            파일 업로드
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection class="border-b">
        <QForm
          class="flex justify-between gap-4"
          @submit.prevent="refresh()"
        >
          <QSpace />
          <QInput
            v-model="filename"
            type="text"
            autofocus
            clearable
            dense
            label="검색어"
          >
            <template #append>
              <QIcon
                class="cursor-pointer"
                name="search"
                tabindex="-1"
                @click="refresh()"
              />
            </template>
          </QInput>
        </QForm>
      </QCardSection>

      <QCardSection>
        <QTable
          :columns="columns"
          :pagination="pagination"
          :rows="rows"
        >
          <template #body-cell-filename="props">
            <QTd
              :props="props"
              class="flex items-center justify-between"
            >
              <span>{{ props.value }}</span>
              <QBtnGroup
                class="space-x-1"
                flat
              >
                <QBtn
                  color="primary"
                  dense
                  flat
                  :href="props.row.src"
                  icon="open_in_new"
                  target="_blank"
                  rel="noopener noreferrer"
                  round
                />
                <QBtn
                  color="primary"
                  dense
                  :download="props.value"
                  flat
                  :href="`${props.row.src}?disposition=download`"
                  icon="download"
                  round
                />
              </QBtnGroup>
            </QTd>
          </template>
        </QTable>
        <div class="row q-mt-md justify-center">
          <QPagination
            v-model="pagination.page"
            :max="totalPages"
          />
        </div>
      </QCardSection>
    </QCard>
  </QPage>
</template>
