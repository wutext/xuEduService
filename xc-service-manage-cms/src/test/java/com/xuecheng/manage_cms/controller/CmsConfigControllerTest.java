package com.xuecheng.manage_cms.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsConfigControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void config_model_find_api() {
        ResponseEntity<Map> mapResponseEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getconfig/5a791725dd573c3574ee333f", Map.class);
        Map body = mapResponseEntity.getBody();
        System.out.println(body);
    }

}