package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void cms_test() {

        int pageNum = 1;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNum,pageSize);
        Page<CmsPage> cmsPageList  = cmsPageRepository.findAll(pageable);
        System.out.println(cmsPageList);
    }

    @Test
    public void find_by_pageName_test() {

        CmsPage cmsPage = cmsPageRepository.findByPageName("402885816243d2dd016243f24c030002.html");

        System.out.println(cmsPage.getDataUrl());
    }

}