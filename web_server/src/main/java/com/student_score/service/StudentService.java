package com.student_score.service;

import com.student_score.dao.StudentDao;
import com.student_score.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:53
 **/
@Service
public class StudentService {

    @Resource
    StudentDao studentDao;

    public List<Student> findStudentByName(String name){
        return studentDao.findStudentByName(name);
    }

    public List<Student> findStudentByNum(String num){
        return studentDao.findStudentByNum(num);
    }

    @Transactional
    public void insertStudent(Student student){
        studentDao.insertStudent(student);
    }

    @Transactional
    public void updateStudent(Student student){
        studentDao.updateStudent(student);
    }

    @Transactional
    public void delStudent(Student student){
        studentDao.delStudent(student);
    }

    @Transactional
    public void delAllStudent( ){
        studentDao.delAllStudent();
    }

    public List<Student> findAllStudent(){
        return studentDao.findAllStudent();
    }

    //    某门课的分数排名
    public List<Student>findAllStudentDesByScore(Classinfo classinfo){
        return studentDao.findAllStudentDesByScore(classinfo);
    }

    //    某门课的某个学生签到查询
    public List<Sign> findAllSignByStudent(String stuNum, String classNum){
        return studentDao.findAllSignByStudent(stuNum,classNum);
    }

    //    某门课的某个班级签到记录查询
    public List<Sign> findAllSignByClass(String signNum,String classNum){
        return studentDao.findAllSignByClass(signNum, classNum);
    }

    @Transactional
    public void insertSign(Sign sign){
        studentDao.insertSign(sign);
    }

    @Transactional
    public void delSignByStudent(Sign sign){
        studentDao.delSignByStudent(sign);
    }

    @Transactional
    public void delAllSignByClass(Classinfo classinfo){
        studentDao.delAllSignByClass(classinfo);
    }

    //  某门课的某个学生作业查询
    public List<Homework> findAllHomeWorkByStudent(Student student){
        return studentDao.findAllHomeWorkByStudent(student);
    }

    public List<Homework>findAllHomeworkByClass(String homeworkNum, String classNum){
        return studentDao.findAllHomeworkByClass(homeworkNum, classNum);
    }

    @Transactional
    public void updateHomework(Homework homework){
        studentDao.updateHomework(homework);
    }

    @Transactional
    public void delAllHomeworkByStudent(Student student){
        studentDao.delAllHomeworkByStudent(student);
    }

    @Transactional
    public void delAllHomeworkByClass(Classinfo classinfo){
        studentDao.delAllHomeworkByClass(classinfo);
    }

    //    实验查询
    public List<Experiment> findAllExperimentByStudent(Student student){
        return studentDao.findAllExperimentByStudent(student);
    }

    public List<Experiment> findAllExperimentByClass(String expNum, String classNum){
        return studentDao.findAllExperimentByClass(expNum,classNum);
    }

    @Transactional
    public void  updateExperiment(Experiment experiment){
        studentDao.updateExperiment(experiment);
    }

    @Transactional
    public void delAllExpermientByStudent(Student student){
        studentDao.delAllExpermientByStudent(student);
    }

    @Transactional
    public void delAllExperimentByClass(Classinfo classinfo){
        studentDao.delAllExperimentByClass(classinfo);
    }

    //    学生的课程查询.分数查询
    public List<Classinfo> findAllClassinfoByStudent(Student student){
        return studentDao.findAllClassinfoByStudent(student);
    }

    //查询某门课的排名和分数
    public List<StuClass> findStuClassByStuAndClass(String stuNum, String classNum){
        return studentDao.findStuClassByStuAndClass(stuNum,classNum);
    }

    @Transactional
    public void insertClassinfo(Classinfo classinfo){
        studentDao.insertClassinfo(classinfo);
    }

    @Transactional
    public void delClassinfo(@Param("classNum") String classNum,@Param("teaNum") String teaNum){
        studentDao.delClassinfo(classNum,teaNum);
    }

    @Transactional
    public void delClassByStudent(StuClass stuClass){
        studentDao.delClassByStudent(stuClass);
    }

    @Transactional
    public void delStuClass(String stuNum, String classNum) {
        studentDao.delStuClass(stuNum,classNum);
    }

    public void insertStuClass(StuClass stuClass) {
        studentDao.insertStuClass(stuClass);
    }

    public List<StuClass> findAllStuClassByClass(String classNum) {
        return studentDao.findAllStuClassByClass(classNum);
    }

    public List<Classinfo> findClassInfoByClass(String classNum) {
        return studentDao.findClassInfoByClass(classNum);
    }

    @Transactional
    public void insertExp(Experiment experiment) {
        studentDao.insertExperiment(experiment);
    }

    @Transactional
    public void insertHomework(Homework homework) {
        studentDao.insertHomework(homework);
    }

    @Transactional
    public void insertTeaClass(String teaNum, String classNum) {
        studentDao.insertTeaClass(teaNum,classNum);
    }

    @Transactional
    public void updateClassinfo(Classinfo classinfo) {
        studentDao.updateClassinfo(classinfo);
    }

    public int findStuNums(String classNum){
        return studentDao.findStuNums(classNum);
    }

    public int findMaxSignTimes(String classNum) {
        return  studentDao.findMaxSignTimes(classNum);
    }

    public int findMaxHomeworkTimes(String classNum) {
        return studentDao.findMaxHomeworkTimes(classNum);
    }

    public int findMaxExpTimes(String classNum) {
        return studentDao.findMaxExpTimes(classNum);
    }

    public String findHasNewHomework(String classNum) {
        return studentDao.findHasNewHomework(classNum);
    }

    @Transactional
    public void setHasNewHomework(String classNum, String hasNewHomework) {
        studentDao.setHasNewHomework(classNum, hasNewHomework);
    }

    public String findHasNewExp(String classNum) {
        return studentDao.findHasNewExp(classNum);
    }

    @Transactional
    public void setHasNewExp(String classNum, String hasNewExp) {
        studentDao.setHasNewExp(classNum,hasNewExp);
    }

    public int findTotalSignTimes(String classNum, String stuNum) {
        return studentDao.findTotalSignTimes(classNum,stuNum);
    }

    @Transactional
    public void updateStuClass(StuClass stuClass) {
        studentDao.updateStuClass(stuClass);
    }

    public List<StuClass> findAllStuClassByClassOrderByScore(String classNum) {
        return studentDao.findAllStuClassByClassOrderByScore(classNum);
    }

    public String findTotalHomeworkScore(String classNum, String stuNum) {
        return studentDao.findTotalHomeworkScore(classNum,stuNum);
    }

    public String findTotalExpScore(String classNum, String stuNum) {
        return studentDao.findTotalExpScore(classNum,stuNum);
    }

    public List<Sign> findAllSigns(String classNum) {
        return studentDao.findAllSigns(classNum);
    }

    public List<Experiment> findAllExperiments(String classNum) {
        return studentDao.findAllExperiments(classNum);
    }

    public List<Homework> findAllHomeworks(String classNum) {
        return studentDao.findAllHomeworks(classNum);
    }

    public String findClassinfoSigntimes(String classNum) {
        return studentDao.findClassinfoSigntimes(classNum);
    }
}
