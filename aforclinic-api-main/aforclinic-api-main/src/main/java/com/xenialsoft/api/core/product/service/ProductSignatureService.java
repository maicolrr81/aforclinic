package com.xenialsoft.api.core.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.product.mapper.ProductSignatureMapper;
import com.xenialsoft.api.core.product.model.ProductSignatureResponse;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSignatureService {
	
	private final ProductSignatureMapper mapper;
    
    /**
     * id로 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductSignatureResponse> getProductSignatureList() {
        return mapper.selectList().stream().map(ProductSignatureResponse::from).toList();
    }

    /**
     * 추가
     * @param productBundle
     */
    @Transactional
    public void createSignature(String id) {
        
        int count = mapper.insert(id);
        if (count != 1) {
            throw new ServiceException("시그니처에 상품 등록 중 오류가 발생하였습니다.");
        }
        return;
    }
    
    /**
     * 삭제
     * @param id
     */
    @Transactional
    public void deleteSignature() {
        mapper.delete();
    }
    
    /**
     * 저장
     * @param idList
     */
    public void saveSignature(List<String> idList) {

        deleteSignature();
        
        if(idList != null && idList.size() > 0) {
            for(String id : idList) {
                createSignature(id);
            }
        }
    }
    
}
