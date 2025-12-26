package com.xenialsoft.api.core.beforeafter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.beforeafter.mapper.BeforeAfterCategoriesMapper;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterCategories;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeforeAfterCategoriesService {

	private final BeforeAfterCategoriesMapper mapper;

	@Transactional
	public void create(BeforeAfterCategories request) {

		int count = mapper.insert(request);
		if (count != 1) {
			throw new ServiceException("카테고리 추가 중 오류가 발생하였습니다.");
		}
	}

	@Transactional
	public void delete(String id) {

		int count = mapper.delete(id);
		if (count == 0) {
			throw new ServiceException("카테고리 삭제 중 오류가 발생하였습니다.");
		}
	}
}
