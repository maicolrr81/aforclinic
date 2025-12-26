<script setup lang="ts">
definePageMeta({ layout: `policy` });

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `마케팅 정보 수신 동의 - 에이포의원`,
  description: `마케팅 정보 수신 동의`,

  // Open Graph
  ogTitle: `마케팅 정보 수신 동의 - 에이포의원`,
  ogDescription: `마케팅 정보 수신 동의`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `마케팅 정보 수신 동의 - 에이포의원`,
  twitterDescription: `마케팅 정보 수신 동의`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});
</script>

<template>
  <header>
    <h1>마케팅 정보 수신 동의</h1>
    <p>
      에이포의원에서 제공하는 이벤트/혜택 등 다양한 정보를 휴대전화(채널 알림 또는 문자), 이메일로 받아보실 수 있습니다.<br>
      서비스의 중요 안내사항 및 예약에 대한 정보는 마케팅 정보 수신 동의 여부와 관계없이 발송됩니다.
    </p>
  </header>
</template>
