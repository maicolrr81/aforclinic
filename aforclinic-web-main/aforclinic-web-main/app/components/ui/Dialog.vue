<script setup lang="ts">
const { $dialog } = useNuxtApp();

// 상태에서 값 가져오기
const message = computed(() => $dialog.state.options.message);
const title = computed(() => $dialog.state.options.title || '');
const showCancel = computed(() => $dialog.state.options.showCancel || false);
const confirmText = computed(() => $dialog.state.options.confirmText || '확인');
const cancelText = computed(() => $dialog.state.options.cancelText || '취소');

function onConfirm() {
  $dialog.close(true);
}

function onCancel() {
  $dialog.close(false);
}
</script>

<template>
  <UiModal
    :cancel-text="cancelText"
    :confirm-text="confirmText"
    :show-cancel="showCancel"
    :title="title"
    no-header-close
    no-close-on-backdrop
    @confirm="onConfirm"
    @cancel="onCancel"
  >
    <div class="whitespace-break-spaces py-4 text-center text-sm md:text-base">
      {{ message }}
    </div>
  </UiModal>
</template>
