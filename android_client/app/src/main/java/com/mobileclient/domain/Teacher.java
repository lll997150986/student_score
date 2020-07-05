package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:48
 **/
public class Teacher  implements Serializable {
    String teaNum;
    String teaName;
    String teaTel;
    String teaSex;
    String teaEmail;
    String teaDes;
    String teaPasswd;

    public String getTeaNum() {
        return teaNum;
    }

    public void setTeaNum(String teaNum) {
        this.teaNum = teaNum;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTeaTel() {
        return teaTel;
    }

    public void setTeaTel(String teaTel) {
        this.teaTel = teaTel;
    }

    public String getTeaSex() {
        return teaSex;
    }

    public void setTeaSex(String teaSex) {
        this.teaSex = teaSex;
    }

    public String getTeaEmail() {
        return teaEmail;
    }

    public void setTeaEmail(String teaEmail) {
        this.teaEmail = teaEmail;
    }

    public String getTeaDes() {
        return teaDes;
    }

    public void setTeaDes(String teaDes) {
        this.teaDes = teaDes;
    }

    public String getTeaPasswd() {
        return teaPasswd;
    }

    public void setTeaPasswd(String teaPasswd) {
        this.teaPasswd = teaPasswd;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teaNum='" + teaNum + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaTel='" + teaTel + '\'' +
                ", teaSex='" + teaSex + '\'' +
                ", teaEmail='" + teaEmail + '\'' +
                ", teaDes='" + teaDes + '\'' +
                ", teaPasswd='" + teaPasswd + '\'' +
                '}';
    }
}
