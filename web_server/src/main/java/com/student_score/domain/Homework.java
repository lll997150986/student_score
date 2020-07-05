package com.student_score.domain;

import lombok.Data;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:37
 **/
@Data
public class Homework {
    String homeworkNum;
    String stuNum;
    String classNum;
    String homeworkDes;
    Double homeworkScore;
}
