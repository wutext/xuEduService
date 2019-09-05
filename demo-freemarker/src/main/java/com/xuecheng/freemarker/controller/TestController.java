package com.xuecheng.freemarker.controller;

import com.xuecheng.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/banner/{id}")
    public String banner(@PathVariable("id") String id, Map<String, Object> map) {
        ResponseEntity<Map> mapResponseEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getconfig/"+id, Map.class);
        Map body = mapResponseEntity.getBody();
        map.putAll(body);
        return "index_banner";
    }

    @RequestMapping("/course/{id}")
    public String course(@PathVariable("id") String id,Map<String, Object> map) {
        ResponseEntity<Map> mapResponseEntity = restTemplate.getForEntity("http://localhost:31200/course/courseView/"+id, Map.class);
        Map body = mapResponseEntity.getBody();
        map.putAll(body);
        return "course";
    }

    @RequestMapping("/param")
    public String param(Map<String, Object> map) {

        map.put("name", "zhangsan");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setBirthday(new Date());
        map.put("stu1",stu1);
        return "test1";
    }
}
