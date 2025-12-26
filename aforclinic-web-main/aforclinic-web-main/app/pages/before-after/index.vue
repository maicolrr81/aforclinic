<script setup lang="ts">
import type { BeforeAfterBanner } from '#shared/types/BeforeAfter';
import type { Category } from '#shared/types/Category';

definePageMeta({ middleware: [`requires-auth`] });

const { $api } = useNuxtApp();
const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `전후사진 - 에이포의원`,
  description: `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`,

  // Open Graph
  ogTitle: `전후사진 - 에이포의원`,
  ogDescription: `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `전후사진 - 에이포의원`,
  twitterDescription: `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

// 배너
const { data: banners } = await useAsyncData<BeforeAfterBanner[]>(async () => {
  const data = await $api<ApiPagedResponse<BeforeAfterBanner>>(`/public/beforeafters/banners`);
  return data.data.content ?? [];
});

// 태그
const { data: tags } = await useAsyncData<string[]>(async () => {
  const data = await $api<ApiResponse<string[]>>(`/public/beforeafters/tags`);
  return data.data ?? [];
});

// 카테고리
const { data: categories } = await useAsyncData<Category[]>(async () => {
  const data = await $api<ApiResponse<Category[]>>(`/public/categories`);
  return data.data ?? [];
});

const selectedTag = ref<string>();
const selectedCategory = ref<string>();
const page = ref(1);
const size = ref(6);

const params = computed(() => ({
  tag: selectedTag.value,
  categoryId: selectedCategory.value,
  page: page.value,
  size: size.value,
}));

const { data } = await useAsyncData<ApiPagedResponse<BeforeAfter>>(() => $api(`/public/beforeafters`, {
  method: `get`,
  params: params.value,
}), {
  watch: [() => params.value],
});

const items = computed(() => data.value?.data.content ?? []);
const isEmpty = computed(() => items.value.length === 0);
const totalPages = computed(() => data.value?.data.page?.totalPages ?? 0);
const isFirst = computed(() => page.value === 1);
const isLast = computed(() => page.value === totalPages.value);

const selectedItem = ref<BeforeAfter>();
const isOpen = ref(false);

function getEmbedUrl(url: string): string {
  try {
    const parsedUrl = new URL(url);
    const hostname = parsedUrl.hostname.replace('www.', '');
    const pathnameParts = parsedUrl.pathname.split('/').filter(Boolean); // ['shorts', 'abc123'] 등

    // YouTube - youtu.be short link
    if (hostname === 'youtu.be' && pathnameParts.length === 1) {
      return `https://www.youtube.com/embed/${pathnameParts[0]}`;
    }

    // YouTube - full URL with v param
    if (hostname === 'youtube.com' || hostname === 'm.youtube.com') {
      const videoId = parsedUrl.searchParams.get('v');
      if (videoId) {
        return `https://www.youtube.com/embed/${videoId}`;
      }

      // Shorts URL: /shorts/abc123
      if (pathnameParts[0] === 'shorts' && pathnameParts[1]) {
        return `https://www.youtube.com/embed/${pathnameParts[1]}`;
      }
    }

    // Instagram - post or reel
    if (hostname === 'instagram.com') {
      if ((pathnameParts[0] === 'p' || pathnameParts[0] === 'reel') && pathnameParts[1]) {
        return `https://www.instagram.com/reel/${pathnameParts[1]}/embed`;
      }
    }

    return url;
  }
  catch {
    return url;
  }
}

function onClickTag(tag?: string) {
  selectedTag.value = tag;
}

function onClickCategory(categoryId: string) {
  selectedCategory.value = selectedCategory.value === categoryId
    ? undefined
    : categoryId;
}
</script>

<template>
  <section class="space-y-2 p-4 text-center md:space-y-4 md:p-8">
    <h1 class="text-xl md:text-3xl">
      예뻐졌어요 ♥
    </h1>
    <p class="text-sm md:text-base">
      실제 시술을 받은 후기 및 전후사진입니다.
    </p>
  </section>

  <section class="max-w-screen-md py-4 ps-4 md:px-8">
    <div class="flex gap-4 overflow-x-auto">
      <template
        v-for="(banner, index) of banners"
        :key="index"
      >
        <NuxtImg
          v-if="banner.mediaType === `IMAGE`"
          class="aspect-[9/16] w-48 rounded-md md:w-72"
          :src="banner.mediaContent"
          loading="lazy"
        />
        <iframe
          v-else-if="banner.mediaType === `VIDEO`"
          class="aspect-[9/16] w-48 rounded-md md:w-72"
          :src="getEmbedUrl(banner.mediaContent)"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
        />
      </template>
    </div>
  </section>

  <section class="space-y-4 px-4 py-4 md:px-8">
    <div class="flex gap-x-2 overflow-x-auto md:flex-wrap md:gap-4 md:overflow-x-visible">
      <button
        class="flex items-center justify-center whitespace-nowrap rounded-full border bg-gray-100 px-4 py-1 text-sm font-light hover:bg-black hover:text-white"
        :class="{
          '!bg-black text-white': !selectedTag,
        }"
        @click="onClickTag()"
      >
        #전체보기
      </button>
      <button
        v-for="(tag, index) of tags"
        :key="index"
        class="flex whitespace-nowrap rounded-full border bg-gray-100 px-2 py-1 text-xs font-light hover:bg-black hover:text-white md:text-sm"
        :class="{
          '!bg-black text-white': selectedTag === tag,
        }"
        @click="onClickTag(tag)"
      >
        #{{ tag }}
      </button>
    </div>

    <div class="flex flex-wrap gap-2 md:gap-4">
      <button
        v-for="(category, index) of categories"
        :key="index"
        class="flex rounded-full border bg-gray-100 px-2 py-1 text-xs font-light hover:bg-black hover:text-white md:px-4 md:text-sm"
        :class="{
          '!bg-black text-white': selectedCategory === category.id,
        }"
        @click="onClickCategory(category.id)"
      >
        {{ category.name }}
      </button>
    </div>
  </section>

  <section class="py-4">
    <div
      v-if="!isEmpty"
      class="flex items-center gap-2"
    >
      <button
        class="h-7 w-7 md:h-9 md:w-9"
        :class="{
          'pointer-events-none text-gray-400': isFirst,
        }"
        @click="page -= 1"
      >
        <NuxtIcon
          name="mdi:chevron-left"
          class="h-full w-full font-semibold"
        />
      </button>
      <div class="grid flex-1 grid-cols-2 grid-rows-3 gap-2 md:grid-cols-3 md:grid-rows-2 md:gap-4">
        <div
          v-for="(item, index) of items"
          :key="index"
          class="flex aspect-[4/5] w-full flex-col justify-between border"
        >
          <NuxtImg
            :src="`${$config.public.apiBase}/files/${item.image}`"
            class="h-full w-full border-none object-cover hover:cursor-pointer"
            loading="lazy"
            @click="{
              selectedItem = item;
              isOpen = true;
            }"
          />
          <div class="col-span-2 flex flex-col justify-center p-2 text-xs text-gray-800 md:text-sm">
            <h3 class="font-medium">
              {{ item.title }}
            </h3>
          </div>
        </div>
      </div>

      <button
        class="h-7 w-7 md:h-9 md:w-9"
        :class="{
          'pointer-events-none text-gray-400': isLast,
        }"
        @click="page += 1"
      >
        <NuxtIcon
          name="mdi:chevron-right"
          class="h-full w-full font-semibold"
        />
      </button>
    </div>
  </section>

  <UiModal
    v-model="isOpen"
    panel-class="!max-w-[600px]"
    no-footer
    @close="selectedItem = undefined"
  >
    <section class="space-y-2 text-center md:space-y-4">
      <h3 class="text-xl md:text-3xl">
        예뻐졌어요 ♥
      </h3>
      <p class="text-sm md:text-base">
        실제 시술을 받은 후기 및 전후사진입니다.
      </p>
    </section>

    <section class="mt-4">
      <div class="border">
        <div class="flex aspect-[4/5] w-full flex-col justify-between border">
          <NuxtImg
            :src="`${$config.public.apiBase}/files/${selectedItem?.image}`"
            class="h-full w-full border-none object-cover hover:cursor-pointer"
            loading="lazy"
          />
          <div class="items-center p-2 text-xs text-gray-800 md:text-sm">
            <h3 class="font-medium">
              {{ selectedItem?.title }}
            </h3>
            <p>
              {{ selectedItem?.description }}
            </p>
          </div>
        </div>
      </div>
    </section>
  </UiModal>
</template>
