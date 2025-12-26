import type { Event } from './Event';

export interface Product {
  /** 상품 식별자 */
  id: string;
  /** 상품명 */
  name: string;
  /** 상품 설명 */
  description?: string;
  /** 조정 가격 */
  adjustedPrice: number;
  /** 할인 가격 */
  discountedPrice: number;
  /** 이벤트 정보 */
  event?: Pick<Event, 'id' | 'endDate'>;
}
