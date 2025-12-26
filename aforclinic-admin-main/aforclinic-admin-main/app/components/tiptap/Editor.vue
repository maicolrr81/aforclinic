<script setup lang="ts">
import TiptapColor from '@tiptap/extension-color';
import TiptapPlaceholder from '@tiptap/extension-placeholder';
import TiptapTextAlign from '@tiptap/extension-text-align';
import TiptapTextStyle from '@tiptap/extension-text-style';
import TiptapUnderline from '@tiptap/extension-underline';

// import Commands from './extensions/Commands';
// import suggestion from './extensions/Suggestion';

const modelValue = defineModel<string>({
  default: `<p></p>`,
});

const editor = useEditor({
  content: modelValue.value,
  extensions: [
    TiptapStarterKit.configure({
      heading: {
        levels: [1, 2, 3],
      },
    }),
    TiptapColor,
    TiptapImage.configure({
      inline: true,
    }),
    TiptapPlaceholder.configure({
      placeholder: `여기에 내용을 입력하세요.`,
    }),
    TiptapTextAlign.configure({
      types: [`heading`, `paragraph`],
      alignments: [`left`, `center`, `right`],
      defaultAlignment: `left`,
    }),
    TiptapTextStyle,
    TiptapUnderline,
    // Commands.configure({
    //   suggestion,
    // }),
  ],
  onUpdate: ({ editor }) => {
    modelValue.value = editor.getHTML();
  },
});

watch(modelValue, (newValue) => {
  if (editor.value && newValue !== editor.value.getHTML()) {
    editor.value.commands.setContent(newValue);
  }
});

onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy();
  }
});
</script>

<template>
  <div class="flex flex-col gap-y-0.5 border bg-white">
    <TiptapEditorToolbar
      v-if="editor"
      :editor="editor"
    />
    <TiptapEditorContent
      class="prose max-w-none"
      :editor="editor"
    />
  </div>
</template>
