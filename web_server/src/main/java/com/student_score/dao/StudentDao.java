package com.student_score.dao;

import com.student_score.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentDao {
    List<Student> findStudentByName(String name);
    List<Student> findStudentByNum(String num);
    void insertStudent(Student student);
    void updateStudent(Student student);
    void delStudent(Student student);
    void delAllStudent( );
    List<Student> findAllStudent();

//    某门课的分数排名
    List<Student>findAllStudentDesByScore(Classinfo classinfo);
//    某门课的某个学生签到查询
    List<Sign> findAllSignByStudent(String stuNum,String classNum);
//    某门课的某个班级某次签到记录查询
    List<Sign> findAllSignByClass(String signNum, String classNum);

    void insertSign(Sign sign);
    void delSignByStudent(Sign sign);
    void delAllSignByClass(Classinfo classinfo);

//  某门课的某个学生作业查询
    List<Homework> findAllHomeWorkByStudent(Student student);
    List<Homework>findAllHomeworkByClass(String homeworkNum, String classNum);

    void insertHomework(Homework homework);
    void updateHomework(Homework homework);
    void delAllHomeworkByStudent(Student student);
    void delAllHomeworkByClass(Classinfo classinfo);

//    实验查询
    List<Experiment> findAllExperimentByStudent(Student student);
    List<Experiment> findAllExperimentByClass(String expNum, String classNum);

    void  updateExperiment(Experiment experiment);
    void delAllExpermientByStudent(Student student);
    void delAllExperimentByClass(Classinfo classinfo);

//    学生的课程查询.分数查询
    List<Classinfo> findAllClassinfoByStudent(Student student);
    List<StuClass> findStuClassByStuAndClass(String stuNum, String classNum);
    void insertClassinfo(Classinfo classinfo);
    void delClassinfo(@Param("classNum") String classNum, @Param("teaNum") String teaNum);
    void delClassByStudent(StuClass stuClass);

    void delStuClass(String stuNum, String classNum);

    void insertStuClass(StuClass stuClass);

    List<StuClass> findAllStuClassByClass(String classNum);

    List<Classinfo> findClassInfoByClass(String classNum);

    void insertExperiment(Experiment experiment);

    void insertTeaClass(String teaNum, String classNum);

    void updateClassinfo(Classinfo classinfo);

    int findStuNums(String classNum);

    int findMaxSignTimes(String classNum);

    int findMaxHomeworkTimes(String classNum);

    int findMaxExpTimes(String classNum);

    String findHasNewHomework(String classNum);

    void setHasNewHomework(String classNum, String hasNewHomework);

    String findHasNewExp(String classNum);

    void setHasNewExp(String classNum, String hasNewExp);

    int findTotalSignTimes(String classNum, String stuNum);

    void updateStuClass(StuClass stuClass);

    List<StuClass> findAllStuClassByClassOrderByScore(String classNum);

    String findTotalHomeworkScore(String classNum, String stuNum);

    String findTotalExpScore(String classNum, String stuNum);

    List<Sign> findAllSigns(String classNum);

    List<Experiment> findAllExperiments(String classNum);

    List<Homework> findAllHomeworks(String classNum);

    String findClassinfoSigntimes(String classNum);
}
