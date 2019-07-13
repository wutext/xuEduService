package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController {

    @Autowired
    private CmsSiteService cmsSiteService;

    @RequestMapping("/list")
    public QueryResponseResult listAll() {
        return cmsSiteService.list();
}

}
