import type { Attachment } from './Attachment';

export type PostType =
  'DEFAULT' |
  'NOTICE' |
  'LANDING';

export type PostStatus =
  'DRAFT' |
  'PENDING' |
  'PUBLISHED' |
  'HIDDEN' |
  'DELETED';

export interface Post {
  /** 순번 */
  sequence?: number;
  /** 게시글 식별자 */
  id: string;
  /** 타입 */
  type: PostType;
  /** 제목 */
  title: string;
  /** 본문 */
  content: string;
  /** 상태 */
  status: PostStatus;
  /** 등록일시 */
  createdAt?: string;
  /** 등록자 */
  createdBy?: string;
  /** 수정일시 */
  modifiedAt?: string;
  /** 수정자 */
  modifiedBy?: string;
  /** 첨부파일 */
  attachments?: Attachment[];
}

/**
 * 랜딩페이지
 */
export interface LandingPost extends Omit<Post, 'attachments'> {
  type: 'LANDING';
  /** 설명 */
  description?: string;
  /** 이미지 식별자 */
  imageId?: string;
  /** 시작일 */
  startDate: string;
  /** 종료일 */
  endDate: string;
};
