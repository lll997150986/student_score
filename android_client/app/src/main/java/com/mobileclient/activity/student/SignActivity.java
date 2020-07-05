package com.mobileclient.activity.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.activity.teacher.ExperimentListActivity;
import com.mobileclient.activity.teacher.HomeworkListActivity;
import com.mobileclient.activity.teacher.SignListActivity;
import com.mobileclient.app.Declare;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.service.ScoreService;
import com.mobileclient.util.ToastUtil;

public class SignActivity extends Activity{
	private TextView title;
	private Button sign_btn;
	private ImageView back_btn;
	private ClassInfoService classInfoService = new ClassInfoService();
	private ScoreService scoreService = new ScoreService();
	String classNum;
	String stuNum;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去除title   
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
					
		setContentView(R.layout.sign);
		classNum = (String) getIntent().getExtras().get("classNum");
//		classNum = getIntent().getExtras("");
		title = (TextView)findViewById(R.id.title);
		title.setText("课程"+classNum+"签到");
		Declare declare = (Declare) getApplicationContext();
		stuNum = declare.getUserName();
		sign_btn = findViewById(R.id.sign_btn);
		sign_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						int current = scoreService.getCurrentSignTime(classNum);
						String isValid = classInfoService.getSign(classNum);
						if (isValid.contains("false")){
							handler.sendEmptyMessage(0);
						}
						else {
							 result = classInfoService.doSign(""+current,stuNum,classNum);
							handler.sendEmptyMessage(1);
						}
					}
				}).start();
			}
		});

		back_btn = findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) { 
				SignActivity.this.finish();
			}
			
		});	
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					ToastUtil.putMessage(SignActivity.this, "尚未开始签到！");
					break;
				case 1:
					ToastUtil.putMessage(SignActivity.this,result);
					break;
				default:
					break;
			}
		}
	};
}
