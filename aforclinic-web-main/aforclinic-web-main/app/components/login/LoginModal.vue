<script setup lang="ts">
const auth = useAuthStore();
const nuxtApp = useNuxtApp();
const { $modal } = nuxtApp;

const username = ref(``);
const password = ref(``);
const hasError = ref(false);

const credentials = reactive({
  username,
  password,
});

function reset() {
  credentials.username = ``;
  credentials.password = ``;
  hasError.value = false;
}

async function onLogin() {
  try {
    await auth.login(credentials);
    reset();
    $modal.reset();
  }
  catch {
    hasError.value = true;
  }
}
</script>

<template>
  <UiModal>
    <form class="flex flex-col gap-y-4">
      <img
        alt="로고"
        class="mx-auto w-16 md:w-24"
        src="/images/b7d8805545ac582e300583227d69fb270a3eec167799b760fd222b8002cdec94.webp"
        width="100px"
      >
      <p class="py-4 text-xs font-medium md:text-sm">
        로그인, 회원가입 시 고객님께 간편 시술 예약은 물론, 이벤트 정보와 전후사진, 멤버십 혜택 등 다양한 서비스를 이용하실 수 있습니다.
      </p>
      <input
        v-model="username"
        type="text"
        class="border p-3 text-xs md:text-sm"
        placeholder="아이디"
        @keyup.enter="onLogin"
      >
      <input
        v-model="password"
        type="password"
        class="border p-3 text-xs md:text-sm"
        autocomplete="current-password"
        placeholder="비밀번호"
        @keyup.enter="onLogin"
      >
      <p
        v-if="hasError"
        class="-mt-3 text-sm text-red-600"
      >
        계정 또는 비밀번호가 일치하지 않습니다.
      </p>
    </form>
    <template #buttons>
      <div class="mt-4 grid grid-rows-[1fr_1fr_auto_auto] gap-y-4">
        <button
          type="submit"
          class="inline-flex items-center justify-center rounded-md border border-transparent bg-gray-100 px-4 py-2 text-sm text-black hover:bg-gray-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-gray-500 focus-visible:ring-offset-2 md:h-[52px] md:text-lg"
          @click="onLogin"
        >
          로그인
        </button>
        <!-- <NuxtLink
          :href="`${runtimeConfig.public.apiBase}/oauth2/authorization/kakao`"
          class="grid max-h-[52px] bg-[url('~/assets/icons/kakao_login_large_wide.png')] bg-cover bg-center bg-no-repeat"
        /> -->
        <a
          href="/api/auth/oauth"
          class="grid max-h-[52px] bg-[url('~/assets/icons/kakao_login_large_wide.png')] bg-cover bg-center bg-no-repeat"
        >
          <span class="sr-only">
            카카오 로그인
          </span>
        </a>
        <NuxtLink
          class="text-center text-xs text-gray-600 md:text-sm"
          to="/signup"
          @click="$modal.reset()"
        >
          회원가입
        </NuxtLink>
      </div>
    </template>
  </UiModal>
</template>
