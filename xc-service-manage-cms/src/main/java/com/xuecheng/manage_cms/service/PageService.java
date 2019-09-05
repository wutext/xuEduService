package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.rabbitmq.RabbitSender;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private RabbitSender rabbitSender;

    private static final SimpleDateFormat DATE_FORMATE= new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

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

    /**
     * 获取页面模板
     * @param pageId
     * @return
     */
    public String getPageHtml(String pageId) {

        CmsPage cmsPage = this.findById(pageId);
        if(StringUtils.isEmpty(cmsPage.getDataUrl())) {
            ExceptionCast.cast(CommonCode.PAGE_URL_ISNULL);
        }
        //获取模型数据
        Map model = getModelByPageId(cmsPage.getDataUrl());
        if(CollectionUtils.isEmpty(model)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //获取模板
        String templateHtml = getTemplateByPageId(cmsPage.getTemplateId());

        if(StringUtils.isEmpty(templateHtml)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //组装页面实现页面静态化
        String html = generateHtml(templateHtml, model);
        return html;
    }

    private String generateHtml(String templateHtml, Map model) {
        //创建配置对象   
        Configuration configuration=new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateHtml);
        //向configuration配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try {
            Template template = configuration.getTemplate("template", "utf-8");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTemplateByPageId(String templateId) {

        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(templateId);
        if(cmsTemplate.isPresent()) {

            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.get().getTemplateFileId())));
            //打开一个下载虎留对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            String str = null;
            try {
                str = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(str);
        }
        return null;
    }

    private Map getModelByPageId(String dataUrl) {
        //根据dataUrl远程获取模型数据(页面中需要宣入的数据)
        ResponseEntity<Map> mapResponseEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = mapResponseEntity.getBody();
        return body;
    }

    public String sendPage(String pageId) {

        Map<String, Object> map = new HashMap();

        map.put("pageId", pageId);
        map.put("send_time", DATE_FORMATE.format(new Date()));
        rabbitSender.sendMessage(pageId,map);
        return pageId;
    }
}
