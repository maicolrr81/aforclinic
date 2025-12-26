/**
 * 예약 타입
 */
export type AppointmentType =
  | 'EVENT' // 이벤트
  | 'QUICK' // 바로 예약
  | 'PROCEDURE'; // 시술 예약;

/**
 * 예약 기본 정보
 */
export interface Appointment {
  /** 예약 식별자 */
  id: string;
  /** 예약 타입 */
  type: AppointmentType;

  /** 예약자명 */
  nickname?: string;
  /** 예약자 연락처 */
  contact?: string;
  /** 예약 내용 */
  content?: string;
  /** 예약일 */
  date?: string;

  /** 개인정보처리방침 동의 */
  privacyAgreed?: boolean;
  /** 14세 이상 여부 */
  ageConfirmed?: boolean;
  /** 마케팅 수신 동의 */
  marketingAgreed?: boolean;

  /** 날짜 변경 또는 취소 가능 여부 */
  editable?: boolean;
  /** 취소 여부 */
  cancel?: boolean;
  /** 과거 예약 여부 */
  past?: boolean;
}

/**
 * 바로 예약 타입
 */
export interface QuickAppointment extends Omit<Appointment, 'type'> {
  type: 'QUICK';
};

/**
 * 바로 예약 생성 요청 타입
 */
export type CreateQuickAppointment = Pick<
  QuickAppointment,
  'type' | 'nickname' | 'contact' | 'content' | 'date' | 'privacyAgreed' | 'ageConfirmed' | 'marketingAgreed'
>;

/**
 * 시술 예약 타입
 */
export interface ProcedureAppointment extends Omit<Appointment, 'type'> {
  type: 'PROCEDURE';
};

/**
 * 시술 예약 생성 요청 타입
 */
export type CreateProcedureAppointment = Pick<
  ProcedureAppointment,
  'type' | 'content' | 'date' | 'privacyAgreed' | 'ageConfirmed' | 'marketingAgreed'
>;

/**
 * 이벤트 예약 타입
 */
export interface EventAppointment extends Omit<Appointment, 'type'> {
  type: 'EVENT';
};

/**
 * 이벤트 예약 생성 요청 타입
 */
export type CreateEventAppointment = Pick<
  EventAppointment,
  'type' | 'nickname' | 'contact' | 'content'
>;

/**
 * 간단 이벤트 예약 생성 요청 타입
 */
export type CreateSimpleEventAppointment = Pick<
  CreateEventAppointment,
  'type' | 'content'
>;

/**
 * 예약 날짜 변경
 */
export interface RescheduleAppointment {
  date: string;
}

/**
 * 예약 취소
 */
export interface CancelAppointment {
  status: 'CANCEL';
};
