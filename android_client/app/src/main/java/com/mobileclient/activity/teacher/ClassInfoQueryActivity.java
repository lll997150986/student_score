package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.service.ClassInfoService;

public class ClassInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明班级编号输入框
	private EditText ET_classNo;
	// 声明班级名称输入框
	private EditText ET_className;
	private CheckBox cb_classDes;
	/*查询过滤条件保存到这个对象中*/
	private Classinfo queryConditionClassInfo = new Classinfo();
	private ClassInfoService service = new ClassInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.classinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置班级查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back);
		back_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_classNo = (EditText) findViewById(R.id.ET_classNo);
		ET_className = (EditText) findViewById(R.id.ET_className);
		cb_classDes =  findViewById(R.id.cb_classDes);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/

					queryConditionClassInfo.setClassNum(ET_classNo.getText().toString());
					if(cb_classDes.isChecked()) {
						queryConditionClassInfo.setClassDes(ET_className.getText().toString());
					}
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionClassInfo", queryConditionClassInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
