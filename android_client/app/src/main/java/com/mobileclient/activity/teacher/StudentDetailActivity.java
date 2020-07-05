package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.domain.result.StuClass;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.service.StudentService;

public class StudentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学号控件
	private TextView TV_studentNum;
	// 声明所在班号
	private TextView TV_classNum;
	//	所处班级
	private TextView TV_stuClass;
	// 声明姓名控件
	private TextView TV_stuName;
	// 声明班级排名
	private TextView TV_stuRank;
	// 声明期末总分
	private TextView TV_stuScore;
	// 声明评奖评优情况
	private TextView TV_stuClassDes;
	// 声明联系电话
	private TextView TV_stuTel;
	/* 要保存的学生信息 */
	StuClass stuClass ;
	/* 学生管理业务逻辑层 */
	private StudentService studentService = new StudentService();
	private ClassInfoService classInfoService = new ClassInfoService();
	private String studentNum;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生详情");
		ImageView back = (ImageView) this.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_studentNum = (TextView) findViewById(R.id.TV_studentNo);
		TV_classNum = (TextView) findViewById(R.id.TV_classNum);
		TV_stuClass = (TextView) findViewById(R.id.TV_stuClass);
		TV_stuName = (TextView) findViewById(R.id.TV_stuName);
		TV_stuRank = (TextView) findViewById(R.id.TV_stuRank);
		TV_stuScore = (TextView) findViewById(R.id.TV_stuScore);
		TV_stuClassDes = (TextView) findViewById(R.id.TV_stuClassDes);
		TV_stuTel = (TextView) findViewById(R.id.TV_stuTel);
		Bundle extras = this.getIntent().getExtras();
		stuClass = (StuClass) extras.getSerializable("stuClass");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
		if (stuClass != null){
			this.TV_studentNum.setText(stuClass.getStuNum());
			this.TV_classNum.setText(stuClass.getClassNum());
			this.TV_stuClass.setText(stuClass.getStuClass());
			this.TV_stuName.setText(stuClass.getStuName());
			this.TV_stuRank.setText(stuClass.getStuRank());
			this.TV_stuScore.setText(""+stuClass.getStuScore());
			this.TV_stuClassDes.setText(stuClass.getStuClassDes());
			this.TV_stuTel.setText(stuClass.getStuTel());
		}
	}
}
