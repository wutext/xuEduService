package com.xuecheng.freemarker.controller;

import com.google.common.io.Resources;
import com.xuecheng.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTest {

    /**
     * 静态页面模板生成(已编辑好的页面进行页面导出到当前计算机某个目录下)
     * @throws Exception
     */
    @Test
    public void param() throws Exception {
        //创建配置类     
        Configuration configuration=new Configuration(Configuration.getVersion());
        //设置模板路径
        URL resource = Resources.getResource("templates/");
        String path = resource.getPath();
        configuration.setDirectoryForTemplateLoading(new File(path));
        configuration.setDefaultEncoding("utf-8");

        //获取模板
        Template template = configuration.getTemplate("course.ftl");
        Map<String, Object> map = new HashMap();
        map.put("name", "zhangsan");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setBirthday(new Date());
        map.put("stu1",stu1);
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content,"utf-8");
        FileOutputStream outputStream = new FileOutputStream(new File("E:/video/test1.html"));
        IOUtils.copy(inputStream,outputStream);

    }

    /**
     * 通过自定义模板内容并将页面导出生成页面
     * * @throws Exception
     */
    @Test
    public void create_template_by_string() throws Exception {
        //创建配置类     
        Configuration configuration=new Configuration(Configuration.getVersion());
        //模板内容，这里测试时使用简单的字符串作为模板
         String templateString="<html>\n" +
                 " <head></head>\n" +
                 " <body>\n" +
                 " 名称： ${name}\n" +
                 "</body>\n" +
                 "</html>";

        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        Template template = configuration.getTemplate("template", "utf-8");
        Map<String, Object> map = new HashMap();
        map.put("name", "zhangsan");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setBirthday(new Date());
        map.put("stu1",stu1);
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content,"utf-8");
        FileOutputStream outputStream = new FileOutputStream(new File("E:/video/test1-string.html"));
        IOUtils.copy(inputStream,outputStream);

    }
}