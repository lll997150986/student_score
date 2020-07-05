package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:39
 **/
public class Sign   implements Serializable {
    String signNum;
    String stuNum;
    String classNum;
    String signDes;

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

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

    public String getSignDes() {
        return signDes;
    }

    public void setSignDes(String signDes) {
        this.signDes = signDes;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "signNum='" + signNum + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", signDes='" + signDes + '\'' +
                '}';
    }
}
