<script setup lang="ts">
definePageMeta({ layout: `policy` });

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `이용약관 - 에이포의원`,
  description: `이용약관 - 2023년 5월 30일 개정`,

  // Open Graph
  ogTitle: `이용약관 - 에이포의원`,
  ogDescription: `이용약관 - 2023년 5월 30일 개정`,
  ogImage: `${runtimeConfig.public.siteBase}/logo.png`,
  ogImageWidth: `198`,
  ogImageHeight: `60`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: 'website',
  ogLocale: 'ko_KR',
  ogSiteName: '에이포의원',

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `이용약관 - 에이포의원`,
  twitterDescription: `이용약관 - 2023년 5월 30일 개정`,
  twitterImage: `${runtimeConfig.public.siteBase}/logo.png`,
});
</script>

<template>
  <header>
    <h1>이용약관</h1>
  </header>

  <nav>
    <h2 class="text-lg font-semibold">
      이용약관의 순서는 다음과 같습니다.
    </h2>
    <ol class="list-inside list-disc">
      <li><a href="#chapter1">제1장 총칙</a></li>
      <li><a href="#chapter2">제2장 회원 가입과 서비스 이용</a></li>
      <li><a href="#chapter3">제3장 서비스 탈퇴, 재가입 및 이용 제한</a></li>
      <li><a href="#chapter4">제4장 서비스에 관한 책임의 제한</a></li>
      <li><a href="#chapter5">제5장 의무</a></li>
      <li><a href="#chapter6">제6장 분쟁조정</a></li>
      <li><a href="#footer">부칙</a></li>
    </ol>
  </nav>

  <section id="chapter1">
    <h2>제1장 총칙</h2>

    <h3>
      제1조 목적
    </h3>
    <p>
      이 약관은 에이포의원(이하 ‘본원’)에서 제공하는 서비스 이용조건 및 절차에 관한 사항과 기타 필요한 사항을 본원과 이용자의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.
    </p>

    <h3>
      제2조 약관의 효력과 변경
    </h3>
    <ol class="list-decimal">
      <li>이 약관은 이용자에게 공시함으로써 효력이 발생합니다.</li>
      <li>본원은 사정 변경이나 영업상 중요 사유가 있을 때 약관을 변경할 수 있으며, 변경된 약관은 동일하게 공시하여 효력이 발생합니다.</li>
    </ol>

    <h3>
      제3조 약관 외 준칙
    </h3>
    <p>이 약관에 명시되지 않은 사항은 관계법령에 따릅니다.</p>
  </section>

  <section id="chapter2">
    <h2>제2장 회원 가입과 서비스 이용</h2>

    <h3>
      제1조 회원의 정의
    </h3>
    <p>
      회원이란 본원에서 회원으로 적합하다고 인정하는 일반 개인으로, 본 약관에 동의하고 회원가입 양식을 작성하여 'ID'와 '비밀번호'를 발급받은 사람을 말합니다.
    </p>

    <h3>
      제2조 서비스 가입의 성립
    </h3>
    <ol class="list-decimal">
      <li>서비스 가입은 이용자의 신청과 본원의 승낙, 약관 동의로 성립됩니다.</li>
      <li>회원가입 시 요청하는 개인정보를 제공해야 합니다.</li>
      <li>승낙 후 ID 및 필요한 정보를 안내합니다.</li>
      <li>ID는 변경 불가하며, 1인당 1개의 ID만 발급됩니다.</li>
      <li>
        다음의 경우 가입이 거절됩니다:
        <ul class="list-disc">
          <li>타인 명의 사용</li>
          <li>실명 미사용</li>
          <li>허위 정보 기재</li>
          <li>사회 질서 저해 목적</li>
        </ul>
      </li>
    </ol>

    <h3>
      제3조 서비스 이용 및 제한
    </h3>
    <ol class="list-decimal">
      <li>서비스는 특별한 문제 없으면 24시간 이용 가능합니다.</li>
      <li>정기 점검 등으로 제한될 수 있습니다.</li>
      <li>온라인 상담은 담당자 사정에 따라 제한될 수 있습니다.</li>
    </ol>

    <h3>
      제4조 서비스의 사용료
    </h3>
    <ol class="list-decimal">
      <li>회원에게 무료 제공됩니다.</li>
      <li>유료화 시 사전 공지합니다.</li>
    </ol>
  </section>

  <section id="chapter3">
    <h2>제3장 서비스 탈퇴, 재가입 및 이용 제한</h2>

    <h3>
      제1조 서비스 탈퇴
    </h3>
    <p>회원 본인이 전자메일로 탈퇴 신청을 하면, 본인 확인 후 탈퇴 처리합니다.</p>

    <h3>
      제2조 서비스 재가입
    </h3>
    <p>탈퇴한 회원이 재가입을 원할 경우, 본인 확인 후 재가입할 수 있습니다.</p>

    <h3>
      제3조 서비스 이용제한
    </h3>
    <p>다음 행위 발생 시 사전 통지 없이 서비스 이용이 중단될 수 있습니다.</p>
    <ul class="list-disc">
      <li>공공 질서 위반</li>
      <li>범죄 행위 관련</li>
      <li>사회적 공익 저해</li>
      <li>타인 ID 도용</li>
      <li>명예 훼손</li>
      <li>이중 가입</li>
      <li>서비스 방해 행위</li>
      <li>기타 약관 위반</li>
    </ul>
  </section>

  <section id="chapter4">
    <h2>제4장 서비스에 관한 책임의 제한</h2>

    <h3>
      제1조 온라인상담
    </h3>
    <p>본원은 보안을 위해 노력하지만, 다음 사유로 인한 상담 내용 공개에 책임지지 않습니다.</p>
    <ul class="list-disc">
      <li>사용자 부주의로 인한 암호 유출</li>
      <li>삭제 기능 사용</li>
      <li>천재지변 등 불가항력</li>
    </ul>

    <h3>
      제2조 정보 서비스
    </h3>
    <p>
      본 서비스 정보는 참고용이며, 의학적 진단을 대신하지 않습니다. 실제 진단이 필요할 경우 전문의를 찾아야 합니다.
    </p>
  </section>

  <section id="chapter5">
    <h2>제5장 의무</h2>

    <h3>
      제1조 회사의 의무
    </h3>
    <p>본원은 안정적 서비스 제공과 회원의 의견을 처리할 의무가 있습니다.</p>

    <h3>
      제2조 회원정보 보안의 의무
    </h3>
    <p>
      ID, 비밀번호 관리는 회원 책임이며, 개인정보 보호 및 쿠키 사용 등에 동의합니다.
    </p>
  </section>

  <section id="chapter6">
    <h2>제6장 분쟁조정</h2>
    <ol class="list-decimal">
      <li>분쟁은 최대한 합의로 해결합니다.</li>
      <li>소송이 필요한 경우 회사 소재지 관할 법원으로 합니다.</li>
    </ol>
  </section>

  <hr>

  <footer id="footer">
    <p><strong>부칙:</strong> 이 약관은 2023년 5월 30일부터 시행합니다.</p>
    <!-- <NuxtLink
        to="/terms/[slug]"
        class="mt-2 text-sm text-blue-500"
      >
        이전 이용약관 보기
      </NuxtLink> -->
  </footer>
</template>
