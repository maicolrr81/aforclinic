<script setup lang="ts">
import type { LocationQueryRaw } from 'vue-router';

import type { Appointment } from '~~/shared/types/Appointment';
import { exportFile, type QTableColumn } from 'quasar';

const { $api, $dialog } = useNuxtApp();

const route = useRoute();
const router = useRouter();

// 타입 정보
const typeOptions = computed(() => [
  { label: `이벤트`, value: `EVENT` },
  { label: `바로 예약`, value: `QUICK` },
  { label: `시술 예약`, value: `PROCEDURE` },
]);

// 상태 정보
const statusOptions = computed(() => [
  { label: `접수`, value: `PENDING` },
  { label: `예약 성공`, value: `CONFIRMED` },
  { label: `재연락`, value: `FOLLOW_UP` },
  { label: `부재1`, value: `NO_ANSWER_1` },
  { label: `부재2`, value: `NO_ANSWER_2` },
  { label: `부재3`, value: `NO_ANSWER_3` },
  { label: `부재4`, value: `NO_ANSWER_4` },
  { label: `부재> 카톡1`, value: `NO_ANSWER_KAKAO_1` },
  { label: `부재> 카톡2`, value: `NO_ANSWER_KAKAO_2` },
  { label: `거절`, value: `REJECTED` },
  { label: `중복`, value: `DUPLICATED` },
  { label: `결번`, value: `INVALID_NUMBER` },
  { label: `마감`, value: `CLOSED` },
  { label: `취소`, value: `CANCEL` },
]);

// 검색 개수
const rowsPerPageOptions = computed<{
  label: string;
  value: number | undefined;
}[]>(() => [
  { label: `전체`, value: undefined },
  { label: `3`, value: 3 },
  { label: `10`, value: 10 },
  { label: `20`, value: 20 },
  { label: `50`, value: 50 },
  { label: `100`, value: 100 },
]);

const type = ref(route.query.type as string || ``);
const status = ref(route.query.status as string || ``);

const nickname = ref(route.query.nickname as string || ``);
const contact = ref(route.query.contact as string || ``);
const content = ref(route.query.content as string || ``);

const page = ref(Number(route.query.page || 1));
const size = ref<number | undefined>(route.query.size ? Number(route.query.size) : undefined);

const isLoading = ref(false);

const pagination = reactive({
  page, // 현재 페이지
  rowsPerPage: size,
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => {
  const baseParams = {
    type: type.value,
    status: status.value,
    nickname: nickname.value,
    contact: contact.value,
    content: content.value,
    page: pagination.page,
  };
  return pagination.rowsPerPage === undefined
    ? { ...baseParams, unpaged: true }
    : { ...baseParams, size: pagination.rowsPerPage };
});

// 컬럼 정보
const columns = computed<QTableColumn[]>(() => ([
  { field: `sequence`, name: `sequence`, label: `순번`, align: `center`, style: `width: 60px` },
  { field: `type`, name: `type`, label: `예약 형태`, align: `center`, style: `width: 120px`, format: (val) => { const option = typeOptions.value.find(opt => opt.value === val); return (option ? option.label : val); } },
  { field: `nickname`, name: `nickname`, label: `예약자명`, align: `center`, style: `width: 120px` },
  { field: `contact`, name: `contact`, label: `연락처`, align: `center`, style: `width: 120px` },
  { field: `content`, name: `content`, label: `내용`, align: `left`, headerClasses: `text-center`, style: `text-wrap: wrap;` },
  { field: `date`, name: `date`, label: `예약 일자`, align: `center`, style: `width: 140px` },
  { field: `status`, name: `status`, label: `상태`, align: `center`, style: `width: 200px`, format: (val) => { const option = statusOptions.value.find(opt => opt.value === val); return (option ? option.label : val); } },
  { field: `memo`, name: `memo`, label: `메모`, align: `center`, style: `width: 240px` },
  { field: `createdAt`, name: `createdAt`, label: `신청일자`, align: `center`, style: `width: 160px` },
]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  type.value = (newQuery.type) as string || ``;
  status.value = (newQuery.status) as string || ``;
  nickname.value = (newQuery.nickname) as string || ``;
  contact.value = (newQuery.contact) as string || ``;
  content.value = (newQuery.content) as string || ``;
  page.value = Number(newQuery.page) || 1;
  size.value = newQuery.size ? Number(newQuery.size) : undefined;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data, refresh } = await useAsyncData<ApiPagedResponse<Appointment>>(async () => {
  const data = await $api<ApiPagedResponse<Appointment>>(`/appointments`, {
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
    query: { ...params.value } as LocationQueryRaw,
  });
}

const modifiedStatusData = shallowRef<UpdateAppointmentStatusWithId>();
const modifiedMemoData = shallowRef<UpdateAppointmentMemoWithId>();

function markAsModifiedStatus(id: string, newValue: AppointmentStatus) {
  modifiedStatusData.value = { id, status: newValue };
}

function markAsModifiedMemo(id: string, newValue: string) {
  modifiedMemoData.value = { id, memo: newValue };
}

async function updateStatus() {
  if (modifiedStatusData.value) {
    isLoading.value = true;
    try {
      await $api(`/appointments/${modifiedStatusData.value.id}`, {
        method: `patch`,
        body: {
          status: modifiedStatusData.value.status,
        } satisfies UpdateAppointmentStatus,
      });
    }
    finally {
      modifiedStatusData.value = undefined;
      isLoading.value = false;
    }
  }
}

async function updateMemo() {
  if (modifiedMemoData.value) {
    isLoading.value = true;
    try {
      await $api(`/appointments/${modifiedMemoData.value.id}`, {
        method: `patch`,
        body: {
          memo: modifiedMemoData.value.memo,
        } satisfies UpdateAppointmentMemo,
      });
    }
    finally {
      modifiedMemoData.value = undefined;
      isLoading.value = false;
    }
  }
}

function wrapCsvValue(val: string, formatFn?: ((val: any, row: any) => string), row?: any) {
  let formatted = formatFn !== void 0
    ? formatFn(val, row)
    : val;

  formatted = formatted === void 0 || formatted === null
    ? ''
    : String(formatted);

  formatted = formatted.split('"').join('""');

  return `"${formatted}"`;
}

function exportTable() {
  // naive encoding to csv format
  const content = [columns.value.map(col => wrapCsvValue(col.label))].concat(
    rows.value.map(row => columns.value.map(col => wrapCsvValue(
      typeof col.field === 'function'
        ? col.field(row)
        : row[col.field as keyof typeof row ?? col.name],
      col.format,
      row,
    )).join(',')),
  ).join('\r\n');

  const status = exportFile(
    'eventList.csv',
    `\uFEFF${content}`,
    'text/csv',
  );

  if (status !== true) {
    $dialog.alert(`내보내기에 실패했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            예약 목록
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex flex-col justify-between gap-4 md:flex-row md:gap-2"
          @submit.prevent="updateQueryParams"
        >
          <fieldset class="grid flex-1 grid-cols-1 gap-4 md:grid-cols-3 md:gap-2 lg:grid-cols-6">
            <QSelect
              v-model="size"
              :options="rowsPerPageOptions"
              label="검색개수"
              map-options
              emit-value
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
            <QSelect
              v-model="type"
              :options="typeOptions"
              label="타입"
              map-options
              emit-value
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
            <QSelect
              v-model="status"
              :options="statusOptions"
              label="상태"
              map-options
              emit-value
              dense
              clearable
              @update:model-value="updateQueryParams()"
            />
            <QInput
              v-model="nickname"
              type="text"
              label="예약자명"
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
            <QInput
              v-model="contact"
              type="text"
              label="연락처"
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
            <QInput
              v-model="content"
              type="text"
              label="예약내용"
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
          <fieldset class="flex flex-col gap-4 md:flex-row md:gap-2">
            <QBtn
              color="primary"
              icon-right="archive"
              label="엑셀 내보내기"
              @click="exportTable"
            />
          </fieldset>
        </QForm>
      </QCardSection>

      <QCardSection class="pt-0">
        <QTable
          :columns
          :rows
          :pagination
          :loading="isLoading"
        >
          <template #body-cell-status="props">
            <QTd :props>
              <div class="grid grid-cols-[1fr_auto] gap-1">
                <QSelect
                  v-model="props.row.status"
                  :options="statusOptions"
                  label="상태 선택"
                  map-options
                  emit-value
                  dense
                  @update:model-value="(newValue) => markAsModifiedStatus(props.row.id, newValue)"
                />
                <QBtn
                  label="적용"
                  class="px-2 "
                  :class="{
                    'bg-blue-500 text-white': modifiedStatusData?.id === props.row.id,
                  }"
                  dense
                  size="sm"
                  :disable="modifiedStatusData?.id !== props.row.id"
                  @click="updateStatus()"
                />
              </div>
            </QTd>
          </template>
          <template #body-cell-memo="props">
            <QTd :props>
              <div class="grid grid-cols-[1fr_auto] gap-1">
                <QInput
                  v-model="props.row.memo"
                  type="textarea"
                  dense
                  rows="1"
                  @update:model-value="(newValue) => markAsModifiedMemo(props.row.id, newValue as string)"
                />
                <QBtn
                  label="적용"
                  class="px-2 "
                  :class="{
                    'bg-blue-500 text-white': modifiedMemoData?.id === props.row.id,
                  }"
                  dense
                  size="sm"
                  :disable="modifiedMemoData?.id !== props.row.id"
                  @click="updateMemo()"
                />
              </div>
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
