<script setup lang="ts">
definePageMeta({
  layout: `splash`,
  headThemeColor: `transparent`,
});

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `에이포의원`,
  description: `당신의 아름다움을 항상 연구합니다.`,

  // Open Graph
  ogTitle: `에이포의원`,
  ogDescription: `당신의 아름다움을 항상 연구합니다.`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `에이포의원`,
  twitterDescription: `당신의 아름다움을 항상 연구합니다.`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});
</script>

<template>
  <section
    class="h-full w-full bg-[url('/images/c100285a75954747491d7daa9c1f3d8809eb781e246f12f105a66f7387dc8fcc.png')] bg-cover bg-center md:absolute md:left-0 md:top-0 md:z-[800]"
    @click="navigateTo(`/home`)"
  >
    <div class="absolute bottom-20 left-1/2 -translate-x-1/2 text-center font-light text-white">
      <h1>
        WELCOME TO AFORCLINIC
      </h1>
      <div class="mt-2">
        <NuxtLink
          class="click font-medium hover:underline"
          to="/home"
        >
          CLICK
        </NuxtLink>
      </div>
    <!-- <div class="-ms-2 mt-4 flex flex-col items-center justify-center">
        <span class="arrow" />
        <span class="arrow -mt-1" />
        <span class="arrow -mt-1" />
      </div> -->
    </div>
  </section>
</template>

<style lang="css" scoped>
.arrow {
  @apply block w-3 h-3 rotate-45 border-r-2 border-b-2 border-white mr-0 mb-1 ml-1;
  -webkit-animation: mouse-scroll 1s infinite;
  -moz-animation: mouse-scroll 1s infinite;
  animation: mouse-scroll 1s infinite;
}

.arrow:nth-child(1) {
  -webkit-animation-delay: 0.1s;
  -moz-animation-delay: 0.1s;
  -webkit-animation-direction: alternate;
  animation-direction: alternate;
  animation-delay: alternate;
}

.arrow:nth-child(2) {
  -webkit-animation-delay: 0.2s;
  -moz-animation-delay: 0.2s;
  -webkit-animation-direction: alternate;
  animation-delay: 0.2s;
  animation-direction: alternate;
}

.arrow:nth-child(3) {
  -webkit-animation-delay: 0.3s;
  -moz-animation-delay: 0.3s;
  -webkit-animation-direction: alternate;
  animation-delay: 0.3s;
  animation-direction: alternate;
}

@keyframes mouse-scroll {
  0% {
    opacity: 0.2;
  }
  50% {
    opacity: 0.7;
  }
  100% {
    opacity: 1;
  }
}
</style>
