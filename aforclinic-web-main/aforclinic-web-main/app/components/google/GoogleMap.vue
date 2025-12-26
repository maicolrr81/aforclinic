<script setup lang="ts">
const {
  lat,
  lng,
  zoom = 16,
  language = `ko`,
  markerTitle,
} = defineProps<{
  lat: number;
  lng: number;
  zoom?: number;
  language?: string;
  region?: string;
  markerTitle?: string;
}>();

const config = useAppConfig();
const element = useTemplateRef(`element`);

const { load } = useScriptTag(
  `https://maps.googleapis.com/maps/api/js?key=${config.google.maps.apiKey}&loading=async&language=${language}&libraries=maps,marker`,
  () => {},
  {
    async: true,
    defer: true,
    manual: true,
  },
);

async function initializeMap() {
  try {
    // 스크립트 로드
    await load();

    // Google Maps가 준비될 때까지 대기
    while (typeof google === 'undefined' || !google.maps?.importLibrary) {
      await new Promise(resolve => setTimeout(resolve, 100));
    }

    const { Map } = await google.maps.importLibrary('maps') as google.maps.MapsLibrary;
    const { Marker } = await google.maps.importLibrary('marker') as google.maps.MarkerLibrary;

    const map = new Map(element.value as HTMLElement, {
      center: { lat, lng },
      zoom,
    });

    // eslint-disable-next-line unused-imports/no-unused-vars
    const marker = new Marker({
      map,
      position: { lat, lng },
      title: markerTitle,
    });

    // 드래그 후 원래 위치로 돌아가기
    let timeout: ReturnType<typeof setTimeout>;
    map.addListener('dragend', () => {
      if (timeout) {
        clearTimeout(timeout);
      }
      timeout = setTimeout(() => {
        map.panTo({ lat, lng });
      }, 5000);
    });
  }
  catch (error) {
    console.error('Google Maps 초기화 실패:', error);
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
