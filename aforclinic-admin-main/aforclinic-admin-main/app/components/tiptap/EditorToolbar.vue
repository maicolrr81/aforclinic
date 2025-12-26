<script setup lang="ts">
import type { Editor } from '@tiptap/core';

const props = defineProps<{ editor: Editor }>();

const showContentTypeMenu = ref(false);
const showColorMenu = ref(false);

function shouldShow(props: TiptapShouldShowProps): boolean {
  const { state, from } = props;
  const { doc, selection } = state;
  const { empty } = selection;

  if (empty) { return false; }

  const nodeAtSelection = doc.nodeAt(from);
  if (nodeAtSelection && nodeAtSelection.type.name === 'image') {
    return false;
  }

  return true;
}

const fixeds: TiptapMenuAction[] = [
  {
    name: 'paragraph',
    label: '본문',
    icon: 'mdi:format-paragraph',
    command: () => props.editor?.chain().focus().setParagraph().run(),
    isActive: () => props.editor?.isActive(`paragraph`),
  },
  {
    name: 'heading 1',
    label: '제목 1',
    icon: 'mdi:format-header-1',
    command: () => props.editor?.chain().focus().toggleHeading({ level: 1 }).run(),
    isActive: () => props.editor?.isActive(`heading`, { level: 1 }),
  },
  {
    name: 'heading 2',
    label: '제목 2',
    icon: 'mdi:format-header-2',
    command: () => props.editor?.chain().focus().toggleHeading({ level: 2 }).run(),
    isActive: () => props.editor?.isActive(`heading`, { level: 2 }),
  },
  {
    name: 'heading 3',
    label: '제목 3',
    icon: 'mdi:format-header-3',
    command: () => props.editor?.chain().focus().toggleHeading({ level: 3 }).run(),
    isActive: () => props.editor?.isActive(`heading`, { level: 3 }),
  },
  {
    name: 'text left',
    label: '왼쪽정렬',
    icon: 'mdi:align-horizontal-left',
    command: () => props.editor?.chain().focus().setTextAlign('left').run(),
    isActive: () => props.editor?.isActive({ textAlign: `left` }),
  },
  {
    name: 'text center',
    label: '가운데정렬',
    icon: 'mdi:align-horizontal-center',
    command: () => props.editor?.chain().focus().setTextAlign('center').run(),
    isActive: () => props.editor?.isActive({ textAlign: `center` }),
  },
  {
    name: 'text right',
    label: '오른쪽정렬',
    icon: 'mdi:align-horizontal-right',
    command: () => props.editor?.chain().focus().setTextAlign('right').run(),
    isActive: () => props.editor?.isActive({ textAlign: `right` }),
  },
  {
    name: 'bullet list',
    label: '글머리 기호',
    icon: 'mdi:format-list-bulleted',
    command: () => props.editor?.chain().focus().toggleBulletList().run(),
    isActive: () => props.editor?.isActive(`bulletList`),
  },
  {
    name: 'ordered list',
    label: '번호 매기기',
    icon: 'mdi:format-list-numbered',
    command: () => props.editor?.chain().focus().toggleOrderedList().run(),
    isActive: () => props.editor?.isActive(`orderedList`),
  },
  // {
  //   name: 'code block',
  //   label: '코드 블록',
  //   icon: 'mdi:code',
  //   command: () => props.editor?.chain().focus().toggleCodeBlock().run(),
  //   isActive: () => props.editor?.isActive(`codeBlock`),
  // },
  {
    name: 'blockquote',
    label: '인용구',
    icon: 'mdi:format-quote-close',
    command: () => props.editor?.chain().focus().toggleBlockquote().run(),
    isActive: () => props.editor?.isActive(`blockquote`),
  },
  // {
  //   name: 'table',
  //   label: '테이블',
  //   icon: 'mdi:table',
  //   command: () => props.editor?.chain().focus().toggleBlockquote().run(),
  //   isActive: () => props.editor?.isActive(`blockquote`),
  // },
  {
    name: 'image',
    label: '이미지',
    icon: 'mdi:image',
    command: async () => {
      const { open, onChange } = useFileUpload({
        accept: `image/*`,
        directory: false,
        multiple: false,
      });
      open();
      onChange(image => props.editor
        .chain()
        .focus()
        .setImage({ src: image.src })
        .insertContent({ type: `paragraph` })
        .run());
    },
    // command: async ({ editor, range }) => {
    //   const { open, onChange } = useFileUpload({
    //     accept: `image/*`,
    //     directory: false,
    //   });
    //   open();
    //   onChange((...newFiles) => {
    //     newFiles.forEach((newFile, index) => {
    //       if (index > 0) {
    //         editor
    //           .chain()
    //           .focus()
    //           .setImage({ src: newFile.src })
    //           .run();
    //       }
    //       else {
    //         editor
    //           .chain()
    //           .focus()
    //           .deleteRange(range)
    //           .setImage({ src: newFile.src })
    //           .run();
    //       }
    //     });
    //   });
    // },
  },
];

const bubbles: TiptapMenuAction[] = [
  {
    name: 'bold',
    label: '굵게',
    icon: 'mdi:format-bold',
    command: () => props.editor?.chain().focus().toggleBold().run(),
    isActive: () => props.editor?.isActive(`bold`),
  },
  {
    name: 'italic',
    label: '기울임꼴',
    icon: 'mdi:format-italic',
    command: () => props.editor?.chain().focus().toggleItalic().run(),
    isActive: () => props.editor?.isActive(`italic`),
  },
  {
    name: 'underline',
    label: '밑줄',
    icon: 'mdi:format-underline',
    command: () => props.editor?.chain().focus().toggleUnderline().run(),
    isActive: () => props.editor?.isActive(`underline`),
  },
  {
    name: 'strike',
    label: '취소선',
    icon: 'mdi:format-strikethrough',
    command: () => props.editor?.chain().focus().toggleStrike().run(),
    isActive: () => props.editor?.isActive(`strike`),
  },
];

const colors: TiptapTextColor[] = [
  { name: '검정', value: '#000000' },
  { name: '회색', value: '#808080' },
  { name: '빨강', value: '#FF0000' },
  { name: '주황', value: '#FFA500' },
  { name: '노랑', value: '#FFFF00' },
  { name: '초록', value: '#008000' },
  { name: '파랑', value: '#0000FF' },
  { name: '보라', value: '#800080' },
  { name: '분홍', value: '#FFC0CB' },
  { name: '하늘', value: '#87CEEB' },
];

function toggleColorMenu(): void {
  showColorMenu.value = !showColorMenu.value;
  showContentTypeMenu.value = false;
}

function setTextColor(color: string): void {
  props.editor?.chain().focus().setColor(color).run();
  showColorMenu.value = false;
}
</script>

<template>
  <div class="sticky top-[125px] z-[1] flex items-center justify-center space-x-2 border-b bg-gray-50 p-3">
    <button
      v-for="fixed in fixeds"
      :key="fixed.name"
      type="button"
      :class="{
        'bg-gray-300/80 text-gray-900': fixed.isActive?.(),
      }"
      :title="fixed.label"
      class="grid h-8 w-8 place-items-center rounded hover:bg-gray-300/80"
      @click="fixed.command()"
    >
      <NuxtIcon
        :name="fixed.icon"
        class="h-5 w-5"
      />
    </button>
  </div>
  <TiptapBubbleMenu
    v-if="editor"
    :editor="editor"
    :tippy-options="{ duration: 100 }"
    :should-show="shouldShow"
    class="mb-2.5 flex space-x-2 rounded-md bg-white p-1 shadow-md"
  >
    <div class="flex items-center space-x-2">
      <button
        v-for="bubble in bubbles"
        :key="bubble.name"
        type="button"
        :class="{
          'bg-gray-300/80 text-gray-900': bubble.isActive?.(),
        }"
        :title="bubble.label"
        class="grid h-8 w-8 place-items-center rounded hover:bg-gray-300/80"
        @click="bubble.command()"
      >
        <NuxtIcon
          :name="bubble.icon"
          class="h-5 w-5"
        />
      </button>
    </div>
    <div class="relative flex items-center">
      <button
        :class="{ 'bg-gray-300/80 text-gray-900': showColorMenu }"
        class="grid h-8 w-8 place-items-center rounded hover:bg-gray-300/80"
        type="button"
        title="Text color"
        @click="toggleColorMenu"
      >
        <NuxtIcon
          name="mdi:format-color-text"
          class="h-5 w-5"
        />
      </button>
      <div
        v-if="showColorMenu"
        class="absolute left-0 top-full z-10 w-20 rounded-md bg-white py-1.5 shadow-md"
      >
        <button
          v-for="color in colors"
          :key="color.name"
          type="button"
          :class="{
            'bg-gray-300/80': editor.isActive('textStyle', {
              color: color.value,
            }),
          }"
          class="flex w-full items-center rounded px-3 py-2 text-left hover:bg-gray-300/80"
          @click="setTextColor(color.value)"
        >
          <span
            class="mr-2 h-4 w-4 rounded-full border-2 border-gray-200"
            :style="{ backgroundColor: color.value }"
          />
          <span class="text-sm font-medium text-gray-600">{{
            color.name
          }}</span>
        </button>
      </div>
    </div>
  </TiptapBubbleMenu>
</template>
