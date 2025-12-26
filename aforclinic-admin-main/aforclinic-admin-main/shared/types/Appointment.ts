/**
 * 예약 타입
 */
export type AppointmentType =
  | 'EVENT' // 이벤트
  | 'QUICK' // 바로 예약
  | 'PROCEDURE' // 시술 예약
;

/**
 * 예약 상태
 */
export type AppointmentStatus =
  | 'PENDING' // 접수
  | 'CONFIRMED' // 예약 성공
  | 'FOLLOW_UP' // 재연락
  | 'NO_ANSWER_1' // 부재1
  | 'NO_ANSWER_2' // 부재2
  | 'NO_ANSWER_3' // 부재3
  | 'NO_ANSWER_4' // 부재4
  | 'NO_ANSWER_KAKAO_1' // 부재 > 카톡1
  | 'NO_ANSWER_KAKAO_2'// 부재 > 카톡2
  | 'REJECTED' // 거절
  | 'DUPLICATED' // 중복
  | 'INVALID_NUMBER' // 결번
  | 'CLOSED' // 마감
  | 'CANCEL' // 취소
  | 'DELETED' // 삭제
;

/**
 * 예약 기본 정보
 */
export interface Appointment {
  /** 예약 식별자 */
  id: string;

  /** 예약 타입 */
  type: AppointmentType;

  /** 이벤트명 (EVENT 타입 한정) */
  eventTitle?: string;

  /** 예약자명 */
  nickname?: string;

  /** 예약자 연락처 */
  contact?: string;

  /** 예약 내용 */
  content?: string;

  /** 예약일자 */
  date?: string;

  /** 메모 (관리자용) */
  memo?: string;

  /** 예약 상태 */
  status?: AppointmentStatus;

  /** 개인정보처리방침 동의 여부 */
  privacyAgreed?: boolean;

  /** 14세 이상 여부 */
  ageConfirmed?: boolean;

  /** 마케팅 수신 동의 여부 */
  marketingAgreed?: boolean;

  /** 등록일시 */
  createdAt?: string;

  /** 등록자 */
  createdBy?: string;

  /** 수정일시 */
  modifiedAt?: string;

  /** 수정자 */
  modifiedBy?: string;
}

export type UpdateAppointmentWithId = Pick<Appointment, 'id' | 'memo' | 'status'>;
export type UpdateAppointment = Omit<UpdateAppointmentWithId, 'id'>;

export type UpdateAppointmentMemoWithId = Pick<UpdateAppointmentWithId, 'id' | 'memo'>;
export type UpdateAppointmentMemo = Omit<UpdateAppointmentMemoWithId, 'id'>;

export type UpdateAppointmentStatusWithId = Pick<UpdateAppointmentWithId, 'id' | 'status'>;
export type UpdateAppointmentStatus = Omit<UpdateAppointmentStatusWithId, 'id'>;
