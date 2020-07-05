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
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;

public class TeacherEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明教师编号TextView
	private TextView TV_teacherNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮件输入框
	private EditText ET_email;
	// 声明附加信息输入框
	private EditText ET_teaDes;
	protected String carmera_path;
	/*要保存的老师信息*/
	Teacher teacher = new Teacher();
	/*老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();

	private Declare declare;
	private String teacherNum;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_edit);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑老师信息");
		ImageView back = (ImageView) this.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_teacherNo = (TextView) findViewById(R.id.TV_teacherNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_teaDes = (EditText) findViewById(R.id.ET_memo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		declare = (Declare) getApplicationContext();
		teacherNum = declare.getUserName();
		/*单击修改老师按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;
					}
					teacher.setTeaPasswd(ET_password.getText().toString());
					/*验证获取姓名*/
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;
					}
					teacher.setTeaName(ET_name.getText().toString());
					/*验证获取性别*/
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;
					}
					teacher.setTeaSex(ET_sex.getText().toString());
					/*验证获取联系电话*/
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;
					}
					teacher.setTeaTel(ET_telephone.getText().toString());
					/*验证获取邮件*/
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "邮件输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;
					}
					teacher.setTeaEmail(ET_email.getText().toString());
					/*验证获取附加信息*/
					if(ET_teaDes.getText().toString().equals("")) {
						Toast.makeText(TeacherEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teaDes.setFocusable(true);
						ET_teaDes.requestFocus();
						return;
					}
					teacher.setTeaDes(ET_teaDes.getText().toString());
					/*调用业务逻辑层上传老师信息*/
					TeacherEditActivity.this.setTitle("正在更新老师信息，稍等...");
					teacher.setTeaNum(teacherNum);
					String result = teacherService.UpdateTeacher(teacher);
					Toast.makeText(getApplicationContext(), result, 1).show();
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
		teacher = teacherService.findTeacher(teacherNum);
		this.TV_teacherNo.setText(teacherNum);
		this.ET_password.setText(teacher.getTeaPasswd());
		this.ET_name.setText(teacher.getTeaName());
		this.ET_sex.setText(teacher.getTeaSex());
		this.ET_telephone.setText(teacher.getTeaTel());
		this.ET_email.setText(teacher.getTeaEmail());
		this.ET_teaDes.setText(teacher.getTeaDes());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}