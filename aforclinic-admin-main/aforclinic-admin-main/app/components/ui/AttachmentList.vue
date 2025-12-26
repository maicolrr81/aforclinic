<script setup lang="ts">
import type { AttachmentListProps } from '~~/shared/types/ComponentProps';

const {
  bordered = false,
  readonly = false,
} = defineProps<AttachmentListProps>();

const modelValue = defineModel<Attachment[]>({ default: [] });

const { open, onChange } = useFileUpload();

onChange((...items) => modelValue.value = [
  ...modelValue.value,
  ...(items.map(({ id: fileId, filename, size }) => ({
    fileId,
    filename,
    size,
  }))),
]);

function remove(fileId: string) {
  modelValue.value = modelValue.value.filter(data => data.fileId !== fileId);
}
</script>

<template>
  <QList :bordered>
    <QItemLabel
      v-if="!readonly"
      header
      overline
      class="flex items-center justify-between border-b"
    >
      <span class="text-sm">
        첨부파일
      </span>
      <QBtn
        color="blue"
        icon="add"
        dense
        flat
        @click="open()"
      />
    </QItemLabel>
    <template v-if="modelValue.length > 0">
      <QItem
        v-for="(attachment, index) of modelValue"
        :key="index"
      >
        <QItemSection avatar>
          <NuxtIcon
            name="mdi:attach-file"
            class="text-red-500"
            size="28"
          />
        </QItemSection>
        <QItemSection class="flex-row items-center justify-between ">
          <a
            class="text-gray-600 hover:underline"
            :href="`${$config.public.apiBase}/files/${attachment.fileId}`"
            target="_blank"
          >
            {{ attachment.filename }}
          </a>
          <QBtn
            v-if="!readonly"
            color="red"
            icon="close"
            dense
            flat
            @click="remove(attachment.fileId)"
          />
        </QItemSection>
      </QItem>
    </template>
    <QItem v-else />
  </QList>
</template>
