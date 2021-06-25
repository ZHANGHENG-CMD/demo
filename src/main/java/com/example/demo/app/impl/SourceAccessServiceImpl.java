package com.example.demo.app.impl;

import com.example.demo.app.SourceAccessService;
import org.springframework.stereotype.Service;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/4/1 15:13
 */
@Service
public class SourceAccessServiceImpl implements SourceAccessService {

    @Override
    public String selectByParam(String param) {
        return param+"123";
    }
}
