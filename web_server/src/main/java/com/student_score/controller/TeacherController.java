package com.student_score.controller;

import com.student_score.domain.*;
import com.student_score.service.StudentService;
import com.student_score.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:51
 **/
//@EnableScheduling
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;



    //    老师使用工号登陆
    @PostMapping("/login")
    String login(String teaNum, String passwd){
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
            passwd = URLDecoder.decode(passwd,"utf8");

        List<Teacher> teachers = teacherService.findTeacherByNum(teaNum);
        for (Teacher teacher : teachers){
            if (teacher!=null && teacher.getTeaPasswd().equals(passwd)){
                return "success";
            }
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("regist")
    String regist(String teaNum, String teaName, String teaSex, String teaTel, String teaEmail, String teaDes, String teaPasswd){
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
            teaName = URLDecoder.decode(teaName,"utf8");
            teaSex = URLDecoder.decode(teaSex,"utf8");
            teaTel = URLDecoder.decode(teaTel,"utf8");
            teaEmail = URLDecoder.decode(teaEmail,"utf8");
            teaDes = URLDecoder.decode(teaDes,"utf8");
            teaPasswd = URLDecoder.decode(teaPasswd,"utf8");


            List<Teacher> teachers = teacherService.findTeacherByNum(teaNum);
                if (teachers == null || teachers.size() == 0){
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }





//    教师个人信息
    @GetMapping("/info")
    Teacher getInfo(String teaNum){
        List<Teacher> teachers = null;
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
            teachers = teacherService.findTeacherByNum(teaNum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (teachers != null){
            return teachers.get(0);
        }
        return null;
    }

    //    修改教师个人信息
    @PutMapping("/info")
    String changeInfo(String teaNum,String teaTel, String teaEmail, String teaPasswd,String teaDes,String teaSex){
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
            teaTel = URLDecoder.decode(teaTel,"utf8");
            teaEmail = URLDecoder.decode(teaEmail,"utf8");
            teaPasswd = URLDecoder.decode(teaPasswd,"utf8");
            teaDes = URLDecoder.decode(teaDes,"utf8");
            teaSex = URLDecoder.decode(teaSex,"utf8");
            List<Teacher> teachers = teacherService.findTeacherByNum(teaNum);
            Teacher teacher = teachers.get(0);
            teacher.setTeaTel(teaTel);
            teacher.setTeaPasswd(teaPasswd);
            teacher.setTeaEmail(teaEmail);
            teacher.setTeaDes(teaDes);
            teacher.setTeaSex(teaSex);
            teacherService.updateTeacher(teacher);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }

    //    替学生退课
    @DeleteMapping("/lesson")
    String quit(String stuNum, String classNum){
        try {
            stuNum = URLDecoder.decode(stuNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
            studentService.delStuClass(stuNum,classNum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }

    //    替学生选课
    @PostMapping("/lesson")
    String choose(String stuNum, String classNum){
        try {
            stuNum = URLDecoder.decode(stuNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
            StuClass stuClass = new StuClass();
            stuClass.setClassNum(classNum);
            stuClass.setStuNum(stuNum);
            studentService.insertStuClass(stuClass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }

//    修改学生选课信息，包括成绩和评价
    @PutMapping("/lesson")
    String updateLesson(String stuNum, String classNum,String stuScore, String stuClassDes){
        try {
            stuNum = URLDecoder.decode(stuNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
            stuScore = URLDecoder.decode(stuScore,"utf8");
            stuClassDes = URLDecoder.decode(stuClassDes,"utf8");
            int rank = 1;
            List<StuClass> stuClasses = studentService.findAllStuClassByClass(classNum);
            for (StuClass stuClass : stuClasses){
                rank++;
                if (stuClass.getStuNum().equals(stuNum)){
                  break;
                }
            }
            teacherService.updateStuClass(stuNum, classNum, stuScore, stuClassDes,""+rank);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }

    //返回指定课程的所有学生信息
    @GetMapping("/lesson")
    List<com.student_score.domain.result.StuClass> getLesson(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<com.student_score.domain.result.StuClass> stuClasses = teacherService.findAllStuClassByClassNum(classNum);
        return stuClasses;
    }


//    签到
    @PutMapping("/sign")
    String setTag(String classNum,String isValid,String signTime){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            isValid = URLDecoder.decode(isValid,"utf8");
            signTime = URLDecoder.decode(signTime,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        teacherService.updateIsValid(classNum, isValid,signTime);
        return "success";
    }

//    查看签到状态
    @GetMapping("/sign")
    List<Sign> getSign(String signNum, String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            signNum = URLDecoder.decode(signNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Sign> signs = studentService.findAllSignByClass(signNum, classNum);
        return signs;
    }

    @GetMapping("/sign_times")
    String getSignTimes( String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int times = studentService.findMaxSignTimes(classNum);
        return ""+times;
    }

    //显示某次实验的信息
    @GetMapping("/experiment")
    List<Experiment> getExp(String expNum, String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            expNum = URLDecoder.decode(expNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Experiment> experiments = studentService.findAllExperimentByClass(expNum, classNum);
        return experiments;
    }

    @PutMapping("/experiment")
    String  changeExp(String expNum, String classNum, String stuNum, String expScore, String expDes){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            expNum = URLDecoder.decode(expNum,"utf8");
            stuNum = URLDecoder.decode(stuNum,"utf8");
            expScore = URLDecoder.decode(expScore,"utf8");
            expDes = URLDecoder.decode(expDes,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Experiment experiment = new Experiment();
        experiment.setExpDes(expDes);
        experiment.setExpScore(Double.parseDouble(expScore));
        experiment.setExpNum(expNum);
        experiment.setClassNum(classNum);
        experiment.setStuNum(stuNum);
        studentService.updateExperiment(experiment);
        return "success";
    }

    @PostMapping("/experiment")
    String insertExp(String expNum, String classNum, String stuNum, String expScore, String expDes){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            expNum = URLDecoder.decode(expNum,"utf8");
            stuNum = URLDecoder.decode(stuNum,"utf8");
            expScore = URLDecoder.decode(expScore,"utf8");
            expDes = URLDecoder.decode(expDes,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Experiment experiment = new Experiment();
        experiment.setExpDes(expDes);
        experiment.setExpScore(Double.parseDouble(expScore));
        experiment.setExpNum(expNum);
        experiment.setClassNum(classNum);
        experiment.setStuNum(stuNum);
        studentService.insertExp(experiment);
        return "success";
    }

    @GetMapping("/experiment_times")
    String getExpTimes( String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int times = studentService.findMaxExpTimes(classNum);
        return ""+times;
    }

    @GetMapping("/new_exp")
    String hasNewExp(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result  = studentService.findHasNewExp(classNum);
        return result;
    }

    @PutMapping("/new_exp")
    String setNewExp(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int max = Integer.parseInt(getExpTimes(classNum));
        List<com.student_score.domain.result.StuClass> stuClasses = getLesson(classNum);
        Experiment experiment = new Experiment();
        for (com.student_score.domain.result.StuClass stuClass : stuClasses){
            String stuNum = stuClass.getStuNum();
            experiment.setExpNum(""+(max+1));
            experiment.setStuNum(stuNum);
            experiment.setClassNum(classNum);
            studentService.insertExp(experiment);
        }
        return "success";
    }

    //显示某次作业的信息
    @GetMapping("/homework")
    List<Homework> getHomework(String homeworkNum, String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            homeworkNum = URLDecoder.decode(homeworkNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Homework> homeworks = studentService.findAllHomeworkByClass(homeworkNum, classNum);
        return homeworks;
    }

    @PutMapping("/homework")
    String  changeHomework(String homeworkNum, String classNum, String stuNum, String homeworkScore, String homeworkDes){
        try {
            homeworkNum = URLDecoder.decode(homeworkNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
            stuNum = URLDecoder.decode(stuNum,"utf8");
            homeworkScore = URLDecoder.decode(homeworkScore,"utf8");
            homeworkDes = URLDecoder.decode(homeworkDes,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Homework homework = new Homework();
        homework.setClassNum(classNum);
        homework.setHomeworkScore(Double.parseDouble(homeworkScore));
        homework.setHomeworkDes(homeworkDes);
        homework.setStuNum(stuNum);
        homework.setHomeworkNum(homeworkNum);
        studentService.updateHomework(homework);
        return "success";
    }

    @PostMapping("/homework")
    String insertHomework(String homeworkNum, String classNum, String stuNum, String homeworkScore, String homeworkDes){
        try {
            homeworkNum = URLDecoder.decode(homeworkNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
            stuNum = URLDecoder.decode(stuNum,"utf8");
            homeworkScore = URLDecoder.decode(homeworkScore,"utf8");
            homeworkDes = URLDecoder.decode(homeworkDes,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Homework homework = new Homework();
        homework.setClassNum(classNum);
        homework.setHomeworkScore(Double.parseDouble(homeworkScore));
        homework.setHomeworkDes(homeworkDes);
        homework.setStuNum(stuNum);
        homework.setHomeworkNum(homeworkNum);
        studentService.insertHomework(homework);
        return "success";
    }

    @GetMapping("/homework_times")
    String getHomeworkTimes( String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int times = studentService.findMaxHomeworkTimes(classNum);
        return ""+times;
    }

    @GetMapping("/new_homework")
    String hasNewHomework(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result  = studentService.findHasNewHomework(classNum);
        return result;
    }

    @PutMapping("/new_homework")
    String setNewHomework(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int max = Integer.parseInt(getHomeworkTimes(classNum));
        List<com.student_score.domain.result.StuClass> stuClasses = getLesson(classNum);
        Homework homework = new Homework();
        for (com.student_score.domain.result.StuClass stuClass : stuClasses){
            String stuNum = stuClass.getStuNum();
            homework.setHomeworkNum(""+(max+1));
            homework.setStuNum(stuNum);
            homework.setClassNum(classNum);
            studentService.insertHomework(homework);
        }
        return "success";
    }




//    开课
    @PostMapping("/class")
    String insertClassinfo(String teaNum,String classNum, String classLocation, String classDes,  String bornDate){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            teaNum = URLDecoder.decode(teaNum,"utf8");
            classLocation = URLDecoder.decode(classLocation,"utf8");
            classDes = URLDecoder.decode(classDes,"utf8");
            bornDate = URLDecoder.decode(bornDate,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Classinfo> classinfos = studentService.findClassInfoByClass(classNum);
        if (classinfos == null || classinfos.size() == 0){
            Classinfo classinfo = new Classinfo();
            classinfo.setClassNum(classNum);
            classinfo.setClassLocation(classLocation);
            classinfo.setClassDes(classDes);
            classinfo.setClassStudents(0);
            classinfo.setBornDate(bornDate);
            classinfo.setIsValid("false");
            studentService.insertClassinfo(classinfo);
        }
        studentService.insertTeaClass(teaNum,classNum);
        return "success";
    }

    @PutMapping("/class")
    String updateClassinfo(String classNum, String classLocation, String classDes,  String bornDate){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
            classLocation = URLDecoder.decode(classLocation,"utf8");
            classDes = URLDecoder.decode(classDes,"utf8");
            bornDate = URLDecoder.decode(bornDate,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Classinfo> classinfos = studentService.findClassInfoByClass(classNum);
        if (classinfos == null || classinfos.size() == 0){
            return null;
        }
        Classinfo classinfo = classinfos.get(0);
        classinfo.setClassLocation(classLocation);
        classinfo.setClassDes(classDes);
        classinfo.setBornDate(bornDate);
        classinfo.setIsValid("false");
        int stuNums = studentService.findStuNums(classNum);
        classinfo.setClassStudents(stuNums);
        studentService.updateClassinfo(classinfo);
        return "success";
    }

    //    显示教授的所有课程信息
    @GetMapping("/class")
    List<Classinfo> getLessons(String teaNum){
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        List<Classinfo> classinfos = teacherService.findAllClassByTeacher(teacher);
        for (Classinfo classinfo : classinfos){
            String classNum = classinfo.getClassNum();
            int nums = teacherService.findStudents(classNum);
            classinfo.setClassStudents(nums);
        }
        return classinfos;
    }

    @DeleteMapping("/class")
    String delClassinfo(String teaNum, String classNum){
        try {
            teaNum = URLDecoder.decode(teaNum,"utf8");
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        studentService.delClassinfo(classNum, teaNum);
        return "success";
    }

    @PutMapping ("/score")
    public String setScore(String classNum){
        try {
            classNum = URLDecoder.decode(classNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int maxSignTimes = studentService.findMaxSignTimes(classNum);
        int maxExpTimes = studentService.findMaxExpTimes(classNum);
        int maxHomeworkTimes = studentService.findMaxHomeworkTimes(classNum);
        List<StuClass> stuClasses = studentService.findAllStuClassByClass(classNum);
        int peopleNums = stuClasses.size();
        for (int i = 0; i < peopleNums; i++){
            String stuNum = stuClasses.get(i).getStuNum();
            int signTimes = studentService.findTotalSignTimes(classNum,stuNum);
            double signScore = signTimes*1.0/maxSignTimes * 20 ;
            String re1 = studentService.findTotalHomeworkScore(classNum,stuNum);
            double totalHomeworkScores;
            if (re1 == null ){
                totalHomeworkScores=0.0;
            }else {
                totalHomeworkScores = Double.parseDouble(re1);
            }
            double homeworkScore;
            if (maxHomeworkTimes == 0){
                homeworkScore = 0;
            }else {
                homeworkScore = (totalHomeworkScores/(maxHomeworkTimes * 10.0)) * 30.0;
            }
             String re2 = studentService.findTotalExpScore(classNum,stuNum);
            double totalExpScores;
             if (re2 == null){
                 totalExpScores=0.0;
             }else {
                 totalExpScores = Double.parseDouble(re2);
             }
            double expScore;
             if (maxExpTimes == 0){
                 expScore = 0;
             }else {
                 expScore = (totalExpScores/(maxExpTimes * 10.0)) * 50.0;
             }
            double sum = signScore + homeworkScore + expScore;
            if (sum < 60){
                stuClasses.get(i).setStuClassDes("不及格");
            }else {
                if (sum > 90){
                    stuClasses.get(i).setStuClassDes("优");
                }else {
                    stuClasses.get(i).setStuClassDes("良");
                }
            }
            stuClasses.get(i).setStuScore(sum);
            studentService.updateStuClass(stuClasses.get(i));
        }

        List<StuClass> stuClasses2 = studentService.findAllStuClassByClassOrderByScore(classNum);
        for (int i = 0; i < stuClasses2.size(); i++){
            stuClasses2.get(i).setStuRank(i+1+"");
        }
        return "success";
    }



//    邮件内容

    private List<Sign> getSignContent(String teaNum){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        List<Classinfo> classinfos = teacherService.findAllClassByTeacher(teacher);
        String classNum;
        List<Sign>totalList = new ArrayList<>();
        for (Classinfo classinfo : classinfos){
            classNum = classinfo.getClassNum();
            List<Sign> signs = studentService.findAllSigns(classNum);
            totalList.addAll(signs);
        }
        return totalList;
    }



    private List<Experiment> getExpContent(String teaNum){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        List<Classinfo> classinfos = teacherService.findAllClassByTeacher(teacher);
        String classNum;
        List<Experiment>totalList = new ArrayList<>();
        for (Classinfo classinfo : classinfos){
            classNum = classinfo.getClassNum();
            List<Experiment> signs = studentService.findAllExperiments(classNum);
            totalList.addAll(signs);
        }
        return totalList;
    }

    private List<Homework> getHomeworkContent(String teaNum){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        List<Classinfo> classinfos = teacherService.findAllClassByTeacher(teacher);
        String classNum;
        List<Homework>totalList = new ArrayList<>();
        for (Classinfo classinfo : classinfos){
            classNum = classinfo.getClassNum();
            List<Homework> signs = studentService.findAllHomeworks(classNum);
            totalList.addAll(signs);
        }
        return totalList;
    }

    private List<StuClass> getStuClassContent(String teaNum){
        Teacher teacher = new Teacher();
        teacher.setTeaNum(teaNum);
        List<Classinfo> classinfos = teacherService.findAllClassByTeacher(teacher);
        String classNum;
        List<StuClass>totalList = new ArrayList<>();
        for (Classinfo classinfo : classinfos){
            classNum = classinfo.getClassNum();
            List<StuClass> stuClasses = studentService.findAllStuClassByClassOrderByScore(classNum);
            totalList.addAll(stuClasses);
        }
        return totalList;
    }

    @PutMapping ("/regular_mail")
    @Scheduled(cron =" 0 0 8 ? * 1,4 " )//每周一，周四的早上8点发送邮件
    public String sendRegularMail(String mailAddress,String teaNum){
        try {
            mailAddress = URLDecoder.decode(mailAddress,"utf8");
            teaNum = URLDecoder.decode(teaNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendMail(mailAddress,teaNum);
        return "success";
    }



    @PutMapping ("/mail")
    public String sendMail(String mailAddress,String teaNum){
        try {
            mailAddress = URLDecoder.decode(mailAddress,"utf8");
            teaNum = URLDecoder.decode(teaNum,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalMailAddress = mailAddress;
        String finalTeaNum = teaNum;
        String finalTeaNum1 = teaNum;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
//                发送方
                    helper.setFrom(from);
                    helper.setSubject("成绩报告");
                    helper.setTo(finalMailAddress);
                    helper.setText("教师工号"+ finalTeaNum1 +"的成绩报告");
                    helper.addAttachment("teaNum+"+finalTeaNum +"_record.txt",getEmailContent(finalTeaNum));
                    mailSender.send(mimeMessage);

                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        return "success";
    }




    private File getEmailContent(String teaNum){
        String url = null;
//        获取签到信息
        List<Sign>signList = getSignContent(teaNum);
//获取实验信息
        List<Experiment> experimentList = getExpContent(teaNum);
//        获取作业信息
        List<Homework> homeworkList = getHomeworkContent(teaNum);
//        总评成绩信息
        List<StuClass> stuClassList = getStuClassContent(teaNum);
        File dir = new File(".\\"+teaNum);
        if(!dir.isDirectory()){
            dir.mkdir();//创建目录
        }
        File file = new File(dir,"record.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            writer.write("------------------------------签到信息--------------------------------\n");
            writer.append("班级号\t\t签到\t\t学号\t\t成绩\t\t评价\n");
            for (Sign sign : signList){
                writer.append(sign.getClassNum()+"\t\t"+sign.getSignNum()+"\t\t"+sign.getStuNum()+"\t\t"+"10\t\t"+"优\n");
            }
            writer.append("------------------------------作业信息--------------------------------\n");
            writer.append("班级号\t\t作业\t\t学号\t\t成绩\t\t评价\n");
            for (Homework homework : homeworkList){
                writer.append(homework.getClassNum()+"\t\t"+homework.getHomeworkNum()+"\t\t"+homework.getStuNum()+"\t\t"+homework.getHomeworkScore()+"\t\t"+homework.getHomeworkDes()+"\n");
            }
            writer.append("------------------------------实验信息--------------------------------\n");
            writer.append("班级号\t\t实验\t\t学号\t\t成绩\t\t评价\n");
            for (Experiment experiment : experimentList){
                writer.append(experiment.getClassNum()+"\t\t"+experiment.getExpNum()+"\t\t"+experiment.getStuNum()+"\t\t"+experiment.getExpScore()+"\t\t"+experiment.getExpDes()+"\n");
            }
            writer.append("------------------------------总评--------------------------------\n");
            writer.append("班级号\t\t学号\t\t排名\t\t成绩\t\t评价\n");
            for (StuClass stuClass : stuClassList ){
                writer.append(stuClass.getClassNum()+"\t\t"+stuClass.getStuNum()+"\t\t"+stuClass.getStuRank()+"\t\t"+stuClass.getStuScore()+"\t\t"+stuClass.getStuClassDes()+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
