import type { ApiPageRequest } from './Api';

export interface FileMetadataParams extends ApiPageRequest {
  /**
   * 파일명
   */
  filename?: string;
}

export interface FileMetadata {
  /**
   * 파일 고유 식별자
   */
  id: string;
  /**
   * 파일명
   */
  filename: string;
  /**
   * 파일 경로
   */
  path: string;
  /**
   * 파일 크기
   */
  size: number;
  /**
   * 파일 생성 일자
   */
  createdAt: string;
  /**
   * 파일 수정 일자
   */
  modifiedAt: string;
}

export interface FileUploadResponse extends Pick<FileMetadata, 'id' | 'filename' | 'size' | 'createdAt'> {

}
