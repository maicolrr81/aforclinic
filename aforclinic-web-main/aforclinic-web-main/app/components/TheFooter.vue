<script setup lang="ts">
const expanded = ref(false);
</script>

<template>
  <footer class="flex flex-col gap-4 bg-gray-100 p-6 md:p-8">
    <section class="flex text-xs font-medium text-gray-700">
      <nav class="flex gap-2">
        <NuxtLink
          to="/policy/terms"
          target="_blank"
        >
          이용약관
        </NuxtLink>
        |
        <NuxtLink
          to="/policy/privacy"
          target="_blank"
        >
          개인정보처리방침
        </NuxtLink>
        |
        <button
          type="button"
          @click="expanded = !expanded"
        >
          사업자 정보
        </button>
      </nav>
    </section>
    <section
      v-if="expanded"
      class="text-xs text-gray-700"
    >
      <dl class="grid grid-cols-[7rem_1fr] gap-y-2">
        <dt>상호명</dt>
        <dd>에이포의원</dd>
        <dt>대표</dt>
        <dd>김광수</dd>
        <dt>사업자등록번호</dt>
        <dd>411-24-65795</dd>
        <dt>주소</dt>
        <dd>서울시 강남구 논현로 857, A857 지하 1층(신사동 582-10)</dd>
        <dt>대표번호</dt>
        <dd><a href="tel:0261041199">02-6104-1199</a></dd>
      </dl>
    </section>
  </footer>
</template>
