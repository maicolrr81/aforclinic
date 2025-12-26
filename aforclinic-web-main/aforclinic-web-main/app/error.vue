<script setup lang="ts">
import type { NuxtError } from '#app';

const { error } = defineProps<{ error: NuxtError }>();

const message = computed(() => {
  switch (error.statusCode) {
    case 404:
      return `요청하신 페이지를 찾을 수 없습니다. 주소를 다시 확인해 주세요.`;
    case 500:
      return `서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.`;
    case 401:
      return `접근 권한이 없습니다. 로그인 후 다시 시도해 주세요.`;
    case 403:
      return `이 페이지에 접근할 권한이 없습니다.`;
    case 400:
      return `잘못된 요청입니다. 입력 정보를 확인해 주세요.`;
    case 502:
      return `일시적인 서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.`;
    case 503:
      return `서비스를 일시적으로 사용할 수 없습니다. 잠시 후 다시 시도해 주세요.`;
    default:
      return `오류가 발생했습니다. (오류 코드: ${error.statusCode})`;
  }
});

console.log(`error`, error);
</script>

<template>
  <div class="flex h-svh items-center justify-center">
    <div class="flex flex-col items-center justify-center gap-y-12">
      <h1 class="text-center text-[6rem] font-black">
        {{ error.statusCode }}
      </h1>
      <h3 class="whitespace-pre-line text-center text-xl font-medium leading-loose">
        {{ message }}
      </h3>
      <NuxtLink
        to="/"
        class="rounded bg-pink-500 px-6 py-4 text-lg tracking-[0.25rem] text-white shadow-lg hover:bg-pink-600"
      >
        TAKE ME HOME
      </NuxtLink>
    </div>
  </div>
</template>
