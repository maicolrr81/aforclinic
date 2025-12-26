export interface Event {
  /** 이벤트 식별자 */
  id: string;
  /** 제목 */
  title: string;
  /** 설명 */
  description?: string;
  /** 이미지 식별자 */
  imageId?: string;
  /** 시작일 */
  startDate: string;
  /** 종료일 */
  endDate: string;
  /** 최소 가격 */
  minDiscountedPrice?: number;
  /** 최대 가격 */
  maxDiscountedPrice?: number;
  /** 상품 목록 */
  products?: EventProduct[];
}

export interface EventProduct {
  /** 이벤트 식별자 */
  eventId: string;
  /** 상품 식별자 */
  productId: string;
  /** 상품명 */
  productName: string;
  /** 상품 설명 */
  productDescription?: string;
  /** 조정 가격 */
  adjustedPrice: number;
  /** 할인가격 */
  discountedPrice: number;
}

export interface EventPost {
  /** 이벤트 식별자 */
  id: string;
  /** 이벤트명 */
  title: string;
  /** 이벤트 설명 */
  description?: string;
  /** 본문 정보 */
  post: Post;
}
