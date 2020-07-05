package com.mobileclient.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


import com.mobileclient.activity.custom.HttpCallBackHandler;
import com.mobileclient.domain.Student;
import com.mobileclient.domain.result.StuClass;
import com.mobileclient.util.HttpUtil;

/*学生管理业务逻辑层*/
public class StudentService {
	private String result;
	private  List<StuClass> studentList = null;
	/* 添加学生 */
	public String AddStudent(Student student,String classNum) {
		try {
			String url = HttpUtil.BASE_URL + "teacher/lesson?stuNum="+URLEncoder.encode(student.getStuNum(),"utf8")+
					"&classNum="+URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}
				@Override
				public void onError(String e) {
				}
			}, "POST");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 查询学生 */
	public List<StuClass> QueryStudent(String classNum) {
		try {
			String url = HttpUtil.BASE_URL + "teacher/lesson?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					studentList = com.alibaba.fastjson.JSONObject.parseArray(result,StuClass.class);
				}
				@Override
				public void onError(String e) {
				}
			},"GET");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return studentList;
	}


	/* 更新学生 */
	public String UpdateStudent(com.mobileclient.domain.StuClass student) {
		try {
			String url = HttpUtil.BASE_URL + "teacher/lesson?classNum="+ URLEncoder.encode(student.getClassNum(),"utf8")+
                    "&stuNum="+ URLEncoder.encode(student.getStuNum(),"utf8")+
					"&stuScore="+URLEncoder.encode(""+student.getStuScore(),"utf8")+
                    "&stuClassDes="+URLEncoder.encode(student.getStuClassDes(),"utf8");

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

	/* 删除学生 */
	public String DeleteStudent(Student student,String classNum) {
		try {
			String url = HttpUtil.BASE_URL + "teacher/lesson?classNum="+ URLEncoder.encode(classNum,"utf8")+"&stuNum="+
					URLEncoder.encode(student.getStuNum(),"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}
				@Override
				public void onError(String e) {
					result = e;
				}
			}, "DELETE");
		} catch (Exception e) {
			e.printStackTrace();
			result= "班级信息删除失败!";
		}
		return result;
	}

	/* 根据学号获取学生对象 */
	public StuClass GetStudent(String classNum, String stuNum)  {
		List<StuClass> stuClassList = QueryStudent(classNum);
		for (StuClass stuClass : stuClassList){
			if (stuClass.getStuNum().equals(stuNum)){
				return stuClass;
			}
		}
		return null;
	}
}
