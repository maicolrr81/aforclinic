<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `시술 후 주의사항 - 에이포의원`,
  description: `시술 후 주의사항`,

  // Open Graph
  ogTitle: `시술 후 주의사항 - 에이포의원`,
  ogDescription: `시술 후 주의사항`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `시술 후 주의사항 - 에이포의원`,
  twitterDescription: `시술 후 주의사항`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});

const { data: aftercares } = await useAsyncData(() => $fetch(`/api/aftercares`));
</script>

<template>
  <article>
    <h1 class="border-b p-4 text-center text-xl font-bold md:p-8 md:text-3xl">
      시술 후 주의사항
    </h1>
    <HeadlessDisclosure
      v-for="aftercare of aftercares"
      v-slot="{ open }"
      :key="aftercare.name"
      as="section"
    >
      <HeadlessDisclosureButton class="flex w-full items-center justify-between border-x border-b p-3 md:p-6">
        <span
          class="truncate text-base md:text-lg"
          :title="aftercare.name"
        >
          {{ aftercare.name }}
        </span>
        <Transition
          name="fade"
          mode="out-in"
        >
          <NuxtIcon
            class="h-6 w-6 text-gray-500 transition md:h-8 md:w-8"
            :class="{ 'rotate-180': open }"
            name="mdi:chevron-down"
          />
        </Transition>
      </HeadlessDisclosureButton>

      <HeadlessDisclosurePanel
        as="ol"
        class="list-decimal space-y-2 border-x border-b bg-gray-50 p-4 text-sm font-light md:space-y-4 md:p-6 md:text-base"
      >
        <li
          v-for="(item, index) of aftercare.items"
          :key="index"
          class="ms-2 md:ms-6"
        >
          <template v-if="typeof item === 'string'">
            {{ item }}
          </template>
          <ol
            v-else
            class="mt-2 list-inside list-disc"
          >
            {{ item.text }}
            <li
              v-for="(item2, index2) of item.items"
              :key="index2"
              class="mt-2"
            >
              {{ item2 }}
            </li>
          </ol>
        </li>
      </HeadlessDisclosurePanel>
    </HeadlessDisclosure>
  </article>
</template>
