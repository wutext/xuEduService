package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceTest {
    @Autowired
    private PageService pageService;

    @Test
    public void findAll() throws Exception {

        QueryPageRequest queryPageRequest = new QueryPageRequest();
        queryPageRequest.setSiteId("5a751fab6abb5044e0d19ea1");
        queryPageRequest.setPageAliase("课程详情");
        QueryResponseResult cmsPages = pageService.findAll(1, 10, queryPageRequest);
        System.out.println(cmsPages!=null);
    }

}