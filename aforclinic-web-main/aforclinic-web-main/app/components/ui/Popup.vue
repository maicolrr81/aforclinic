<script setup lang="ts">
import type { Carousel } from '#components';

const { $api } = useNuxtApp();

const { data } = await useAsyncData<Popup[]>(async () => {
  const data = await $api<ApiPagedResponse<Popup>>(`/public/popups`);
  return data.data.content ?? [];
});

const dayjs = useDayjs();
const { isLock } = useDocumentScrollLock();

const hidePopupUntil = useLocalStorage<string | null>('hide-popup-until', null);
const hideCardPopupUntil = useLocalStorage<string[]>('hide-card-popup-until', []);

const popups = shallowRef(data.value?.filter(popup => popup.type === `CAROUSEL`) ?? []);
const modals = shallowRef<Popup[]>([]);

const carousel = ref<InstanceType<typeof Carousel> | null>(null);
const selectedCarouselIndex = ref(0);

const isOpen = ref(false);

onMounted(() => {
  const today = dayjs().startOf('day');
  const hiddenUntil = hidePopupUntil.value ? dayjs(hidePopupUntil.value) : null;
  const shouldShow = !hiddenUntil || today.isAfter(hiddenUntil);
  isOpen.value = shouldShow && (popups.value?.length ?? 0) > 0;
  modals.value = (data.value?.filter(popup => popup.type === `MODAL`).filter(popup => !hideCardPopupUntil.value.includes(popup.id))) ?? [];
});

watch(isOpen, newValue => isLock.value = newValue);

function onSlideEnd({ currentSlideIndex }: {
  currentSlideIndex: number;
  prevSlideIndex: number;
  slidesCount: number;
  slidingToIndex: number;
}) {
  selectedCarouselIndex.value = currentSlideIndex;
}

function dismissPopupToday() {
  hidePopupUntil.value = dayjs().format('YYYY-MM-DD');
  isOpen.value = false;
}

function closeModalPopup(modalPopup: Popup) {
  modals.value = modals.value.filter(modal => modal !== modalPopup);
}

function dissmissModalPopupToday(modalPopup: Popup) {
  hideCardPopupUntil.value.push(modalPopup.id);
  closeModalPopup(modalPopup);
}
</script>

<template>
  <Teleport to="body">
    <div>
      <!-- 오버레이 -->
      <Transition name="fade">
        <div
          v-if="isOpen"
          class="fixed inset-0 z-[950] bg-black/50"
        />
      </Transition>

      <div
        v-if="isOpen"
        class="fixed left-1/2 top-1/2 z-[951] w-full max-w-[1000px] -translate-x-1/2 -translate-y-1/2"
      >
        <Carousel
          ref="carousel"
          :autoplay="5000"
          :items-to-show="1"
          :pause-autoplay-on-hover="true"
          :transition="500"
          :mouse-drag="false"
          :touch-drag="false"
          :wrap-around="true"
          class="bg-[#e3e3e3]"
          @slide-end="onSlideEnd"
        >
          <Slide
            v-for="(popup, index) of popups"
            :key="index"
            class="aspect-[100/47] max-h-[470px]"
          >
            <UiPopupItem v-bind="popup" />
          </Slide>
          <template #addons>
            <Navigation />
          </template>
        </Carousel>
        <ul class="flex flex-wrap bg-white">
          <li
            v-for="(popup, index) in popups"
            :key="index"
            class="flex-[1_1_33.3%] border text-sm text-gray-400 hover:font-medium hover:text-neutral-800"
            :class="{
              'font-medium text-neutral-800': selectedCarouselIndex === index,
            }"
          >
            <button
              class="flex h-full w-full items-center justify-center md:h-10"
              @click="(carousel as any)?.slideTo(index)"
            >
              {{ popup.title }}
            </button>
          </li>
        </ul>
        <div class="flex items-center justify-between border-t border-t-white bg-neutral-200 p-1">
          <button
            class="px-2 py-1 text-sm text-neutral-400 hover:text-neutral-500 md:text-base"
            @click="dismissPopupToday()"
          >
            오늘은 그만 볼래요.
          </button>
          <button
            class="px-2 py-1 text-sm text-neutral-800 hover:text-neutral-500 md:text-base"
            @click="isOpen = false"
          >
            닫기
          </button>
        </div>
      </div>
    </div>
  </Teleport>

  <!-- 카드형 팝업 -->
  <Teleport to="body">
    <div
      v-for="(modal, index) of modals"
      :key="index"
      class="lg:modal-transform fixed left-1/2 top-1/2 z-[940] w-max max-w-full -translate-x-1/2 -translate-y-1/2 p-4 lg:left-[var(--position-left)] lg:top-[var(--position-top)] lg:translate-x-0 lg:translate-y-0 lg:p-0"
      :style="{
        '--position-top': `${modal.positionTop ?? 0}px`,
        '--position-left': `${modal.positionLeft ?? 0}px`,
      }"
    >
      <component
        :is="modal.linkUri ? `a` : `div`"
        :href="modal.linkUri"
      >
        <img
          :alt="modal.title"
          :src="`${$config.public.apiBase}/files/${modal.imageId}`"
          class="w-full object-contain"
          style="
            max-width: calc(100vw - 1rem);
            max-height: calc(100vh - 1rem);
          "
        >
      </component>
      <div class="flex items-center justify-between border-t border-t-white bg-neutral-200 p-1">
        <button
          class="px-2 py-1 text-sm text-neutral-400 hover:text-neutral-500 md:text-base"
          @click="dissmissModalPopupToday(modal)"
        >
          오늘은 그만 볼래요.
        </button>
        <button
          class="px-2 py-1 text-sm text-neutral-800 hover:text-neutral-500 md:text-base"
          @click="closeModalPopup(modal)"
        >
          닫기
        </button>
      </div>
    </div>
  </Teleport>
</template>

<style lang="css" scoped>
.carousel :deep(.carousel__prev),
.carousel :deep(.carousel__next) {
  @apply h-8 w-8 md:h-12 md:w-12 flex justify-center items-center rounded-full hover:bg-black bg-black/60 text-white;
}

@media (min-width: 1024px) {
  .modal-transform {
    transform: translateX(min(0px, calc(100vw - (var(--position-left) + 100%) - 1rem)))
      translateY(min(0px, calc(100vh - (var(--position-top) + 100%))));
  }
}
</style>
