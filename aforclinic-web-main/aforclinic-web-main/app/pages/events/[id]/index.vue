<script setup lang="ts">
import type { Event } from '#shared/types/Event';

const { $api } = useNuxtApp();

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

const { data, error } = await useAsyncData<Partial<Event>>(async () => {
  const data = await $api<ApiResponse<Event>>(`/public/events/${route.params.id}`);
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

const dayjs = useDayjs();

const cart = useCartStore();

const title = computed(() => data.value?.title);
const description = computed(() => data.value?.description);
const endDate = computed(() => data.value?.endDate);

const imageId = computed(() => data.value?.imageId);

const minPrice = computed(() => data.value?.minDiscountedPrice);
const maxPrice = computed(() => data.value?.maxDiscountedPrice);

const items = computed(() => data.value?.products);

function remaining(date?: string) {
  const diff = dayjs(date, `YYYY-MM-DD`).diff(dayjs(), `day`);
  if (diff < 0) {
    return `마감`;
  }
  return `${diff}일 남음`;
}
</script>

<template>
  <section>
    <NuxtImg
      v-if="imageId"
      :src="`${$config.public.apiBase}/files/${imageId}`"
      class="w-full"
      loading="lazy"
    />
    <div class="relative flex flex-col gap-4 p-4 md:gap-8 md:px-8 md:py-6">
      <UiBadge class="absolute -top-3 left-3">
        EVENT
      </UiBadge>
      <div>
        <h2 class="font-bold">
          {{ title }}
        </h2>
        <p class="mt-2 whitespace-break-spaces text-xs font-medium text-gray-400">
          {{ description }}
        </p>
      </div>
      <div class="flex flex-col items-end justify-end">
        <span class="text-[10px] text-cyan-800 md:text-xs">
          ~  {{ endDate }} ({{ remaining(endDate) }})
        </span>
        <span class="text-gray-700">
          <span class="text-base font-medium md:text-lg lg:text-xl">
            <template v-if="minPrice">
              {{ currency(minPrice) }}
              ~
            </template>
            <template v-if="maxPrice">
              {{ currency(maxPrice) }}
            </template>
          </span>
        </span>
      </div>
    </div>
  </section>
  <section class="px-4 md:px-8">
    <div class="flex flex-col gap-6 pb-4 md:pb-8">
      <ProductCard
        v-for="item of items"
        :key="item.productId"
        v-model="cart.items"
        :product-id="item.productId"
        :product-name="item.productName"
        :description="item.productDescription"
        :adjusted-price="item.adjustedPrice"
        :discounted-price="item.discountedPrice"
        event
        :until="endDate"
      />
    </div>
  </section>
</template>
