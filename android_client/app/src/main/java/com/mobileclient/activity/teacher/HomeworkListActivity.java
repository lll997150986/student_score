package com.mobileclient.activity.teacher;

import android.app.Activity;
import android.nfc.FormatException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobileclient.R;
import com.mobileclient.app.Declare;
import com.mobileclient.domain.Homework;
import com.mobileclient.service.ScoreService;
import com.mobileclient.util.ClassInfoSimpleAdapter;
import com.mobileclient.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeworkListActivity extends Activity {
	ClassInfoSimpleAdapter adapter;
	ListView lv;
	List<Homework> list;
	private String teaNum;
	private int homeworkNum =1;;
	private String classNum;
	private ImageView back;
	private ImageView search;
	private Spinner spinner_times;
	private Button btn_save;
	private Button btn_addTimes;
	private ScoreService scoreService = new ScoreService();
	List<String> times = new ArrayList();
	ArrayAdapter times_adapter;
	List<Homework> editList = new ArrayList<>();
	Map<Integer,String> listMap = new HashMap<>();
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
		btn_save = findViewById(R.id.btn_save);
		btn_addTimes = findViewById(R.id.btn_addTimes);
		btn_addTimes.setVisibility(View.VISIBLE);
		btn_save.setVisibility(View.VISIBLE);
		spinner_times = findViewById(R.id.spinner_times);
		classNum = getIntent().getStringExtra("classNum");

		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						double re;
						if (list != null && list.size() > 0){
							for (Integer i : listMap.keySet() ){
								try {
									 re = Double.parseDouble(listMap.get(i));
									list.get(i).setHomeworkScore(re);
								}catch (NumberFormatException e){
									list.get(i).setHomeworkDes(listMap.get(i));
								}
								if (list.get(i).getHomeworkScore()==null){
									list.get(i).setHomeworkScore(0.0);
								}
								if (list.get(i).getHomeworkDes()==null){
									list.get(i).setHomeworkDes("优");
								}

								editList.add(list.get(i));
							}
						}
						scoreService.updateHomework(editList);
						setViews();
					}
				}).start();
			}
		});


		btn_addTimes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						scoreService.addHomework(classNum);
						setViews();
					}
				}).start();
			}
		});

		int maxHomeworkTimes = scoreService.getMaxHomeworkTimes(classNum);
		for (int i = 1; i <= maxHomeworkTimes; i++){
			times.add("第"+i+"次作业");
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
				homeworkNum = arg2 + 1;
				setViews();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}

		});
		// 设置默认值
		spinner_times.setVisibility(View.VISIBLE);
		//标题栏控件
		TextView title =  this.findViewById(R.id.title);
		title.setText("作业记录");
		setViews();
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}

	private void setViews() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.show();
				final Handler handler = new Handler();
				new Thread(){
					@Override
					public void run() {
						//在子线程中进行下载数据操作
						list = getDatas();
						if (list!=null){
							//发送消失到handler，通知主线程下载完成
							handler.post(new Runnable() {
								@Override
								public void run() {
							dialog.cancel();
									lv.setAdapter(new HomeworkAdapter());
								}
							});
						}
					}
				}.start();
			}
		});


	}

	private List<Homework> getDatas() {
			/* 查询班级信息 */
		return scoreService.getHomeworks(""+homeworkNum, classNum);
	}



	private class HomeworkAdapter extends BaseAdapter {

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
		public View getView(final int i, View convertView, ViewGroup viewGroup) {
			final ViewHolder holder ;
			///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
			if (convertView == null) {
				convertView = LayoutInflater.from(HomeworkListActivity.this).inflate(R.layout.homework_list_item,null);
				holder = new ViewHolder();
				Homework homework = list.get(i);
				holder.tv_homeworkNum = (TextView)convertView.findViewById(R.id.tv_homeworkNum);
				holder.tv_stuNum = (TextView)convertView.findViewById(R.id.tv_stuNum);
				holder.et_stuScore = convertView.findViewById(R.id.et_stuScore);
				holder.et_homeworkDes = convertView.findViewById(R.id.et_homeworkDes);

				holder.tv_homeworkNum.setText("作业"+homework.getHomeworkNum());
				holder.tv_stuNum.setText("学号:"+homework.getStuNum());
				holder.et_stuScore.setText(""+homework.getHomeworkScore());
				holder.et_homeworkDes.setText(homework.getHomeworkDes());

				holder.et_stuScore.addTextChangedListener(new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
					@Override
					public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
					@Override
					public void afterTextChanged(Editable editable) {
						listMap.put(i,editable.toString());
					}
				});
				holder.et_homeworkDes.addTextChangedListener(new TextWatcher() {
					@Override
					public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
					@Override
					public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
					@Override
					public void afterTextChanged(Editable editable) {
						listMap.put(i,editable.toString());
					}
				});
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
		TextView tv_homeworkNum;
		TextView tv_stuNum;
		EditText et_stuScore;
		EditText et_homeworkDes;
	}
}


