<script setup lang="ts">
import type { Event } from '#shared/types/Event';
import type { SiteSetting } from '#shared/types/SiteSettings';

const { $api, $dialog } = useNuxtApp();
// const { open, onChange } = useFileUpload();

const { data } = await useAsyncData(async () => {
  const data = await $api<ApiResponse<SiteSetting>>(`/site-settings`);
  return data.data;
});

const { data: events } = await useAsyncData<Event[]>(async () => {
  const data = await $api<ApiPagedResponse<Event>>(`/events`, {
    method: `get`,
    params: {
      unpaged: true,
    },
  });
  return data.data.content ?? [];
});

const highlightEventOptions = ref<{
  label: string;
  value: Event;
}[]>();
const selectedHighlightEvent = ref<string>();
const selectedHighlightEvents = ref<{
  eventId: string;
  eventTitle: string;
}[]>([]);
const selectedHighlightEventIds = computed(() => selectedHighlightEvents.value.map(selectedHighlightEvent => selectedHighlightEvent.eventId));

const recommendedKeyword = ref<string>(``);
const recommendedKeywords = ref<string[]>([]);

watchEffect(async () => {
  // 메인에서 보여줄 이벤트 목록
  const rawHighlightedEvents = data.value?.settings?.highlightedEvents;
  try {
    const eventIds = rawHighlightedEvents ? JSON.parse(rawHighlightedEvents) : [];
    if (eventIds.length > 0) {
      const data = await $api<ApiPagedResponse<Event>>(`/events`, {
        method: `get`,
        params: {
          eventIds: rawHighlightedEvents ? JSON.parse(rawHighlightedEvents) : [],
          unpaged: true,
        },
      });
      selectedHighlightEvents.value = data.data.content.map(event => ({
        eventId: event.id,
        eventTitle: event.title,
      }));
    }
  }
  catch {
    selectedHighlightEvents.value = [];
  }

  // 추천 검색 키워드
  const rawRecommendedKeywords = data.value?.settings?.recommendedKeywords;
  try {
    recommendedKeywords.value = rawRecommendedKeywords ? JSON.parse(rawRecommendedKeywords) : [];
  }
  catch {
    recommendedKeywords.value = [];
  }
});

const body = computed(() => ({
  settings: {
    recommendedKeywords: JSON.stringify(recommendedKeywords.value),
    highlightedEvents: JSON.stringify(selectedHighlightEventIds.value),
  },
}));

function addHighlightedEvent(newValue: Event) {
  const duplicatedHighlightEvent = selectedHighlightEvents.value.find(selectedHighlightEvent => selectedHighlightEvent.eventId === newValue.id);

  if (duplicatedHighlightEvent) { return; }

  const foundedEvent = events.value?.find(event => (event.id === newValue.id));
  if (foundedEvent) {
    selectedHighlightEvents.value.push({
      eventId: foundedEvent.id,
      eventTitle: foundedEvent.title,
    });
    selectedHighlightEvent.value = foundedEvent.title;
  }
}

function removeHighlightedEvent(eventId: string) {
  selectedHighlightEvents.value = selectedHighlightEvents.value
    .filter(selectedHighlightEvent => selectedHighlightEvent.eventId !== eventId);
}

function updateHighlightedEventOptions(filter = '') {
  const lower = filter.toLowerCase();
  highlightEventOptions.value = events.value
    ?.filter(event =>
      !selectedHighlightEvents.value.find(selectedHighlightEvent => selectedHighlightEvent.eventId === event.id)
      && event.title.toLowerCase().includes(lower))
    ?.map(event => ({
      label: event.title,
      value: event,
    }));
}

function highlightedEventFilterFn(val: string, update: (cb: () => void) => void) {
  update(() => updateHighlightedEventOptions(val));
}

function addRecommendedKeyword() {
  const newValue = recommendedKeyword.value;
  if (newValue && !recommendedKeywords.value.includes(newValue)) {
    recommendedKeywords.value.push(newValue);
  }
  recommendedKeyword.value = ``;
}

function removeRecommendedKeyword(keyword: string) {
  recommendedKeywords.value = recommendedKeywords.value.filter(recommendedKeyword => recommendedKeyword !== keyword);
}

async function submit() {
  try {
    await $api(`/site-settings`, {
      method: `post`,
      body: body.value,
    });
    $dialog.alert(`사이트 설정을 변경했습니다.`);
  }
  catch {
    $dialog.alert(`사이트 설정 중 오류가 발생했습니다.`);
  }
}
</script>

<template>
  <QPage class="container mx-auto">
    <QCard>
      <QCardSection class="pb-0">
        <QToolbar>
          <QToolbarTitle>
            사이트 설정
          </QToolbarTitle>
        </QToolbar>
      </QCardSection>

      <QForm @submit.prevent="submit()">
        <!-- <QCardSection>
          <QToolbar class="gap-x-2 px-0">
            <QToolbarTitle class="text-lg">
              메인 배너 설정
            </QToolbarTitle>
            <QBtn
              color="blue"
              @click="open()"
            >
              추가
            </QBtn>
          </QToolbar>
          <div class="space-y-2">
            <fieldset
              v-for="(banner, index) of banners"
              :key="index"
              class="flex gap-2"
            >
              <QInput
                v-model="banner.image"
                class="flex-1"
                label="이미지"
                dense
              />
              <QInput
                v-model="banner.to"
                class="flex-1"
                label="경로"
                dense
              />
            </fieldset>
          </div>
          <fieldset class="flex gap-2">
            <QInput
              class="flex-1"
              label="이미지"
              dense
            />
            <QInput
              class="flex-1"
              label="경로"
              dense
            />
            <QBtn
              color="blue"
              label="추가"
            />
          </fieldset>
        </QCardSection> -->

        <QCardSection>
          <QToolbar class="gap-x-2 px-0">
            <QToolbarTitle class="text-lg">
              메인 표시 이벤트
            </QToolbarTitle>
          </QToolbar>

          <fieldset class="space-y-2">
            <QSelect
              v-model="selectedHighlightEvent"
              :options="highlightEventOptions"
              emit-value
              map-options
              input-debounce="0"
              label="이벤트를 검색합니다."
              dense
              use-input
              hide-selected
              @filter="highlightedEventFilterFn"
              @update:model-value="addHighlightedEvent"
            />

            <QList
              bordered
              dense
              class="rounded"
            >
              <QItemLabel class="p-4 text-base text-primary">
                메인에 표시할 이벤트 목록
              </QItemLabel>
              <QItem
                v-for="(event, index) of selectedHighlightEvents"
                :key="index"
                class="flex items-center gap-6"
                clickable
              >
                <div>
                  {{ event.eventTitle }}
                </div>
                <QSpace />
                <QBtn
                  icon="close"
                  color="red"
                  flat
                  dense
                  @click="removeHighlightedEvent(event.eventId)"
                />
              </QItem>
            </QList>
          </fieldset>
        </QCardSection>

        <QCardSection>
          <QToolbar class="gap-x-2 px-0">
            <QToolbarTitle class="text-lg">
              추천 검색 키워드
            </QToolbarTitle>
          </QToolbar>
          <div class="mb-3 flex flex-wrap gap-2">
            <QChip
              v-for="(item, index) of recommendedKeywords"
              :key="index"
              removable
              color="blue"
              text-color="white"
              @remove="removeRecommendedKeyword(item)"
            >
              {{ item }}
            </QChip>
          </div>
          <QInput
            v-model="recommendedKeyword"
            label="검색 키워드"
            dense
            @keydown.enter.prevent="addRecommendedKeyword()"
          />
        </QCardSection>

        <QCardActions class="justify-end gap-x-4 p-4">
          <QBtn
            type="submit"
            class="!px-8 !py-2"
            color="blue"
            label="저장"
          />
        </QCardActions>
      </QForm>
    </QCard>
  </QPage>
</template>
