<script setup lang="ts">
const { theme } = useAppConfig();
const { $drawer } = useNuxtApp();

const auth = useAuthStore();
const user = useUserStore();
const route = useRoute();

const lookup = computed(() => {
  const result: Record<string, string[]> = {};
  const traverse = (items: Array<Omit<Navigation, `link`> | Omit<Navigation, `items`>>, texts: string[] = []) => {
    for (const item of items) {
      const newTexts = [...texts, item.text];
      if (`link` in item) { result[item.link] = newTexts; }
      if (`items` in item) { traverse((item).items, newTexts); }
    }
  };
  traverse(theme.drawer.navs);
  return result;
});

const breadcrumbs = computed(() => {
  const matchedKey = Object.keys(lookup.value)
    .reduce((longest, key) => (route.path.startsWith(key) && key.length > longest.length ? key : longest), ``);
  return lookup.value[matchedKey] ?? [];
});

async function onLogout() {
  await auth.logout();
}
</script>

<template>
  <ClientOnly>
    <QHeader
      class="bg-secondary"
      height-hint="125"
    >
      <QToolbar class="h-[75px] px-8">
        <QToolbarTitle>
          <NuxtLink to="/">
            관리자
          </NuxtLink>
        </QToolbarTitle>
        <QSpace />
        <QBtn
          dense
          flat
          :label="user.data?.nickname || `사용자명`"
        />
        <QBtn
          class="ms-2"
          dense
          flat
          icon="logout"
          @click="onLogout"
        />
      </QToolbar>
      <QToolbar class="border bg-white px-8 text-black">
        <QBtn
          dense
          flat
          icon="menu"
          rounded
          @click="$drawer.toggle()"
        />
        <QBreadcrumbs
          class="px-4"
          active-color="black"
          separator=">"
        >
          <QBreadcrumbsEl
            v-for="breadcrumb of breadcrumbs"
            :key="breadcrumb"
          >
            {{ breadcrumb }}
          </QBreadcrumbsEl>
        </QBreadcrumbs>
      </QToolbar>
    </QHeader>
  </ClientOnly>
</template>
