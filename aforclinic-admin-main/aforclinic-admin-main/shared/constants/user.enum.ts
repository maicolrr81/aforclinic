function toOptions<T extends Record<string, string>>(obj: T) {
  return Object.entries(obj).map(([value, text]) => ({ value, text }));
}

export const USER_ROLE = {
  ADMIN: '관리자',
  MANAGER: '매니저',
  USER: '회원',
} as const;

export type UserRole = keyof typeof USER_ROLE;
export type UserRoleName = typeof USER_ROLE[keyof typeof USER_ROLE];

export const USER_ROLE_OPTIONS = toOptions(USER_ROLE);

export const USER_ADMIN_ROLE = {
  ADMIN: '관리자',
  MANAGER: '매니저',
} as const;

export type UserAdminRole = keyof typeof USER_ADMIN_ROLE;
export type UserAdminRoleName = typeof USER_ADMIN_ROLE[keyof typeof USER_ADMIN_ROLE];

export const USER_ADMIN_ROLE_OPTIONS = toOptions(USER_ADMIN_ROLE);

export const USER_STATUS = {
  ACTIVE: '활성',
  DELETED: '탈퇴',
} as const;

export type UserStatus = keyof typeof USER_STATUS;
export type UserStatusName = typeof USER_STATUS[keyof typeof USER_STATUS];

export const USER_STATUS_OPTIONS = toOptions(USER_STATUS);
