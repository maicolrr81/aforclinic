export interface Cart {
  /**
   * 사용자 식별자
   */
  userId: string;
  /**
   * 상품 식별자
   */
  productId: string;
}

export type CreateCart = Pick<Cart, 'productId'>;

export interface DeleteCart {
  productId: string | string[];
};
