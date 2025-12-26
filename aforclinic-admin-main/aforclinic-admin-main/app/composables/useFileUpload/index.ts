import type { UseFileDialogOptions } from '@vueuse/core';

export interface CustomFileMetadata extends FileUploadResponse {
  src: string;
}

export function useFileUpload(options: UseFileDialogOptions = {}) {
  const runtimeConfig = useRuntimeConfig();

  const { $q } = useNuxtApp();

  options.reset ??= true;

  const { open, reset, onChange } = useFileDialog(options);
  const { on, trigger } = createEventHook<CustomFileMetadata[]>();

  const data = ref<CustomFileMetadata[]>([]);
  const pending = ref(false);

  onChange(async (selectedFiles) => {
    if (!selectedFiles?.length) { return; }

    $q.loading.show({ message: `처리 중입니다...` });

    const formData = new FormData();
    Array.from(selectedFiles).forEach(file => formData.append('files', file));

    try {
      pending.value = true;

      const res = await $fetch<ApiResponse<FileUploadResponse[]>>(`/files`, {
        baseURL: runtimeConfig.public.apiBase,
        method: `post`,
        body: formData,
      });

      data.value = res.data.map(data => ({
        ...data,
        src: `${runtimeConfig.public.apiBase}/files/${data.id}`,
      }));

      await trigger(...data.value);
    }
    catch (error) {
      console.log(`error`, error);
      data.value = [];
    }
    finally {
      pending.value = false;
      $q.loading.hide();
    }
  });

  return {
    data,
    open,
    reset,
    onChange: on,
    pending,
  };
}
