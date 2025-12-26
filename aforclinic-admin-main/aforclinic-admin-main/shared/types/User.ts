export type UserRole =
  'ADMIN' |
  'MANAGER' |
  'USER';

export type UserStatus =
  'PENDING' |
  'ACTIVE' |
  'INACTIVE' |
  'DELETED';

export interface User {
  /**
   * 사용자 식별자
   */
  id: string;
  /**
   * 로그인 식별자
   */
  username: string;
  /**
   * 비밀번호
   */
  password?: string;
  /**
   * 역할
   */
  role: UserRole;
  /**
   * 상태
   */
  status: UserStatus;
  /**
   * 사용자명
   */
  nickname?: string;
  /**
   * 생년월일
   */
  birthDate?: string;
  /**
   * 핸드폰 번호
   */
  phoneNumber?: string;
  /**
   * 제공자
   */
  provider?: string;
  /**
   * 마지막 로그인 일시
   */
  lastLoginAt?: string;
  /**
   * 등록일시
   */
  createdAt: string;
  /**
   * 등록자
   */
  createdBy: string;
  /**
   * 수정일시
   */
  modifiedAt: string;
  /**
   * 수정자
   */
  modifiedBy: string;
}

export const UserRoleDisplay: Record<UserRole, string> = {
  ADMIN: '관리자',
  MANAGER: '매니저',
  USER: '사용자',
};

export const UserRoleOptions = [
  { label: '관리자', value: 'ADMIN' },
  { label: '매니저', value: 'MANAGER' },
  { label: '사용자', value: 'USER' },
];

export function getUserRoleDisplay(role: UserRole): string {
  return UserRoleDisplay[role] || role;
}
