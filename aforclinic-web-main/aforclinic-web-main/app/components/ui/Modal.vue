<script setup lang="ts">
import type { ModalProps } from '#shared/types/ComponentProps';

const {
  title,
  confirmText = `확인`,
  cancelText = `취소`,
  showCancel,
  noCloseOnBackdrop,
  noHeaderClose,
  noFooter,
  fullscreen,
  panelClass,
} = defineProps<ModalProps>();

const emit = defineEmits<{
  cancel: [];
  close: [];
  confirm: [];
}>();

const modelValue = defineModel<boolean>({ default: false });

const { isLock } = useDocumentScrollLock();

function cancel() {
  emit(`cancel`);
  close();
}

function close() {
  if (noCloseOnBackdrop) { return; }
  emit(`close`);
  modelValue.value = false;
}

function confirm() {
  emit(`confirm`);
  close();
}

watch(modelValue, (newValue) => {
  isLock.value = newValue;
});
</script>

<template>
  <HeadlessTransitionRoot
    appear
    as="template"
    :show="modelValue"
  >
    <HeadlessDialog
      as="div"
      class="relative z-[9999]"
      @close="close"
    >
      <HeadlessTransitionChild
        as="template"
        enter="duration-300 ease-out"
        enter-from="opacity-0"
        enter-to="opacity-100"
        leave="duration-200 ease-in"
        leave-from="opacity-100"
        leave-to="opacity-0"
      >
        <div class="fixed inset-0 bg-black/25" />
      </HeadlessTransitionChild>

      <div class="fixed inset-0 overflow-y-auto">
        <div
          class="flex min-h-full items-center justify-center p-4 text-center"
          :class="{
            'h-svh !p-0': fullscreen,
          }"
        >
          <HeadlessTransitionChild
            as="template"
            enter="duration-300 ease-out"
            enter-from="opacity-0 scale-95"
            enter-to="opacity-100 scale-100"
            leave="duration-200 ease-in"
            leave-from="opacity-100 scale-100"
            leave-to="opacity-0 scale-95"
          >
            <HeadlessDialogPanel
              as="article"
              class="flex w-full max-w-md transform flex-col overflow-auto rounded-2xl bg-white p-4 text-left align-middle shadow-xl transition-all md:p-8"
              :class="[panelClass, {
                'h-full max-w-none rounded-none': fullscreen,
              }]"
            >
              <section
                v-if="title || !noHeaderClose"
                class="flex items-center justify-end gap-4 py-2"
              >
                <HeadlessDialogTitle
                  v-if="title"
                  as="h3"
                  class="p4 flex-1 text-lg font-semibold leading-6 text-gray-900"
                >
                  {{ title }}
                </HeadlessDialogTitle>
                <button
                  v-if="!noHeaderClose"
                  type="button"
                  class="flex items-center"
                  @click="close"
                >
                  <NuxtIcon
                    name="mdi:close"
                    size="24"
                  />
                </button>
              </section>

              <div class="flex-1 overflow-y-auto">
                <slot />
              </div>

              <template v-if="!noFooter">
                <slot name="buttons">
                  <div class="mt-4 flex flex-row gap-4">
                    <button
                      v-if="showCancel"
                      type="button"
                      class="inline-flex flex-1 justify-center rounded-md border border-transparent bg-gray-100 px-4 py-2 text-xs font-medium text-blue-900 hover:bg-gray-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-gray-500 focus-visible:ring-offset-2 md:text-sm"
                      @click="cancel"
                    >
                      {{ cancelText }}
                    </button>
                    <button
                      type="button"
                      class="inline-flex flex-1 justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 text-xs font-medium text-blue-900 hover:bg-blue-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2 md:text-sm"
                      @click="confirm"
                    >
                      {{ confirmText }}
                    </button>
                  </div>
                </slot>
              </template>
            </HeadlessDialogPanel>
          </HeadlessTransitionChild>
        </div>
      </div>
    </HeadlessDialog>
  </HeadlessTransitionRoot>
</template>
