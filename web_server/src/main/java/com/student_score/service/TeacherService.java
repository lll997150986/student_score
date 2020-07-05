package com.student_score.service;

import com.student_score.dao.TeacherDao;
import com.student_score.domain.Classinfo;
import com.student_score.domain.TeaClass;
import com.student_score.domain.Teacher;
import com.student_score.domain.result.StuClass;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class TeacherService {
    @Resource
    TeacherDao teacherDao;


    @Transactional
    public void insertTeacher(Teacher teacher){
        teacherDao.insertTeacher(teacher);
    }

    @Transactional
    @CacheEvict(key ="#teachers",allEntries=true)
    public void delTeacher(Teacher teacher){
        teacherDao.delTeacher(teacher);
    }

    @Transactional
    @CachePut(key = "#teachers")
    public void updateTeacher(Teacher teacher){
        teacherDao.updateTeacher(teacher);
    }

    @Cacheable(key ="#teachers")
    public List<Teacher> findTeacherByNum(String teaNum){
        return teacherDao.findTeacherByNum(teaNum);
    }

    public List<Classinfo> findAllClassByTeacher(Teacher teacher){
        return teacherDao.findAllClassByTeacher(teacher);
    }

    @Transactional
    public void delTeaClass(TeaClass teaClass){
        teacherDao.delTeaClass(teaClass);
    }


    public List<Teacher> findAllTeacher() {
        return teacherDao.findAllTeacher();
    }

    public List<StuClass> findAllStuClassByClassNum(String classNum) {
        return teacherDao.findAllStuClassByClassNum(classNum);
    }

    @Transactional
    public void updateIsValid(String classNum, String isValid,String signTime) {
        teacherDao.updateIsValid(classNum,isValid,signTime);
    }

    @Transactional
    public void updateStuClass(String stuNum, String classNum, String stuScore, String stuClassDes, String rank) {
        teacherDao.updateStuClass(stuNum,classNum, stuScore, stuClassDes, rank);
    }

    public int findStudents(String classNum) {
        return teacherDao.findStudents(classNum);
    }
}
