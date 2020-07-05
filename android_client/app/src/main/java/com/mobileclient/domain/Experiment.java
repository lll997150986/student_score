package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:35
 **/
public class Experiment  implements Serializable {
    String  expNum;
    String stuNum;
    String classNum;
    String expDes;
    Double expScore;

    public String getExpNum() {
        return expNum;
    }

    public void setExpNum(String expNum) {
        this.expNum = expNum;
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

    public String getExpDes() {
        return expDes;
    }

    public void setExpDes(String expDes) {
        this.expDes = expDes;
    }

    public Double getExpScore() {
        return expScore;
    }

    public void setExpScore(Double expScore) {
        this.expScore = expScore;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "expNum='" + expNum + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", expDes='" + expDes + '\'' +
                ", expScore=" + expScore +
                '}';
    }
}
