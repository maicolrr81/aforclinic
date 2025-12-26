package com.xenialsoft.api.core.auth.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUserInfo {

    /**
     * 회원번호
     */
    private final String id;

    /**
     * 서비스 연결 완료된 시각(UTC+0)
     */
    private final LocalDateTime connectedAt;

    /**
     * 카카오계정 정보
     */
    private final KakaoAccount account;

    @SuppressWarnings("unchecked")
    public static KakaoUserInfo from(Map<String, Object> attributes) {
        String id = (String) attributes.get("id");
        String connectedAtStr = (String) attributes.get("connected_at");
        LocalDateTime connectedAt = connectedAtStr != null
                ? Instant.parse(connectedAtStr).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
                : null;
        KakaoAccount account = KakaoAccount.from((Map<String, Object>) attributes.get("kakao_account"));
        return new KakaoUserInfo(id, connectedAt, account);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class KakaoAccount {

        /**
         * 사용자 동의 시 프로필 정보(닉네임/프로필 사진) 제공 가능
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진)
         */
        private final Boolean profileNeedsAgreement;
        /**
         * 사용자 동의 시 닉네임 제공 가능
         * 
         * 필요한 동의항목: 닉네임
         */
        private final Boolean profileNicknameNeedsAgreement;
        /**
         * 사용자 동의 시 프로필 사진 제공 가능
         * 
         * 필요한 동의항목: 프로필 사진
         */
        private final Boolean profileImageNeedsAgreement;
        /**
         * 프로필 정보
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진), 닉네임, 프로필 사진
         */
        private final KakaoProfile profile;

        /**
         * 사용자 동의 시 카카오계정 이름 제공 가능
         * 
         * 필요한 동의항목: 이름
         */
        private final Boolean nameNeedsAgreement;
        /**
         * 카카오계정 이름
         * 
         * 필요한 동의항목: 이름
         */
        private final String name;

        /**
         * 사용자 동의 시 카카오계정 대표 이메일 제공 가능
         * 
         * 필요한 동의항목: 카카오계정(이메일)
         */
        private final Boolean emailNeedsAgreement;
        /**
         * 이메일 유효 여부 true: 유효한 이메일 false: 이메일이 다른 카카오계정에 사용돼 만료
         * 
         * 필요한 동의항목: 카카오계정(이메일)
         */
        private final Boolean emailValid;
        /**
         * 이메일 인증 여부 true: 인증된 이메일 false: 인증되지 않은 이메일
         * 
         * 필요한 동의항목: 카카오계정(이메일)
         */
        private final Boolean emailVerified;
        /**
         * 카카오계정 대표 이메일
         * 
         * 필요한 동의항목: 카카오계정(이메일) 중요: 이메일 사용 시 주의 사항
         */
        private final String email;

        /**
         * 사용자 동의 시 연령대 제공 가능
         * 
         * 필요한 동의항목: 연령대
         */
        private final Boolean ageRangeNeedsAgreement;
        /**
         * 연령대
         * <ul>
         * <li>1~9: 1세 이상 10세 미만</li>
         * <li>10~14: 10세 이상 15세 미만</li>
         * <li>15~19: 15세 이상 20세 미만</li>
         * <li>20~29: 20세 이상 30세 미만</li>
         * <li>30~39: 30세 이상 40세 미만</li>
         * <li>40~49: 40세 이상 50세 미만</li>
         * <li>50~59: 50세 이상 60세 미만</li>
         * <li>60~69: 60세 이상 70세 미만</li>
         * <li>70~79: 70세 이상 80세 미만</li>
         * <li>80~89: 80세 이상 90세 미만</li>
         * <li>90~: 90세 이상</li>
         * </ul>
         * 
         * 필요한 동의항목: 연령대
         */
        private final String ageRange;

        /**
         * 사용자 동의 시 출생 연도 제공 가능
         * 
         * 필요한 동의항목: 출생 연도
         */
        private final Boolean birthyearNeedsAgreement;
        /**
         * 출생 연도(YYYY 형식)
         * 
         * 필요한 동의항목: 출생 연도
         */
        private final String birthyear;

        /**
         * 사용자 동의 시 생일 제공 가능
         * 
         * 필요한 동의항목: 생일
         */
        private final Boolean birthdayNeedsAgreement;
        /**
         * 생일(MMDD 형식)
         * 
         * 필요한 동의항목: 생일
         */
        private final String birthday;
        /**
         * 생일 타입 SOLAR(양력) 또는 LUNAR(음력)
         * 
         * 필요한 동의항목: 생일
         */
        private final String birthdayType;
        /**
         * 생일의 윤달 여부
         * 
         * 필요한 동의항목: 생일
         */
        private final Boolean leapMonth;

        /**
         * 사용자 동의 시 성별 제공 가능
         * 
         * 필요한 동의항목: 성별
         */
        private final Boolean genderNeedsAgreement;
        /**
         * 성별 female: 여성 male: 남성
         * 
         * 필요한 동의항목: 성별
         */
        private final String gender;

        /**
         * 사용자 동의 시 전화번호 제공 가능
         * 
         * 필요한 동의항목: 카카오계정(전화번호)
         */
        private final Boolean phoneNumberNeedsAgreement;
        /**
         * 카카오계정의 전화번호 국내 번호인 경우 +82 00-0000-0000 형식 해외 번호인 경우 자릿수, 붙임표(-) 유무나 위치가 다를 수
         * 있음 (참고: libphonenumber)
         * 
         * 필요한 동의항목: 카카오계정(전화번호)
         */
        private final String phoneNumber;

        /**
         * 사용자 동의 시 CI 참고 가능
         * 
         * 필요한 동의항목: CI(연계정보)
         */
        private final Boolean ciNeedsAgreement;
        /**
         * 연계정보
         * 
         * 필요한 동의항목: CI(연계정보)
         */
        private final String ci;
        /**
         * CI 발급 시각, UTC*
         * 
         * 필요한 동의항목: CI(연계정보)
         */
        private final LocalDateTime ciAuthenticatedAt;

        @SuppressWarnings("unchecked")
        public static KakaoAccount from(Map<String, Object> attributes) {
            if (attributes == null) {
                return null;
            }

            Boolean profileNeedsAgreement = (Boolean) attributes.get("profile_needs_agreement");
            Boolean profileNicknameNeedsAgreement = (Boolean) attributes.get("profile_nickname_needs_agreement");
            Boolean profileImageNeedsAgreement = (Boolean) attributes.get("profile_image_needs_agreement");
            KakaoProfile profile = KakaoProfile.from((Map<String, Object>) attributes.get("profile"));

            Boolean nameNeedsAgreement = (Boolean) attributes.get("name_needs_agreement");
            String name = (String) attributes.get("name");

            Boolean emailNeedsAgreement = (Boolean) attributes.get("email_needs_agreement");
            Boolean emailValid = (Boolean) attributes.get("is_email_valid");
            Boolean emailVerified = (Boolean) attributes.get("is_email_verified");
            String email = (String) attributes.get("email");

            Boolean ageRangeNeedsAgreement = (Boolean) attributes.get("age_range_needs_agreement");
            String ageRange = (String) attributes.get("age_range");

            Boolean birthyearNeedsAgreement = (Boolean) attributes.get("birthyear_needs_agreement");
            String birthyear = (String) attributes.get("birthyear");

            Boolean birthdayNeedsAgreement = (Boolean) attributes.get("birthday_needs_agreement");
            String birthday = (String) attributes.get("birthday");
            String birthdayType = (String) attributes.get("birthday_type");
            Boolean leapMonth = (Boolean) attributes.get("is_leap_month");

            Boolean genderNeedsAgreement = (Boolean) attributes.get("gender_needs_agreement");
            String gender = (String) attributes.get("gender");

            Boolean phoneNumberNeedsAgreement = (Boolean) attributes.get("phone_number_needs_agreement");
            String phoneNumber = (String) attributes.get("phone_number");

            Boolean ciNeedsAgreement = (Boolean) attributes.get("ci_needs_agreement");
            String ci = (String) attributes.get("ci");
            String ciAuthenticatedAtStr = (String) attributes.get("ci_authenticated_at");
            LocalDateTime ciAuthenticatedAt = ciAuthenticatedAtStr != null
                    ? Instant.parse(ciAuthenticatedAtStr).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
                    : null;

            return new KakaoAccount(profileNeedsAgreement, profileNicknameNeedsAgreement, profileImageNeedsAgreement,
                    profile, nameNeedsAgreement, name, emailNeedsAgreement, emailValid, emailVerified, email,
                    ageRangeNeedsAgreement, ageRange, birthyearNeedsAgreement, birthyear, birthdayNeedsAgreement,
                    birthday, birthdayType, leapMonth, genderNeedsAgreement, gender, phoneNumberNeedsAgreement,
                    phoneNumber, ciNeedsAgreement, ci, ciAuthenticatedAt);
        }

    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class KakaoProfile {

        /**
         * 닉네임
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 닉네임
         */
        private final String nickname;
        /**
         * 프로필 미리보기 이미지 URL 110px * 110px 또는 100px * 100px
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
         */
        private final String thumbnailImageUrl;
        /**
         * 프로필 사진 URL 640px * 640px 또는 480px * 480px
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
         */
        private final String profileImageUrl;
        /**
         * 프로필 사진 URL이 기본 프로필 사진 URL인지 여부 사용자가 등록한 프로필 사진이 없을 경우, 기본 프로필 사진 제공 true: 기본
         * 프로필 사진 false: 사용자가 등록한 프로필 사진
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
         */
        private final Boolean defaultImage;
        /**
         * 닉네임이 기본 닉네임인지 여부 사용자가 등록한 닉네임이 운영정책에 부합하지 않는 경우, "닉네임을 등록해주세요"가 기본 닉네임으로 적용됨
         * true: 기본 닉네임 false: 사용자가 등록한 닉네임
         * 
         * 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 닉네임
         */
        private final Boolean defaultNickname;

        public static KakaoProfile from(Map<String, Object> attributes) {
            if (attributes == null) {
                return null;
            }

            String nickname = (String) attributes.get("nickname");
            String thumbnailImageUrl = (String) attributes.get("thumbnail_image_url");
            String profileImageUrl = (String) attributes.get("profile_image_url");
            Boolean defaultImage = (Boolean) attributes.get("is_default_image");
            Boolean defaultNickname = (Boolean) attributes.get("is_default_nickname");

            return new KakaoProfile(nickname, thumbnailImageUrl, profileImageUrl, defaultImage, defaultNickname);
        }

    }

}
