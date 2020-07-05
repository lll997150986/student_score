package com.mobileclient.domain.result;


import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 11:23
 **/
public class StuClass implements Serializable{
    String stuNum;
    String classNum;
    String stuName;
    String stuRank;
    Double stuScore;
    String stuClassDes;
    String stuClass;
    String stuTel;


    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuRank() {
        return stuRank;
    }

    public void setStuRank(String stuRank) {
        this.stuRank = stuRank;
    }

    public Double getStuScore() {
        return stuScore;
    }

    public void setStuScore(Double stuScore) {
        this.stuScore = stuScore;
    }

    public String getStuClassDes() {
        return stuClassDes;
    }

    public void setStuClassDes(String stuClassDes) {
        this.stuClassDes = stuClassDes;
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

    @Override
    public String toString() {
        return "StuClass{" +
                "stuNum='" + stuNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuRank='" + stuRank + '\'' +
                ", stuScore=" + stuScore +
                ", stuClassDes='" + stuClassDes + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", stuTel='" + stuTel + '\'' +
                '}';
    }
}
