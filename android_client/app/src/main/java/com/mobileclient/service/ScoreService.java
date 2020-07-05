package com.mobileclient.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.activity.custom.HttpCallBackHandler;
import com.mobileclient.domain.Experiment;
import com.mobileclient.domain.Homework;
import com.mobileclient.domain.Sign;
import com.mobileclient.util.HttpUtil;

/*成绩管理业务逻辑层*/
public class ScoreService {
	String result;
	List<Sign> signs;
	List<Homework> homeworks;
	List<Experiment> experiments;
	int signTimes;

//开启、关闭签到
	public String sign(String classNum, String isValid,String signTimes){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/sign?classNum="+ URLEncoder.encode(classNum,"utf8")+
					"&isValid="+ URLEncoder.encode(isValid,"utf8")+
					"&signTime="+URLEncoder.encode(signTimes,"utf8");

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


	public int getMaxSignTimes(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/sign_times?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					signTimes = Integer.parseInt(result);
				}

				@Override
				public void onError(String e) {

				}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signTimes;
	}

//查询签到名单
	public List<Sign> getSigns(String signNum, String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/sign?classNum="+ URLEncoder.encode(classNum,"utf8")+
					"&signNum="+ URLEncoder.encode(signNum,"utf8");

			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					signs = com.alibaba.fastjson.JSONObject.parseArray(result,Sign.class);
				}
				@Override
				public void onError(String e) {}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signs;
	}
//查询某次作业名单
	public List<Homework> getHomeworks(String homeworkNum, String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/homework?classNum="+ URLEncoder.encode(classNum,"utf8")+
					"&homeworkNum="+ URLEncoder.encode(homeworkNum,"utf8");

			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					homeworks = com.alibaba.fastjson.JSONObject.parseArray(result,Homework.class);
				}
				@Override
				public void onError(String e) {}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homeworks;
	}



	public int getMaxHomeworkTimes(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/homework_times?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					signTimes = Integer.parseInt(result);
				}

				@Override
				public void onError(String e) {

				}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signTimes;
	}

	public String getHasNewHomework(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/new_homework?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}
				@Override
				public void onError(String e) {}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String addHomework(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/new_homework?classNum="+ URLEncoder.encode(classNum,"utf8");
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


//查询某次实验名单
	public List<Experiment> getExps(String expNum, String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/experiment?classNum="+ URLEncoder.encode(classNum,"utf8")+
					"&expNum="+ URLEncoder.encode(expNum,"utf8");

			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					experiments = com.alibaba.fastjson.JSONObject.parseArray(result,Experiment.class);
				}
				@Override
				public void onError(String e) {}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return experiments;
	}


	public int getMaxExpTimes(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/experiment_times?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					signTimes = Integer.parseInt(result);
				}

				@Override
				public void onError(String e) {

				}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signTimes;
	}

	public String getHasNewExp(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/new_exp?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
				}

				@Override
				public void onError(String e) {

				}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String addExp(String classNum){
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/new_exp?classNum="+ URLEncoder.encode(classNum,"utf8");
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


//	作业信息更改
	public String updateHomework(List<Homework> list){
		for (Homework homework : list){
			String url = null;
			try {
				url = HttpUtil.BASE_URL + "teacher/homework?homeworkNum="+ URLEncoder.encode(homework.getHomeworkNum(),"utf8")+
						"&classNum="+ URLEncoder.encode(homework.getClassNum(),"utf8") +
						"&stuNum="+ URLEncoder.encode(homework.getStuNum(),"utf8") +
						"&homeworkScore="+ URLEncoder.encode(""+homework.getHomeworkScore(),"utf8") +
						"&homeworkDes="+ URLEncoder.encode(homework.getHomeworkDes(),"utf8");
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
		}
		return result;
	}


	//	实验信息更改
	public String updateExp(List<Experiment> list){
		for (Experiment experiment : list){
			String url = null;
			try {
				url = HttpUtil.BASE_URL + "teacher/experiment?expNum="+ URLEncoder.encode(experiment.getExpNum(),"utf8")+
						"&classNum="+ URLEncoder.encode(experiment.getClassNum(),"utf8") +
						"&stuNum="+ URLEncoder.encode(experiment.getStuNum(),"utf8") +
						"&expScore="+ URLEncoder.encode(""+experiment.getExpScore(),"utf8") +
						"&expDes="+ URLEncoder.encode(experiment.getExpDes(),"utf8");
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
		}
		return result;
	}


//	计算总分
	public String calcScore(String classNum) {
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "teacher/score?classNum="+ URLEncoder.encode(classNum,"utf8");
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


	public int getCurrentSignTime(String classNum) {
		String url = null;
		try {
			url = HttpUtil.BASE_URL + "student/sign_time?classNum="+ URLEncoder.encode(classNum,"utf8");
			result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
				@Override
				public void onFinish(String result2) {
					result = result2;
					signTimes = Integer.parseInt(result);
				}
				@Override
				public void onError(String e) {
				}
			},"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signTimes;
	}
}
