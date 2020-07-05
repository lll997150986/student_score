package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:45
 **/
public class Student  implements Serializable {
    String stuNum;
    String stuName;
    String stuSex;
    String stuClass;//所在班级信息
    String stuTel;
    String stuDes;
    String stuEmail;
    String stuPasswd;

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getStuTel() {
        return stuTel;
    }

    public void setStuTel(String stuTel) {
        this.stuTel = stuTel;
    }

    public String getStuDes() {
        return stuDes;
    }

    public void setStuDes(String stuDes) {
        this.stuDes = stuDes;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getStuPasswd() {
        return stuPasswd;
    }

    public void setStuPasswd(String stuPasswd) {
        this.stuPasswd = stuPasswd;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", stuTel='" + stuTel + '\'' +
                ", stuDes='" + stuDes + '\'' +
                ", stuEmail='" + stuEmail + '\'' +
                ", stuPasswd='" + stuPasswd + '\'' +
                '}';
    }
}
