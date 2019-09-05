package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:24
 **/
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService pageService;

    @GetMapping("/search/{pageNo}/{pageSize}")
    public QueryResponseResult findAll(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("pageSize") int pageSize,
                                    QueryPageRequest queryPageRequest) {
        return pageService.findAll(pageNo,pageSize, queryPageRequest);
    }

    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,
                                        @PathVariable("size")int size,
                                        QueryPageRequest queryPageRequest) {
        return pageService.findAll(page,size,queryPageRequest);
    }

    @PostMapping("/save")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return  pageService.add(cmsPage);
    }

    @GetMapping("/get/{id}")
    public CmsPage getById(@PathVariable("id") String id) {
        return pageService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id,
                                @RequestBody CmsPage cmsPage) {
        return pageService.updatePage(id, cmsPage);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delById(@PathVariable("id") String id) {
        return pageService.delById(id);
    }

    @GetMapping("/preview/{pageId}")
    public String getPageHtml(@PathVariable("pageId") String pageId) {

        return pageService.getPageHtml(pageId);
    }

    @PostMapping("/sendPage/{pageId}")
    public String sendPage(@PathVariable("pageId") String pageId) {

        return pageService.sendPage(pageId);
    }
}
