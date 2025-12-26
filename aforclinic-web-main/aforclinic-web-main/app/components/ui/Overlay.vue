<template>
  <div
    v-if="$overlay.visible"
    class="fixed inset-0 z-[9999] flex items-center justify-center bg-black/40"
  >
    <div class="flex flex-col items-center gap-2">
      <svg
        class="h-10 w-10 animate-spin text-white"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
      >
        <circle
          class="opacity-25"
          cx="12"
          cy="12"
          r="10"
          stroke="currentColor"
          stroke-width="4"
        />
        <path
          class="opacity-75"
          fill="currentColor"
          d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
        />
      </svg>
      <span class="text-sm text-white">처리 중입니다...</span>
    </div>
  </div>
</template>
