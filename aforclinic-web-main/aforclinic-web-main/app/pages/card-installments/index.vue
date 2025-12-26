<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `카드사별 무이자 할부 혜택 - 에이포의원`,
  description: `5만원 이상 결제 시 할부 혜택 적용`,

  // Open Graph
  ogTitle: `카드사별 무이자 할부 혜택 - 에이포의원`,
  ogDescription: `5만원 이상 결제 시 할부 혜택 적용`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `카드사별 무이자 할부 혜택 - 에이포의원`,
  twitterDescription: `5만원 이상 결제 시 할부 혜택 적용`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const [DefineCardInstallmentDisclosure, CardInstallmentDisclosure] = createReusableTemplate();
</script>

<template>
  <DefineCardInstallmentDisclosure v-slot="{ name, image, $slots }">
    <HeadlessDisclosure
      v-slot="{ open }"
      as="div"
      class="rounded-lg border"
    >
      <HeadlessDisclosureButton class="flex h-12 w-full items-center gap-x-2 px-4 py-2 md:h-16 md:gap-x-4 md:px-8 md:py-4">
        <img
          :alt="name"
          :src="image"
          width="96"
          class="w-16 md:w-24"
        >
        <div class="flex-1 text-start text-xs font-medium text-gray-700 md:text-sm">
          {{ name }}
        </div>
        <NuxtIcon
          class="transition"
          :class="{
            'rotate-180': open,
          }"
          name="mdi:chevron-down"
          size="24"
        />
      </HeadlessDisclosureButton>
      <HeadlessDisclosurePanel class="px-4 pb-4 md:px-8">
        <ul class="space-y-2 border-t pt-4 text-xs md:space-y-4 md:text-sm">
          <Component :is="$slots.default" />
        </ul>
      </HeadlessDisclosurePanel>
    </HeadlessDisclosure>
  </DefineCardInstallmentDisclosure>

  <article>
    <section class="px-4 py-8 md:p-8">
      <h2 class="text-lg font-bold md:text-xl">
        카드사별<br>
        무이자 할부 혜택
      </h2>
      <p class="mt-4 text-xs text-gray-400 md:text-sm">
        5만원 이상 결제 시 할부 혜택 적용
      </p>
    </section>

    <section class="space-y-2 px-4 pb-4 md:px-8 md:pb-8">
      <CardInstallmentDisclosure
        name="국민카드 (~ 9/30)"
        image="/images/1ef1bcf9516ef1cef8d96633a7311fcfff44aa5f91c3db4227e2ca01779ec50f.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 3개월
        </li>
        <li>
          6개월 (1 ~ 3회차 본인부담, 4 ~ 6회차 면제)
        </li>
        <li>
          10개월 (1 ~ 5회차 본인부담, 6 ~ 10회차 면제)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="신한카드 (~ 12/31)"
        image="/images/212d5bf30715c11f658b9a2f8299bb2dfba2e82fa079f68215b38d0f4739a57e.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 3개월
        </li>
        <li>
          10개월 (1 ~ 4회차 본인부담, 5 ~ 10회차 면제)
        </li>
        <li>
          제외: KB비씨카드, NH농협카드, 전북, 사업자카드
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="BC카드 (~ 9/30)"
        image="/images/6135ddc78c64beb52323d8c989c25a4d08c6d0335be8711c06a91a22904346e8.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 3개월
        </li>
        <li>
          10개월 (1 ~ 4회차 본인부담, 5 ~ 10회차 면제)
        </li>
        <li>
          12개월 (1 ~ 5회차 본인부담, 6 ~ 12회차 면제)
        </li>
        <li>
          (수협카드는 개인만 가능, 2 ~ 4개월 무이자)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="현대카드 (~ 12/31)"
        image="/images/67fea7d3727f8b00a6425595d3974199ce29881891a9fe019670b46c62fd5eb2.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 3개월
        </li>
        <li>
          10개월 (1 ~ 5회차 본인부담, 6 ~ 10회차 면제)
        </li>
        <li>
          12개월 (1 ~ 6회차 본인부담, 7 ~ 12회차 면제)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="삼성카드 (~ 12/31)"
        image="/images/becca408f8786a5ecb009d7850ed84aaa62832d5bd93f0c8f7cacda85ab0e521.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 5개월
        </li>
        <li>
          7개월 (1 ~ 3회차 본인부담, 4 ~ 7회차 면제)
        </li>
        <li>
          11개월 (1 ~ 5회차 본인부담, 6 ~ 11회차 면제)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="농협카드 (~ 12/31)"
        image="/images/56a7eac06e3a4aeaa43d0c50a9ef8c1c491370e4601c1a03d613e33913754032.svg"
      >
        <li class="font-bold">
          5만원 이상 1 ~ 6개월
        </li>
        <li>
          7개월 ~ 10개월 (1 ~ 3회차 본인부담, 나머지 면제)
        </li>
        <li>
          12개월 (1 ~ 4회차 본인부담, 나머지 면제)
        </li>
        <li>
          18개월 (1 ~ 5회차 본인부담, 나머지 면제)
        </li>
        <li>
          24개월 (1 ~ 6회차 본인부담, 나머지 면제)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="우리카드 (~ 9/30)"
        image="/images/1515e33e9ce222fdfa66c73bd53326accb26dbe780c77fbe645337ae660aa43a.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 4개월
        </li>
        <li>
          10개월 (1 ~ 4회차 본인부담, 나머지 면제)
        </li>
        <li>
          12개월 (1 ~ 5회차 본인부담, 나머지 면제)
        </li>
      </CardInstallmentDisclosure>

      <CardInstallmentDisclosure
        name="하나카드 (~ 12/31)"
        image="/images/2231d855933dd0cb477ee064bc7fc585ef4d5c4de24394b37646f94a3e24a464.svg"
      >
        <li class="font-bold">
          5만원 이상 2 ~ 3개월
        </li>
        <li>
          10개월 (1 ~ 4회차 본인부담, 나머지 면제)
        </li>
        <li>
          12개월 (1 ~ 5회차 본인부담, 나머지 면제)
        </li>
        <li>
          제외 : 하나BC, 하나토스제휴카드
        </li>
      </CardInstallmentDisclosure>
    </section>

    <p class="p-4 text-xs text-gray-400 md:p-8 md:text-sm">
      * 자세한 사항은 상담 문의하시면 자세히 안내드리겠습니다.
    </p>
  </article>
</template>
