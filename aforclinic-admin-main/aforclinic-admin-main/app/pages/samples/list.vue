<script setup lang="ts">
import type { QTableColumn } from 'quasar';

const { $api } = useNuxtApp();
const route = useRoute();
const router = useRouter();

// 1. 검색 조건을 설정한다.
const nickname = ref(route.query.nickname as string || ``);

// 2. 페이지네이션을 정의한다.
const pagination = ref({
  page: Number(route.query.page || 1),
  rowsNumber: 0,
});

// 3. 파라미터 정보를 정의한다.
const params = computed(() => ({
  nickname: nickname.value,
  page: pagination.value.page,
}));

// 4. 테이블 헤더를 정의한다.
const columns = computed<QTableColumn[]>(() => ([
  { field: `id`, name: 'id', label: `아이디`, align: 'center', hint: 'aaa' },
  { field: `nickname`, name: 'nickname', label: `이름`, align: `center` },
  { field: `email`, name: 'email', label: `이메일`, align: `center` },
  { field: `role`, name: 'role', label: `역할`, align: `center` },
]));

// 5. 서버로 데이터를 요청한다.
const { data, refresh } = useAsyncData<ApiPagedResponse<User>>(() => $api('/users', { params: params.value }));

// 6. 테이블 데이터 정의
const rows = computed(() => data.value?.data.content ?? []);

// 7. 페이지네이션 총 페이지 개수 정의
const totalPages = computed(() => data.value?.data.page.totalPages ?? 1);

// 8. 페이지네이션 정보가 변경되면 갱신
watch(() => pagination.value.page, onRetreive);

const a = ref(``);
const selected = ref([]);

function onRetreive() {
  refresh();
  router.push({
    query: {
      ...params.value,
    },
  });
}
</script>

<template>
  <QPage class="container mx-auto ">
    <QCard class="rounded">
      <QCardSection class="pb-0">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            사용자 목록
          </QToolbarTitle>
          <QBtn color="blue">
            사용자 생성
          </QBtn>
          <QBtn color="red">
            사용자 삭제
          </QBtn>
        </QToolbar>
      </QCardSection>

      <QCardSection class="border-b">
        <QForm
          class="flex justify-between gap-4"
          @submit.prevent="onRetreive()"
        >
          <QSelect
            v-for="i in 4"
            :key="i"
            v-model="a"
            :options="['회원', '매니저', '관리자']"
            label="역할"
            outlined
            dense
            class="w-36"
          />
          <QSpace />
          <QInput
            v-model="nickname"
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
                @click="onRetreive()"
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
          selection="multiple"
          :selected="selected"
        >
          <!-- <template #header-cell="props">
              <QTd
                :props="props"
              >
                {{ props.col.label }}
                <QTooltip
                  self="bottom middle"
                >
                  {{ props.col.label }}
                </QTooltip>
              </QTd>
            </template>
            <template #body-cell="props">
              <QTd :props="props">
                {{ props.value }}
                <QTooltip
                  self="bottom middle"
                >
                  {{ props.value }}
                </QTooltip>
              </QTd>
            </template> -->
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
