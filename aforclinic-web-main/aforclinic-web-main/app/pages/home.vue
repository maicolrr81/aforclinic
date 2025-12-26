<script setup lang="ts">
import type { Event } from '#shared/types/Event';
import type { SiteSetting } from '#shared/types/SiteSetting';

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

const { $api, $config } = useNuxtApp();

const { data: settings } = await useAsyncData<ApiResponse<SiteSetting>>(() => $api(`/public/site-settings`));

const { data: signatures } = await useAsyncData<Product[]>(async () => {
  const data = await $api<ApiPagedResponse<Product>>(`/public/products/signatures`);
  return data.data.content ?? [];
});

const { data: mainbannerData } = await useAsyncData<MainBanner[]>(async () => {
  const data = await $api<ApiPagedResponse<MainBanner>>(`/public/mainbanners`);
  return data.data.content ?? [];
});

const slides = ref(mainbannerData.value);

const { data: hightlightEvents } = await useAsyncData<Event[]>(async () => {
  const data = await $api<ApiPagedResponse<Event>>(`/public/events/main-events`, {
    method: `get`,
  });
  return data.data.content ?? [];
});

const text = ref(``);
const recommendedKeywords = computed<string[]>(() => {
  const raw = settings.value?.data.settings.recommendedKeywords;
  return raw ? JSON.parse(raw) : [];
});
</script>

<template>
  <section>
    <ClientOnly>
      <Carousel
        :autoplay="5000"
        :items-to-show="1"
        :pause-autoplay-on-hover="true"
        :transition="500"
        :wrap-around="true"
      >
        <Slide
          v-for="(slide, index) of slides"
          :key="index"
        >
          <NuxtLink
            class="w-full"
            :to="slide.linkUri"
          >
            <NuxtImg
              :alt="slide.title"
              :src="`${$config.public.apiBase}/files/${slide.imageId}`"
              class="max-h-[300px] w-full object-contain"
            />
          </NuxtLink>
        </Slide>
      </Carousel>
    </ClientOnly>
  </section>

  <section class="py-4 md:py-12">
    <div class="px-4 md:px-6">
      <div class="flex items-center justify-between">
        <h3 class="text-base font-semibold">
          이 달의 베스트 이벤트
        </h3>
        <NuxtLink to="/events">
          <img
            src="~/assets/icons/icon-general-arrow-long-right-black.svg"
            alt="이 달의 베스트 이벤트"
          >
        </NuxtLink>
      </div>
    </div>
    <div class="mt-6 min-w-0 max-w-screen-md px-4 md:px-6">
      <div class="flex gap-2 overflow-x-auto pb-4 max-md:scrollbar-hide md:gap-4 md:pb-6">
        <template
          v-for="(hightlightEvent, index) of hightlightEvents"
          :key="index"
        >
          <div class="grid h-44 w-64 flex-none grid-rows-[auto_1fr_auto] rounded-br-3xl rounded-tl-3xl bg-gray-100 p-6">
            <div class="text-sm font-semibold md:text-base">
              {{ hightlightEvent.title }}
            </div>
            <div
              :title="hightlightEvent.description"
              class="overflow-y-auto whitespace-break-spaces pt-1 text-[10px] text-gray-400 max-md:scrollbar-hide md:text-xs"
            >
              {{ hightlightEvent.description }}
            </div>
            <div class="flex items-end justify-between">
              <span class="text-base font-semibold md:text-base">
                {{ currency(hightlightEvent.minDiscountedPrice ?? 0) }}
              </span>
              <NuxtLink
                :to="`/events/${hightlightEvent.id}`"
                class="flex h-8 items-center gap-2 rounded-full bg-black px-4 text-white"
              >
                <span class="text-xs text-white">
                  더보기
                </span>
                <NuxtIcon
                  name="mdi:chevron-right"
                  size="16"
                />
              </NuxtLink>
            </div>
          </div>
        </template>
      </div>
    </div>
  </section>

  <section>
    <NuxtLink to="/membership">
      <img
        alt="MEMBERSHIP"
        src="/images/d916961e1b9093324b3585997b27aff8a562c665dcb2e8f768a3c4114a21adb5.webp"
        width="100%"
        class="h-20 object-cover md:h-auto"
      >
    </NuxtLink>
  </section>

  <section class="p-4 md:px-8 md:py-12">
    <div class="flex items-center justify-between">
      <h3 class="text-base font-semibold">
        시그니쳐 시술
      </h3>
      <NuxtLink to="/procedures/signatures">
        <img
          alt="시그니쳐 시술"
          src="~/assets/icons/icon-general-arrow-long-right-black.svg"
        >
      </NuxtLink>
    </div>
    <div class="mt-6 min-w-0 max-w-screen-md ">
      <div class="flex items-center gap-x-4 overflow-x-auto whitespace-nowrap text-sm text-gray-600 max-md:scrollbar-hide md:text-base">
        <span
          v-for="(signature, index) of signatures"
          :key="index"
        >
          {{ signature.name }}
        </span>
      </div>
    </div>
    <div class="mt-8">
      <label class="flex h-12 appearance-none items-center gap-4 rounded-full bg-gray-100 px-4">
        <input
          v-model="text"
          type="text"
          class="h-full grow bg-transparent text-xs font-medium focus:outline-none"
          placeholder="원하는 시술을 검색해 보세요."
          @keyup.enter="navigateTo(`/procedures?text=${text}`)"
        >
        <button class="h-6 w-6">
          <NuxtIcon
            class="text-gray-400"
            name="mdi:search"
            size="24"
          />
        </button>
      </label>
      <div class="mt-4 flex flex-wrap justify-center gap-x-4 gap-y-2 text-xs font-normal text-gray-400 md:gap-x-8 md:text-sm">
        <NuxtLink
          v-for="(recommendedKeyword, index) of recommendedKeywords"
          :key="index"
          :to="`/procedures?text=${recommendedKeyword}`"
          class="underline"
        >
          {{ recommendedKeyword }}
        </NuxtLink>
      </div>
    </div>
  </section>

  <section>
    <div class="h-48 bg-[url('/images/fa5540a16dd26b149ecafbe4ad6537d9cafc6b6d5abb827fa8972126f77eb8b1.webp')] bg-cover md:h-56 md:bg-auto md:bg-[position:-160px_-135px]">
      <NuxtLink
        class="flex h-full w-full items-center justify-end gap-4 px-8"
        href="https://youtube.com/@의사광수"
        target="_blank"
      >
        <span class="font-normal text-white">
          YouTube 바로가기
        </span>
        <img
          alt="유튜브 바로가기"
          class="w-12 md:w-16"
          src="~/assets/icons/youtube_social_icon_red.png"
        >
      </NuxtLink>
    </div>
  </section>

  <section class="space-y-4 p-4 md:px-6 md:py-12">
    <div class="flex justify-between">
      <h3 class="font-semibold">
        에이포의원 오시는 길
      </h3>
      <div>
        <NuxtLink to="/about">
          <img
            src="~/assets/icons/icon-general-arrow-long-right-black.svg"
            alt="에이포의원 오시는 길"
          >
        </NuxtLink>
      </div>
    </div>
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
    <img
      alt="오시는 길"
      class="mt-8"
      src="/images/a2eb585963aef5e327c2a7c9c1387018adb995c37fa78456f9fe5431a2963a14.webp"
      width="100%"
    >
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
  </section>

  <UiPopup />
</template>
