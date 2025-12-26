<script setup lang="ts">
const { theme } = useAppConfig();
const { $drawer } = useNuxtApp();

const navs = computed(() => theme.drawer.navs);
</script>

<template>
  <QDrawer
    v-model="$drawer.open"
    show-if-above
    side="left"
    bordered
  >
    <QScrollArea class="fit">
      <QList
        v-for="(nav, index) of navs"
        :key="index"
        class="border-b border-gray-200"
      >
        <QItemLabel
          header
          overline
        >
          {{ nav.text }}
        </QItemLabel>
        <QItem
          v-for="item of nav.items"
          :key="item.link"
          v-ripple
          :active="$route.path === item.link"
          :active-class="$style.active"
          clickable
          :to="item.link"
          :target="item.target"
          class="px-4"
        >
          <QItemSection
            avatar
            class="min-w-8"
          >
            <NuxtIcon
              v-if="item.icon"
              :name="item.icon"
              size="24"
            />
          </QItemSection>
          <QItemSection class="text-base font-medium">
            {{ item.text }}
          </QItemSection>
        </QItem>
      </QList>
    </QScrollArea>
  </QDrawer>
</template>

<style module>
.active {
  @apply bg-gray-100 font-black text-black;
}
</style>
