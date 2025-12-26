<script setup lang="ts">
definePageMeta({
  headThemeColor: `sensitive`,
  mainClass: `!pt-0`,
  middleware: [`requires-auth`],
});

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `VIP 멤버십 - 에이포의원`,
  description: `쓸수록 커지는 PREMIUM BENEFIT, 결제 금액별 VIP 멤버십 등급에 따라 다양한 혜택을 드립니다.`,

  // Open Graph
  ogTitle: `VIP 멤버십 - 에이포의원`,
  ogDescription: `쓸수록 커지는 PREMIUM BENEFIT, 결제 금액별 VIP 멤버십 등급에 따라 다양한 혜택을 드립니다.`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `VIP 멤버십 - 에이포의원`,
  twitterDescription: `쓸수록 커지는 PREMIUM BENEFIT, 결제 금액별 VIP 멤버십 등급에 따라 다양한 혜택을 드립니다.`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const [DefineMembershipDisclosure, MembershipDisclosure] = createReusableTemplate();
</script>

<template>
  <section class="relative h-dvh">
    <div class="h-full bg-[url('/images/dfa1c4c5dbc903450bcb311492522cc81d91f0d2c4aeb49c74e8530d07e0e702.jpg')] bg-cover bg-left-top">
      <h1 class="absolute left-8 top-1/2 -translate-y-1/2 whitespace-pre-line text-xl md:left-12 md:text-3xl">
        <p class="font-bold">
          VIP
        </p>
        MEMBERSHIP
      </h1>
      <div class="absolute bottom-32 left-8 w-[calc(100%-4rem)] space-y-6 whitespace-pre-line text-base leading-normal md:left-12 md:text-lg">
        <ul class="w-full">
          <li class="flex items-center gap-2">
            <div class="flex flex-[3] items-center gap-2">
              <b>SILVER</b>
              <hr class="flex-1 border-t border-black">
            </div>
            <div class="flex-[2]">
              매월 주어지는
            </div>
          </li>
          <li class="flex items-center gap-2">
            <div class="flex flex-[3] items-center gap-2">
              <b>GOLD</b>
              <hr class="flex-1 border-t border-black">
            </div>
            <div class="flex-[2]">
              에이포 멤버십만의
            </div>
          </li>
          <li class="flex items-center gap-2">
            <div class="flex flex-[3] items-center gap-2">
              <b>VIP</b>
              <hr class="flex-1 border-t border-black">
            </div>
            <div class="flex-[2]">
              스페셜한 혜택을
            </div>
          </li>
          <li class="flex items-center gap-2">
            <div class="flex flex-[3] items-center gap-2">
              <b>VVIP</b>
              <hr class="flex-1 border-t border-black">
            </div>
            <div class="flex-[2]">
              누려보세요.
            </div>
          </li>
        </ul>
      </div>
      <div class="absolute bottom-20 left-1/2 -translate-x-1/2 text-sm font-light text-white">
        <div class="-ms-2 flex flex-col items-center justify-center">
          <span class="arrow" />
          <span class="arrow -mt-1" />
          <span class="arrow -mt-1" />
        </div>
      </div>
    </div>
  </section>

  <DefineMembershipDisclosure v-slot="{ color, name, $slots }">
    <HeadlessDisclosure
      v-slot="{ open }"
      as="div"
      class="rounded-lg border"
    >
      <HeadlessDisclosureButton class="flex w-full items-center gap-6 p-4 md:px-8 md:py-4">
        <NuxtIcon
          class="h-5 w-5 md:h-6 md:w-6"
          :class="color"
          name="ion:diamond"
        />
        <div
          class="flex-1 text-start text-base font-medium md:text-lg"
          :class="color"
        >
          {{ name }}
        </div>
        <NuxtIcon
          class="h-5 w-5 transition md:h-6 md:w-6"
          :class="{
            'rotate-180': open,
          }"
          name="mdi:chevron-down"
        />
      </HeadlessDisclosureButton>
      <HeadlessDisclosurePanel class="px-4 pb-4 md:px-8">
        <ul class="space-y-4 border-t pt-4 text-xs md:text-sm">
          <Component :is="$slots.default" />
        </ul>
      </HeadlessDisclosurePanel>
    </HeadlessDisclosure>
  </DefineMembershipDisclosure>

  <article class="min-h-dvh">
    <section class="px-4 py-8 md:p-8">
      <h2 class="text-lg font-bold md:text-xl">
        쓸수록 커지는<br>
        PREMIUM BENEFIT
      </h2>
      <p class="mt-4 text-xs text-gray-400 md:text-sm">
        결제 금액별 VIP 멤버십 등급에 따라<br>
        다양한 혜택을 드립니다.
      </p>
    </section>

    <section class="space-y-2 px-4 md:px-8">
      <MembershipDisclosure
        name="SILVER"
        color="text-blue-400"
      >
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:ticket"
            size="18"
          />
          <p>
            300만원
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:brightness-percent"
            size="18"
          />
          <p>
            5% 적립금
          </p>
        </li>
        <li class="flex items-center gap-4">
          <p class="indent-8 text-xs text-gray-500">
            유효기간: 1년
          </p>
        </li>
      </MembershipDisclosure>

      <MembershipDisclosure
        name="GOLD"
        color="text-yellow-400"
      >
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:ticket"
            size="18"
          />
          <p>
            500만원
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:brightness-percent"
            size="18"
          />
          <p>
            7% 적립금
          </p>
        </li>
        <li class="flex items-center gap-4">
          <p class="indent-8 text-xs text-gray-500">
            유효기간: 1년
          </p>
        </li>
      </MembershipDisclosure>

      <MembershipDisclosure
        name="VIP"
        color="text-purple-400"
      >
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:ticket"
            size="18"
          />
          <p>
            1000만원
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:brightness-percent"
            size="18"
          />
          <p>
            10% 적립금
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:injection"
            size="18"
          />
          <p>
            2회 피부관리 무료<br>
            (보톡스/아쿠아필/LDM/물광필)
          </p>
        </li>
        <li class="flex items-center gap-4">
          <p class="indent-8 text-xs text-gray-500">
            유효기간: 2년
          </p>
        </li>
      </MembershipDisclosure>

      <MembershipDisclosure
        name="VVIP"
        color="text-black"
      >
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:ticket"
            size="18"
          />
          <p>
            2000만원
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:brightness-percent"
            size="18"
          />
          <p>
            15% 적립금
          </p>
        </li>
        <li class="flex items-center gap-4">
          <NuxtIcon
            name="mdi:injection"
            size="18"
          />
          <p>
            5회 피부관리 무료<br>
            (보톡스/하이드라페이셜/LDM/라라필)
          </p>
        </li>
        <li class="flex items-center gap-4">
          <p class="indent-8 text-xs text-gray-500">
            유효기간: 3년
          </p>
        </li>
      </MembershipDisclosure>
    </section>

    <p class="p-4 text-xs text-gray-400 md:p-8 md:text-sm">
      * 자세한 사항은 상담 문의하시면 자세히 안내드리겠습니다.
    </p>
  </article>
</template>
