package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileclient.R;
import com.mobileclient.activity.admin.AdminMainActivity;
import com.mobileclient.activity.custom.HttpCallBackHandler;
import com.mobileclient.activity.student.StudentMainActivity;
import com.mobileclient.app.Declare;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends Activity {
	private TextView title,register;
	private Button btn_login;
	private ImageView back_btn;
	private EditText et_username;
	private EditText et_pwd;
	//用户身份选择下拉框
	private Spinner Spinner_identify;
	private ArrayAdapter<String> identify_adapter;
	private static  String[] identify_ShowText  = null;

	private ImageView search; 
	private MyProgressDialog dialog;
	private static final String TAG = "LoginActivity";
	private static String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
					
		setContentView(R.layout.user_login);
		dialog = MyProgressDialog.getInstance(this);
		title = (TextView) findViewById(R.id.title);
		et_username = (EditText)findViewById(R.id.et_username);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		title.setText("用户登录");
		search = (ImageView)findViewById(R.id.search);
		search.setVisibility(View.GONE);
		register = (TextView)findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) { 
//				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//				startActivity(intent);
			} 
		});
		back_btn =  findViewById(R.id.back);
		back_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) { 
				finish();
			} 
		});
		
		Spinner_identify =  findViewById(R.id.Spinner_identify);
		identify_ShowText = new String[] {"学生","教师","管理员"};
		// 将可选内容与ArrayAdapter连接起来
		identify_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, identify_ShowText);
		// 设置下拉列表的风格
		identify_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		Spinner_identify.setAdapter(identify_adapter);
		// 添加事件Spinner事件监听
		String spinnerChosenText ;
		Spinner_identify.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				 Declare declare = (Declare) LoginActivity.this.getApplication();
				switch(arg2) {
					case 0:
						declare.setIdentify("student");
						break;
					case 1:
						declare.setIdentify("teacher");
						break;
					case 2:
						declare.setIdentify("admin");
					default:
						declare.setIdentify("student");
						break;
				} 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
			});
			// 设置默认值
		Spinner_identify.setVisibility(View.VISIBLE);
		 
		
		btn_login =  findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if("".equals(et_username.getText().toString())){
					Toast.makeText(LoginActivity.this, "用户名必填", 0).show();
					return;
				}
				if("".equals(et_pwd.getText().toString())){
					Toast.makeText(LoginActivity.this, "密码必填", 0).show();
					return;
				}
				dialog.show();
				final Declare declare = (Declare) LoginActivity.this.getApplication();
				String url = HttpUtil.BASE_URL;
				try {
					if (declare.getIdentify().equals("admin")){
						url = url+"admin/"+"login?username="+URLEncoder.encode( et_username.getText().toString(),"utf8")+
								"&passwd="+ URLEncoder.encode( et_pwd.getText().toString(),"utf8");
					}else if(declare.getIdentify().equals("teacher")){
							url = url+"teacher/"+"login?teaNum="+URLEncoder.encode( et_username.getText().toString(),"utf8")+
									"&passwd="+ URLEncoder.encode( et_pwd.getText().toString(),"utf8");
					}else {
							url = url+"student/"+"login?stuNum="+URLEncoder.encode( et_username.getText().toString(),"utf8")+
									"&passwd="+ URLEncoder.encode( et_pwd.getText().toString(),"utf8");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// 查询返回结果
				 result = HttpUtil.queryStringFormMethod(url, new HttpCallBackHandler() {
					@Override
					public void onFinish(String back) {
						result = back;
						if(result.equalsIgnoreCase("success")){
							declare.setUserName(et_username.getText().toString());
							handler.sendEmptyMessage(1);
						}else{
							handler.sendEmptyMessage(0);
							ToastUtil.putMessage(LoginActivity.this, "登录失败，用户名密码不匹配");
						}
					}
					@Override
					public void onError(String e) {
						ToastUtil.putMessage(LoginActivity.this, e);
					}
				},"POST");
				Log.d(TAG, "run:-->login result:  "+result);
			}
		});
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				ToastUtil.putMessage(LoginActivity.this, "登陆成功");
				Intent intent = new Intent();
				Declare declare = (Declare) LoginActivity.this.getApplication();
				if(declare.getIdentify().equals("admin")){
					intent.setClass(LoginActivity.this, AdminMainActivity.class);
				}
				else if (declare.getIdentify().equals("teacher")){
					intent.setClass(LoginActivity.this, TeacherMainActivity.class);
				}
				else {
					intent.setClass(LoginActivity.this, StudentMainActivity.class);
				}
				startActivity(intent);
				LoginActivity.this.finish();
				break;
			case 0:
				ToastUtil.putMessage(LoginActivity.this, "登录失败，用户名密码不匹配");
				break;

			default:
				break;
			}
			dialog.cancel();
		}
	};

}
