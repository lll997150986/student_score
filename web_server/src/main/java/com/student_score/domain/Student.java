package com.student_score.domain;


import lombok.Data;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:45
 **/
@Data
public class Student {
    String stuNum;
    String stuName;
    String stuSex;
    String stuClass;//所在班级信息
    String stuTel;
    String stuDes;
    String stuEmail;
    String stuPasswd;

}
