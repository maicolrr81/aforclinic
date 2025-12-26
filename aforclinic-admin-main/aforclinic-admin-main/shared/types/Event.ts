export type EventStatus =
  'ACTIVE' |
  'CANCELED' |
  'CLOSED' |
  'DELETED';

export interface Event {
  /**
   * 이벤트 식별자
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
  /**
   * 이미지 식별자
   */
  imageId?: string;
  /**
   * 시작일
   */
  startDate: string;
  /**
   * 종료일
   */
  endDate: string;
  /**
   * 연결 상품
   */
  products: EventProduct[];
  /**
   * 상태
   */
  status?: EventStatus;
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

export interface CreateEvent extends Pick<Event, 'title' | 'description' | 'imageId' | 'startDate' | 'endDate'> {
  products: CreateEventProduct[];
}

export interface UpdateEvent extends Pick<Event, 'title' | 'description' | 'imageId' | 'startDate' | 'endDate' | 'status'> {
  products: UpdateEventProduct[];
}

export interface EventProduct {
  /** 이벤트 식별자 */
  eventId: string;
  /** 상품 식별자 */
  productId: string;
  /** 상품명 */
  productName?: string;
  /** 정가에서 조정된 가격 */
  adjustedPrice: number;
  /** 할인 가격 */
  discountedPrice: number;
  /** 메인화면 표시 여부 */
  mainVisible: boolean;
  /** 화면 표시 순서 */
  displayOrder: number;
}

export type CreateEventProduct = Pick<
  EventProduct,
  'productId' | 'productName' | 'adjustedPrice' | 'discountedPrice' | `mainVisible` | `displayOrder`
>;

export type UpdateEventProduct = Pick<
  EventProduct,
  'productId' | 'productName' | 'adjustedPrice' | 'discountedPrice' | `mainVisible` | `displayOrder`
>;
