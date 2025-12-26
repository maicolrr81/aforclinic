/**
 * Nuxt 설정 파일입니다.
 *
 * 이 파일은 Nuxt 애플리케이션의 설정을 정의하는 데 사용됩니다.
 *
 * 이 파일에는 절대 비밀 값을 포함하지 마세요!
 *
 * 이 파일은 사용자 클라이언트 번들에 노출됩니다.
 */
export default defineAppConfig({
  google: {
    maps: {
      apiKey: `AIzaSyBRisV1XHp98twcWJgPbYYvHjYfAekTGaU`,
    },
  },
  naver: {
    maps: {
      apiKey: `7g8jmjs7ah`,
    },
  },
  i18n: {
    enabled: true,
    locales: {
      ja: {
        label: `JPN`,
        to: `/ja`,
      },
      ko: {
        label: `KR`,
        to: `/home`,
      },
    },
  },
});
