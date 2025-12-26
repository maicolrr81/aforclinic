<script setup lang="ts">
const { y, arrivedState } = useWindowScroll({
  offset: {
    bottom: 100,
  },
});

const auth = useAuthStore();
const cart = useCartStore();

const show = ref(true);
const last = ref(0);

const computedClasses = computed<Record<string, boolean>>(() => ({
  'translate-y-[150%]': !show.value,
}));

const [DefineDockButton, DockButton] = createReusableTemplate();

watch(y, (current) => {
  if (import.meta.server) { return; }
  if (arrivedState.top || arrivedState.bottom) {
    show.value = true;
    last.value = current;
    return;
  }

  if (Math.abs(current - last.value) < 100) { return; }

  const isScrollingUp = current < last.value;
  show.value = isScrollingUp;
  last.value = current;
}, {
  immediate: true,
});
</script>

<template>
  <DefineDockButton v-slot="{ $slots, ...props }">
    <NuxtLink
      class="flex flex-1 flex-col items-center justify-center"
      v-bind="props"
    >
      <NuxtIcon
        is="div"
        v-if="props.icon"
        :name="props.icon"
        :size="props.size ?? 24"
      />
      <Component :is="$slots.default" />
    </NuxtLink>
  </DefineDockButton>
  <nav
    class="fixed bottom-0 z-[500] flex h-14 w-full max-w-screen-md border-t bg-white transition"
    :class="computedClasses"
  >
    <!-- <DockButton
      icon="mdi:home"
      to="/#home"
    >
      <span class="text-xs">
        홈
      </span>
    </DockButton> -->
    <DockButton
      icon="mdi:event-star"
      to="/procedures/events"
    >
      <span class="text-xs">
        이벤트
      </span>
    </DockButton>
    <DockButton
      href="https://pf.kakao.com/_IMNZn/chat/"
      target="_blank"
    >
      <img
        src="~/assets/icons/icon-service-kakaotalk-black.svg"
        alt="카카오톡"
        class="h-6 w-6"
        width="24"
        height="24"
      >
      <span class="text-xs">
        카카오톡
      </span>
    </DockButton>
    <div class="flex-1">
      <DockButton
        class="absolute -top-4 left-1/2 h-12 w-12 -translate-x-1/2 rounded-full bg-white !py-0 ring-4 ring-cyan-300 md:h-14 md:w-14"
        :class="{
          'cursor-pointer': !auth.isAuthenticated,
        }"
        icon="mdi:cart"
        size="28"
        to="/appointments"
      >
        <ClientOnly>
          <span class="absolute right-1 top-1 flex h-4 w-4 items-center justify-center rounded-full bg-black text-[10px] font-medium text-white md:right-2 md:top-2 md:h-5 md:w-5 md:text-xs">
            {{ cart.count }}
          </span>
        </ClientOnly>
      </DockButton>
    </div>

    <DockButton
      icon="mdi:calendar-heart"
      to="/appointments/quick"
    >
      <span class="text-xs">
        바로예약
      </span>
    </DockButton>
    <DockButton
      icon="mdi:phone"
      to="tel:0261041199"
    >
      <span class="text-xs">
        전화연결
      </span>
    </DockButton>
  </nav>
</template>

<style lang="css" scoped>
</style>
