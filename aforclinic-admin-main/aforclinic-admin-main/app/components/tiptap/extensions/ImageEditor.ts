import { mergeAttributes, Node, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import ImageEditor from '../ImageEditor.vue';

export default Node.create<{
  src?: string;
}>({
  name: 'imageEditor',
  group: 'block',
  atom: true,
  draggable: true,

  addAttributes() {
    return {
      src: {
        default: null,
      },
    };
  },

  parseHTML() {
    return [
      {
        tag: 'div[data-type="image-editor"]',
      },
    ];
  },

  renderHTML({ HTMLAttributes }) {
    return [
      'div',
      mergeAttributes(HTMLAttributes, { 'data-type': 'image-editor' }),
    ];
  },

  addNodeView() {
    return VueNodeViewRenderer(ImageEditor as Component<NodeViewProps>);
  },
});
