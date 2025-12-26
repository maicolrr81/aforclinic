export interface ApiPageRequest {
  /**
   * 페이지
   *
   * @default 1
   */
  page?: number;
  /**
   * 사이즈
   *
   * @default 10
   */
  size?: number;
}

export interface ApiResponse<T = any> {
  /**
   * 응답 데이터
   */
  data: T;
  /**
   * 응답 코드
   */
  code?: string;
  /**
   * 응답 메시지
   */
  message?: string;
  /**
   * 응답 시간
   */
  timestamp: string;
}

export type ApiErrorResponse = Pick<
  ApiResponse,
  'code' | 'message' | 'timestamp'
>;

export type ApiPagedResponse<T> = ApiResponse<PagedModel<T>>;

export interface PagedModel<T> {
  /**
   * 응답 데이터
   */
  content: T[];
  /**
   * 페이지네이션
   */
  page: {
    /**
     * 페이지
     */
    number: number;
    /**
     * 사이즈
     */
    size: number;
    /**
     * 총 개수
     */
    totalElements: number;
    /**
     * 총 페이지 개수
     */
    totalPages: number;
  };
}
