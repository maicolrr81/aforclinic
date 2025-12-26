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
</script>

<template>
  <QLayout view="hHh lpR fFf">
    <QPageContainer>
      <QPage class="flex items-center justify-center">
        <div class="flex flex-col items-center justify-center gap-y-24">
          <h1 class="text-[10rem] font-black tracking-widest">
            {{ error.statusCode }}
          </h1>
          <h3 class="text-4xl font-medium">
            {{ message }}
          </h3>
          <QBtn
            color="pink"
            class="px-6 py-4 text-lg tracking-[0.25rem]"
            to="/"
          >
            TAKE ME HOME
          </QBtn>
        </div>
      </QPage>
    </QPageContainer>
  </QLayout>
</template>
