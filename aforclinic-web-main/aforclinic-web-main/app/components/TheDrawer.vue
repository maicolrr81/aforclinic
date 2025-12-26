<script setup lang="ts">
const { $dialog, $drawer, $modal } = useNuxtApp();

const auth = useAuthStore();
const { isLock } = useDocumentScrollLock();

const route = useRoute();

const navs = computed<{
  label: string;
  to?: string;
  onClick?: () => void;
}[]>(() => [
      {
        label: `이벤트`,
        to: `/events`,
      },
      {
        label: `전체시술`,
        to: `/procedures`,
      },
      {
        label: `전후사진`,
        to: auth.isAuthenticated ? `/before-after` : undefined,
        onClick: () => block(`의료법상 로그인 후 전후사진 열람이 가능합니다.
에이포 의원은 의료법을 준수합니다.`),
      },
      {
        label: `VIP 멤버십`,
        to: `/membership`,
        onClick: () => block(`VIP 멤버십은 로그인이 필요합니다.`),
      },
      {
        label: `카드사별 무이자 할부 혜택`,
        to: `/card-installments`,
      },
      {
        label: `게시판`,
        to: `/boards`,
      },
      {
        label: `병원소개`,
        to: `/about`,
      },
      {
        label: `시술 후 주의사항`,
        to: `/aftercare`,
      },
    ]);

async function block(message: string) {
  if (auth.isAuthenticated) { return; }
  const confirm = await $dialog.confirm(message);
  if (confirm) {
    $modal.state.isLoginModalOpen = true;
  }
}

watch(() => route.fullPath, () => $drawer.open = false);
watch(() => $drawer.open, newValue => isLock.value = newValue);
</script>

<template>
  <div>
    <!-- 오버레이 -->
    <Transition name="fade">
      <div
        v-if="$drawer.open"
        class="fixed inset-0 z-[800] bg-black/50"
        @click="$drawer.open = false"
      />
    </Transition>

    <!-- 오프캔버스: 위에서 아래로 슬라이드 -->
    <Transition name="slide-down">
      <aside
        v-if="$drawer.open"
        class="fixed left-1/2 top-0 z-[801] h-full w-full max-w-screen-md -translate-x-1/2 bg-white pt-16 shadow-lg"
      >
        <div class="flex items-center justify-between p-4 md:p-8">
          <h2 class="text-xl font-semibold md:text-3xl">
            CATEGORY
          </h2>
          <button
            class="h-6 w-6 md:h-8 md:w-8"
            @click="$drawer.open = false"
          >
            <NuxtIcon
              name="mdi:close"
              class="h-full w-full"
            />
          </button>
        </div>
        <ul class="space-y-4 p-4 text-base font-medium md:space-y-6 md:p-8 md:text-xl">
          <li
            v-for="(nav, index) of navs"
            :key="index"
          >
            <NuxtLink
              class="hover:cursor-pointer hover:underline"
              :to="nav.to"
              @click="() => {
                nav.onClick?.();
                $drawer.open = false;
              }"
            >
              {{ nav.label }}
            </NuxtLink>
          </li>
          <hr>
          <li>
            <NuxtLink
              class="hover:underline"
              to="/appointments/quick"
              @click="$drawer.open = false"
            >
              바로 예약
            </NuxtLink>
          </li>
          <li>
            <NuxtLink
              class="hover:underline"
              href="tel:02-6104-1199"
              @click="$drawer.open = false"
            >
              전화 상담
            </NuxtLink>
          </li>
        </ul>
      </aside>
    </Transition>
  </div>
</template>

<style scoped>
/* Fade transition for overlay */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Slide-down transition for offcanvas */
.slide-down-enter-active,
.slide-down-leave-active {
  transition: transform 0.3s ease;
}
.slide-down-enter-from,
.slide-down-leave-to {
  transform: translate(-50%, -100%);
}
</style>
