package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Sign;
import com.mobileclient.service.ScoreService;
import com.mobileclient.util.ClassInfoSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class SignListActivity extends Activity {
	ClassInfoSimpleAdapter adapter;
	ListView lv;
	List<Sign> list;
	private String teaNum;
	private int signNum = ChooseActivity.getSignTimes();;
	private String classNum;
	private ImageView back;
	private ImageView search;
	private Spinner spinner_times;
	private ScoreService scoreService = new ScoreService();
	List<String> times = new ArrayList();
	ArrayAdapter times_adapter;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.sign_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		teaNum = declare.getUserName();
		lv = (ListView) findViewById(R.id.h_list_view);
		back = findViewById(R.id.back);
		search = findViewById(R.id.search);
		search.setVisibility(View.GONE);
		spinner_times = findViewById(R.id.spinner_times);
		classNum = getIntent().getStringExtra("classNum");
		int maxSignTimes = scoreService.getMaxSignTimes(classNum);
		for (int i = 1; i <= maxSignTimes; i++){
			times.add("第"+i+"次");
		}
		// 将可选内容与ArrayAdapter连接起来
		times_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, times);
		// 设置下拉列表的风格
		times_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_times.setAdapter(times_adapter);
		// 添加事件Spinner事件监听
		String spinnerChosenText ;
		spinner_times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//根据下拉框选择的签到次数，显示不同的签到记录
				signNum = arg2 + 1;
				setViews();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}

		});
		// 设置默认值
		spinner_times.setVisibility(View.VISIBLE);
		//标题栏控件
		TextView title =  this.findViewById(R.id.title);
		title.setText("已签到名单");
		setViews();
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

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
	        			lv.setAdapter(new SignAdapter());
					}
				});
			}
		}.start();
	}

	private List<Sign> getDatas() {
			/* 查询班级信息 */
		return scoreService.getSigns(""+signNum, classNum);
	}



	private class SignAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int i) {
			return list.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder ;
			///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
			if (convertView == null) {
				convertView = LayoutInflater.from(SignListActivity.this).inflate(R.layout.sign_list_item,null);
				holder = new ViewHolder();
				Sign sign = list.get(i);
				holder.tv_signNum = (TextView)convertView.findViewById(R.id.tv_signNum);
				holder.tv_stuNum = (TextView)convertView.findViewById(R.id.tv_stuNum);
				holder.tv_signNum.setText("签到"+sign.getSignNum());
				holder.tv_stuNum.setText("学号："+sign.getStuNum());
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
	  /*设置各个控件的展示内容*/
	  /*返回修改好的view*/
			return convertView;
		}
	}

	static class ViewHolder{
		TextView tv_signNum;
		TextView tv_stuNum;
	}
}


