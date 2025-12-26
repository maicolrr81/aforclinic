const appConfig: AppConfig = {
  theme: {
    header: {

    },
    drawer: {
      navs: [
        {
          text: `회원`,
          items: [
            {
              text: `회원 관리`,
              link: `/users`,
              icon: `mdi:card-account-details-outline`,
            },
            {
              text: `예약 관리`,
              link: `/appointments`,
              icon: `mdi:event-heart`,
            },
          ],
        },

        {
          text: `상품 관리`,
          items: [
            {
              text: `상품 카테고리 관리`,
              link: `/products/categories`,
              icon: `mdi:category`,
            },
            {
              text: `상품 관리`,
              link: `/products`,
              icon: `mdi:box-variant`,
            },
            {
              text: `상품 이벤트 관리`,
              link: `/products/events`,
              icon: `mdi:box-variant`,
            },
            {
              text: `시그니처 관리`,
              link: `/products/signatures`,
              icon: `mdi:archive-favorite-outline`,
            },
          ],
        },
        {
          text: `콘텐츠 관리`,
          items: [
            // {
            //   text: `공지사항 관리`,
            //   link: `/notices`,
            //   icon: `mdi:notice-board`,
            // },
            {
              text: `랜딩페이지 관리`,
              link: `/landings`,
              icon: `mdi:event-star`,
            },
            {
              text: `게시글 관리`,
              link: `/posts`,
              icon: `mdi:post`,
            },
            {
              text: `팝업 관리`,
              link: `/popups`,
              icon: `mdi:dock-window`,
            },
            // {
            //   text: `블로그 관리`,
            //   link: `/blog`,
            //   icon: `mdi:blog-outline`,
            // },
          ],
        },
        {
          text: `전후사진`,
          items: [
            {
              text: `전후사진 배너 관리`,
              link: `/beforeafters/banners`,
              icon: `qlementine-icons:media-16`,
            },
            {
              text: `전후사진 관리`,
              link: `/beforeafters`,
              icon: `ooui:table-move-column-after-ltr`,
            },
          ],
        },
        {
          text: `설정`,
          items: [
            {
              text: `메인화면 배너 관리`,
              link: `/mainbanners`,
              icon: `majesticons:image-frame-line`,
            },
            {
              text: `사이트 설정`,
              link: `/settings`,
              icon: `mdi:settings`,
            },
          ],
        },

      ],
    },
    footer: {

    },
  },
};

export default defineAppConfig({ ...appConfig });
