package com.xuecheng.freemarker.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class Student {

    private String name;
    private Date birthday;
    private List<Student> friends;
    private Student bestFriend;
}
