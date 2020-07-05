package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.service.ClassInfoService;

import java.util.Calendar;

public class ClassInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;

	// 声明班级编号TextView
	private EditText ET_classNum;
	// 声明班级名称输入框
	private EditText ET_className;
	//	教室信息
	private EditText ET_classLocation;
	// 开办日期
	private EditText ET_bornDate;
	/*要保存的班级信息*/
	Classinfo classInfo = new Classinfo();
	/*班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	Declare declare;

	private String birth="2020/6/29";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_add);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加班级");
		declare =  (Declare) getApplicationContext();
		ImageView back = (ImageView) this.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_classNum = (EditText) findViewById(R.id.ET_classNo);
		ET_className = (EditText) findViewById(R.id.ET_className);
		ET_classLocation = findViewById(R.id.ET_classLocation);
		ET_bornDate = findViewById(R.id.ET_bornDate);
		final Calendar calendar = Calendar.getInstance();
		ET_bornDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				new DatePickerDialog(ClassInfoAddActivity.this, 2, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
						birth=i+"/"+(i1+1)+"/"+i2;
						ET_bornDate.setText(birth);
					}
				},calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)).show();
			}
		});
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加班级按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取班级编号*/
					if(ET_classNum.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classNum.setFocusable(true);
						ET_classNum.requestFocus();
						return;
					}
					classInfo.setClassNum(ET_classNum.getText().toString());
					/*验证获取班级名称*/ 
					if(ET_className.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "班级信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_className.setFocusable(true);
						ET_className.requestFocus();
						return;	
					}
					classInfo.setClassDes(ET_className.getText().toString());
					if (birth.equals("")){
						Toast.makeText(ClassInfoAddActivity.this, "开办日期不能为空!", Toast.LENGTH_LONG).show();
						return;
					}
					classInfo.setBornDate(birth);
					/*验证获取教室信息*/
					if(ET_classLocation.getText().toString().equals("")) {
						Toast.makeText(ClassInfoAddActivity.this, "教室信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_classLocation.setFocusable(true);
						ET_classLocation.requestFocus();
						return;
					}
					classInfo.setClassLocation(ET_classLocation.getText().toString());


					/*调用业务逻辑层上传班级信息*/
					ClassInfoAddActivity.this.setTitle("正在上传班级信息，稍等...");
					String result = classInfoService.AddClassInfo(classInfo,declare.getUserName());
					Toast.makeText(getApplicationContext(), result, 1).show();
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
