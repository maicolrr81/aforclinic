<script setup lang="ts">
import type { QTableColumn } from 'quasar';

const { $api, $q } = useNuxtApp();

const route = useRoute();

const userId = route.params.id;

console.log(`userId`, userId);

// const nickname = ref('');
// const birthDate = ref('');
// const email = ref('');
// const phoneNumber = ref('');
// const createdAt = ref('');
// const role = ref('');

// ref는 script에서 .value로 값을 가져와야 함 (template에서는 변수 그대로 사용)
// reactive는 script랑 template 모두 변수 그대로 사용
// const pagination = ref({
//   page: 1, // 현재 페이지
//   rowsPerPage: 2, // 한 페이지 당 행 개수
//   rowsNumber: 0, // 전체 행 개수
// });

// const tab = ref('tabMember');

// const nickname = ref('');
// const role = ref('');

const { data, refresh } = useAsyncData(async () => {
  //
  try {
    const res = await $api<ApiResponse<User>>(`/users/${userId}`);
    if (res) {
      return res.data;
    }
  }
  catch (e: any) {
    console.log(`error`, e);
  }
  $q.dialog({
    title: `알림`,
    message: `사용자를 찾을 수 없습니다.`,
    ok: `확인`,
  }).onOk(() => {
    navigateTo(`/users`);
  });
});

const userData = computed(() => data.value ?? {});

if (import.meta.client) {
  console.log('userData.value', userData.value);
}

// const columns = computed<QTableColumn[]>(() => ([
//   { field: `-`, name: '-', label: `순서`, align: 'center' },
//   // { field: `username`, name: 'username', label: `아이디`, align: 'center', hint: 'aaa' },
//   // { field: `id`, name: 'id', label: `아이디`, align: 'center', hint: 'aaa' },
//   { field: `nickname`, name: 'nickname', label: `이름`, align: `center` },
//   { field: `birthDate`, name: 'birthDate', label: `생년월일`, align: `center` },
//   { field: `email`, name: 'email', label: `이메일`, align: `center` },
//   { field: `phoneNumber`, name: 'phoneNumber', label: `전화번호`, align: `center` },
//   { field: `createdAt`, name: 'createdAt', label: `가입일`, align: `center` },
//   { field: `role`, name: 'role', label: `역할`, align: `center` },
// ]));

// function onRefresh() {
//   refresh();
// }
</script>

<template>
  <QPage
    class="container mx-auto "
    padding
  >
    <QToolbar class="">
      <QToolbarTitle>
        사용자 조회
      </QToolbarTitle>
    </QToolbar>
    <QCard
      class="mb-4"
      flat
      bordered
    >
      <QCardSection class="grid gap-y-2">
        <QInput
          v-model="userData.nickname"
          filled
          label="이름"
          readonly
        />
        <QInput
          v-model="userData.birthDate"
          filled
          label="생일"
          readonly
        />
        <QInput
          v-model="userData.email"
          filled
          label="이메일"
          readonly
        />
        <QInput
          v-model="userData.phoneNumber"
          filled
          label="전화번호"
          readonly
        />
        <QInput
          v-model="userData.createdAt"
          filled
          label="생성일"
          readonly
        />
        <QInput
          v-model="userData.role"
          filled
          label="역할"
          readonly
        />
      </QCardSection>
    </QCard>

    <!-- <QCard
      class="mb-4"
      flat
    >
      <QForm
        class="flex justify-end"
        @submit.prevent="onRefresh"
      >
        <QInput
          v-model="nickname"
          type="text"
          label="검색어"
          clearable
          autofocus
        >
          <template #append>
            <QIcon
              name="search"
              class="cursor-pointer"
              @click="onRefresh"
            >
              <QTooltip
                self="bottom middle"
              >
                조회
              </QTooltip>
            </QIcon>
          </template>
        </QInput>
      </QForm>
    </QCard> -->
    <!-- <QTable
      :pagination="pagination"
      :rows="rows"
      :columns="columns"
      row-key="id"
      hide-pagination
      @row-click="onRowClick"
    >
      <template #header-cell="props">
        <QTd
          :props="props"
          :class="$style['table-header']"
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
        <QTd
          :props="props"
        >
          {{ props.value }}
          <QTooltip
            self="bottom middle"
          >
            {{ props.value }}
          </QTooltip>
        </QTd>
      </template>
    </QTable>
    <div class="row q-mt-md justify-center">
      <QPagination
        v-model="pagination.page"
        color="grey-8"
        :max="maxPages"
        size="sm"
      />
    </div> -->
    <!--
    <pre>{{ data }}</pre> -->
  </QPage>
</template>

<style module>
.table-header.table-header {
  @apply bg-primary text-gray-100;
  /* @apply bg-[#ff1493] text-gray-100; */
}
</style>
