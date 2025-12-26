export type CategoryStatus = 'ACTIVE' | 'DELETED';

export interface Category {
  /**
   * 순번
   */
  sequence?: number;
  /**
   * 카테고리 식별자
   */
  id: string;
  /**
   * 카테고리명
   */
  name?: string;
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
  status?: CategoryStatus;
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

export type CreateCategory = Pick<Category, 'name' | 'description' | 'displayOrder'>;
export type UpdateCategory = Pick<Category, 'name' | 'description' | 'displayOrder' | 'status'>;
export type UpdateCategoryStatus = Pick<Category, 'status'>;
export type ReorderCategory = Pick<Category, 'id' | 'displayOrder'>;
