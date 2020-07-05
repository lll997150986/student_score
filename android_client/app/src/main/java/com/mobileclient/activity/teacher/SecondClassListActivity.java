package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.util.ActivityUtils;
import com.mobileclient.util.ClassInfoSimpleAdapter;

import java.util.List;

public class SecondClassListActivity extends Activity {
	ClassInfoSimpleAdapter adapter;
	ListView lv;
	List<Classinfo> list;
	String classNum;
	/* 班级操作业务逻辑层对象 */
	ClassInfoService classInfoService = new ClassInfoService();
	/*保存查询参数条件的班级对象*/
	private Classinfo queryConditionClassInfo, resultClassinfo;
	private String teaNum;
	private String teaName;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.classinfo_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		teaNum = declare.getUserName();
		lv = (ListView) findViewById(R.id.h_list_view);
		//标题栏控件
		TextView title =  this.findViewById(R.id.title);
		title.setText("课程列表");
		ImageView add_btn = this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SecondClassListActivity.this, ClassInfoAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setViews();
	}

	private void setViews() {
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new ClassInfoSimpleAdapter(SecondClassListActivity.this, list);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(SecondClassListActivity.this,ChooseActivity.class);
				if (list != null){
					intent.putExtra("classNum",list.get(i).getClassNum());
				}
				startActivity(intent);
			}
		});
		// 添加长按点击
	}

	private List<Classinfo> getDatas() {
			/* 查询班级信息 */
		return classInfoService.QueryClassInfo(teaNum);
	}

}
