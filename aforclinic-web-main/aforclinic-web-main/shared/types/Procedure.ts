export interface Procedure {
/**
 * 상품 식별자
 */
  id: string;
  /**
   * 상품 카테고리 식별자
   */
  categoryId: string;
  /**
   * 상품 카테고리명
   */
  categoryName: string;
  /**
   * 번들 여부
   */
  bundle: boolean;
  /**
   * 상품명
   */
  name: string;
  /**
   * 상품 설명
   */
  description?: string;
  /**
   * 이미지
   */
  image: string;
  /**
   * 프로모션 여부
   */
  isPromotion: boolean;
  /**
   * 정가
   */
  regularPrice: number;
  /**
   * 행사가
   */
  promotionPrice: number;
}

export interface ProcedureCategory {
  /**
   * 카테고리 식별자
   */
  id: string;
  /**
   * 슬러그
   */
  slug?: string;
  /**
   * 카테고리명
   */
  name: string;
  /**
   * 카테고리설명
   */
  description?: string;
}
