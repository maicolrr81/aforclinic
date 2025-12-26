<script setup lang="ts">
const auth = useAuthStore();
const user = useUserStore();

const [DefineIconButton, IconButton] = createReusableTemplate();

const nickname = computed(() => user.data?.nickname ?? ``);
</script>

<template>
  <DefineIconButton v-slot="{ to, icon, $slots }">
    <NuxtLink
      class="flex flex-col items-center justify-center gap-2 text-center text-sm text-gray-700 hover:underline md:text-base"
      :to
    >
      <div class="flex h-12 w-12 items-center justify-center rounded-full bg-cyan-100 md:h-20 md:w-20">
        <NuxtIcon
          class="h-8 w-8 text-cyan-600 md:h-12 md:w-12"
          :name="icon"
        />
      </div>
      <span class="text-xs md:text-sm">
        <Component :is="$slots.default" />
      </span>
    </NuxtLink>
  </DefineIconButton>

  <div class="container mx-auto grid min-h-screen w-auto max-w-screen-md grid-rows-[1fr_auto] bg-white pb-[3.5rem]">
    <TheHeader />
    <TheMain>
      <section class="mt-4 p-4 md:p-8">
        <h1 class="mb-2 text-xl font-bold md:text-2xl">
          <span class="font-normal text-gray-600">
            반갑습니다.
          </span>
          {{ nickname }} 님
        </h1>
        <div class="flex items-center justify-between">
          <NuxtLink
            class="text-sm text-gray-600"
            to="/mypage/edit"
          >
            내 정보 수정 >
          </NuxtLink>
          <button
            class="text-sm"
            @click="auth.logout()"
          >
            로그아웃
          </button>
        </div>
      </section>
      <section class="p-4 md:p-8">
        <div class="flex items-center justify-between gap-4">
          <IconButton
            icon="mdi:calendar-text"
            to="/mypage"
          >
            예약 내역 확인
          </IconButton>
          <IconButton
            icon="mdi:heart"
            to="/events"
          >
            이벤트
          </IconButton>
          <IconButton
            icon="mdi:diamond-stone"
            to="/membership"
          >
            멤버십
          </IconButton>
          <IconButton
            icon="mdi:dollar"
            to="/card-installments"
          >
            카드사 할인정보
          </IconButton>
        </div>
      </section>
      <slot />
    </TheMain>
    <TheFooter />
    <TheDock />
    <TheDrawer />
  </div>
</template>
