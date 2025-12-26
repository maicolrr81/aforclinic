export const ROLES: Record<Role, string> = {
  ADMIN: `관리자`,
  MANAGER: `매니저`,
  USER: `회원`,
};

export function toRoleName(role: Role) {
  return ROLES[role] || ``;
}
