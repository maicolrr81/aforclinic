export default defineNuxtRouteMiddleware((to) => {
  // 메인 페이지를 예약으로 설정
  if (to.path === `/`) {
    return navigateTo(`/appointments`);
  }
});
