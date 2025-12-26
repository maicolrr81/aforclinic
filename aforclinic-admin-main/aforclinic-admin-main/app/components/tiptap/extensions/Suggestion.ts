import type { Editor, Range } from '@tiptap/core';
import type { Content, Instance as TippyInstance } from 'tippy.js';
import { VueRenderer } from '@tiptap/vue-3';
import tippy from 'tippy.js';
import CommandsList from '../CommandsList.vue';

interface SuggestionItem {
  name: string;
  description: string;
  icon: string;
  command: (props: { editor: Editor; range: Range }) => void;
}

interface SuggestionProps {
  query: string;
  editor: Editor;
  range: Range;
  clientRect: () => DOMRect;
}

const items: SuggestionItem[] = [
  {
    name: '본문',
    description: '간단한 텍스트',
    icon: 'mdi:format-paragraph',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .setParagraph()
        .run();
    },
  },
  {
    name: '제목 1',
    description: '주요 섹션 제목',
    icon: 'mdi:format-header-1',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .setNode('heading', { level: 1 })
        .run();
    },
  },
  {
    name: '제목 2',
    description: '중요한 섹션의 중간 제목',
    icon: 'mdi:format-header-2',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .setNode('heading', { level: 2 })
        .run();
    },
  },
  {
    name: '제목 3',
    description: '소제목으로 적합한 스타일',
    icon: 'mdi:format-header-3',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .setNode('heading', { level: 3 })
        .run();
    },
  },
  {
    name: '글머리 기호',
    description: '글머리 기호 목록을 만듭니다.',
    icon: 'mdi:format-list-bulleted',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .toggleBulletList()
        .run();
    },
  },
  {
    name: '번호 매기기',
    description: '번호가 매겨진 목록을 만듭니다.',
    icon: 'mdi:format-list-numbered',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .toggleOrderedList()
        .run();
    },
  },
  // {
  //   name: '코드 블록',
  //   description: '구문 강조가 적용된 코드 블록을 추가합니다.',
  //   icon: 'mdi:code',
  //   command: ({ editor, range }) => {
  //     editor.chain().focus().deleteRange(range).setNode('codeBlock').run();
  //   },
  // },
  {
    name: '인용구',
    description: '인용구를 삽입합니다.',
    icon: 'mdi:format-quote-close',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .toggleBlockquote()
        .run();
    },
  },
  {
    name: '이미지',
    description: '이미지를 삽입합니다.',
    icon: 'mdi:image',
    command: async ({ editor, range }) => {
      const { open, onChange } = useFileUpload({
        accept: `image/*`,
        directory: false,
      });
      open();
      onChange((...newFiles) => {
        newFiles.forEach((newFile, index) => {
          if (index > 0) {
            editor
              .chain()
              .focus()
              .setImage({ src: newFile.src })
              .run();
          }
          else {
            editor
              .chain()
              .focus()
              .deleteRange(range)
              .setImage({ src: newFile.src })
              .run();
          }
        });
      });
    },
  },
  {
    name: '구분선',
    description: '구분선을 삽입합니다.',
    icon: 'mdi:horizontal-line',
    command: ({ editor, range }) => {
      editor
        .chain()
        .focus()
        .deleteRange(range)
        .setHorizontalRule()
        .run();
    },
  },
];

export const suggestion = {
  items: ({ query }: { query: string }): SuggestionItem[] => items
    .filter(item => item.name.toLowerCase().startsWith(query.toLowerCase())),

  render: () => {
    let component: VueRenderer;
    let popups: TippyInstance[];

    return {
      onStart: (props: SuggestionProps) => {
        component = new VueRenderer(CommandsList, {
          props,
          editor: props.editor,
        });

        if (!props.clientRect) {
          return;
        }

        popups = tippy('body', {
          getReferenceClientRect: props.clientRect,
          appendTo: () => document.body,
          content: component.element as Content,
          showOnCreate: true,
          interactive: true,
          trigger: 'manual',
          placement: 'bottom-start',
        });
      },

      onUpdate(props: SuggestionProps) {
        component.updateProps(props);

        if (!props.clientRect) {
          return;
        }
        popups.forEach(popup => popup.setProps({
          getReferenceClientRect: props.clientRect,
        }));
      },

      onKeyDown(props: { event: KeyboardEvent }) {
        if (props.event.key === 'Escape') {
          popups.forEach(popup => popup.hide());
          return true;
        }

        return component.ref?.onKeyDown(props.event);
      },

      onExit() {
        popups.forEach(popup => popup.destroy());
        component.destroy();
      },
    };
  },
};

export default suggestion;
