package com.student_score.domain;

import lombok.Data;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:35
 **/
@Data
public class Experiment {
    String  expNum;
    String stuNum;
    String classNum;
    String expDes;
    Double expScore;
}
