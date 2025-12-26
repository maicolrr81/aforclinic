<script setup lang="ts">
definePageMeta({
  middleware: [`requires-agreements`],
});

const { $api, $dialog } = useNuxtApp();

const agreements = useAgreementsStore();

const username = ref(``); // 아이디(이메일)
const usernameValidation = ref<{
  valid: boolean;
  message: string;
}>();
const checkUsername = useDebounceFn(async () => {
  try {
    const data = await $api<ApiResponse<boolean>>(`/public/users/check-id`, {
      method: `get`,
      params: {
        username: username.value,
      },
    });
    usernameValidation.value = {
      valid: data.data,
      message: data.message || `알 수 없는 응답입니다.`,
    };
  }
  catch {
    usernameValidation.value = {
      valid: false,
      message: `아이디 확인 중 오류가 발생했습니다.`,
    };
  }
}, 300);

const nickname = ref(``);
const nicknameValidation = ref<Valid>();
const checkNickname = useDebounceFn(() => {
  const value = nickname.value;

  if (!value) {
    nicknameValidation.value = {
      valid: false,
      message: `필수정보입니다.`,
    };
    return;
  }
  nicknameValidation.value = {
    valid: true,
  };
}, 500);

const phoneNumber = ref(``);
const phoneNumberValidation = ref<Valid>();
const checkPhoneNumber = useDebounceFn(() => {
  // const value = phoneNumber.value.replace(/\D/g, '');
  const value = phoneNumber.value;

  if (!value) {
    phoneNumberValidation.value = {
      valid: false,
      message: `전화번호를 입력해주세요.`,
    };
    return;
  }

  // if (!/^01[0|16-9]\d{7,8}$/.test(value)) {
  //   phoneNumberValidation.value = {
  //     valid: false,
  //     message: `유효한 전화번호 형식이 아닙니다.`,
  //   };
  //   return;
  // }

  phoneNumberValidation.value = {
    valid: true,
    message: ``,
  };
}, 500);

const password = ref(``);
const passwordValidation = ref<Valid>();
const checkPassword = useDebounceFn(() => {
  const value = password.value;

  if (!value) {
    passwordValidation.value = {
      valid: false,
      message: `필수정보입니다.`,
    };
    return;
  }
  if (value.length < 8 || value.length > 16) {
    passwordValidation.value = {
      valid: false,
      message: `비밀번호는 8자 이상 16자 이하로 입력해주세요.`,
    };
    return;
  }

  const hasLetter = /[A-Z]/i.test(value);
  const hasNumber = /\d/.test(value);
  const hasSpecial = /[!@#$%^&*(),.?":{}|<>_\-\\[\]/+=~`]/.test(value);
  if (!hasLetter || !hasNumber || !hasSpecial) {
    passwordValidation.value = {
      valid: false,
      message: '영문, 숫자, 특수문자를 모두 포함해야 합니다.',
    };
    return;
  }

  passwordValidation.value = {
    valid: true,
    message: `안전한 비밀번호입니다.`,
  };
}, 500);

const confirmPassword = ref(``);
const confirmPasswordValidation = ref<Valid>();
const checkConfirmPassword = useDebounceFn(() => {
  if (!confirmPassword.value) {
    confirmPasswordValidation.value = {
      valid: false,
      message: '비밀번호 확인을 입력해주세요.',
    };
    return;
  }

  if (confirmPassword.value !== password.value) {
    confirmPasswordValidation.value = {
      valid: false,
      message: '비밀번호가 일치하지 않습니다.',
    };
    return;
  }

  confirmPasswordValidation.value = {
    valid: true,
    message: `동일합니다.`,
  };
}, 500);

const isCorrect = computed(() => {
  if (usernameValidation.value?.valid
    && nicknameValidation.value?.valid
    && phoneNumberValidation.value?.valid
    && passwordValidation.value?.valid
    && confirmPasswordValidation.value?.valid
  ) {
    return true;
  }
  return false;
});

async function submit() {
  try {
    await $api(`/public/users`, {
      method: `post`,
      body: {
        username: username.value,
        password: password.value,
        nickname: nickname.value,
        phoneNumber: phoneNumber.value,
        agreements: [
          {
            type: `TERMS_OF_SERVICE`,
            agreed: agreements.terms,
          },
          {
            type: `PRIVACY_POLICY`,
            agreed: agreements.privacy,
          },
          {
            type: `MARKETING`,
            agreed: agreements.marketing,
          },
        ],
      } satisfies CreateUser,
    });
    // 회원가입 성공하면 홈으로 이동
    await $dialog.alert(`회원가입이 완료되었습니다.`);
    navigateTo(`/`);
  }
  catch {
    $dialog.alert(`회원가입 중 오류가 발생했습니다.`);
  }
}

onUnmounted(() => agreements.$reset());
</script>

<template>
  <section class="p-8">
    <h1 class="text-3xl">
      회원가입
    </h1>
    <h3 class="mt-8 text-2xl">
      고객님 환영합니다!<br>
      예약 관리를 위해<br>
      이름과 전화번호를 입력해주세요.
    </h3>
    <form
      class="mt-8 flex flex-col gap-8"
      @submit.prevent="submit()"
    >
      <fieldset>
        <label class="flex flex-col gap-4 border-b border-b-gray-600 py-4">
          <span>
            이름
          </span>
          <input
            v-model="nickname"
            type="text"
            class="flex-1"
            placeholder="이름을 입력해주세요."
            @input="checkNickname()"
          >
        </label>
        <div
          v-if="nicknameValidation"
          class="invalid-feedback"
        >
          {{ nicknameValidation.message }}
        </div>
      </fieldset>
      <fieldset>
        <label class="flex flex-col gap-4 border-b border-b-gray-600 py-4">
          <span>
            연락처
          </span>
          <input
            v-model="phoneNumber"
            type="text"
            class="flex-1"
            placeholder="숫자만 입력해주세요."
            maxlength="15"
            @input="checkPhoneNumber()"
          >
        </label>
        <div
          v-if="phoneNumberValidation && !phoneNumberValidation.valid"
          class="invalid-feedback"
        >
          {{ phoneNumberValidation.message }}
        </div>
      </fieldset>
      <fieldset>
        <label class="flex flex-col gap-4 border-b border-b-gray-600 py-4">
          <span>
            아이디
          </span>
          <input
            v-model="username"
            type="text"
            class="flex-1"
            placeholder="이메일 주소를 입력해주세요."
            @input="checkUsername()"
          >
        </label>
        <div
          v-if="usernameValidation"
          :class="{
            'valid-feedback': usernameValidation.valid,
            'invalid-feedback': !usernameValidation.valid,
          }"
        >
          {{ usernameValidation.message }}
        </div>
      </fieldset>
      <fieldset>
        <label class="flex flex-col gap-4 border-b border-b-gray-600 py-4">
          <span>
            비밀번호
          </span>
          <input
            v-model="password"
            type="password"
            class="flex-1"
            autocomplete="new-password"
            placeholder="비밀번호를 입력해주세요."
            @input="checkPassword()"
          >
        </label>
        <div
          v-if="passwordValidation"
          :class="{
            'valid-feedback': passwordValidation.valid,
            'invalid-feedback': !passwordValidation.valid,
          }"
        >
          {{ passwordValidation.message }}
        </div>
      </fieldset>
      <fieldset>
        <label class="flex flex-col gap-4 border-b border-b-gray-600 py-4">
          <span>
            비밀번호 확인
          </span>
          <input
            v-model="confirmPassword"
            type="password"
            class="flex-1"
            autocomplete="new-password"
            placeholder="비밀번호를 입력해주세요."
            @input="checkConfirmPassword()"
          >
        </label>
        <div
          v-if="confirmPasswordValidation"
          :class="{
            'valid-feedback': confirmPasswordValidation.valid,
            'invalid-feedback': !confirmPasswordValidation.valid,
          }"
        >
          {{ confirmPasswordValidation.message }}
        </div>
      </fieldset>
      <div class="mt-12 text-center">
        <button
          type="submit"
          class="inline-flex h-[45px] w-60 items-center justify-center rounded-md border border-transparent bg-cyan-300 px-4 py-2 text-base font-medium text-black hover:bg-cyan-400 focus:outline-none focus-visible:ring-2 focus-visible:ring-cyan-500 focus-visible:ring-offset-2 disabled:bg-gray-100 disabled:text-gray-200"
          :disabled="!isCorrect"
        >
          가입하기
        </button>
      </div>
    </form>
  </section>
</template>
