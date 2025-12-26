package com.xenialsoft.api.core.product.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.product.mapper.ProductCategoriesMapper;
import com.xenialsoft.api.core.product.model.ProductCategories;
import com.xenialsoft.api.core.product.model.ProductCategoriesRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoriesService {

    private final ProductCategoriesMapper mapper;

//    /**
//     * id로 조회
//     * 
//     * @param id
//     * @return
//     */
//    @Transactional(readOnly = true)
//    public List<ProductCategoriesResponse> getProductBundleList(String id) {
//        return mapper.selectListById(id).stream().map(ProductCategoriesResponse::from).toList();
//    }

    /**
     * 상품 식별자로 카테고리 식별자 조회
     * 
     * @param idList
     * @return
     */
    public List<ProductCategories> getByProductId(List<String> idList) {
        return mapper.selectByProductId(idList);
    }

    /**
     * 추가
     * 
     * @param request
     */
    @Transactional
    public void create(ProductCategoriesRequest request) {

        ProductCategories productCategories = ProductCategoriesRequest.from(request);

        int count = mapper.insert(productCategories);
        if (count != 1) {
            throw new ServiceException("상품에 카테고리를 등록 중 오류가 발생하였습니다.");
        }
        return;
    }

//    /**
//     * 삭제
//     * 
//     * @param id
//     */
//    @Transactional
//    public void delete(String id) {
//        mapper.delete(id);
//    }

    /**
     * 화면표시순서 변경
     * 
     * @param request
     */
    @Transactional
    public void reorderProductCategories(List<ProductCategoriesRequest> request) {
        request.stream().forEach(this::reorderProductCategory);
    }

    @Transactional
    public void reorderProductCategory(ProductCategoriesRequest request) {

        ProductCategories productCategories = ProductCategoriesRequest.from(request);

        int count = mapper.reorder(productCategories);
        if (count != 1) {
            throw new ServiceException("카테고리의 상품 순서 변경 중 오류가 발생했습니다.");
        }
    }

    /**
     * 저장
     * 
     * @param id
     * @param requestList
     */
    @Transactional
    public void save(String id, List<ProductCategoriesRequest> requestList) {

        Set<String> updIdSet = new HashSet<String>();
        if (requestList != null && requestList.size() > 0) {
            for (ProductCategoriesRequest data : requestList) {
                updIdSet.add(data.getCategoryId());
            }
        }

        List<ProductCategories> productCategoriesList = mapper.selectListById(id);
        if (productCategoriesList != null && productCategoriesList.size() > 0) {
            Set<String> regIdSet = new HashSet<String>();
            for (ProductCategories data : productCategoriesList) {
                regIdSet.add(data.getCategoryId());
            }
            for (String regId : regIdSet) {
                if (updIdSet.contains(regId)) {
                    // 수정안함
                    updIdSet.remove(regId);
                } else {
                    mapper.deleteProductCategories(id, regId);
                }
            }
        }

        if (updIdSet.size() > 0) {
            for (String updId : updIdSet) {
                ProductCategories productCategories = new ProductCategories();
                productCategories.setProductId(id);
                productCategories.setCategoryId(updId);
                productCategories.setDisplayOrder(0);
                mapper.insert(productCategories);
            }
        }
    }

}
