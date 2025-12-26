package com.xenialsoft.api.custom.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xenialsoft.api.custom.test.mapper.TestMapper;
import com.xenialsoft.api.custom.test.model.Test;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestMapper mapper;

    public List<Test> getListAll() {
        return mapper.selectListAll();
    }

}
