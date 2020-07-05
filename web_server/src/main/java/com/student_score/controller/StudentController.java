package com.student_score.controller;

import com.student_score.domain.*;
import com.student_score.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:52
 **/
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

//    学生使用学号登陆
    @PostMapping("/login")
    String login(String stuNum, String passwd){
        List<Student> students = studentService.findStudentByNum(stuNum);
        for (Student student : students){
            if (passwd.equals(student.getStuPasswd())){
                return "success";
            }
        }
        return null;
    }

//    注册
    @PostMapping("regist")
    String regist(String stuNum, String stuName, String stuSex, String stuTel, String stuEmail, String stuDes, String stuPasswd,String stuClass){
        List<Student> students = studentService.findStudentByNum(stuNum);
        if (students == null || students.size() == 0){
            Student student = new Student();
            student.setStuNum(stuNum);
            student.setStuName(stuName);
            student.setStuSex(stuSex);
            student.setStuTel(stuTel);
            student.setStuEmail(stuEmail);
            student.setStuDes(stuDes);
            student.setStuPasswd(stuPasswd);
            student.setStuClass(stuClass);
            studentService.insertStudent(student);
            return "success";
        }
        return null;
    }


    //学生查看课程界面
    @GetMapping("/lessons")
    List<Classinfo> getLessons(String stuNum){
        try {
            stuNum =URLDecoder.decode(stuNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Student student = new Student();
        student.setStuNum(stuNum);
        List<Classinfo> classinfos = studentService.findAllClassinfoByStudent(student);
        return classinfos;
    }

//    查看具体课程信息
    @GetMapping("/lesson")
    Classinfo getLesson(String stuNum, String classNum){
        Student student = new Student();
        student.setStuNum(stuNum);
        List<Classinfo> classinfos = studentService.findAllClassinfoByStudent(student);
        for (Classinfo classinfo : classinfos){
            if (classinfo.getClassNum().equals(classNum)){
                return classinfo;
            }
        }
        return null;
    }

//    查看具体课程信息-成绩
    @GetMapping("/lesson/detail")
    List<StuClass> getLessonDetail(String stuNum, String classNum){
        List<StuClass> stuClassList = studentService.findStuClassByStuAndClass(stuNum, classNum);
        return stuClassList;
    }

//    退课
    @DeleteMapping("/lesson")
    String quit(String stuNum, String classNum){
        studentService.delStuClass(stuNum,classNum);
        return "success";
    }

//    选课
    @PostMapping("/lesson")
    String choose(String stuNum, String classNum){
        StuClass stuClass = new StuClass();
        stuClass.setClassNum(classNum);
        stuClass.setStuNum(stuNum);
        studentService.insertStuClass(stuClass);
        return "success";
    }

//    签到
    @PostMapping("/sign")
    String sign(String signNum,String stuNum, String classNum){
        try {
            signNum = URLDecoder.decode(signNum,"utf8");
            stuNum = URLDecoder.decode(stuNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Classinfo> classinfos = studentService.findClassInfoByClass(classNum);
        if (classinfos.get(0)!=null && classinfos.get(0).getIsValid().contains("true")){
            Sign sign = new Sign();
            sign.setClassNum(classNum);
            sign.setStuNum(stuNum);
            sign.setSignNum(signNum);
            studentService.insertSign(sign);
            return "success";
        }
        return null;
    }

//    签到是否有效
    @GetMapping("/sign")
    String isValid( String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Classinfo> classinfos = studentService.findClassInfoByClass(classNum);
        if (classinfos.get(0)!=null && classinfos.get(0).getIsValid().contains("true")){
            return "true";
        }else {
            return "false";
        }
    }
//    获取当前签到次数
    @GetMapping("sign_time")
    String getSign(String classNum){
        return studentService.findClassinfoSigntimes(classNum);
    }

//    查看排名
    @GetMapping("/rank")
    List<StuClass> getRank(String classNum){
        List<StuClass> stuClasses = studentService.findAllStuClassByClass(classNum);
        return stuClasses;
    }

//    个人信息
    @GetMapping("/info")
    Student getInfo(String stuNum){
        List<Student> students = studentService.findStudentByNum(stuNum);
        return students.get(0);
    }


//    修改个人信息
    @PutMapping("/info")
    String changeInfo(String stuNum,String stuTel, String stuEmail, String stuPasswd){
        List<Student> students = studentService.findStudentByNum(stuNum);
        Student student = students.get(0);
        student.setStuPasswd(stuPasswd);
        student.setStuEmail(stuEmail);
        student.setStuTel(stuTel);
        return "success";
    }

    //显示某次作业的信息
    @GetMapping("/homework")
    List<Homework> getHomework(String homeworkNum, String classNum){
        List<Homework> homeworks = studentService.findAllHomeworkByClass(homeworkNum, classNum);
        return homeworks;
    }


    //显示某次实验的信息
    @GetMapping("/experiment")
    List<Experiment> getExp(String expNum, String classNum){
        List<Experiment> experiments = studentService.findAllExperimentByClass(expNum, classNum);
        return experiments;
    }

}
