package com.student_score.dao;

import com.student_score.domain.Classinfo;
import com.student_score.domain.TeaClass;
import com.student_score.domain.Teacher;
import com.student_score.domain.result.StuClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherDao {

    void insertTeacher(Teacher teacher);
    void delTeacher(Teacher teacher);
    void updateTeacher(Teacher teacher);
    List<Teacher> findTeacherByNum(String teaNum);

    List<Classinfo> findAllClassByTeacher(Teacher teacher);
    void delTeaClass(TeaClass teaClass);


    List<Teacher> findAllTeacher();

    List<StuClass> findAllStuClassByClassNum(String classNum);

    void updateIsValid(String classNum, String isValid, String signTime);

    void updateStuClass(String stuNum, String classNum, String stuScore, String stuClassDes, String rank);

    int findStudents(String classNum);
}
