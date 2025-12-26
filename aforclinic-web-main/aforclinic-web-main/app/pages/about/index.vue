<script setup lang="ts">
import type { ShallowRef } from 'vue';

definePageMeta({
  headThemeColor: `transparent`,
  mainClass: `!pt-0`,
});

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `병원소개 - 에이포의원`,
  description: `나만을 위한 아름다움. 에이포는 개인이 가지고 있는 고유의 美는 살리면서 자연스럽게 당신의 美에 날개를 달아드립니다.`,

  // Open Graph
  ogTitle: `병원소개 - 에이포의원`,
  ogDescription: `나만을 위한 아름다움. 에이포는 개인이 가지고 있는 고유의 美는 살리면서 자연스럽게 당신의 美에 날개를 달아드립니다.`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `병원소개 - 에이포의원`,
  twitterDescription: `나만을 위한 아름다움. 에이포는 개인이 가지고 있는 고유의 美는 살리면서 자연스럽게 당신의 美에 날개를 달아드립니다.`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const windowScroll = useWindowScroll();
const windowSize = useWindowSize();
const isShow = computed(() => windowScroll.y.value > (windowSize.height.value - 65));

const sections = ref<{
  id: string;
  title: string;
  ref: Readonly<ShallowRef<HTMLElement | null>>;
  active: boolean;
}[]>([
  {
    id: `about-hospital`,
    title: `병원 소개`,
    ref: useTemplateRef<HTMLElement>(`about-hospital`),
    active: false,
  },
  {
    id: `equipment`,
    title: `장비 소개`,
    ref: useTemplateRef<HTMLElement>(`equipment`),
    active: false,
  },
  {
    id: `tour`,
    title: `둘러보기`,
    ref: useTemplateRef<HTMLElement>(`tour`),
    active: false,
  },
  {
    id: `medical-team`,
    title: `의료진 소개`,
    ref: useTemplateRef<HTMLElement>(`medical-team`),
    active: false,
  },
  {
    id: `location`,
    title: `오시는 길`,
    ref: useTemplateRef<HTMLElement>(`location`),
    active: false,
  },
]);

const observers: (() => void)[] = [];

onMounted(() => {
  sections.value.forEach((section) => {
    const { stop } = useIntersectionObserver(section.ref, ([entry]) => {
      section.active = entry?.isIntersecting || false;
    }, {
      threshold: 0.5,
    });
    observers.push(stop);
  });
});

onUnmounted(() => {
  observers.forEach(stop => stop());
});
</script>

<template>
  <aside
    class="fixed top-16 z-[101] flex w-full max-w-screen-md justify-between gap-4 bg-white px-4 py-3 text-xs transition md:justify-start md:px-8"
    :class="{
      'opacity-0': !isShow,
    }"
  >
    <NuxtLink
      v-for="(section, index) of sections"
      :key="index"
      :to="`#${section.id}`"
      :class="{
        'text-blue-500': section.active,
      }"
    >
      {{ section.title }}
    </NuxtLink>
  </aside>

  <section
    id="about-hospital"
    ref="about-hospital"
    class="relative h-dvh"
  >
    <div class="h-full bg-[url('/images/ef981c7b55551d1f238c6b6b60463e3378a8c8282060a1b8177cb9c4c9f665fb.webp')] bg-cover bg-left-top">
      <h1 class="absolute left-8 top-1/2 w-24 -translate-y-1/2 whitespace-pre-line text-xl md:left-12 md:text-3xl">
        CUSTOMIZE BEAUTY SOLUTION
      </h1>
      <div class="absolute bottom-32 left-8 space-y-6 whitespace-pre-line text-base leading-normal md:left-12 md:text-xl">
        <p>
          나만을 위한 아름다움.
        </p>
        <p>
          에이포는 개인이 가지고 있는<br>
          고유의 美는 살리면서 자연스럽게<br>
          당신의 美에 날개를 달아드립니다.
        </p>
      </div>
    </div>
  </section>

  <section
    id="equipment"
    ref="equipment"
    class="scroll-m-16 pt-4 md:pt-12"
  >
    <div class="w-full">
      <figure>
        <a
          href="/images/98d9139b2dff0794f951fafaf6c62c81072e20c09f990db41ce4d5b1fb5886f0.jpg"
          target="_blank"
        >
          <img
            alt="병원 장비"
            class="w-full object-cover"
            src="/images/98d9139b2dff0794f951fafaf6c62c81072e20c09f990db41ce4d5b1fb5886f0.jpg"
          >
        </a>
      </figure>
    </div>
  </section>

  <section
    id="tour"
    ref="tour"
    class="scroll-m-16 p-4 md:px-6 md:py-12"
  >
    <div class="flex flex-col gap-4">
      <div class="w-full">
        <figure>
          <a
            href="/images/bf97d8c105b1cbe32cfca84a09253344c15e9ada0cda26f1fb54bd17d0d076e0.jpg"
            target="_blank"
          >
            <img
              alt="병원소개 이미지1"
              class="h-80 w-full rounded-3xl object-cover"
              src="/images/2ea922dd10087b377fb6f4ed8175c7133eab75c0dabbe0c5ddf879e924a2400e.webp"
            >
          </a>
        </figure>
      </div>
      <div class="flex w-full gap-4">
        <div class="flex w-1/3 flex-col gap-4">
          <div class="flex-1">
            <a
              href="/images/fc9f420cb402ff89c8dea72c27dfcb5ed29ff278b3b93778b3616f8a165353e3.jpg"
              target="_blank"
            >
              <img
                alt="병원소개 이미지2"
                class="h-60 w-full rounded-3xl object-cover"
                src="/images/6dae012c3811a9fbe4b40869e10450b0af5192d6662cee1559c1e61585ff85d0.webp"
              >
            </a>
          </div>
          <div class="flex-1">
            <a
              href="/images/1ba0a195c8cb188ab004beaca8b05d81dbcd07e59e285f30cccced5ac75bcf5b.jpg"
              target="_blank"
            >
              <img
                alt="병원소개 이미지3"
                class="h-20 w-full rounded-3xl object-cover"
                src="/images/8d8fddc031923ccd991ae8dfaba9d1198a2448864a70a43fee052ad7193a915d.webp"
              >
            </a>
          </div>
        </div>
        <div class="w-2/3">
          <a
            href="/images/c442e56961226bab697ebd373f822d346d43f3d1654458376799d0023b8b27ac.jpg"
            target="_blank"
          >
            <img
              alt="병원소개 이미지4"
              class="h-full w-full rounded-3xl object-cover"
              src="/images/c442e56961226bab697ebd373f822d346d43f3d1654458376799d0023b8b27ac.jpg"
            >
          </a>
        </div>
      </div>
      <div class="flex gap-4">
        <div class="w-2/3">
          <a
            href="/images/c7922be480a8e90ff06689e144a5b57371f26f725efd3c6790655e16ae1d24b8.jpg"
            target="_blank"
          >
            <img
              alt="병원소개 이미지5"
              class="h-48 w-full rounded-3xl object-cover"
              src="/images/21a84804911fd9b550e08a3476398dacea18bc9f3f18c0e08da30823f5b4f93f.webp"
            >
          </a>
        </div>
        <div class="w-1/3">
          <a
            href="/images/cd7dd804540fdfceebe8068a96f03ee7c0855c9ba760f59800b595fc44069c2f.jpg"
            target="_blank"
          >
            <img
              alt="병원소개 이미지6"
              class="h-48 w-full rounded-3xl object-cover"
              src="/images/66de1128c179f59cd821e685e321cff6ad379c043388466e9dbb662e4a1bbee7.webp"
            >
          </a>
        </div>
      </div>
    </div>
  </section>

  <section
    id="medical-team"
    ref="medical-team"
    class="scroll-m-16 space-y-2 p-4 md:space-y-4 md:px-6 md:py-12"
  >
    <div class="relative mx-auto max-w-sm">
      <img
        alt="김광수 대표원장"
        src="/images/KakaoTalk_20251020_112818898.jpg"
        class="rounded-lg object-cover"
      >
      <div class="absolute bottom-4 left-4 text-white">
        <p class="text-xl md:text-3xl">
          김광수 대표원장
        </p>
        <p class="text-base md:text-2xl">
          KIM KWANG SOO
        </p>
      </div>
    </div>

    <div class="mx-auto max-w-sm">
      <ul class="mt-4 list-inside list-disc space-y-2 text-sm md:text-base">
        <li>
          전&#41; 마법의손클리닉 원장
        </li>
        <li>
          전&#41; 메이퓨어의원 수서점, 목동점 원장
        </li>
        <li>
          전&#41; 상상의원 신사본점 원장
        </li>
        <li>
          전&#41; 지우개의원 원장
        </li>
        <li>
          현&#41; 에이포의원 대표원장
        </li>
      </ul>

      <ul class="mt-4 list-inside list-disc space-y-2 text-sm  md:mt-8 md:text-base">
        <li>
          대표미용성형레이저의학회 정회원
        </li>
        <li>
          대한레이저피부모발학회 정회원
        </li>
        <li>
          대한비만미용학회 정회원
        </li>
        <li>
          대한필러학회 정회원
        </li>
      </ul>
    </div>
  </section>

  <section
    id="location"
    ref="location"
    class="scroll-m-16"
  >
    <div class="space-y-4 p-4 md:px-6 md:py-12">
      <h2 class="font-semibold">
        오시는 길 · 진료시간 안내
      </h2>
      <dl class="grid grid-cols-[auto_1fr] gap-4 gap-y-16 text-sm md:grid-cols-[10rem_1fr]">
        <dt class="font-medium">
          주소
        </dt>
        <dd>
          서울시 강남구 논현로 857, A857 지하 1층(신사동 582-10)
          <div class="mt-4 flex gap-6">
            <a
              class="font-bold text-gray-800 hover:underline"
              href="http://map.naver.com/p/entry/place/1675719584?placePath=%2Fhome"
              target="_blank"
            >
              <img
                alt="네이버 지도"
                class="me-2 inline rounded ring-1"
                src="~/assets/icons/navermap_service_icon.png"
                width="20px"
                title="네이버 지도"
              >
              네이버 지도
            </a>
            <a
              class="font-bold text-gray-800 hover:underline"
              href="https://map.kakao.com/?urlX=506213&urlY=1118285&urlLevel=3&itemId=1761134696&q=%EC%97%90%EC%9D%B4%ED%8F%AC%EC%9D%98%EC%9B%90&srcid=1761134696&map_type=TYPE_MAP"
              target="_blank"
            >
              <img
                alt="카카오맵"
                class="me-2 inline rounded ring-1"
                src="~/assets/icons/kakaomap_service_icon.png"
                width="20px"
                title="카카오맵"
              >
              카카오 맵
            </a>
          </div>
        </dd>
      </dl>

      <NaverMap
        :lat="$config.public.naver.maps.lat"
        :lng="$config.public.naver.maps.lng"
      />

      <dl class="grid grid-cols-[auto_1fr] gap-4 gap-y-4 text-sm md:grid-cols-[10rem_1fr]">
        <dt class="font-medium">
          간략위치
        </dt>
        <dd class="text-gray-500">
          압구정역 4번 출구 도보 1분
        </dd>
        <dt class="font-medium">
          주차안내
        </dt>
        <dd class="space-y-1 text-gray-500">
          <p>발렛이용 가능</p>
          <p>주차비 지원</p>
        </dd>
        <dt class="font-medium">
          진료시간
        </dt>
        <dd class="space-y-1 text-gray-500">
          <div>월,목&nbsp;&nbsp;&nbsp;오전 10시 30분 ~ 오후 8시</div>
          <div>화,금&nbsp;&nbsp;&nbsp;오전 10시 30분 ~ 오후 7시</div>
          <div>토요일&nbsp;&nbsp;&nbsp;오전 10시 30분 ~ 오후 4시</div>
          <div>점심시간&nbsp;&nbsp;&nbsp;오후 1시 ~ 오후 2시</div>
          <div class="space-y-1 pt-2 text-xs">
            <p>* 토요일은 점심시간 없이 진료합니다.</p>
            <p>* 수요일, 일요일은 휴진입니다.</p>
          </div>
        </dd>
      </dl>
    </div>
  </section>
</template>
