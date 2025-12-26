import type { Category } from './Product';

export type BeforeAfterBannerMediaType = `IMAGE` | `VIDEO`;
export type BeforeAfterBannerStatus = `ACTIVE` | `DELETED`;
export interface BeforeAfterBanner {
  /**
   * 식별자
   */
  id: string;
  /**
   * 제목
   */
  title: string;
  /**
   * 설명
   */
  description?: string;

  mediaType: BeforeAfterBannerMediaType;

  mediaContent: string;

  status?: BeforeAfterBannerStatus;
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
}

export type BeforeAfterStatus = `ACTIVE` | `DELETED`;

export interface BeforeAfter {
  /**
   * 식별자
   */
  id: string;
  /**
   * 카테고리 식별자
   */
  categoryId: string;
  /**
   * 제목
   */
  title: string;
  /**
   * 설명
   */
  description?: string;
  /**
   * 이미지 식별자
   */
  image?: string;
  /**
   * 전 이미지 식별자
   */
  beforeImage?: string;
  /**
   * 후 이미지 식별자
   */
  afterImage?: string;
  /**
   * 후 흐림 이미지 식별자
   */
  afterBlurImage?: string;
  /**
   * 상태
   */
  status?: BeforeAfterStatus;
  /**
   * 카테고리 목록
   */
  beforeAfterCategoriesList?: BeforeAfterCategories[];
  /**
   * 태그 목록
   */
  beforeAfterTagsList?: BeforeAfterTags[];
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
}

export type CreateBeforeAfter = Pick<BeforeAfter, `title` | `description` | `image` | `beforeImage` | `afterImage` | `afterBlurImage` | `beforeAfterCategoriesList` | `beforeAfterTagsList`>;
export type UpdateBeforeAfter = Pick<BeforeAfter, `title` | `description` | `image` | `beforeImage` | `afterImage` | `afterBlurImage` | `beforeAfterCategoriesList` | `beforeAfterTagsList`>;

// export type UpdateBeforeAfter = Pick<BeforeAfter, `title` | `description` | `imageId` | `startDate` | `endDate` | `status` | `eventProductsList`>;

// export type UpdateBeforeAfterStatus = Pick<UpdateBeforeAfter, `status`>;

export interface BeforeAfterCategories {

  beforeAfterId: string;

  categoryId: string;

  category: Category | null;
}

export interface BeforeAfterTags {

  beforeAfterId: string;

  tag: string;
}
