<script setup lang="ts">
import type { QForm, QInput, ValidationRule } from 'quasar';

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

const userData = ref<Partial<User>>({
  role: 'ADMIN',
});

const { data: checkDuplicateData, execute: checkDuplicateDataExecute } = useAsyncData(() => $api<ApiResponse>(`/users/check-id/${userData.value.username}`, {
  method: `get`,
}), {
  immediate: false,
});

const { execute: createDataExecute } = useAsyncData(() => $api<ApiResponse>(`/users`, {
  method: `post`,
  body: {
    username: userData.value.username,
    password: userData.value.password,
    nickname: userData.value.nickname,
    birthDate: userData.value.birthDate,
    phoneNumber: userData.value.phoneNumber,
    role: userData.value.role,
  },
}), {
  immediate: false,
});

const idError = ref<boolean>(false);
const idErrorMessage = ref<string>(``);

const form = useTemplateRef<QForm | null>(`form`);

const passwordRef = useTemplateRef<QInput | null>(`passwordRef`);
const passwordConfirmRef = useTemplateRef<QInput | null>(`passwordConfirmRef`);
const nameRef = useTemplateRef<QInput | null>(`nameRef`);
const birthDateRef = useTemplateRef<QInput | null>(`birthDateRef`);
const phoneNumberRef = useTemplateRef<QInput | null>(`phoneNumberRef`);
// if (passwordRef.value) { console.log('비밀번호 오류 상태> ', passwordRef.value.validate() 정상일때 true);  passwordRef.value.resetValidation(); // 에러메시지 초기화}

const certificationOK = ref<boolean>(false); // 아이디 인증

const procCheckUserID = ref<boolean>(false);

watch(() => userData.value.username, () => {
  certificationOK.value = false;
  idError.value = false;
  idErrorMessage.value = ``;
  // procCheckUserID.value = false;
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
  userData.value = {
    role: 'ADMIN',
  };
  userPasswordConfirm.value = '';
  certificationNumber.value = '';
}

function closeClick() {
  clearData();
  emit('close');
}

async function saveClick() {
  if (!certificationOK.value) {
    idError.value = true;
    idErrorMessage.value = `아이디를 인증하세요`;
    return;
  }

  if (!(await form.value?.validate(false))) {
    return;
  }

  await createDataExecute();

  // console.log(userData.value.id);

  // try {
  //   await update();
  //   console.log(`updateError`, updateError.value);
  // }
  // catch {
  //   console.log(`updateError`, updateError);
  // }

  clearData(); // 빈값으로 초기화

  emit('ok', '1234');
}

// watch(error, () => {
//   emit(`close`);
// });

// 아이디 중복 체크
async function checkUserId() {
  await checkDuplicateDataExecute();

  if (checkDuplicateData.value) {
    console.log('dupChkData', checkDuplicateData.value.data.count);

    if (checkDuplicateData.value.data.count === 0) {
      // 아이디 중복 없음
      // certificationNumber.value = '';
      // procCheckUserID.value = true;

      idError.value = false;
      idErrorMessage.value = '';

      certificationOK.value = true;
    }
    else {
      // 아이디 중복 됨.
      idError.value = true;
      idErrorMessage.value = checkDuplicateData.value?.data.message;
    }
  }

  // TODO 아이디 중복 안된 경우, 인증 메일 보내기!!!!!!!!!!!!!!
}

function checkCertification() {
  // 인증 확인 체크
  // TODO

  // 인증번호가 정상일 때만 false로 완료 된 것처럼 처리한다.
  procCheckUserID.value = false;
}

const passwordConfirmRules: ValidationRule[] = [
  (val: string) => !!val || '비밀번호 확인을 입력하세요',
  () => (userData.value.password === userPasswordConfirm.value) || '비밀번호와 비밀번호 확인이 일치하지 않습니다.',
];
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
            사용자 생성
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
        <QForm
          ref="form"
          class="grid grid-cols-1 gap-x-4 gap-y-2"
        >
          <UiEnumSelect
            v-model="userData.role"
            name="adminrole"
            :rules="roleRules"
            class="mb-5"
          />
          <div class="grid grid-cols-[auto,100px] gap-x-2">
            <QInput
              v-if="userData.role === 'USER'"
              v-model.trim="userData.username"
              type="email"
              label="아이디(이메일)"
              outlined
              clearable
              :rules="emailRules"
              lazy-rules="ondemand"
              :error="idError"
              :error-message="idErrorMessage"
            />
            <QInput
              v-else
              v-model.trim="userData.username"
              type="text"
              label="아이디"
              outlined
              clearable
              :rules="idRules"
              lazy-rules="ondemand"
              :error="idError"
              :error-message="idErrorMessage"
              tabindex="1"
            >
              <template #append>
                <NuxtIcon
                  v-if="certificationOK"
                  name="mdi:success-circle-outline"
                  class="text-green-400"
                  size="28"
                />
              </template>
            </QInput>
            <QBtn
              color="blue-6"
              class="h-14"
              @click="checkUserId"
            >
              아이디 확인
            </QBtn>
          </div>
          <div
            v-if="userData.role === 'USER'"
            class="grid grid-cols-[auto,100px] gap-x-2"
          >
            <QInput
              v-model.trim="certificationNumber"
              type="text"
              label="인증번호"
              outlined
              clearable
              :rules="certificationNumberRules"
              lazy-rules="ondemand"
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
            ref="passwordRef"
            v-model.trim="userData.password"
            :type="isPasswordSecret ? 'password' : 'text'"
            label="비밀번호"
            outlined
            clearable
            :rules="passwordRules"
            lazy-rules="ondemand"
            tabindex="2"
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
            ref="passwordConfirmRef"
            v-model.trim="userPasswordConfirm"
            :type="isPasswordSecret ? 'password' : 'text'"
            label="비밀번호 확인"
            outlined
            clearable
            :rules="passwordConfirmRules"
            lazy-rules="ondemand"
            tabindex="3"
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
            ref="nameRef"
            v-model.trim="userData.nickname"
            type="text"
            label="이름"
            outlined
            :rules="nameRules"
            lazy-rules="ondemand"
            tabindex="4"
          />
          <QInput
            ref="birthDateRef"
            v-model="userData.birthDate"
            type="text"
            label="생년월일"
            outlined
            clearable
            :rules="dateRules"
            lazy-rules="ondemand"
            tabindex="5"
          >
            <template #append>
              <UiDatePicker v-model="userData.birthDate" />
            </template>
          </QInput>
          <QInput
            ref="phoneNumberRef"
            v-model.trim="userData.phoneNumber"
            type="tel"
            label="전화번호"
            outlined
            clearable
            :rules="phoneNumberRules"
            lazy-rules="ondemand"
            tabindex="6"
          />
        </QForm>
      </QCardSection>
      <QCardActions class="p-4">
        <QSpace />
        <QBtn @click="closeClick">
          취소
        </QBtn>
        <QBtn
          color="blue"
          tabindex="5"
          @click="saveClick"
        >
          저장
        </QBtn>
      </QCardActions>
    </QCard>
  </QDialog>
</template>

<style module>
</style>
