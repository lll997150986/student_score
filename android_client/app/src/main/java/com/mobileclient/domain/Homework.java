package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:37
 **/
public class Homework  implements Serializable {
    String homeworkNum;
    String stuNum;
    String classNum;
    String homeworkDes;
    Double homeworkScore;

    public String getHomeworkNum() {
        return homeworkNum;
    }

    public void setHomeworkNum(String homeworkNum) {
        this.homeworkNum = homeworkNum;
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

    public String getHomeworkDes() {
        return homeworkDes;
    }

    public void setHomeworkDes(String homeworkDes) {
        this.homeworkDes = homeworkDes;
    }

    public Double getHomeworkScore() {
        return homeworkScore;
    }

    public void setHomeworkScore(Double homeworkScore) {
        this.homeworkScore = homeworkScore;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "homeworkNum='" + homeworkNum + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", classNum='" + classNum + '\'' +
                ", homeworkDes='" + homeworkDes + '\'' +
                ", homeworkScore=" + homeworkScore +
                '}';
    }
}
