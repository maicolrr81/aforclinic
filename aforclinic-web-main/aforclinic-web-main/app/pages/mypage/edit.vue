<script setup lang="ts">
import type { UpdateUser } from '#shared/types/User';
import type { Valid } from '#shared/types/Valid';

definePageMeta({
  middleware: [`requires-auth`],
});

const { $api, $dialog } = useNuxtApp();

const auth = useAuthStore();
const user = useUserStore();

const isUser = computed(() => user.data?.role === 'USER');

const nickname = ref(user.data?.nickname ?? ``);
const nicknameValidation = ref<Valid>();
const checkNickname = useDebounceFn(() => {
  if (!nickname.value) {
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

const phoneNumber = ref(user.data?.phoneNumber ?? ``);
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

async function submit() {
  try {
    await $api(`/public/users/me`, {
      method: `put`,
      body: {
        nickname: nickname.value,
        phoneNumber: phoneNumber.value,
      } satisfies UpdateUser,
    });
    await user.load();
    await $dialog.alert(`회원 정보가 수정되었습니다.`);
    navigateTo(`/mypage`);
  }
  catch {
    $dialog.alert(`회원 정보 수정 중 오류가 발생했습니다.`);
  }
}

async function leave() {
  const isConfirmed = await $dialog.confirm(`정말 탈퇴하시겠습니까?`);

  if (!isConfirmed) { return; }

  try {
    await $api(`/public/users/me`, {
      method: `delete`,
    });
    await $dialog.alert(`회원 탈퇴가 완료되었습니다.`);
    auth.logout();
  }
  catch {
    $dialog.alert(`회원 탈퇴 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <section class="p-8">
    <h1 class="text-3xl">
      내 정보 수정
    </h1>
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

      <div class="relative mt-12 flex items-center justify-center gap-x-4">
        <button
          type="submit"
          class="inline-flex h-[45px] w-60 items-center justify-center rounded-md border border-transparent bg-cyan-300 px-4 py-2 text-base font-medium text-black hover:bg-cyan-400 focus:outline-none focus-visible:ring-2 focus-visible:ring-cyan-500 focus-visible:ring-offset-2 disabled:bg-gray-100 disabled:text-gray-200"
        >
          수정하기
        </button>
      </div>

      <div class="mt-12 flex items-center justify-end">
        <button
          v-if="isUser"
          type="button"
          class="font-medium text-red-500 hover:underline"
          @click="leave()"
        >
          탈퇴하기
        </button>
      </div>
    </form>
  </section>
</template>
