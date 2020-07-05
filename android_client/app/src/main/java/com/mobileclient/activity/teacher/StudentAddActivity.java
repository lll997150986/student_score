package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileclient.R;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;

public class StudentAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明学号输入框
	private EditText ET_studentNum;
	/*要保存的学生信息*/
	Student student = new Student();
	/*学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();

	private String classNum;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_add);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加学生");
		ImageView back = (ImageView) this.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_studentNum = (EditText) findViewById(R.id.ET_studentNum);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		classNum = getIntent().getStringExtra("classNum");
		/*单击添加学生按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取学号*/
					if(ET_studentNum.getText().toString().equals("")) {
						Toast.makeText(StudentAddActivity.this, "学号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentNum.setFocusable(true);
						ET_studentNum.requestFocus();
						return;
					}
					student.setStuNum(ET_studentNum.getText().toString());
					/*调用业务逻辑层上传学生信息*/
					StudentAddActivity.this.setTitle("正在上传学生信息，稍等...");
					String result = studentService.AddStudent(student,classNum);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}
}
