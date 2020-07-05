package com.mobileclient.activity.teacher;

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
import com.mobileclient.service.ScoreService;
import com.mobileclient.util.ToastUtil;

public class ChooseActivity extends Activity{
	private TextView title;
	private Button sign_btn;
	private Button homework_btn;
	private Button exp_btn;
	private Button sum_btn;
	private Button sign_record_btn;
	private ImageView back_btn;
	private  static int  signTimes;
	private ScoreService scoreService = new ScoreService();
	String classNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去除title   
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
					
		setContentView(R.layout.choose);
		classNum = getIntent().getStringExtra("classNum");
		title = (TextView)findViewById(R.id.title);
		title.setText("课程"+classNum+"操作选择");

		sign_btn = findViewById(R.id.sign_btn);
		homework_btn = findViewById(R.id.homework_btn);
		exp_btn = findViewById(R.id.exp_btn);
		sum_btn = findViewById(R.id.sum_btn);
		sign_record_btn = findViewById(R.id.sign_record_btn);
		sign_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						signTimes = scoreService.getMaxSignTimes(classNum)+1;
						scoreService.sign(classNum,"true",""+signTimes);
						ToastUtil.putMessage(ChooseActivity.this,"第"+signTimes+
								"次签到开始！计时一分钟！");
						handler.sendEmptyMessageDelayed(1,60*1000);
					}
				}).start();
			}
		});
		sign_record_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ChooseActivity.this,SignListActivity.class);
				intent.putExtra("classNum",classNum);
				startActivity(intent);
			}
		});

//		作业记录
		homework_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ChooseActivity.this,HomeworkListActivity.class );
				intent.putExtra("classNum",classNum);
				startActivity(intent);
			}
		});
//实验记录
		exp_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ChooseActivity.this,ExperimentListActivity.class );
				intent.putExtra("classNum",classNum);
				startActivity(intent);
			}
		});

//		最终成绩计算
		sum_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String reult = scoreService.calcScore(classNum);
				ToastUtil.putMessage(ChooseActivity.this,reult);
			}
		});

		back_btn = findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) { 
				ChooseActivity.this.finish();
			}
			
		});	
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					break;
				case 1://计时结束
					scoreService.sign(classNum,"false",""+signTimes);
					ToastUtil.putMessage(ChooseActivity.this,"第"+signTimes+
							"次签到结束！");
					break;
				default:
					break;
			}
		}
	};

	public static int getSignTimes() {
		return signTimes;
	}
}
