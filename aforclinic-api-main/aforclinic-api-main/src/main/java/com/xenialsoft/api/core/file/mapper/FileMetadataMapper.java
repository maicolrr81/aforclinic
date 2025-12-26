package com.xenialsoft.api.core.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.file.model.FileMetadata;
import com.xenialsoft.api.core.file.model.FileMetadataPageRequest;

// @formatter:off
/**
 * 파일 관련 데이터베이스 작업을 처리하는 MyBatis 매퍼 인터페이스.
 * 
 * <p>
 * 이 인터페이스는 파일 정보에 관련된 CRUD 작업을 수행하는 메서드를 정의하며,
 * MyBatis가 SQL 매핑을 통해 자동으로 구현을 제공합니다. 주로 {@code file} 테이블에 대한
 * 조회, 삽입, 수정, 삭제 작업을 수행하는 메서드들이 포함됩니다.
 * </p>
 * 
 * <p>
 * 각 메서드는 {@link FileMetadata} 엔티티와 매핑되어 있으며, 해당 파일의 고유 식별자, 해시값,
 * 경로, 크기, 확장자, 생성 일자 등 다양한 파일 관련 정보를 처리합니다.
 * </p>
 * 
 * @author xenialsoft
 * @since  2024.02.09
 */
// @formatter:on
@Mapper
public interface FileMetadataMapper {

    /**
     * id로 파일 정보 조회
     * 
     * @param id
     * @return
     */
    public FileMetadata selectById(@Param("id") String id);

    /**
     * 조회 조건으로 파일 목록 조회
     * 
     * @param search
     * @param paging
     * @return
     */
    public List<FileMetadata> selectList(@Param("search") FileMetadataPageRequest search,
            @Param("paging") ApiPageRequest paging);

    /**
     * 조회 조건으로 총 개수 조회
     * 
     * @param search
     * @return
     */
    public long selectTotalCount(@Param("search") FileMetadataPageRequest search);

    /**
     * 파일 저장
     * 
     * @param file
     * @return
     */
    public int insert(FileMetadata file);

}
