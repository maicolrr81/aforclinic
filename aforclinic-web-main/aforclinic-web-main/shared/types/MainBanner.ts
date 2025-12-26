export type MainBannerStatus = 'ACTIVE' | 'DELETED';

export interface MainBanner {
  /**
   * 순번
   */
  sequence?: number;
  /**
   * 메인화면 배너 식별자
   */
  id: string;
  /**
   * 제목
   */
  title: string;
  /**
   * 이미지 식별자
   */
  imageId: string;
  /**
   * 링크
   */
  linkUri: string;
  /**
   * 화면 표시 순서
   */
  displayOrder?: number;
  /**
   * 상태
   */
  status?: MainBannerStatus;
  /**
   * 등록일시
   */
  createdAt?: string;
  /**
   * 등록자
   */
  createdBy?: string;
  /**
   * 수정일시
   */
  modifiedAt?: string;
  /**
   * 수정자
   */
  modifiedBy?: string;
};

export type CreateMainBanner = Pick<MainBanner, 'title' | `imageId` | `linkUri` | 'displayOrder'>;
export type UpdateMainBanner = Pick<MainBanner, 'title' | 'imageId' | `linkUri` | 'displayOrder' | 'status'>;
export type UpdateMainBannerStatus = Pick<MainBanner, 'status'>;
export type ReorderMainBanner = Pick<MainBanner, 'id' | 'displayOrder'>;
