package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsSiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult list() {
        List<CmsSite> cmsList = (List<CmsSite>) cmsSiteRepository.findAll();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(cmsList);//数据列表
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }
}
