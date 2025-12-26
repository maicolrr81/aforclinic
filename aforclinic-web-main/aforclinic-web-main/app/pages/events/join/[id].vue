<script setup lang="ts">
import type { CreateEventAppointment, CreateSimpleEventAppointment } from '#shared/types/Appointment';

definePageMeta({ layout: `none` });

const { $api, $dialog } = useNuxtApp();

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<LandingPost>>(async () => {
  const data = await $api<ApiResponse<LandingPost>>(`/public/landings/${route.params.id}`);
  return data.data;
});

// 데이터가 없는 경우
if (error.value) {
  showError({ statusCode: 404 });
}

useSeoMeta({
  // Basic
  title: `${data.value?.title} - 에이포의원`,
  description: data.value?.description,

  // Open Graph
  ogTitle: `${data.value?.title} - 에이포의원`,
  ogDescription: data.value?.description,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterTitle: `${data.value?.title} - 에이포의원`,
  twitterDescription: data.value?.description,
  twitterCard: `summary_large_image`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const nickname = ref(``);
const hasErrorNickname = ref(false);

const contact = ref(``);
const hasErrorContact = ref(false);

function clear() {
  nickname.value = ``;
  contact.value = ``;

  hasErrorNickname.value = false;
  hasErrorContact.value = false;
}

async function submit(type: string) {
  if (type === `REQUEST`) {
    let hasError = false;
    if (!nickname.value) {
      hasErrorNickname.value = true;
      hasError = true;
    }
    if (!contact.value) {
      hasErrorContact.value = true;
      hasError = true;
    }
    if (hasError) {
      return;
    }

    const confirm = await $dialog.confirm(
      `이름: ${nickname.value}\r\n연락처: ${contact.value}\r\n\r\n위 내용이 맞다면 "확인"을 눌러주세요.`,
      {
        title: `🎉 상담 신청 내용을 확인해주세요.`,
      },
    );

    if (!confirm) { return; }

    try {
      await $api(`/public/appointments`, {
        method: `post`,
        body: {
          type: `EVENT`,
          nickname: nickname.value,
          contact: contact.value,
          content: `[${data.value?.title}] 상담신청`,
        } satisfies CreateEventAppointment,
      });
      clear();
    }
    catch {
      await $dialog.alert(`상담 신청 중 오류가 발생했습니다.`);
      return;
    }
    await $dialog.alert(`상담 신청이 완료됐습니다.`);
  }
  else if (import.meta.client) {
    let content = ``;
    switch (type) {
      case `CALL`:
        content = `전화 문의`;
        break;
      case `NAVER`:
        content = `네이버 예약`;
        break;
      case `KAKAO`:
        content = `카카오 상담`;
        break;
      default:
        return;
    }
    try {
      await $api(`/public/appointments`, {
        method: `post`,
        body: {
          type: `EVENT`,
          content: `[${data.value?.title}] ${content}`,
        } satisfies CreateSimpleEventAppointment,
      });
      const locations: Record<string, string> = {
        CALL: 'tel:0261041199',
        NAVER: 'https://naver.me/FG7zukKO',
        KAKAO: 'https://pf.kakao.com/_IMNZn/chat/',
      };
      window.location.href = locations[type]!;
    }
    catch {
      await $dialog.alert(`상담 신청 중 오류가 발생했습니다.`);
    }
  }
}
</script>

<template>
  <article class="pb-[388px]">
    <div
      class="prose mx-auto max-w-none"
      v-html="data?.content"
    />
    <form
      class="fixed bottom-0 left-1/2 w-full -translate-x-1/2 space-y-2 bg-white p-2 md:max-w-screen-md md:space-y-4"
      @submit.prevent="submit(`REQUEST`)"
    >
      <p class=" bg-gray-100 py-2 text-center text-xs font-medium md:py-4 md:text-sm">
        본원에서는 고객님의 소중한 개인정보를<br>상담 외 어떠한 목적으로도 사용하지 않습니다.
      </p>
      <fieldset class="flex flex-col gap-2 md:gap-4">
        <label class="grid w-full grid-cols-[60px_1fr] items-center">
          <span class="bg-gray-300 py-2 text-center text-xs md:py-4 md:text-sm">
            이 름
          </span>
          <input
            v-model="nickname"
            maxlength="6"
            class="flex-1 bg-gray-100 p-2 text-xs md:p-4 md:text-sm"
            :class="{
              'ring-1 ring-red-500': hasErrorNickname,
            }"
            placeholder="이름을 입력해주세요."
            @input="hasErrorNickname = false"
          >
          <template v-if="hasErrorNickname">
            <div />
            <p class="mt-1 text-xs font-medium text-red-500">
              이름을 입력해주세요.
            </p>
          </template>
        </label>
        <label class="grid w-full grid-cols-[60px_1fr]">
          <span class="bg-gray-300 py-2 text-center text-xs md:py-4 md:text-sm">
            연락처
          </span>
          <input
            v-model="contact"
            class="flex-1 bg-gray-100 p-2 text-xs md:p-4 md:text-sm"
            :class="{
              'ring-1 ring-red-500': hasErrorContact,
            }"
            maxlength="11"
            placeholder="연락처를 입력해주세요."
            @input="() => {
              hasErrorContact = false;
              contact = contact.replace(/[^0-9]/g, '');
            }"
          >
          <template v-if="hasErrorContact">
            <div />
            <p class="mt-1 text-xs font-medium text-red-500">
              정확한 연락처를 입력해주세요.
            </p>
          </template>
        </label>
      </fieldset>
      <div class="grid">
        <button class="bg-blue-500 py-2 text-sm font-bold text-white shadow-md hover:bg-blue-600 md:py-4 md:text-xl">
          상담 신청하기
        </button>
      </div>
      <div class="grid grid-cols-3 gap-2 md:gap-4">
        <button
          class="bg-blue-500 py-2 text-sm font-bold text-white shadow-md hover:bg-blue-600 md:py-4 md:text-base"
          type="button"
          @click="submit(`CALL`)"
        >
          전화문의
        </button>
        <button
          class="bg-blue-500 py-2 text-sm font-bold text-white shadow-md hover:bg-blue-600 md:py-4 md:text-base"
          type="button"
          @click="submit(`NAVER`)"
        >
          네이버예약
        </button>
        <button
          class="bg-blue-500 py-2 text-sm font-bold text-white shadow-md hover:bg-blue-600 md:py-4 md:text-base"
          type="button"
          @click="submit(`KAKAO`)"
        >
          카카오상담
        </button>
      </div>
    </form>
  </article>
</template>
