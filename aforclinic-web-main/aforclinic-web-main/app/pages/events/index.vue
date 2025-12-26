<script setup lang="ts">
import type { Event } from '#shared/types/Event';

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `이벤트 - 에이포의원`,
  description: `다양한 이벤트 혜택으로 한껏 예뻐지세요.`,

  // Open Graph
  ogTitle: `이벤트 - 에이포의원`,
  ogDescription: `다양한 이벤트 혜택으로 한껏 예뻐지세요.`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `이벤트 - 에이포의원`,
  twitterDescription: `다양한 이벤트 혜택으로 한껏 예뻐지세요.`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const { $api } = useNuxtApp();
const dayjs = useDayjs();

const { data } = await useAsyncData<ApiPagedResponse<Event>>(() => $api(`/public/events`));
const items = computed(() => data.value?.data.content ?? []);

const now = dayjs();

function remaining(date: string) {
  const diff = dayjs(date, `YYYY-MM-DD`).diff(now, `day`);
  if (diff < 0) {
    return `마감`;
  }
  return `${diff}일 남음`;
}
</script>

<template>
  <h1 class="p-4 text-xl font-bold md:p-8">
    다양한 EVENT 혜택으로<br>
    한껏 예뻐지세요.
  </h1>
  <section class="flex flex-col gap-8 px-4 pb-4 md:px-8 md:pb-8">
    <div
      v-for="(item, index) of items"
      :key="index"
      class="flex flex-col rounded bg-gray-100"
    >
      <NuxtImg
        v-if="item.imageId"
        :src="`${$config.public.apiBase}/files/${item.imageId}`"
        class="aspect-video w-full object-cover object-top"
        loading="lazy"
        format="webp"
      />
      <div class="relative flex flex-col gap-y-8 bg-gray-100 px-4 py-6 md:px-8">
        <UiBadge class="absolute -top-3 left-3">
          EVENT
        </UiBadge>
        <div>
          <h2 class="font-bold">
            {{ item.title }}
          </h2>
          <p class="mt-2 whitespace-break-spaces text-xs font-medium text-gray-400">
            {{ item.description }}
          </p>
        </div>
        <div class="flex items-end justify-between">
          <div class="flex flex-col font-medium">
            <span class="mb-1.5 text-[10px] text-[#9d8077] md:text-xs">
              ~  {{ item.endDate }} ({{ remaining(item.endDate) }})
            </span>
            <span class="text-gray-700">
              <span class="text-base font-medium md:text-lg lg:text-xl">
                <template v-if="item.minDiscountedPrice">
                  {{ currency(item.minDiscountedPrice) }}
                  ~
                </template>
                <template v-if="item.maxDiscountedPrice">
                  {{ currency(item.maxDiscountedPrice) }}
                </template>
              </span>
              <span class="text-xs">
                원
              </span>
            </span>
          </div>
          <NuxtLink
            :to="`/events/${item.id}`"
            class="flex h-8 items-center gap-2 rounded-full bg-black px-4 text-white"
          >
            <span class="text-xs ">
              더보기
            </span>
            <NuxtIcon
              name="mdi:chevron-right"
              size="16"
            />
          </NuxtLink>
        </div>
      </div>
    </div>
  </section>
</template>
