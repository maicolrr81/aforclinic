<script setup lang="ts">
import type { Editor } from '@tiptap/core';
import { NodeViewWrapper } from '@tiptap/vue-3';

const props = defineProps<{
  editor: Editor;
  node: { attrs: { src: string | undefined }; nodeSize: number };
  updateAttributes: (attrs: { src: string | undefined }) => void;
}>();

const containerRef = ref<HTMLElement | null>(null);
const initialImage = ref<string | null>(null);
const fileInput = ref<HTMLInputElement | null>(null);
const isSelected = ref(false);

const actionItems = computed(() => [
  {
    label: props.node.attrs.src ? '변경' : '추가',
    icon: props.node.attrs.src
      ? 'mdi:refresh'
      : 'mdi:upload',
    handler: replaceImage,
  },
  {
    label: '삭제',
    icon: 'mdi:trash',
    handler: removeImage,
  },
]);

function handleFileSelect(event: Event) {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];
  if (file) {
    initialImage.value = URL.createObjectURL(file);
  }
}

function onImageClick() {
  isSelected.value = !isSelected.value;
}

function replaceImage() {
  isSelected.value = false;
  props.updateAttributes({ src: undefined });
  initialImage.value = null;
  fileInput.value?.click();
}

function removeImage() {
  props.editor.commands.deleteSelection();
}

function handleClickOutside() {
  if (isSelected.value) {
    isSelected.value = false;
  }
}

function onDocumentClick(event: MouseEvent) {
  if (
    !containerRef.value
    || containerRef.value.contains(event.target as Node)
  ) {
    return;
  }
  handleClickOutside();
}

onMounted(() => {
  document.addEventListener('click', onDocumentClick);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', onDocumentClick);
});
</script>

<template>
  <NodeViewWrapper>
    <div ref="containerRef">
      <!-- 이미지가 업로드 되지 않은 상태: 업로드 버튼 표시 -->
      <div
        v-if="!initialImage && !props.node.attrs.src"
        class="relative"
      >
        <div
          class="shadow-xs flex h-32 w-full cursor-pointer flex-col items-center justify-center rounded-lg border border-gray-300/80 bg-white text-black"
          @click="onImageClick"
        >
          <input
            ref="fileInput"
            type="file"
            accept="image/*"
            class="hidden"
            @change="handleFileSelect"
          >
          <div class="grid h-8 w-8 place-items-center rounded-full bg-gray-300/30">
            <NuxtIcon
              name="mdi:upload"
              class="h-3.5 w-3.5 text-black"
            />
          </div>
          <p class="text-sm font-medium">
            이미지 업로드
          </p>
        </div>
        <TiptapImageActionMenu
          v-if="isSelected"
          :actions="actionItems"
        />
      </div>

      <!-- 초기 이미지가 있으면 단순 이미지 태그로 미리보기 -->
      <div
        v-else-if="initialImage && !props.node.attrs.src"
        class="relative text-center"
      >
        <img
          :src="initialImage"
          alt="업로드한 이미지 미리보기"
          class="object-contain transition-all"
          @click="onImageClick"
        >
        <TiptapImageActionMenu
          v-if="isSelected"
          :actions="actionItems"
        />
      </div>

      <!-- 이미 노드에 이미지 src가 있을 때 -->
      <div
        v-else
        class="relative text-center"
      >
        <img
          :src="props.node.attrs.src"
          alt="처리된 이미지"
          class="object-contain transition-all"
          :class="{ 'rounded-md ring-2 ring-gray-300/30 ring-offset-2': isSelected }"
          @click="onImageClick"
        >
        <TiptapImageActionMenu
          v-if="isSelected"
          :actions="actionItems"
        />
      </div>
    </div>
  </NodeViewWrapper>
</template>
