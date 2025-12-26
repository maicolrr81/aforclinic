<script setup lang="ts">
const { $api } = useNuxtApp();
const runtimeConfig = useRuntimeConfig();
const route = useRoute();

const [DefineCategoryButton, CategoryButton] = createReusableTemplate();

const category = computed(() => route.params.category || ``);
const text = ref(route.query.text || ``);
const isEvent = computed(() => route.path.endsWith(`/events`));
const isSignature = computed(() => route.path.endsWith(`/signatures`));

const { data: items } = await useAsyncData<Category[]>(async () => {
  const data = await $api<ApiResponse<Category[]>>(`/public/categories`);
  return data.data ?? [];
});
const selectedItem = computed(() => items.value?.find(item => item.id === category.value));

// 라우트 변경 감지하여 입력값 동기화
watch(() => route.query, (newQuery) => {
  text.value = (newQuery.text) as string || ``;
}, {
  immediate: true,
});

// 조회
function updateQueryParams() {
  if (text.value) {
    navigateTo(`/procedures?text=${text.value}`);
  }
  else {
    navigateTo(`/procedures`);
  }
}

// SEO 설정
watch(selectedItem, (newValue) => {
  useSeoMeta({
    // Basic
    title: `${newValue?.name || `시술 목록`} - 에이포의원`,
    description: `${newValue?.description || `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`}`,

    // Open Graph
    ogTitle: `${newValue?.name || `시술 목록`} - 에이포의원`,
    ogDescription: `${newValue?.description || `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`}`,
    ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
    ogImageWidth: `198`,
    ogImageHeight: `60`,
    ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
    ogType: 'website',
    ogLocale: 'ko_KR',
    ogSiteName: '에이포의원',

    // Twitter
    twitterCard: `summary_large_image`,
    twitterTitle: `${newValue?.name || `시술 목록`} - 에이포의원`,
    twitterDescription: `${newValue?.description || `베스트 이벤트와 프리미엄 시술로 아름다움을 누려보세요.`}`,
    twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
  });
});
</script>

<template>
  <DefineCategoryButton v-slot="{ active, to, $slots }">
    <NuxtLink
      class="text-nowrap rounded-full border border-gray-300 bg-gray-100 px-4 py-2 text-center text-sm text-gray-800 hover:bg-gray-200 focus:bg-gray-100 md:px-6"
      :class="{
        'bg-sky-100 hover:bg-sky-200 focus:bg-sky-100': active,
      }"
      :to
    >
      <Component :is="$slots.default" />
    </NuxtLink>
  </DefineCategoryButton>

  <div class="container mx-auto grid min-h-screen w-auto max-w-screen-md grid-rows-[1fr_auto] bg-white pb-[3.5rem]">
    <TheHeader />
    <TheMain>
      <div class="space-y-4 p-4 md:p-8">
        <h1 class="text-xl font-bold">
          베스트 이벤트와<br>
          프리미엄 시술로 아름다움을 누려보세요.
        </h1>
        <fieldset class="py-4">
          <label class="flex h-12 appearance-none items-center gap-4 rounded-full px-8 ring-2 ring-sky-100">
            <input
              v-model="text"
              type="text"
              class="h-full grow bg-transparent text-xs font-medium focus:outline-none"
              placeholder="원하는 시술을 검색해 보세요."
              @keyup.enter="updateQueryParams()"
            >
            <button
              class="h-6 w-6"
              @click="updateQueryParams()"
            >
              <NuxtIcon
                class="text-gray-400"
                name="mdi:search"
                size="24"
              />
            </button>
          </label>
        </fieldset>
        <section>
          <div class="flex flex-nowrap gap-3 overflow-x-auto max-md:scrollbar-hide md:flex-wrap">
            <div class="flex basis-full gap-2">
              <CategoryButton
                class="flex-1 font-bold"
                to="/procedures/events"
                :active="isEvent"
              >
                이벤트
              </CategoryButton>
              <CategoryButton
                class="flex-1 font-bold"
                to="/procedures/signatures"
                :active="isSignature"
              >
                시그니쳐 시술
              </CategoryButton>
            </div>
            <CategoryButton
              v-for="(item, index) of items"
              :key="index"
              :to="`/procedures/${item.id}`"
              :active="selectedItem?.id === item.id"
            >
              {{ item.name }}
            </CategoryButton>
          </div>
        </section>
      </div>
      <slot />
    </TheMain>
    <TheFooter />
    <TheDock />
    <TheDrawer />
    <TheAside />
  </div>
</template>
