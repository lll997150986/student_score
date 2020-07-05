package com.mobileclient.util;

import java.util.List;

import com.mobileclient.R;
import com.mobileclient.domain.result.StuClass;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StudentSimpleAdapter extends BaseAdapter {
	/*需要绑定的数据*/
	private List<StuClass> list;

	Context context = null;

	//图片异步缓存加载类,带内存缓存和文件缓存
	private SyncImageLoader syncImageLoader;


	public StudentSimpleAdapter(Context context, List<StuClass> list) {
		this.list = list;
		this.context= context;
	}

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

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.student_list_item,null);
			holder = new ViewHolder();
			StuClass info = list.get(position);
			holder.tv_studentNum = (TextView)convertView.findViewById(R.id.tv_studentNum);
			holder.tv_classNum = (TextView)convertView.findViewById(R.id.tv_classNum);
			holder.tv_stuName = (TextView)convertView.findViewById(R.id.tv_stuName);
			holder.tv_stuClass = convertView.findViewById(R.id.tv_stuClass);
			holder.tv_stuTel = convertView.findViewById(R.id.tv_stuTel);
			holder.tv_studentNum.setText("学生学号：" + info.getStuNum());
			holder.tv_classNum.setText("班级编号：" + info.getClassNum());
			holder.tv_stuName.setText("学生姓名：" + info.getStuName());
			holder.tv_stuClass.setText("所在班级：" + info.getStuClass());
			holder.tv_stuTel.setText("联系电话：" + info.getStuTel());
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
	  /*设置各个控件的展示内容*/
	  /*返回修改好的view*/
		return convertView;
	}

	static class ViewHolder{
		TextView tv_studentNum;
		TextView tv_classNum;
		TextView tv_stuName;
		TextView tv_stuClass;
		TextView tv_stuTel;
	}
} 
