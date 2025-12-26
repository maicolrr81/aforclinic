<script setup lang="ts">
definePageMeta({
  layout: 'none',
  middleware: [],
});

const { $q } = useNuxtApp();

const auth = useAuthStore();

const username = ref(``);
const password = ref(``);

const isPasswordVisibility = ref(false);

async function onLogin() {
  try {
    await auth.login({
      username: username.value,
      password: password.value,
    });
    navigateTo(`/`);
  }
  catch {
    $q.dialog({
      title: `알림`,
      message: `로그인에 실패했습니다. 잠시 후 다시 시도해주세요.`,
      ok: `확인`,
    });
  }
}
</script>

<template>
  <div class="flex h-full w-full items-center justify-center">
    <div class="w-[450px] p-4">
      <img
        alt="로그인 로고"
        src="/images/logo.webp"
        width="100%"
      >
      <h1 class="mt-12 text-center text-4xl font-bold">
        관리자 로그인
      </h1>
      <QForm
        class="mt-24 flex flex-col gap-y-4"
        autofocus
        @submit.prevent="onLogin"
      >
        <QInput
          v-model="username"
          clearable
          outlined
          class="control"
          label="아이디"
          tabindex="1"
        />
        <QInput
          v-model="password"
          autocomplete="current-password"
          clearable
          outlined
          label="비밀번호"
          :type="isPasswordVisibility ? `text` : `password`"
          tabindex="2"
        >
          <template #append>
            <QIcon
              class="cursor-pointer"
              :name="isPasswordVisibility ? 'visibility' : 'visibility_off'"
              tabindex="-1"
              @click="isPasswordVisibility = !isPasswordVisibility"
            />
          </template>
        </QInput>
        <div class="mt-8">
          <QBtn
            type="submit"
            class="full-width"
            color="secondary"
            label="로그인"
            size="lg"
            tabindex="3"
          />
        </div>
      </QForm>
    </div>
  </div>
</template>
