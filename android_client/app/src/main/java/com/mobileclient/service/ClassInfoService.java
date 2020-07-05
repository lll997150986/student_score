package com.mobileclient.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mobileclient.activity.custom.HttpCallBackHandler;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.util.HttpUtil;

/*班级管理业务逻辑层*/
public class ClassInfoService {
	private String result;
	private static  List<Classinfo> classInfoList;
	/* 添加班级 */
	public String AddClassInfo(Classinfo classInfo,String teaNum) {
		try {
			String url = HttpUtil.BASE_URL + "teacher/class?teaNum="+URLEncoder.encode(teaNum,"utf8")+
					"&classNum="+URLEncoder.encode(classInfo.getClassNum(),"utf8")+
					"&classLocation="+ URLEncoder.encode(classInfo.getClassLocation(),"utf8")+
					"&classDes="+ URLEncoder.encode(classInfo.getClassDes(),"utf8")+
					"&classStudents="+ URLEncoder.encode(""+classInfo.getClassStudents(),"utf8")+
					"&bornDate="+URLEncoder.encode(classInfo.getBornDate(),"utf8");
            result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}
				@Override
				public void onError(String e) {}
			}, "POST");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/* 查询班级 */
	public List<Classinfo> QueryClassInfo(String teaNum)  {
		String urlString = null;
		try {
			urlString = HttpUtil.BASE_URL + "teacher/class?teaNum="+ URLEncoder.encode(teaNum,"utf8");
			result = HttpUtil.queryStringFormMethod(urlString, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					classInfoList = JSON.parseArray(result,Classinfo.class);
				}
				@Override
				public void onError(String e) {
				}
			}, "GET");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return classInfoList;
	}

	/* 更新班级 */
	public String UpdateClassInfo(Classinfo classInfo) {
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/class?classNum="+ URLEncoder.encode(classInfo.getClassNum(),"utf8") +
                    "&classLocation="+URLEncoder.encode(classInfo.getClassLocation(),"utf8")+
                    "&classDes="+URLEncoder.encode(classInfo.getClassDes(),"utf8")+
                    "&bornDate="+ URLEncoder.encode(classInfo.getBornDate(),"utf8");
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

	/* 删除班级 */
	public String DeleteClassInfo(String teaNum, String classNum) {
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/class?classNum="+ URLEncoder.encode(classNum,"utf8")+"&teaNum="+
                    URLEncoder.encode(teaNum,"utf8");
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

	/* 根据班级编号获取自己教授的班级对象 */
	public Classinfo GetClassInfo(String classNum, String teaNum)  {
		List<Classinfo> classInfos = QueryClassInfo(teaNum);
		for (Classinfo info : classInfos){
			if (info.getClassNum().equals(classNum)){
				return info;
			}
		}
		return null;
	}


//	学生课表
    public List<Classinfo> QueryStuClassInfo(String stuNum) {
		String urlString = null;
		try {
			urlString = HttpUtil.BASE_URL + "student/lessons?stuNum="+ URLEncoder.encode(stuNum,"utf8");
			result = HttpUtil.queryStringFormMethod(urlString, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					classInfoList = JSON.parseArray(result,Classinfo.class);
				}
				@Override
				public void onError(String e) {
				}
			}, "GET");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return classInfoList;
    }

//    学生查询签到
	public String getSign(String classNum){
		String urlString = null;
		try {
			urlString = HttpUtil.BASE_URL + "student/sign?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(urlString, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}
				@Override
				public void onError(String e) {
				}
			}, "GET");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
//	学生签到
	public String doSign(String signNum, String stuNum, String classNum){

		String urlString = null;
		try {
			urlString = HttpUtil.BASE_URL + "student/sign?classNum="+ URLEncoder.encode(classNum,"utf8")+
			"&signNum="+URLEncoder.encode(signNum,"utf8")+
					"&stuNum="+URLEncoder.encode(stuNum,"utf8");
			result = HttpUtil.queryStringFormMethod(urlString, new HttpCallBackHandler() {
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
}
