<script setup lang="ts">
// const {
//   userId,
// } = defineProps<{
//   userId: string;
// }>();

const emit = defineEmits<{
  ok: [result?: string];
  close: [];
}>();

const { $api } = useNuxtApp();

const roleOptions = ref([
  { text: `회원`, value: `USER` },
  { text: `매니저`, value: `MANAGER` },
  { text: `관리자`, value: `ADMIN` },
]);

if (import.meta.client) {
  // console.log(`====userId`, userId);
}

const userData = ref<Partial<User>>({});

const idError = ref<boolean>(false);
const idErrorMessage = ref<string>(``);
const procCheckUserID = ref<boolean>(false);

watch(userData.value.username, () => {
  idError.value = false;
  idErrorMessage.value = ``;
  procCheckUserID.value = false;
}, {
  deep: true,
});

const userPasswordConfirm = ref<string>(``);
const certificationNumber = ref<string>(``);

const isPasswordSecret = ref(true);
function togglePasswordSecret() {
  isPasswordSecret.value = !isPasswordSecret.value;
}

// const { data: userData, error, execute, clear } = useAsyncData<Partial<User>>(async () => {
//   const res = await $api<ApiResponse<User>>(`/users/${userId}`);
//   return res.data;
// }, {
//   default: () => ({}),
//   immediate: false,
//   deep: true,
// });
//
// const { error: updateError, execute: update } = useAsyncData(() => $api<ApiResponse>(`/users/${userData.value.id}`, {
//   method: `put`,
//   body: {
//     nickname: userData.value.nickname,
//     birthDate: userData.value.birthDate,
//     phoneNumber: userData.value.phoneNumber,
//   },
// }), {
//   immediate: false,
// });

function clearData() {
  userData.value = {};
  userPasswordConfirm.value = '';
  certificationNumber.value = '';
}

function closeClick() {
  clearData();
  emit('close');
}

// watch(error, () => {
//   emit(`close`);
// });

function getSearchId() {
  // 아이디 찾기
  // TODO
}
</script>

<template>
  <!-- <QDialog
    @before-show="execute()"
    @before-hide="clear()"
  > -->
  <QDialog>
    <QCard class="w-[48rem]">
      <QCardSection class="row items-center">
        <QToolbar class="gap-x-2 pe-0">
          <QToolbarTitle>
            아이디 찾기
          </QToolbarTitle>
          <QSpace />
          <QBtn
            icon="close"
            flat
            round
            dense
            @click="closeClick"
          />
        </QToolbar>
      </QCardSection>

      <QCardSection>
        <div class="grid grid-cols-1 gap-x-4 gap-y-2">
          <QInput
            v-model="userData.nickname"
            type="text"
            label=""
            outlined
            disable
          >
            휴대폰 번호로 찾기
          </QInput>

          <QInput
            v-model="userData.nickname"
            type="text"
            label="이름"
            outlined
            :rules="[
              val => { if (val) userData.nickname = val.trim(); return true; },
              val => (val.length <= 100) || '이름이 100자를 초과하였습니다.',
            ]"
          />
          <QInput
            v-model="userData.phoneNumber"
            type="tel"
            label="전화번호"
            outlined
            clearable
            :rules="[
              val => { if (val) userData.phoneNumber = val.trim(); return true; },
              val => (!val.trim() || /^\d{10,11}$/.test(val)) || '전화번호가 유효하지 않습니다. (10~11자리 숫자)',
            ]"
            lazy-rules
          />
          <QBtn
            color="blue-5"
            @click="getSearchId"
          >
            확인
          </QBtn>
        </div>
      </QCardSection>
      <QCardActions class="p-4">
        <QSpace />
        <QBtn @click="closeClick">
          취소
        </QBtn>
        <QBtn
          color="blue"
          @click=""
        >
          저장
        </QBtn>
      </QCardActions>
    </QCard>
  </QDialog>
</template>

<style module>
</style>
