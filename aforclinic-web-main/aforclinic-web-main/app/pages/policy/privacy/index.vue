<script setup lang="ts">
definePageMeta({ layout: `policy` });

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `개인정보처리방침 - 에이포의원`,
  description: `개인정보처리방침 - 2023년 5월 30일 개정`,

  // Open Graph
  ogTitle: `개인정보처리방침 - 에이포의원`,
  ogDescription: `개인정보처리방침 - 2023년 5월 30일 개정`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `개인정보처리방침 - 에이포의원`,
  twitterDescription: `개인정보처리방침 - 2023년 5월 30일 개정`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});
</script>

<template>
  <header>
    <h1>개인정보처리방침</h1>
    <p>
      에이포의원(이하 '본원')는 귀하의 개인정보보호를 매우 중요시하며, 『개인정보보호법』을 준수하고 있습니다.<br>
      본원은 개인정보처리방침을 통하여 귀하께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보 보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.
    </p>
  </header>

  <nav>
    <h2>
      개인정보처리방침의 순서는 다음과 같습니다.
    </h2>
    <ol class="list-inside list-decimal">
      <li><a href="#section1">수집하는 개인정보의 항목 및 수집방법</a></li>
      <li><a href="#section2">개인정보의 수집 및 이용목적</a></li>
      <li><a href="#section3">개인정보의 보유 및 이용기간</a></li>
      <li><a href="#section4">개인정보의 파기절차 및 그 방법</a></li>
      <li><a href="#section5">개인정보 제공 및 공유</a></li>
      <li><a href="#section6">수집한 개인정보의 취급위탁</a></li>
      <li><a href="#section7">이용자 및 법정대리인의 권리와 그 행사방법</a></li>
      <li><a href="#section8">동의철회 / 회원탈퇴 방법</a></li>
      <li><a href="#section9">개인정보 자동 수집 장치의 설치/운영 및 그 거부에 관한 사항</a></li>
      <li><a href="#section10">개인정보관리책임자</a></li>
      <li><a href="#section11">개인정보의 안전성 확보조치</a></li>
      <li><a href="#section12">정책 변경에 따른 공지의무</a></li>
    </ol>
  </nav>

  <section id="section1">
    <h2>
      1. 수집하는 개인정보의 항목 및 수집방법
    </h2>

    <p>
      본원은 회원가입 시 서비스 이용을 위해 필요한 최소한의 개인정보만을 수집합니다.
      귀하가 본원의 서비스를 이용하기 위해서는 회원가입 시 필수항목과 선택항목이 있는데,
      메일수신여부 등과 같은 선택 항목은 입력하지 않더라도 서비스 이용에는 제한이 없습니다.
    </p>

    <div>
      <h3>
        ㄱ. [진료정보]
      </h3>
      <p>
        <strong>수집항목:</strong> 성명, 생년월일, 주소, 연락처, 진료기록
      </p>
      <p class="text-gray-500">
        ※ 의료법에 의해 고유식별 정보 및 진료정보를 의무적으로 보유하여야 하며, 별도 동의는 불필요합니다.
      </p>
    </div>

    <div>
      <h3>
        ㄴ. [홈페이지 회원가입 시 수집항목]
      </h3>
      <ul class="list-disc">
        <li>
          <strong>필수항목:</strong> 성명, 아이디, 비밀번호, 주소, 연락처(전화번호, 휴대폰번호), 메일주소
        </li>
        <li>
          <strong>선택항목:</strong> 생일, 문자 수신 여부, 메일 수신 여부, 관심분야
        </li>
        <li>
          <strong>자동 수집 정보:</strong>
          서비스 이용기록, 접속 로그, 쿠키, 접속 IP 정보
        </li>
      </ul>
    </div>

    <div>
      <h3>
        ㄷ. [개인정보 수집방법]
      </h3>
      <p>
        다음과 같은 방법으로 개인정보를 수집합니다.
      </p>
      <ul class="list-disc">
        <li>홈페이지</li>
        <li>서면양식</li>
        <li>팩스</li>
        <li>전화</li>
        <li>상담 게시판</li>
        <li>이메일</li>
      </ul>
    </div>
  </section>

  <section id="section2">
    <h2>2. 개인정보의 수집 및 이용목적</h2>
    <p>
      본원은 수집한 개인정보를 다음의 목적을 위해 활용합니다.
      이용자가 제공한 모든 정보는 하기 목적에 필요한 용도 이외로는 사용되지 않으며, 이용 목적 변경 시 사전 동의를 구합니다.
    </p>

    <div>
      <h3>
        ㄱ. [진료정보]
      </h3>
      <p>진단 및 치료를 위한 진료서비스와 청구, 수납 및 환급 등의 원무서비스 제공</p>
    </div>

    <div>
      <h3>
        ㄴ. [홈페이지 회원정보]
      </h3>
      <ul class="list-disc">
        <li>
          <strong>필수정보:</strong> 진료 예약, 예약조회, 회원제 서비스 제공, 온라인 게시판 이용 등
        </li>
        <li>
          <strong>선택정보:</strong> 병원소식 안내, 설문조사, 맞춤 서비스, 통계 수집 등
        </li>
      </ul>
      <p class="text-gray-500">
        ※ 민감한 개인정보는 수집하지 않습니다. 추가정보는 이용자의 자의로 수집합니다.
      </p>
    </div>
  </section>

  <section id="section3">
    <h2>3. 개인정보의 보유 및 이용기간</h2>
    <p>
      본원은 수집 목적 달성 후 즉시 파기합니다.
    </p>
    <div>
      <h3>
        ㄱ. [진료정보]
      </h3>
      <p>의료법에 따른 진료기록 보관 기준 준수</p>
    </div>
    <div>
      <h3>
        ㄴ. [홈페이지 회원정보]
      </h3>
      <ul class="list-disc">
        <li>회원 탈퇴 또는 제명 시 파기</li>
        <li>소비자 분쟁 처리 기록: 3년</li>
        <li>신용정보 기록: 3년</li>
        <li>본인 확인 기록: 6개월</li>
        <li>방문 기록: 3개월</li>
      </ul>
    </div>
  </section>

  <section id="section4">
    <h2>4. 개인정보의 파기절차 및 방법</h2>
    <div>
      <h3>
        ㄱ. 파기절차
      </h3>
      <p>목적 달성 후 즉시 파기</p>
    </div>
    <div>
      <h3>
        ㄴ. 파기방법
      </h3>
      <ul class="list-disc">
        <li>전자적 파일: 복구 불가능하게 삭제</li>
        <li>서면 자료: 분쇄 또는 소각</li>
      </ul>
    </div>
  </section>

  <section id="section5">
    <h2>5. 개인정보 제공 및 공유</h2>
    <p>
      법령 또는 이용자 동의 없는 제공은 하지 않습니다.
    </p>
    <ul class="list-disc">
      <li>건강보험심사평가원 제출</li>
      <li>통계/연구 목적 가공 제공</li>
      <li>수사기관 요청 시 법적 절차에 따라 제공</li>
    </ul>
  </section>

  <section id="section6">
    <h2>6. 수집한 개인정보의 취급위탁</h2>
    <p>안전한 관리를 위해 계약 체결 및 관리합니다.</p>
    <div class="max-w-full overflow-x-auto">
      <table class="min-w-[650px] table-auto border-collapse border">
        <colgroup>
          <col width="175px">
          <col width="300px">
          <col width="175px">
        </colgroup>
        <thead>
          <tr>
            <th class="border px-4 py-2">
              수탁업체
            </th>
            <th class="border px-4 py-2">
              위탁업무 내용
            </th>
            <th class="border px-4 py-2">
              보유 및 이용기간
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="border px-4 py-2">
              주식회사 위잉코퍼레이션
            </td>
            <td class="border px-4 py-2">
              홈페이지 유지관리, 접속기록 관리, 회원관리
            </td>
            <td class="border px-4 py-2">
              위탁 계약 종료 시까지
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>

  <section id="section7">
    <h2>7. 이용자 및 법정대리인의 권리와 행사방법</h2>
    <p>
      만 14세 미만 아동 가입 시 법정대리인 동의가 필요합니다.
      법정대리인은 아동 개인정보 열람, 정정, 삭제를 요청할 수 있습니다.
    </p>
    <p class="text-gray-500">
      ※ 법에 의해 보관 의무가 있는 정보는 수정/삭제 불가합니다.
    </p>
  </section>

  <section id="section8">
    <h2>8. 동의철회 / 회원탈퇴 방법</h2>
    <p>
      마이페이지의 회원탈퇴 메뉴를 통해 신청하거나, 서면/전화/Fax로 요청 가능합니다.
      요청 후 10일 이내 처리되며, 개인정보는 즉시 파기됩니다.
    </p>
  </section>

  <section id="section9">
    <h2>9. 개인정보 자동 수집 장치의 설치/운영 및 거부</h2>
    <p>
      본원은 쿠키를 사용하여 이용자 정보를 저장하고 관리합니다.
    </p>
    <ul class="list-disc">
      <li>쿠키 목적: 방문자 분석, 맞춤형 서비스 제공</li>
      <li>설정: 브라우저에서 쿠키 허용/거부 가능</li>
    </ul>
    <p class="text-gray-500">
      ※ 쿠키 거부 시 일부 서비스 이용에 제한이 있을 수 있습니다.
    </p>
  </section>

  <section id="section10">
    <h2>10. 개인정보관리책임자</h2>
    <p>
      귀하의 개인정보를 보호하고 개인정보와 관련한 불만을 처리하기 위하여 본원은 아래와 같이 개인정보관리책임자를 두고 있습니다.
    </p>
    <div>
      <ul class="list-none">
        <li><strong>성명:</strong> 김광수</li>
        <li><strong>직위:</strong> 대표원장</li>
        <li><strong>소속:</strong> 에이포의원</li>
        <li><strong>연락처:</strong> 02-6104-1199</li>
      </ul>
    </div>
    <p>
      귀하는 본원의 서비스 이용 중 발생하는 모든 개인정보보호 관련 민원을 개인정보관리책임자에게 신고하실 수 있으며, 신속하고 충분한 답변을 받으실 수 있습니다.
    </p>
    <p>
      기타 개인정보침해 신고나 상담이 필요하신 경우 아래 기관에 문의하시기 바랍니다.
    </p>
    <ul class="list-disc">
      <li>
        개인분쟁조정위원회 (<a
          href="http://www.1336.or.kr"
          target="_blank"
          class="text-blue-600 underline"
        >http://www.1336.or.kr</a>
        / 1336)
      </li>
      <li>
        정보보호마크인증위원회 (<a
          href="http://www.eprivacy.or.kr"
          target="_blank"
          class="text-blue-600 underline"
        >http://www.eprivacy.or.kr</a>
        / 02-580-0533~4)
      </li>
      <li>
        대검찰청 사이버범죄수사단 (<a
          href="http://www.spo.go.kr"
          target="_blank"
          class="text-blue-600 underline"
        >http://www.spo.go.kr</a>
        / 02-3480-3573)
      </li>
      <li>
        경찰청 사이버테러대응센터 (<a
          href="http://www.ctrc.go.kr"
          target="_blank"
          class="text-blue-600 underline"
        >http://www.ctrc.go.kr</a>
        / 02-392-0330)
      </li>
    </ul>
  </section>

  <section id="section11">
    <h2>11. 개인정보의 안전성 확보조치</h2>
    <p>
      본원은 개인정보의 분실, 도난, 누출, 변조 또는 훼손 방지를 위해 다음과 같은 기술적/관리적 대책을 시행하고 있습니다.
    </p>

    <div>
      <h3>
        ㄱ. 비밀번호 암호화
      </h3>
      <p>
        비밀번호는 암호화되어 저장되며, 본인만 알고 있습니다. 본인 확인 후 변경 요청이 가능합니다.
      </p>
    </div>

    <div>
      <h3>
        ㄴ. 해킹 등에 대비한 대책
      </h3>
      <p>
        백업, 백신프로그램, 암호화 저장, 암호화 통신, 침입차단시스템 등을 통해 개인정보 보호를 강화하고 있습니다.
      </p>
    </div>

    <div>
      <h3>
        ㄷ. 개인정보 취급 직원의 최소화 및 교육
      </h3>
      <p>
        개인정보 취급자는 담당자에 한정되며, 비밀번호 관리 및 정기 교육을 통해 개인정보 보호를 강화하고 있습니다.
      </p>
      <p class="text-gray-500">
        ※ 이용자 부주의 또는 인터넷 문제로 발생한 개인정보 유출에 대해서는 책임을 지지 않습니다.
      </p>
    </div>
  </section>

  <section id="section12">
    <h2>12. 정책 변경에 따른 공지의무</h2>
    <p>
      본 개인정보취급방침은 법령, 정책, 보안기술의 변경에 따라 수정될 수 있으며, 변경 시 최소 7일 전에 본원 홈페이지를 통해 공지합니다.
    </p>
  </section>

  <hr>

  <footer>
    <p>
      시행일자 : 2023년 5월 30일
    </p>
  </footer>
</template>
