package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.service.ClassInfoService;
import com.mobileclient.util.ActivityUtils;
import com.mobileclient.util.ClassInfoSimpleAdapter;
import com.mobileclient.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoListActivity extends Activity {
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
		ImageView search =  this.findViewById(R.id.search);
		search.setVisibility(View.VISIBLE);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ClassInfoListActivity.this, ClassInfoQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title =  this.findViewById(R.id.title);
		title.setText("课程列表");
		ImageView add_btn = this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ClassInfoListActivity.this, ClassInfoAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null){
				queryConditionClassInfo = (Classinfo)extras.getSerializable("queryConditionClassInfo");
				if (list != null && list.size() > 0){
					for (Classinfo classinfo : list){
//						开启班级班级信息查询
						if (!TextUtils.isEmpty(queryConditionClassInfo.getClassDes())){
							if (classinfo.getClassNum().contains(queryConditionClassInfo.getClassNum()) &&
									classinfo.getClassDes().contains(queryConditionClassInfo.getClassDes())){
								resultClassinfo = classinfo;
								break;
							}
						}
//						未开启班级班级信息查询
						else {
							if (classinfo.getClassNum().contains(queryConditionClassInfo.getClassNum())){
								resultClassinfo = classinfo;
								break;
							}
						}
					}
				}
			}
			setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionClassInfo = null;
        	resultClassinfo = null;
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
				if (resultClassinfo != null){
					list = new ArrayList<>();
					list.add(resultClassinfo);
				}else {
					list = getDatas();
				}

				if (list != null){
					//发送消失到handler，通知主线程下载完成
					handler.post(new Runnable() {
						@Override
						public void run() {
							dialog.cancel();
							adapter = new ClassInfoSimpleAdapter(ClassInfoListActivity.this, list);
							lv.setAdapter(adapter);
						}
					});
				}
			}
		}.start();


		// 添加长按点击
		lv.setOnCreateContextMenuListener(classInfoListItemListener);
	}

	private OnCreateContextMenuListener classInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑班级信息"); 
			menu.add(0, 1, 0, "删除班级信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑班级信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取班级编号
			classNum = list.get(position).getClassNum();
			Intent intent = new Intent();
			intent.setClass(ClassInfoListActivity.this, ClassInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("classNum", classNum);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除班级信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取班级编号
			classNum = list.get(position).getClassNum();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(ClassInfoListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = classInfoService.DeleteClassInfo(teaNum,classNum);
				ToastUtil.putMessage(getApplicationContext(),result);
				dialog.dismiss();
				setViews();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Classinfo> getDatas() {
			/* 查询班级信息 */
		return classInfoService.QueryClassInfo(teaNum);
	}

}
