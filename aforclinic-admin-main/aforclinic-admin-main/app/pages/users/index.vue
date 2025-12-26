<script setup lang="ts">
import type { QTableColumn } from 'quasar';
import { getUserRoleDisplay } from '~~/shared/types/User';

const { $api } = useNuxtApp();

const route = useRoute();
const router = useRouter();

const role = ref(route.query.role as string || ``);
const status = ref(route.query.status as string || ``);
const provider = ref(route.query.provider as string || ``);
const startDate = ref(route.query.startDate as string || ``);
const endDate = ref(route.query.endDate as string || ``);
const text = ref(route.query.text as string || ``);
const page = ref(Number(route.query.page || 1));

// 페이지네이션
const pagination = reactive({
  page, // 현재 페이지
  rowsNumber: 0, // 전체 개수
});

// 파라미터
const params = computed(() => ({
  role: role.value,
  status: status.value,
  provider: provider.value,
  startDate: startDate.value,
  endDate: endDate.value,
  text: text.value,
  page: pagination.page,
}));

// 컬럼 정보
const columns = computed<QTableColumn[]>(() => ([
  { field: `sequence`, name: `sequence`, label: `순번`, align: `center`, style: `width: 60px` },
  { field: `username`, name: 'username', label: `로그인ID`, align: 'center' },
  { field: `nickname`, name: `nickname`, label: `사용자명`, align: `center` },
  { field: `phoneNumber`, name: `phoneNumber`, label: `휴대폰 번호`, align: `center` },
  { field: `birthDate`, name: 'birthDate', label: `생년월일`, align: `center` },
  { field: `role`, name: `role`, label: `역할`, align: `center` },
  { field: `createdAt`, name: `createdAt`, label: `가입일시`, align: `center`, style: `width: 60px` },
  { field: `lastLoginAt`, name: 'lastLoginAt', label: `마지막 로그인 일시`, align: `center` },
]));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  role.value = newQuery.role as string || ``;
  status.value = newQuery.status as string || ``;
  provider.value = newQuery.provider as string || ``;
  startDate.value = newQuery.startDate as string || ``;
  endDate.value = newQuery.endDate as string || ``;
  text.value = newQuery.text as string || ``;
  page.value = Number(newQuery.page) || 1;
}, {
  immediate: true,
});

// 서버 데이터 불러오기
const { data } = await useAsyncData<ApiPagedResponse<User>>(async () => {
  const data = await $api<ApiPagedResponse<User>>(`/users`, {
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
            사용자 목록
          </QToolbarTitle>
          <!-- <QBtn
            color="blue"
          >
            관리자 등록
          </QBtn> -->
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <QForm
          class="flex justify-start gap-2"
          @submit.prevent="updateQueryParams"
        >
          <QSpace />
          <div class="ml-auto">
            <QInput
              v-model="text"
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

      <QCardSection class="pt-0">
        <QTable
          :columns
          :rows
          :pagination
        >
          <template #body-cell-role="props">
            <QTd :props>
              {{ getUserRoleDisplay(props.value) }}
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
