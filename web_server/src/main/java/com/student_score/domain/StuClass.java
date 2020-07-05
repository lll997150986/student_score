package com.student_score.domain;

import lombok.Data;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:42
 **/
@Data
public class StuClass {
    String stuNum;
    String classNum;
    String stuRank;
    Double stuScore;
    String stuClassDes;
}
