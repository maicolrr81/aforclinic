<script setup lang="ts">
const {
  lat,
  lng,
  zoom = 16,
} = defineProps<{
  lat: number;
  lng: number;
  zoom?: number;
}>();

const config = useAppConfig();
const element = useTemplateRef(`element`);

const { load } = useScriptTag(
  `https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=${config.naver.maps.apiKey}`,
  () => {},
  {
    async: true,
    defer: true,
  },
);

async function initializeMap() {
  try {
    // 스크립트 로드
    await load();

    // Naver Maps가 준비될 때까지 대기
    while (typeof naver === 'undefined' || !naver.maps?.Map) {
      await new Promise(resolve => setTimeout(resolve, 100));
    }

    const map = new naver.maps.Map(element.value as HTMLElement, {
      center: new naver.maps.LatLng(lat, lng),
      scrollWheel: false,
      zoom,
      zoomControl: true,
      zoomControlOptions: {
        position: naver.maps.Position.TOP_LEFT,
      },
    });
    // 마커 추가
    // eslint-disable-next-line unused-imports/no-unused-vars
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(lat, lng),
      map,
    });

    // 초기 위치로 돌아오기
    let timeout: NodeJS.Timeout;
    naver.maps.Event.addListener(map, `dragend`, () => {
      if (timeout) {
        clearTimeout(timeout);
      }
      timeout = setTimeout(() => {
        map.panTo(new naver.maps.LatLng(lat, lng), {
          duration: 500,
          easing: `easeOutCubic`,
        });
      }, 5000);
    });
  }
  catch (error) {
    console.error('Naver Maps 초기화 실패:', error);
  }
}

onMounted(() => {
  initializeMap();
});
</script>

<template>
  <div
    ref="element"
    class="aspect-[7/5]"
  />
</template>
