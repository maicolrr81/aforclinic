<script setup lang="ts">
const route = useRoute();

if (route.params.id === `mnos9plyal`) {
  navigateTo(`/events/join/1`);
}
else {
  navigateTo(`/events/join/${route.params.id}`);
}
</script>
