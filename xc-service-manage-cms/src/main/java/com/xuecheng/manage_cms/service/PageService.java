package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 按条件分页查询
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findAll(int page, int size, QueryPageRequest queryPageRequest) {
        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);
        /**
         * ExampleMatcher： 模糊查询封装器
         * 模糊查询属性pageAliase
         *
         */
        CmsPage cmsPage = new CmsPage();
        if(!StringUtils.isEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(!StringUtils.isEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if(!StringUtils.isEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
                /*.withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());*/

        //对象和模糊查询封装器放入Example对象中
        Example<CmsPage> queryPageRequestExample = Example.of(cmsPage, exampleMatcher);
        /**
         *将封装的查询器和分页传入查询方法中：分局cmsPage对象中优质的属性和模糊查询定义的属性值进行过滤
         */
        Page<CmsPage> all = cmsPageRepository.findAll(queryPageRequestExample,pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 页面查询方法
     * @param page 页码，从1开始记数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){

        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmsPage) {

        CmsPage cms = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),cmsPage.getSiteId(),cmsPage.getPageWebPath());
        cms.getDataUrl();
        if(!ObjectUtils.isEmpty(cms)) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);

    }

    public CmsPage findById(String id) {
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);
        return cmsPage.orElse(null);
    }

    public CmsPageResult updatePage(String id, CmsPage cmsPage) {
        Optional<CmsPage> cmsPage1 = cmsPageRepository.findById(id);
        if(cmsPage1.isPresent()) {
            CmsPage cp = cmsPage1.get();
            cp.setPageAliase(cmsPage.getPageAliase());
            cp.setTemplateId(cmsPage.getTemplateId());
            cp.setPageWebPath(cmsPage.getPageWebPath());
            cp.setSiteId(cmsPage.getSiteId());
            cp.setHtmlFileId(cmsPage.getHtmlFileId());
            cp.setPageName(cmsPage.getPageName());
            cp.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            cp.setDataUrl(cmsPage.getDataUrl());
            cmsPageRepository.save(cp);
            return new CmsPageResult(CommonCode.SUCCESS,cp);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult delById(String id) {
        Optional<CmsPage> cmsPage1 = cmsPageRepository.findById(id);
        if(cmsPage1.isPresent()) {
            cmsPageRepository.delete(cmsPage1.get());
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
