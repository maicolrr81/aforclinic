// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  /**
   * Nuxt 모듈을 설정합니다.
   * 이 배열에 포함된 모듈은 Nuxt 애플리케이션의 빌드, 실행 및
   * 개발 과정에서 사용됩니다. 각 모듈은 특정 기능을 제공하거나
   * Nuxt 애플리케이션을 확장하는 데 사용됩니다.
   *
   * 예시:
   * modules: [
   *   '@nuxtjs/axios', // Axios 모듈을 사용하여 HTTP 요청을 쉽게 관리
   *   '@nuxtjs/auth',  // 인증 모듈을 사용하여 사용자 인증을 처리
   * ]
   */
  modules: [
    '@nuxt/eslint',
    '@nuxt/icon',
    '@nuxt/image',
    '@nuxtjs/sitemap',
    '@nuxtjs/robots',
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@samk-dev/nuxt-vcalendar',
    '@vueuse/nuxt',
    'dayjs-nuxt',
    'nuxt-headlessui',
    'vue3-carousel-nuxt',
  ],
  /**
   * 개발 도구 설정을 구성합니다.
   * 이 설정을 통해 Nuxt 애플리케이션의 개발 환경에서 유용한 도구와
   * 기능들을 활성화할 수 있습니다. 예를 들어, 디버깅 도구나 개발
   * 편의성을 높이는 플러그인들을 포함할 수 있습니다.
   *
   * 예시:
   * devtools: {
   *   enabled: true, // 개발 도구를 활성화
   *   // 추가 설정 옵션들을 여기에 추가할 수 있습니다.
   * }
   */
  devtools: { enabled: false },
  /**
   * 애플리케이션 설정을 구성합니다.
   * 이 설정을 통해 Nuxt 애플리케이션의 전반적인 동작과
   * 관련된 다양한 옵션들을 정의할 수 있습니다.
   * 예를 들어, 페이지 제목, 메타데이터, 로고, CSS 파일 등을
   * 설정할 수 있습니다.
   *
   * 예시:
   * app: {
   *   head: {
   *     title: 'My Nuxt App', // 애플리케이션의 기본 제목
   *     meta: [
   *       { charset: 'utf-8' },
   *       { name: 'viewport', content: 'width=device-width, initial-scale=1' }
   *       // 기타 메타 태그들
   *     ]
   *   },
   *   // 추가 설정 옵션들을 여기에 추가할 수 있습니다.
   * }
   */
  app: {
    baseURL: '/',
    head: {
      charset: 'utf-8',
      htmlAttrs: {
        lang: 'ko',
      },
      title: '에이포의원',
      viewport: 'width=device-width, initial-scale=1, viewport-fit=cover',
      link: [
        { rel: 'icon', href: '/favicon.ico' },
        { rel: 'icon', type: 'image/png', sizes: '32x32', href: '/favicon.png' },
      ],
      meta: [
        {
          name: 'title',
          content: '에이포의원',
        },
        {
          name: 'description',
          content: '당신의 아름다움을 항상 연구합니다.',
        },
        {
          name: 'keywords',
          content: '강남피부과, 압구정피부과, 신사피부과, 압구정로데오피부과, 압구정리프팅잘하는곳, 압구정시술잘하는곳, 압구정리프팅, 압구정스킨부스터, 압구정바디, 압구정다이어트, 압구정제모, 브이올렛, 지방분해주사, 보톡스, 주름보톡스, 사각턱보톡스, 제오민보톡스, 스킨보톡스, 토닝, 피코슈어, 리팟, 클라리티, 목주름, 필러, 벨로테로, 리쥬란, 리쥬란HB, 쥬베룩, 볼륨, 리즈네, 레디어스, 엑소좀, 수면, 울쎄라, 세르프, 온다, 티타늄, 온타늄, 버츄, PTT, 여드름, 잡티, 모공, 아쿠아필, LDM, 라라필, 점제거, 위고비, 다한증, 영양주사, 남자제모, 여자제모, 겨드랑이, 인중, 얼굴전체, 종아리, 다리, 팔',
        },
        {
          name: 'author',
          content: '에이포의원',
        },
        {
          name: 'publisher',
          content: '에이포의원',
        },
        {
          name: 'google-site-verification',
          content: 'oNgzvJqkxWhTntNd12kklRoOT0eQdWg6PXyka9SGUmM',
        },
        {
          name: 'robots',
          content: 'index, follow',
        },
      ],
      script: [
        {
          innerHTML: `
          (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
          new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
          j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
          'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
          })(window,document,'script','dataLayer','GTM-NB82XSMT');
          `,
        },
        {
          src: 'https://www.googletagmanager.com/gtag/js?id=G-GLPEJKBT05',
          async: true,
        },
        {
          innerHTML: `
          window.dataLayer = window.dataLayer || [];
          function gtag(){dataLayer.push(arguments);}
          gtag('js', new Date());
          gtag('config', 'G-GLPEJKBT05');
          `,
        },
      ],
      noscript: [
        {
          innerHTML: `
          <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NB82XSMT"
          height="0" width="0" style="display:none;visibility:hidden"></iframe>
          `,
        },
      ],
    },
  },
  /**
   * 글로벌 CSS 파일 및 CSS 라이브러리를 설정합니다.
   * 이 설정을 통해 애플리케이션 전체에서 사용될 CSS 파일이나
   * CSS 라이브러리를 지정할 수 있습니다. 지정된 CSS 파일은
   * 모든 페이지에 자동으로 포함됩니다.
   *
   * 예시:
   * css: [
   *   '~/assets/css/main.css', // 글로벌 CSS 파일
   *   'vuetify/dist/vuetify.min.css', // 외부 CSS 라이브러리
   *   // 추가 CSS 파일들을 여기에 추가할 수 있습니다.
   * ]
   */
  css: [],
  /**
   * @nuxtjs/sitemap 설정을 구성합니다.
   */
  site: {
    url: 'https://aforclinic.com',
    name: '에이포의원',
  },
  /**
   * 애플리케이션 내에서 설정과 비밀 정보를 노출할 수 있는
   * 런타임 설정(runtime config) API를 제공합니다.
   */
  runtimeConfig: {
    cookieConfig: {
      sessionName: 'XSID',
    },
    public: {
      // eslint-disable-next-line node/prefer-global/process
      apiBase: process.env.NODE_ENV === 'development'
        ? 'http://localhost:8080'
        : 'https://api.aforclinic.com',
      // eslint-disable-next-line node/prefer-global/process
      siteBase: process.env.NODE_ENV === 'development'
        ? 'http://localhost:3000'
        : 'https://aforclinic.com',
      naver: {
        maps: {
          lat: 37.525252,
          lng: 127.028057,
        },
      },
    },
  },
  /**
   * 미래 기능 설정을 구성합니다.
   * 이 설정을 통해 Nuxt의 미래 릴리스에서 도입될 새로운 기능들을
   * 미리 사용할 수 있습니다. 일반적으로 실험적인 기능이나
   * 향후 표준이 될 기능들이 포함됩니다.
   *
   * 예시:
   * future: {
   *   compatibilityVersion: 4, // Nuxt 4 사용을 활성화
   *   // 추가로 사용할 미래 기능들을 여기에 추가할 수 있습니다.
   * }
   */
  future: { compatibilityVersion: 4 },
  /**
   * 애플리케이션의 호환 날짜를 설정합니다.
   * 이 날짜는 Nuxt의 이전 버전과의 하위 호환성을
   * 유지하는 데 사용되며, 애플리케이션이 이 날짜
   * 이전의 기능과 동작에 따라 기대한 대로 동작하도록 보장합니다.
   *
   * 형식: 'YYYY-MM-DD'
   */
  compatibilityDate: '2024-11-01',
  /**
   * Dayjs 설정을 구성합니다.
   */
  dayjs: {
    locales: ['ko'],
    plugins: ['customParseFormat', 'timezone'],
    defaultLocale: 'ko',
    defaultTimezone: 'Asia/Seoul',
  },
  /**
   * ESLint 설정을 구성합니다.
   * ESLint는 JavaScript 및 Vue.js 코드의 스타일과 품질을
   * 분석하고 검사하는 도구입니다. 이 설정을 통해 애플리케이션
   * 코드의 일관성을 유지하고 잠재적인 오류를 사전에
   * 방지할 수 있습니다.
   *
   * 예시:
   * eslint: {
   *   fix: true, // 가능한 경우 자동으로 문제를 수정
   *   cache: false, // 캐시를 사용하여 검사 속도 향상
   *   // 추가 ESLint 설정 옵션들을 여기에 추가할 수 있습니다.
   * }
   */
  eslint: {
    config: {
      standalone: false,
      nuxt: {
        sortConfigKeys: true,
      },
    },
  },
  /**
   * Nuxt/Icon 설정을 구성합니다.
   */
  icon: {
    componentName: 'NuxtIcon',
  },
  /**
   * @nuxtjs/robots 설정을 구성합니다.
   */
  robots: {
    allow: [
      '/',
      '/*',
    ],
    sitemap: [
      '/sitemap.xml',
    ],
  },
  /**
   * TailwindCSS 설정을 구성합니다.
   */
  tailwindcss: {

  },
});
