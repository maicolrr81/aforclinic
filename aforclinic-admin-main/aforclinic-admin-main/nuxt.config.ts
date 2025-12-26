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
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@vueuse/nuxt',
    'dayjs-nuxt',
    'nuxt-quasar-ui',
    'nuxt-tiptap-editor',
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
  devtools: { enabled: true },
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
      title: '관리자 시스템 | XENIALSOFT Inc.',
      viewport: 'width=device-width,initial-scale=1',
      link: [
        // { rel: 'icon', type: '', href: '/favicon.ico', sizes: 'any' },
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
  css: ['~/assets/css/main.css'],
  /**
   * 애플리케이션 내에서 설정과 비밀 정보를 노출할 수 있는
   * 런타임 설정(runtime config) API를 제공합니다.
   */
  runtimeConfig: {
    app: {
      cookie: {
        session: 'XSID',
      },
    },
    public: {
      // eslint-disable-next-line node/prefer-global/process
      apiBase: process.env.NODE_ENV === 'development'
        ? 'http://localhost:8080'
        : 'https://api.aforclinic.com',
      // eslint-disable-next-line node/prefer-global/process
      homepage: process.env.NODE_ENV === 'development'
        ? 'http://localhost:3000'
        : 'https://aforclinic.com',
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
   * vite 설정
   */
  vite: {
    server: {
      allowedHosts: true,
    },
  },
  /**
   * ESLint 설정을 구성합니다.
   * ESLint는 JavaScript 및 Vue.js 코드의 스타일과 품질을
   * 분석하고 검사하는 도구입니다. 이 설정을 통해 애플리케이션
   * 코드의 일관성을 유지하고 잠재적인 오류를 사전에
   * 방지할 수 있습니다.
   */
  eslint: {
    config: {
      standalone: false,
    },
  },
  /**
   * 퀘이사 설정을 구성합니다.
   */
  quasar: {
    plugins: [
      'Dialog',
      'Loading',
    ],
    components: {
      defaults: {
        // QDialog: {
        //   noBackdropDismiss: true,
        //   noEscDismiss: true,
        // },
        QInput: {
          outlined: true,
          lazyRules: 'ondemand',
        },
        QPage: {
          padding: true,
        },
        QPagination: {
          color: 'primary',
          maxPages: 10,
        },
        QSelect: {
          outlined: true,
          lazyRules: 'ondemand',
        },
        QTable: {
          flat: true,
          hidePagination: true,
          tableHeaderClass: ['font-bold'],
          pagination: {
            rowsPerPage: 10,
          },
          noDataLabel: '조회된 내용이 없습니다.',
          rowKey: 'id',
        },
      },
    },
  },
  /**
   * TIPTAP 설정을 구성합니다.
   */
  tiptap: {
    prefix: 'Tiptap',
  },
  /**
   * Nuxt/Icon 설정을 구성합니다.
   */
  icon: {
    componentName: 'NuxtIcon',
  },
});
