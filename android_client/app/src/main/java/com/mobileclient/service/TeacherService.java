package com.mobileclient.service;

import java.net.URLEncoder;
import java.util.List;

import com.mobileclient.activity.custom.HttpCallBackHandler;
import com.mobileclient.domain.Experiment;
import com.mobileclient.domain.Homework;
import com.mobileclient.domain.Sign;
import com.mobileclient.domain.StuClass;
import com.mobileclient.domain.Teacher;
import com.mobileclient.util.HttpUtil;

/*老师管理业务逻辑层*/
public class TeacherService {
    String result;
    Teacher teacher;
    List<StuClass> stuClassList;
    List<Sign>signList;
    List<Experiment>experimentList;
    List<Homework>homeworkList;
    public Teacher findTeacher(String teaNum) {
        String url = null;
        try {
            url = HttpUtil.BASE_URL + "teacher/info?teaNum="+ URLEncoder.encode(teaNum,"utf8");
            result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
                @Override
                public void onFinish(String result2) {
                    result = result2;
                    teacher = com.alibaba.fastjson.JSONObject.parseObject(result,Teacher.class);
                }

                @Override
                public void onError(String e) {
                }
            },"GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }


    public String UpdateTeacher(Teacher teacher) {
        String url = null;
        try {
            url = HttpUtil.BASE_URL + "teacher/info?teaNum="+ URLEncoder.encode(teacher.getTeaNum(),"utf8")+
            "&teaTel="+URLEncoder.encode(teacher.getTeaTel(),"utf8")+
                    "&teaEmail="+URLEncoder.encode(teacher.getTeaEmail(),"utf8")+
                    "&teaPasswd="+URLEncoder.encode(teacher.getTeaPasswd(),"utf8")+
                    "&teaDes="+URLEncoder.encode(teacher.getTeaDes(),"utf8")+
                    "&teaSex="+URLEncoder.encode(teacher.getTeaSex(),"utf8");
            result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
                @Override
                public void onFinish(String result2) {
                    result = result2;
                }

                @Override
                public void onError(String e) {
                }
            },"PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




    public String sendMail(String mailAddress, String teaNum) {
        String url = null;
        try {
            url = HttpUtil.BASE_URL + "teacher/mail?teaNum="+ URLEncoder.encode(teaNum,"utf8")+
            "&mailAddress="+URLEncoder.encode(mailAddress,"utf8");
            result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
                @Override
                public void onFinish(String result2) {
                    result = result2;
                }

                @Override
                public void onError(String e) {
                }
            },"PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String sendRegularMail(String mailAddress, String teaNum) {
        String url = null;
        try {
            url = HttpUtil.BASE_URL + "teacher/regular_mail?teaNum="+ URLEncoder.encode(teaNum,"utf8")+
                    "&mailAddress="+URLEncoder.encode(mailAddress,"utf8");
            result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
                @Override
                public void onFinish(String result2) {
                    result = result2;
                }

                @Override
                public void onError(String e) {
                }
            },"PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
