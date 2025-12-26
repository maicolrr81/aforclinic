export type BeforeAfterBannerMediaType =
  'IMAGE' |
  'VIDEO';

export interface BeforeAfterBanner {
  /** 전후사진 배너 식별자 */
  id: string;
  /** 전후사진 배너 미디어 타입 */
  mediaType: BeforeAfterBannerMediaType;
  /** 전후사진 배너 미디어 정보 */
  mediaContent: string;
}

export interface BeforeAfter {
  /** 전후사진 식별자 */
  id: string;
  /** 전후사진 제목 */
  title: string;
  /** 전후사진 설명 */
  description: string;
  /** 전후사진 */
  image: string;
  /** 전 사진 */
  beforeImage?: string;
  /** 후 사진 */
  afterImage?: string;
}
