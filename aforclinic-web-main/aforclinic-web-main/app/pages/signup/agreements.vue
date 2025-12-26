<script setup lang="ts">
const agreements = useAgreementsStore();

const terms = ref(false);
const privacy = ref(false);
const marketing = ref(false);

const consent = computed({
  get: () => terms.value && privacy.value && marketing.value,
  set: (newValue) => {
    terms.value = newValue;
    privacy.value = newValue;
    marketing.value = newValue;
  },
});

const isEssentialAgreed = computed(() => terms.value && privacy.value);

function onSubmit() {
  agreements.$patch({
    terms: terms.value,
    privacy: privacy.value,
    marketing: marketing.value,
  });
  navigateTo(`/signup`);
}
</script>

<template>
  <section class="p-8">
    <h1 class="text-3xl">
      회원가입
    </h1>
    <form
      class="mt-6 flex flex-col gap-12 md:mt-24"
      @submit.prevent="onSubmit"
    >
      <fieldset>
        <label class="flex items-center gap-2 text-sm">
          <input
            v-model="consent"
            type="checkbox"
            class="h-6 w-6 accent-cyan-800"
          >
          이용약관, 개인정보 수집 및 이용, 마케팅 정보 수신 동의(선택)에 모두 동의합니다.
        </label>
      </fieldset>

      <fieldset>
        <label class="flex items-center gap-2 text-sm">
          <input
            v-model="terms"
            type="checkbox"
            class="h-6 w-6 accent-cyan-800"
          >
          이용약관
          <span class="text-red-600">
            필수
          </span>
        </label>
        <iframe
          src="/policy/terms"
          class="mt-4 w-full border"
        />
      </fieldset>

      <fieldset>
        <label class="flex items-center gap-2 text-sm">
          <input
            v-model="privacy"
            type="checkbox"
            class="h-6 w-6 accent-cyan-800"
          >
          개인정보 수집 및 이용 동의
          <span class="text-red-600">
            필수
          </span>
        </label>
        <iframe
          src="/policy/privacy"
          class="mt-4 w-full border"
        />
      </fieldset>

      <fieldset>
        <label class="flex items-center gap-2 text-sm">
          <input
            v-model="marketing"
            type="checkbox"
            class="h-6 w-6 accent-cyan-800"
          >
          마케팅 정보 수신 동의
          <span class="text-red-600">
            선택
          </span>
        </label>
        <iframe
          src="/policy/marketing"
          class="mt-4 w-full border"
        />
      </fieldset>

      <div class="flex justify-center gap-4">
        <NuxtLink
          is="button"
          class="inline-flex h-[45px] w-60 items-center justify-center rounded-md border border-transparent bg-gray-100 px-4 py-2 text-base font-medium text-black hover:bg-gray-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-gray-500 focus-visible:ring-offset-2"
          to="/"
        >
          취소
        </NuxtLink>
        <button
          type="submit"
          class="inline-flex h-[45px] w-60 items-center justify-center rounded-md border border-transparent bg-cyan-300 px-4 py-2 text-base font-medium text-black hover:bg-cyan-400 focus:outline-none focus-visible:ring-2 focus-visible:ring-cyan-500 focus-visible:ring-offset-2 disabled:bg-gray-100 disabled:text-gray-200"
          :disabled="!isEssentialAgreed"
        >
          확인
        </button>
      </div>
    </form>
  </section>
</template>
