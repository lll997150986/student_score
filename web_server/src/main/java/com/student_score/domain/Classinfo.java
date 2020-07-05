package com.student_score.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 00:08
 **/
@Data
public class Classinfo {
    String classNum;
    String classLocation;
    String classDes;
    Integer classStudents;
    String bornDate;
    String  isValid;
    String hasNewHomework;
    String hasNewExp;
    String signTimes;
}
