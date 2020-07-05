package com.mobileclient.activity.teacher;
 
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
import com.mobileclient.util.ActivityUtils;
import com.mobileclient.util.ToastUtil;

public class MoreActivity extends Activity {
	private TextView title;
	private ImageView search;
	private ImageView btn_edit;
	TextView tv_teaNum;
	TextView tv_teaName;
	TextView tv_teaTel;
	TextView tv_teaEmail;
	private Declare declare;
	Button btn_sendMail;
	Button btn_regularSend;
	EditText tv_recEmail;
	String teaNum;
	String emailAddress;
	Teacher teacher ;
	TeacherService teacherService = new TeacherService();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

		setContentView(R.layout.about);
		title = (TextView)findViewById(R.id.title);
		search = (ImageView)findViewById(R.id.search);
		search.setVisibility(View.GONE);
		title.setText("更多");
		tv_teaNum = findViewById(R.id.tv_teaNum);
		tv_teaName = findViewById(R.id.tv_teaName);
		tv_teaTel = findViewById(R.id.tv_teaTel);
		tv_teaEmail = findViewById(R.id.tv_teaEmail);
		declare =  (Declare) getApplicationContext();
		teaNum = declare.getUserName();


		btn_sendMail = findViewById(R.id.btn_sendMail);
		btn_regularSend = findViewById(R.id.btn_regularSend);
		tv_recEmail = findViewById(R.id.tv_recEmail);
		btn_edit = (ImageView)this.findViewById(R.id.btn_edit);
		btn_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MoreActivity.this,TeacherEditActivity.class);
				startActivityForResult(intent,ActivityUtils.EDIT_CODE);
			}
		});

		btn_sendMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (! TextUtils.isEmpty(tv_recEmail.getText())){
					emailAddress = tv_recEmail.getText().toString();
				}
				teacherService.sendMail(emailAddress,teaNum);
				ToastUtil.putMessage(MoreActivity.this,"邮件已发送");
			}
		});

		btn_regularSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (! TextUtils.isEmpty(tv_recEmail.getText())){
					emailAddress = tv_recEmail.getText().toString();
				}
				teacherService.sendRegularMail(emailAddress,teaNum);
				ToastUtil.putMessage(MoreActivity.this,"已开启定时邮件任务");
			}
		});
		setViews();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
			setViews();
		}
	}

	void setViews(){
		teacher = teacherService.findTeacher(teaNum);
		tv_teaNum.setText("工号: "+teaNum);
		tv_teaName.setText("姓名: "+teacher.getTeaName());
		tv_teaEmail.setText("邮箱: "+teacher.getTeaEmail());
		tv_teaTel.setText("联系电话: "+teacher.getTeaTel());
		tv_recEmail.setText(teacher.getTeaEmail());
		emailAddress = teacher.getTeaEmail();
	}
	 
}
