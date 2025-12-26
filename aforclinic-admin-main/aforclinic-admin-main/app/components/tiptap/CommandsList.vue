<script setup lang="ts">
const props = defineProps<{
  items: TiptapSuggestionItem[];
  command: (item: TiptapSuggestionItem) => void;
}>();

const selectedIndex = ref(0);
const container = templateRef<HTMLElement | null>(`container`, null);
const itemRefs = ref<(HTMLElement | null)[]>([]);

function setItemRef(el: HTMLElement | null, index: number) {
  itemRefs.value[index] = el;
}

function selectItem(index: number) {
  if (props.items[index]) {
    props.command(props.items[index]);
  }
}

function scrollToSelected() {
  nextTick(() => {
    const selectedElement = itemRefs.value[selectedIndex.value];
    if (selectedElement && container.value) {
      const _container = container.value;
      const elementTop = selectedElement.offsetTop;
      const elementBottom = elementTop + selectedElement.offsetHeight;
      const containerTop = _container.scrollTop;
      const containerBottom = containerTop + _container.clientHeight;
      if (elementTop < containerTop) {
        _container.scrollTop = elementTop;
      }
      else if (elementBottom > containerBottom) {
        _container.scrollTop = elementBottom - _container.clientHeight;
      }
    }
  });
}

function onKeyDown(event: KeyboardEvent): boolean {
  if (event.key === 'ArrowUp') {
    selectedIndex.value
      = (selectedIndex.value - 1 + props.items.length) % props.items.length;
    scrollToSelected();
    return true;
  }
  if (event.key === 'ArrowDown') {
    selectedIndex.value = (selectedIndex.value + 1) % props.items.length;
    scrollToSelected();
    return true;
  }
  if (event.key === 'Enter') {
    selectItem(selectedIndex.value);
    return true;
  }
  return false;
}

watch(
  () => props.items,
  () => {
    selectedIndex.value = 0;
    scrollToSelected();
  },
);

onMounted(() => {
  scrollToSelected();
});

defineExpose({ onKeyDown });
</script>

<template>
  <div
    ref="container"
    class="flex max-h-80 w-72 flex-col flex-nowrap items-start justify-start overflow-y-auto overflow-x-hidden rounded-lg border border-gray-200 bg-white py-2 shadow-lg"
  >
    <div class="px-3 py-2 text-sm font-medium text-gray-500">
      액션
    </div>
    <button
      v-for="(item, index) in items"
      :key="item.name"
      :ref="(el) => setItemRef(el as HTMLElement | null, index)"
      class="flex w-full items-center gap-3 px-3 py-2 transition-colors duration-150 hover:bg-gray-100"
      :class="{ 'bg-gray-100': index === selectedIndex }"
      @click="selectItem(index)"
    >
      <div
        class="flex h-8 w-8 items-center justify-center rounded-sm bg-gray-200"
      >
        <NuxtIcon
          :name="item.icon"
          class="h-5 w-5 text-gray-600"
        />
      </div>
      <div class="flex flex-col text-left">
        <span class="text-sm font-medium text-gray-900">{{ item.name }}</span>
        <span class="text-xs text-gray-500">{{ item.description }}</span>
      </div>
    </button>
  </div>
</template>
