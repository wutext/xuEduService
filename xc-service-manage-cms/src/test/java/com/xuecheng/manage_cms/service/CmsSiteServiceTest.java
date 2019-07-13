package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsSiteServiceTest {

    @Autowired
    private CmsSiteService cmsSiteService;

    @Test
    public void list() throws Exception {
        QueryResponseResult queryResponseResult = cmsSiteService.list();
        System.out.println(queryResponseResult.getQueryResult());
    }

}