export type UserRole = 'ADMIN' | 'MANAGER' | 'USER';

export interface User {
  /**
   * 사용자 식별자
   */
  id: string;
  /**
   * 계정 식별자
   */
  username?: string;
  /**
   * 비밀번호
   */
  password?: string;
  /**
   * 사용자명
   */
  nickname: string;
  /**
   * 역할
   */
  role: UserRole;
  /**
   * 핸드폰 번호
   */
  phoneNumber?: string;
  /**
   * 생년월일(YYYY-MM-DD)
   */
  birthDate?: string;
  /**
   * 가입일자(YYYY-MM-DD)
   */
  createdAt: string;
  /**
   * 수정일자(YYYY-MM-DD)
   */
  modifiedAt: string;

  /**
   * 동의 항목
   */
  agreements: UserAgreement[];
}

export type CreateUser = Required<Pick<
  User,
  'username' | 'password' | 'nickname' | 'phoneNumber'
> & {
  agreements: CreateUserAgreement[];
}>;

export type UpdateUser = Pick<
  User,
  'nickname' | 'phoneNumber'
>;

export interface UserAgreement {
  /**
   * 동의항목 식별자
   */
  id: number;
  /**
   * 사용자 식별자
   */
  userId: string;
  /**
   * 동의항목
   */
  type: 'TERMS_OF_SERVICE' | 'PRIVACY_POLICY' | 'MARKETING';
  /**
   * 동의여부
   */
  agreed: boolean;
}

export type CreateUserAgreement = Pick<
  UserAgreement,
  'type' | 'agreed'
>;
