package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Student;
import com.mobileclient.domain.result.StuClass;
import com.mobileclient.service.StudentService;
import com.mobileclient.util.ActivityUtils;
import com.mobileclient.util.StudentSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends Activity {
	StudentSimpleAdapter adapter;
	ListView lv; 
	List<StuClass> list = new ArrayList<>();
	String studentNum;
	/* 学生操作业务逻辑层对象 */
	StudentService studentService = new StudentService();
	/*保存查询参数条件的学生对象*/
	private Student queryConditionStudent;
	private String classNum;
	private Student student;
	private ImageView search;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.student_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		classNum = getIntent().getStringExtra("classNum");
		lv =  findViewById(R.id.h_list_view);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("班级学生列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		search = findViewById(R.id.search);
		search.setImageResource(R.drawable.back);
		search.setVisibility(View.VISIBLE);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		add_btn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("classNum",classNum);
				intent.setClass(StudentListActivity.this, StudentAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionStudent = null;
        	setViews();
        }
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
						adapter = new StudentSimpleAdapter(StudentListActivity.this, list);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(studentListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

            	Intent intent = new Intent();
            	intent.setClass(StudentListActivity.this, StudentDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putSerializable("stuClass",list.get(arg2));
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener studentListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "删除学生信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		 if (item.getItemId() == 0) {// 删除学生信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取学号
			student = new Student();
			student.setStuNum(list.get(position).getStuNum());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(StudentListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = studentService.DeleteStudent(student,classNum);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<StuClass> getDatas() {
		return studentService.QueryStudent(classNum);
	}

}
