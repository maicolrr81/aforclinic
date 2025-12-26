package com.xenialsoft.api.core.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.product.mapper.ProductBundleMapper;
import com.xenialsoft.api.core.product.model.ProductBundle;
import com.xenialsoft.api.core.product.model.ProductBundleRequest;
import com.xenialsoft.api.core.product.model.ProductBundleResponse;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductBundleService {
	
	private final ProductBundleMapper mapper;
    
    /**
     * id로 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductBundleResponse> getProductBundleList(String id) {
        return mapper.selectListById(id).stream().map(ProductBundleResponse::from).toList();
    }
    
    /**
     * 특정 productId가 있는 bundleId의 product Category
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductBundleResponse> getBundleCategoryListByProductId(String id) {
        return mapper.selectBundleCategoryListByProductId(id).stream().map(ProductBundleResponse::from).toList();
    }

    /**
     * 추가
     * @param productBundle
     */
    @Transactional
    public void create(ProductBundleRequest productBundle) {
        
    	ProductBundle newProductBundle = ProductBundleRequest.from(productBundle);
        
        int count = mapper.insert(newProductBundle);
        if (count != 1) {
            throw new ServiceException("패키지에 상품 등록 중 오류가 발생하였습니다.");
        }
        return;
    }
    
    /**
     * 삭제
     * @param id
     */
    @Transactional
    public void delete(String id) {
        mapper.delete(id);
    }
    
    /**
     * 저장
     * @param id
     * @param productBundle
     */
    @Transactional
    public void save(String id, List<ProductBundleRequest> productBundleList) {
    	
        // 삭제
        delete(id);
        
        // 추가
        if(productBundleList != null && productBundleList.size() > 0) {
            for(ProductBundleRequest productBundle : productBundleList) {
                create(productBundle);
            }
        }
    }
    
}
