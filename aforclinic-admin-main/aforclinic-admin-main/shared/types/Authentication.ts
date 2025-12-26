export interface AuthenticationSuccessResponse {
  /**
   * 액세스 토큰
   */
  accessToken: string;
  /**
   * 리프레시 토큰
   */
  refreshToken: string;
}
