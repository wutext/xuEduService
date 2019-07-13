package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);

    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
