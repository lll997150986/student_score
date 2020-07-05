package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:42
 **/
public class StuClass  implements Serializable {
    String stuNum;
    String classNum;
    String stuRank;
    Double stuScore;
    String stuClassDes;

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

    @Override
    public String toString() {
        return "StuClass{" +
                "stuNum='" + stuNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", stuRank='" + stuRank + '\'' +
                ", stuScore=" + stuScore +
                ", stuClassDes='" + stuClassDes + '\'' +
                '}';
    }
}
