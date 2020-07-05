package com.student_score.controller;

import com.student_score.domain.Admin;
import com.student_score.domain.Student;
import com.student_score.domain.Teacher;
import com.student_score.service.AdminService;
import com.student_score.service.StudentService;
import com.student_score.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:52
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @PostMapping("/login")
    String login(String username, String passwd){
        List<Admin> adminList = adminService.queryAdminByName(username);
        for (Admin admin : adminList){
            if (passwd.equals(admin.getPasswd())){
                return "success";
            }
        }
        return null;
    }

    @PostMapping("/teacher")
    String insertTeacher( String teaNum, String teaName, String teaSex, String teaTel, String teaEmail, String teaDes, String teaPasswd){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        teacher.setTeaName(teaName);
        teacher.setTeaTel(teaTel);
        teacher.setTeaDes(teaDes);
        teacher.setTeaEmail(teaEmail);
        teacher.setTeaPasswd(teaPasswd);
        teacher.setTeaSex(teaSex);
        teacherService.insertTeacher(teacher);
        return "success";
    }
    @GetMapping("/teacher")
    List<Teacher> listTeacher(){
        List<Teacher> teachers = teacherService.findAllTeacher();
        return teachers;
    }

    @DeleteMapping("/teacher")
    String deleteTeacher(String teaNum){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        teacherService.delTeacher(teacher);
        return "success";
    }

    @PostMapping("/student")
    String insertStudent(String stuNum, String stuName, String stuSex, String stuTel, String stuEmail, String stuDes, String stuPasswd,String stuClass){
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
    @GetMapping("/student")
    List<Student> listStudent(){
        List<Student> students = studentService.findAllStudent();
        return students;
    }

    @DeleteMapping("/student")
    String deleteStudent(String stuNum){
        Student student = new Student();
        student.setStuNum(stuNum);
        studentService.delStudent(student);
        return "success";
    }




}
