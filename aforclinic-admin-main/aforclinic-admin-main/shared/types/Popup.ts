export type PopupStatus = 'ACTIVE' | 'DELETED';

export interface Popup {
  /**
   * 순번
   */
  sequence?: number;
  /**
   * 팝업 식별자
   */
  id: string;
  /**
   * 유형
   */
  type: string;

  /**
   * 화면 상단으로부터의 거리(px)
   */
  positionTop?: number;

  /**
   * 화면 좌측으로부터의 거리(px)
   */
  positionLeft?: number;
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
   * 시작일
   */
  startDate?: string;
  /**
   * 종료일
   */
  endDate?: string;
  /**
   * 화면 표시 순서
   */
  displayOrder?: number;
  /**
   * 상태
   */
  status: PopupStatus;
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

export type CreatePopup = Pick<Popup, 'type' | 'positionTop' | 'positionLeft' | 'title' | 'imageId' | 'linkUri' | 'startDate' | 'endDate' | 'displayOrder'>;
export type UpdatePopup = Pick<Popup, 'type' | 'positionTop' | 'positionLeft' | 'title' | 'imageId' | 'linkUri' | 'startDate' | 'endDate' | 'displayOrder' | 'status'>;
export type ReorderPopup = Pick<Popup, 'id' | 'displayOrder'>;
