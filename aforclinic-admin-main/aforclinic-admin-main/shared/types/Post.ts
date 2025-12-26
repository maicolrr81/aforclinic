export type PostType =
  | 'DEFAULT' // 기본
  | 'NOTICE' // 공지
  | 'LANDING'; // 랜딩페이지

export type PostStatus =
  | 'DRAFT' // 임시저장
  | 'PENDING' // 승인대기
  | 'PUBLISHED' // 게시됨
  | 'HIDDEN' // 숨김
  | 'DELETED'; // 삭제됨

export interface Post {
  /** 게시글 식별자 */
  id: string;

  /** 게시글 타입 */
  type: PostType;

  /** 제목 */
  title: string;

  /** 본문 내용 */
  content: string;

  /** 게시 상태 */
  status: PostStatus;

  /** 첨부 파일 목록 */
  attachments?: Attachment[];

  /** 등록일시 */
  createdAt?: string;

  /** 등록자 */
  createdBy?: string;

  /** 수정일시 */
  modifiedAt?: string;

  /** 수정자 */
  modifiedBy?: string;
}

/**
 * 기본
 */
export interface DefaultPost extends Post {
  type: 'DEFAULT';
};

export type CreateDefaultPost = Pick<DefaultPost, 'type' | 'title' | 'content' | 'attachments'>;
export type UpdateDefaultPost = Pick<DefaultPost, 'type' | 'title' | 'content' | 'status' | 'attachments'>;

/**
 * 공지사항
 */
export interface NoticePost extends Post {
  type: 'NOTICE';
}

export type CreateNoticePost = Pick<NoticePost, 'type' | 'title' | 'content' | 'attachments'>;
export type UpdateNoticePost = Pick<NoticePost, 'type' | 'title' | 'content' | 'status' | 'attachments'>;

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

export type CreateLandingPost = Pick<
  LandingPost,
  'title' | 'description' | 'content' | 'imageId' | 'startDate' | 'endDate'
>;

export type UpdateLandingPost = Pick<
  LandingPost,
  'title' | 'description' | 'content' | 'imageId' | 'startDate' | 'endDate' | 'status'
>;

//////////////////

// /**
//  * 이벤트
//  */
// export interface EventPost extends Post {
//   type: 'EVENT';
// }

// export type CreateEventPost = Pick<EventPost, 'type' | 'title' | 'content' | 'attachments'>;

// export type UpdateEventPostWithId = Pick<EventPost, 'id' | 'type' | 'title' | 'content' | 'status' | 'attachments'>;
// export type UpdateEventPost = Omit<UpdateEventPostWithId, 'id'>;

// export type UpdateEventPostStatusWithId = Pick<UpdateEventPostWithId, 'id' | 'status'>;
// export type UpdateEventPostStatus = Omit<UpdateEventPostStatusWithId, 'id'>;
