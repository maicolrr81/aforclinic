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
            비밀번호 찾기
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
            메일주소로 비밀번호 찾기
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
            v-if="userData.role === 'USER'"
            v-model="userData.username"
            type="email"
            label="아이디(이메일)"
            outlined
            clearable
            :rules="[
              val => !!val || '아이디(이메일)을 입력하세요',
              val => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(val) || '아이디가 이메일 형식이 아닙니다.',
            ]"
            lazy-rules

            :error="false"
            error-message=""
          />
          <QBtn
            color="blue-5"
            @click="getSearchId"
          >
            메일 전송
          </QBtn>

          <div
            v-if="userData.role === 'USER'"
            class="grid grid-cols-[auto,100px] gap-x-2"
          >
            <QInput
              v-model="certificationNumber"
              type="text"
              label="인증번호"
              outlined
              clearable
              :rules="[
                val => (val.trim().length == 6) || '인증번호 형식이 아닙니다.',
              ]"
              lazy-rules
              :disable="!procCheckUserID"
            />
            <QBtn
              :color="procCheckUserID ? 'blue-9' : 'grey-7'"
              class="h-14"
              :disable="!procCheckUserID"
              @click="checkCertification"
            >
              인증 확인
            </QBtn>
          </div>

          <QInput
            v-model="userData.password"
            :type="isPasswordSecret ? 'password' : 'text'"
            label="비밀번호"
            outlined
            clearable
            :rules="[
              val => { if (val) userData.password = val = val.trim(); return true; },
              val => !!val || '비밀번호를 입력하세요',
              val => ((val.length >= 8 && val.length <= 16)
                && (/[a-zA-Z]/.test(val))
                && (/\d/.test(val))
                && (/[!@#$%^&*]/.test(val))
              ) || `영문, 숫자, 특수문자(!@#$%^&*)를 포함한 8~16자`,
            ]"
            lazy-rules
          >
            <template #append>
              <QBtn
                flat
                round
                size="md"
                @click="togglePasswordSecret"
              >
                <QIcon
                  v-if="isPasswordSecret"
                  name="visibility"
                />
                <QIcon
                  v-else
                  name="visibility_off"
                />
              </QBtn>
            </template>
          </QInput>
          <QInput
            v-model="userPasswordConfirm"
            :type="isPasswordSecret ? 'password' : 'text'"
            label="비밀번호 확인"
            outlined
            clearable
            :rules="[
              val => { if (val) userPasswordConfirm = val = val.trim(); return true; },
              val => !!val || '비밀번호 확인을 입력하세요',
              val => (userData.password == userPasswordConfirm) || '비밀번호와 비밀번호 확인이 일치하지 않습니다.',
            ]"
            lazy-rules
          >
            <template #append>
              <QBtn
                flat
                round
                size="md"
                @click="togglePasswordSecret"
              >
                <QIcon
                  v-if="isPasswordSecret"
                  name="visibility"
                />
                <QIcon
                  v-else
                  name="visibility_off"
                />
              </QBtn>
            </template>
          </QInput>
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
          비밀번호 수정
        </QBtn>
      </QCardActions>
    </QCard>
  </QDialog>
</template>

<style module>
</style>

<!-- TODO 메일 인증되면, 비밀번호 바로 변경할 수 있도록 세팅 필요 !! -->
