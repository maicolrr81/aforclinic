export type ProductCategoryStatus = 'ACTIVE' | 'REMOVED' | 'DELETED';

export interface ProductCategory {
  /**
   * 순번
   */
  sequence?: number;
  /**
   * 상품 카테고리 식별자
   */
  id: string;
  /**
   * 카테고리명
   */
  name: string;
  /**
   * 설명
   */
  description?: string;
  /**
   * 화면 표시 순서
   */
  displayOrder?: number;
  /**
   * 상태
   */
  status: ProductCategoryStatus;
  /**
   * 등록일시
   */
  createdAt?: string;
  /**
   * 등록자
   */
  createdBy?: string;
  /**
   * 등록자
   */
  createdByName?: string;
  /**
   * 수정일시
   */
  modifiedAt?: string;
  /**
   * 수정자
   */
  modifiedBy?: string;
  /**
   * 수정자
   */
  modifiedByName?: string;
}

export type CreateProductCategory = Pick<ProductCategory, 'name' | 'description' | 'displayOrder'>;
export type UpdateProductCategory = Pick<ProductCategory, 'name' | 'description' | 'displayOrder'>;
export type UpdateProductCategoryStatus = Pick<ProductCategory, 'status'>;

export interface Product {
  /**
   * 상품 식별자
   */
  id: string;
  /**
   * 카테고리명
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
  imageId?: string | undefined;

  /**
   * 조정된 정가
   */
  adjustedPrice: number;

  /**
   * 할인된 가격
   */
  discountedPrice: number;

  /**
   * 카테고리 식별자 목록
   */
  categoryList: Category[];

  /**
   * 하위 상품 목록
   */
  productBundleList?: CreateProductBundle[];
}

export type CreateProduct = Pick<Product, 'bundle' | 'name' | 'description' | 'imageId' | `adjustedPrice` | `discountedPrice` | `categoryList` | 'productBundleList'>;
export type UpdateProduct = Pick<Product, 'name' | 'description' | 'imageId' | `adjustedPrice` | `discountedPrice` | `categoryList` | 'productBundleList'>;

export interface ProductBundle {
  /**
   * 패키지 고유 식별자
   */
  bundleId: string;
  /**
   * 상품 고유 식별자
   */
  productId: string;
  /**
   * 수량
   */
  quantity: number;

  /**
   * 상품
   */
  product?: Product;
}

export type CreateProductBundle = Pick<ProductBundle, 'productId' | 'quantity'>;

export interface SelectedProductBundle {
  /**
   * 패키지 고유 식별자
   */
  bundleId: string;
  /**
   * 상품 고유 식별자
   */
  productId: string;

  productName: string;

  productAdjustedPrice: number;

  productDiscountedPrice: number;

  quantity: number;

  categoryId: string;

}

// ---------------------------------- 커스텀DTO
export interface ProductResponse {
  /**
   * 상품 식별자
   */
  id: string;
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
  imageId?: string | undefined;
  /**
   * 조정된 정가
   */
  adjustedPrice: number;

  /**
   * 할인된 가격
   */
  discountedPrice: number;

  /**
   * 카테고리 식별자 목록
   */
  categoryList: Category[];

  /**
   * 하위 상품 목록
   */
  productBundleList?: ProductBundleResponse[];
}

export interface ProductBundleResponse {
  /**
   * 패키지 고유 식별자
   */
  bundleId: string;
  /**
   * 상품 고유 식별자
   */
  productId: string;
  /**
   * 상품명
   */
  productName: string;
  /**
   * 조정된 정가
   */
  productAdjustedPrice: number;

  /**
   * 할인된 가격
   */
  productDiscountedPrice: number;

  /**
   * 수량
   */
  quantity: number;
}

export interface ProductSignatureResponse {
  /**
   * 상품 식별자
   */
  productId: string;
  /**
   * 상품
   */
  product: Product;
}
