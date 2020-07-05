package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:47
 **/
public class TeaClass  implements Serializable {
    String teaNum;
    String classNum;
    String teaClassDes;

    public String getTeaNum() {
        return teaNum;
    }

    public void setTeaNum(String teaNum) {
        this.teaNum = teaNum;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getTeaClassDes() {
        return teaClassDes;
    }

    public void setTeaClassDes(String teaClassDes) {
        this.teaClassDes = teaClassDes;
    }

    @Override
    public String toString() {
        return "TeaClass{" +
                "teaNum='" + teaNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", teaClassDes='" + teaClassDes + '\'' +
                '}';
    }
}
