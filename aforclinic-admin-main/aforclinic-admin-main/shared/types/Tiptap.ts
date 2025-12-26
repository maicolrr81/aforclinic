import type { Editor, Range } from '@tiptap/core';
import type { EditorState } from '@tiptap/pm/state';
import type { EditorView } from '@tiptap/pm/view';

export interface TiptapShouldShowProps {
  editor: Editor;
  view: EditorView;
  state: EditorState;
  oldState?: EditorState;
  from: number;
  to: number;
};

export interface TiptapMenuAction {
  name: string;
  label: string;
  icon: string;
  command: () => void;
  isActive?: () => boolean;
};

export interface TiptapTextColor {
  name: string;
  value: string;
};

export interface TiptapSuggestionItem {
  name: string;
  description: string;
  icon: string;
  command: (props: { editor: Editor; range: Range }) => void;
}

export interface TiptapImageEditorActionItem {
  label: string;
  icon: string;
  handler: () => void;
}
