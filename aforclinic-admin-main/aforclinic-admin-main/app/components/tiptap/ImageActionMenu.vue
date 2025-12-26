<script setup lang="ts">
defineProps<{
  actions: TiptapImageEditorActionItem[];
}>();
</script>

<template>
  <div
    class="absolute left-1/2 top-2 z-10 flex -translate-x-1/2 transform space-x-4 rounded-md border border-gray-300/30 bg-white px-1 py-0.5 shadow backdrop-blur-sm"
  >
    <button
      v-for="(action, index) in actions"
      :key="index"
      class="flex items-center space-x-2 rounded-md p-1.5 text-black transition hover:bg-gray-300/30"
      @click.stop="action.handler"
    >
      <NuxtIcon
        :name="action.icon"
        class="h-3.5 w-3.5"
      />
      <span class="text-sm">
        {{ action.label }}
      </span>
    </button>
  </div>
</template>
