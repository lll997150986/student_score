package com.mobileclient.domain;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 00:08
 **/
public class Classinfo  implements Serializable {
    String classNum;
    String classLocation;
    String classDes;
    Integer classStudents;
    String bornDate;
    String  isValid;
    String hasNewHomework;
    String hasNewExp;

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public String getClassDes() {
        return classDes;
    }

    public void setClassDes(String classDes) {
        this.classDes = classDes;
    }

    public Integer getClassStudents() {
        return classStudents;
    }

    public void setClassStudents(Integer classStudents) {
        this.classStudents = classStudents;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getHasNewHomework() {
        return hasNewHomework;
    }

    public void setHasNewHomework(String hasNewHomework) {
        this.hasNewHomework = hasNewHomework;
    }

    public String getHasNewExp() {
        return hasNewExp;
    }

    public void setHasNewExp(String hasNewExp) {
        this.hasNewExp = hasNewExp;
    }

    @Override
    public String toString() {
        return "Classinfo{" +
                "classNum='" + classNum + '\'' +
                ", classLocation='" + classLocation + '\'' +
                ", classDes='" + classDes + '\'' +
                ", classStudents=" + classStudents +
                ", bornDate='" + bornDate + '\'' +
                ", isValid='" + isValid + '\'' +
                ", hasNewHomework='" + hasNewHomework + '\'' +
                ", hasNewExp='" + hasNewExp + '\'' +
                '}';
    }
}
