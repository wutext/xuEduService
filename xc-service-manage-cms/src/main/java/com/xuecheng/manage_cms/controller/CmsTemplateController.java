package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController {

    @Autowired
    private CmsTemplateService cmsTemplateService;

    @GetMapping("/list")
    public QueryResponseResult listAll() {
        return cmsTemplateService.list();
    }
}
