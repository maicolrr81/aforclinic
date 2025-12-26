package com.xenialsoft.api.core.product.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.product.mapper.ProductMapper;
import com.xenialsoft.api.core.product.model.Product;
import com.xenialsoft.api.core.product.model.ProductBundleRequest;
import com.xenialsoft.api.core.product.model.ProductBundleResponse;
import com.xenialsoft.api.core.product.model.ProductCategories;
import com.xenialsoft.api.core.product.model.ProductCategoriesRequest;
import com.xenialsoft.api.core.product.model.ProductCreateRequest;
import com.xenialsoft.api.core.product.model.ProductPageRequest;
import com.xenialsoft.api.core.product.model.ProductPagedResponse;
import com.xenialsoft.api.core.product.model.ProductResponse;
import com.xenialsoft.api.core.product.model.ProductUpdateRequest;
import com.xenialsoft.api.core.product.model.ProductUpdateStatusRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductCategoriesService productCategoriesService;
    private final ProductBundleService productBundleService;

    /**
     * 목록 개수 조회
     * 
     * @param request
     * @param isadmin
     * @return
     */
    @Transactional(readOnly = true)
    public long getTotalCount(ProductPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProductPagedResponse> getProductList(ProductPageRequest request, 
            ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(ProductPagedResponse::from).toList();
    }

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<ProductResponse> getProductById(String id) {
        Product product = mapper.selectById(id);
        if (product == null) {
            return Optional.empty();
        }

        ProductResponse res = ProductResponse.from(product);

        // 번들인 경우 하위 상품 목록을 조회한다.
        if (product.isBundle()) {
            List<ProductBundleResponse> subProductList = productBundleService.getProductBundleList(id);
            res.setProductBundleList(subProductList);
        }

        return Optional.of(res);
    }

    /**
     * 이름 중복 체크
     * 
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkDuplicateName(String name, String id) {
        return (mapper.selectByName(name, id) > 0);
    }

    /**
     * 패키지에 등록할 상품 목록 조회
     * 
     * @param categoryId
     * @param searchText
     * @return
     */
    @Transactional(readOnly = true)
    public List<Product> getBundleProduct(String categoryId, String searchText) {
        return mapper.selectBundleProduct(categoryId, searchText);
    }

    /**
     * 패키지에 등록할 상품 목록 id로 조회
     * 
     * @param categoryId
     * @param idList
     * @return
     */
    @Transactional(readOnly = true)
    public List<Product> getBundleProductByIds(List<String> idList) {
        if (idList.isEmpty()) {
            return new ArrayList<Product>();
        }
        return mapper.selectByIdList(idList);
    }

    /**
     * 이벤트에 등록할 상품 목록 조회
     * 
     * @param categoryId
     * @param searchText
     * @return
     */
    @Transactional(readOnly = true)
    public List<Product> getEventProduct(String categoryId, String searchText) {
        return mapper.selectEventProduct(categoryId, searchText);
    }

    /**
     * 추가
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void createProduct(ProductCreateRequest request) {

        if (checkDuplicateName(request.getName(), null))
            throw new ServiceException("이름이 중복됩니다.");

        Product newProduct = ProductCreateRequest.from(request);

        int count = mapper.insert(newProduct);
        if (count != 1) {
            String target = request.isBundle() ? "패키지" : "상품";
            throw new ServiceException(target + " 생성 중 오류가 발생하였습니다.");
        }

        // 패키지 인경우
        if (request.isBundle()) {
            String bundleId = newProduct.getId();

            // product_bundle 추가
            List<String> productIdList = new ArrayList<String>();
            if (request.getProductBundleList() != null && request.getProductBundleList().size() > 0) {
                for (ProductBundleRequest bundle : request.getProductBundleList()) {
                    bundle.setBundleId(bundleId);
                    productIdList.add(bundle.getProductId());
                }
                List<Product> productList = getBundleProductByIds(productIdList);
                if (productIdList.size() > productList.size()) {
                    throw new ServiceException("유효하지 않은 상품이 있습니다.");
                }

                for (ProductBundleRequest productBundle : request.getProductBundleList()) {
                    productBundleService.create(productBundle);
                }

                // product_categories 추가
                List<ProductCategories> categoryIdList = productCategoriesService.getByProductId(productIdList);
                if (categoryIdList != null && categoryIdList.size() > 0) {

                    Set<String> categoryIdSet = categoryIdList.stream().map(ProductCategories::getCategoryId)
                            .collect(Collectors.toSet());
                    // Set<String> categoryIdSet = new HashSet<String>();
                    // for(ProductCategories productCategories : categoryIdList) {
                    // categoryIdSet.add(productCategories.getCategoryId());
                    // }

                    for (String categoryId : categoryIdSet) {
                        ProductCategoriesRequest productCategoriesRequest = new ProductCategoriesRequest();
                        productCategoriesRequest.setProductId(bundleId);
                        productCategoriesRequest.setCategoryId(categoryId);
                        productCategoriesService.create(productCategoriesRequest);
                    }
                }

            }

        } else {
            // 일반 상품인 경우 product_categories 추가
            if (request.getCategoryList() == null || request.getCategoryList().size() == 0) {
                throw new ServiceException("상품에 카테고리를 선택하세요.");
            } else if (request.getCategoryList().size() == 1) {
                ProductCategoriesRequest productCategoriesRequest = new ProductCategoriesRequest();
                productCategoriesRequest.setProductId(newProduct.getId());
                productCategoriesRequest.setCategoryId(request.getCategoryList().get(0).getId());
                productCategoriesService.create(productCategoriesRequest);
            } else if (request.getCategoryList().size() > 1) {
                throw new ServiceException("상품에는 1개의 카테고리만 설정할 수 있습니다.");
            }
        }
    }

    /**
     * 수정
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void updateProduct(ProductUpdateRequest request) {

        Product product = mapper.selectById(request.getId());
        if (product == null)
            throw new ServiceException("존재하지 않은 상품입니다.");

        if (checkDuplicateName(request.getName(), request.getId()))
            throw new ServiceException("이름이 중복됩니다.");

        Product updateProduct = ProductUpdateRequest.from(request);

        int count = mapper.update(updateProduct);
        if (count != 1) {
            String target = product.isBundle() ? "패키지" : "상품";
            throw new ServiceException(target + " 정보 수정 중 오류가 발생했습니다.");
        }

        // 패키지 인경우
        if (product.isBundle()) {

            String bundleId = request.getId();

            // product_bundle 추가
            List<String> productIdList = new ArrayList<String>();
            for (ProductBundleRequest bundle : request.getProductBundleList()) {
                bundle.setBundleId(bundleId);
                productIdList.add(bundle.getProductId());
            }
            List<Product> productList = getBundleProductByIds(productIdList);
            if (productIdList.size() > productList.size()) {
                throw new ServiceException("유효하지 않은 상품이 있습니다.");
            }
            productBundleService.save(bundleId, request.getProductBundleList());

            // product_categories 수정
            if (productIdList != null && productIdList.size() > 0) {
                List<ProductCategories> categoryIdList = productCategoriesService.getByProductId(productIdList);

                List<ProductCategoriesRequest> requestList = new ArrayList<ProductCategoriesRequest>();
                if (categoryIdList != null && categoryIdList.size() > 0) {

                    Set<String> categoryIdSet = new HashSet<String>();
                    for (ProductCategories productCategories : categoryIdList) {
                        categoryIdSet.add(productCategories.getCategoryId());
                    }

                    for (String categoryId : categoryIdSet) {
                        ProductCategoriesRequest productCategoriesRequest = new ProductCategoriesRequest();
                        productCategoriesRequest.setProductId(bundleId);
                        productCategoriesRequest.setCategoryId(categoryId);
                        requestList.add(productCategoriesRequest);
                    }
                }
                productCategoriesService.save(bundleId, requestList);
            } else
                productCategoriesService.save(bundleId, Collections.emptyList());

        } else {
            String productId = request.getId();

            // 일반 상품인 경우 product_categories 수정
            if (request.getCategoryList() == null || request.getCategoryList().size() == 0) {
                throw new ServiceException("상품에 카테고리를 선택하세요.");
            } else if (request.getCategoryList().size() == 1) {

                List<ProductCategoriesRequest> requestList = new ArrayList<ProductCategoriesRequest>();
                ProductCategoriesRequest productCategoriesRequest = new ProductCategoriesRequest();
                productCategoriesRequest.setProductId(productId);
                productCategoriesRequest.setCategoryId(request.getCategoryList().get(0).getId());
                requestList.add(productCategoriesRequest);
                productCategoriesService.save(request.getId(), requestList);

            } else if (request.getCategoryList().size() > 1) {
                throw new ServiceException("상품에는 1개의 카테고리만 설정할 수 있습니다.");
            }

            /** 상품이 등록된 패키지의 카테고리 목록 삭제 후 추가 */
            // 상품이 등록된 패키지의 상품 카테고리 목록
            List<ProductBundleResponse> bundleResList = productBundleService.getBundleCategoryListByProductId(productId);
            if (bundleResList != null && bundleResList.size() > 0) {

                // 상품이 등록된 패키지의 카테고리 중복 제거한 데이터 찾기
                Map<String, Set<String>> updBundleCategoryMap = new HashMap<String, Set<String>>();
                for (ProductBundleResponse bundleRes : bundleResList) {
                    String bundleId = bundleRes.getBundleId();
                    if (bundleRes.getCategoryIdList() != null && bundleRes.getCategoryIdList().size() > 0) {

                        for (String categoryId : bundleRes.getCategoryIdList()) {

                            Set<String> categorySet = updBundleCategoryMap.get(bundleId);
                            if (categorySet == null) {
                                updBundleCategoryMap.put(bundleId, categorySet = new HashSet<String>());
                            }
                            categorySet.add(categoryId);
                        }
                    }
                }
                // 상품이 등록된 패키지의 카테고리 목록 수정
                for (String bundleId : updBundleCategoryMap.keySet()) {
                    List<ProductCategoriesRequest> productCategoriesRequestList = new ArrayList<ProductCategoriesRequest>();
                    Set<String> categoryIdSet = updBundleCategoryMap.get(bundleId);
                    for (String categoryId : categoryIdSet) {
                        ProductCategoriesRequest productCategoriesRequest = new ProductCategoriesRequest();
                        productCategoriesRequest.setProductId(bundleId);
                        productCategoriesRequest.setCategoryId(categoryId);
                        productCategoriesRequestList.add(productCategoriesRequest);
                    }
                    productCategoriesService.save(bundleId, productCategoriesRequestList);
                }
            }
        }
    }

    /**
     * 화면표시순서 변경
     * @param request
     */
    @Transactional
    public void reorderProductCategories(List<ProductCategoriesRequest> request) {
        productCategoriesService.reorderProductCategories(request);
    }
    
    
    /**
     * 상태를 삭제로
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void updateDeletedProduct(ProductUpdateStatusRequest request) {

        String id = request.getId();

        if (mapper.selectById(id) == null)
            throw new ServiceException("존재하지 않은 상품입니다.");

        if (mapper.hasBundle(id) > 0)
            throw new ServiceException("번들에 등록된 상품입니다. 번들에 등록된 상품을 먼저 삭제하세요.");

        if (mapper.hasEvent(id) > 0)
            throw new ServiceException("이벤트에 등록된 상품입니다. 이벤트에 등록된 상품을 먼저 삭제하세요.");

        if (mapper.hasCart(id) > 0) {
            if (mapper.deleteCart(id) == 0)
                throw new ServiceException("고객 장바구니에 등록된 상품을 삭제 중 오류가 발생하였습니다.");
        }

        Product product = ProductUpdateStatusRequest.from(request);

        if (mapper.updateStatus(product) == 0) {
            throw new ServiceException("삭제 중 오류가 발생하였습니다.");
        }
    }
}
