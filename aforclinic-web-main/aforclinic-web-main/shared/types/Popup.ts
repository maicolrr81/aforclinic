export interface Popup {
  /**
   * 팝업 식별자
   */
  id: string;
  /**
   * 팝업 타입
   */
  type: 'CAROUSEL' | 'MODAL';
  /**
   * 팝업 제목
   */
  title: string;
  /**
   * 이미지 식별자
   */
  imageId: string;
  /**
   * 링크
   */
  linkUri?: string;
  /**
   * TOP
   */
  positionTop?: number;
  /**
   * LEFT
   */
  positionLeft?: number;
}
