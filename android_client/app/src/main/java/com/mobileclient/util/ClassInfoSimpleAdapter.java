package com.mobileclient.util;

import java.util.List;

import com.mobileclient.R;
import com.mobileclient.domain.Classinfo;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassInfoSimpleAdapter extends BaseAdapter {
    /*需要绑定的数据*/
    private List<Classinfo> list;

    Context context = null;

    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;


    public ClassInfoSimpleAdapter(Context context, List<Classinfo> list) {
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
          convertView = LayoutInflater.from(context).inflate(R.layout.classinfo_list_item,null);
          holder = new ViewHolder();
          Classinfo info = list.get(position);
          holder.tv_classNo = (TextView)convertView.findViewById(R.id.tv_classNo);
          holder.tv_className = (TextView)convertView.findViewById(R.id.tv_className);
          holder.tv_bornDate = (TextView)convertView.findViewById(R.id.tv_bornDate);
          holder.tv_classLocation = convertView.findViewById(R.id.tv_classLocation);
          holder.tv_classStudents = convertView.findViewById(R.id.tv_classStudents);
          holder.tv_classNo.setText("班级编号：   " + info.getClassNum());
          holder.tv_className.setText("班级信息：   " + info.getClassDes());
          holder.tv_bornDate.setText("开办日期：   " + info.getBornDate());
          holder.tv_classLocation.setText("教室信息：   " + info.getClassLocation());
          holder.tv_classStudents.setText("学生人数：   " + info.getClassStudents());
      }
      else {
	      holder = (ViewHolder) convertView.getTag();
      }
	  /*设置各个控件的展示内容*/
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_classNo;
    	TextView tv_className;
    	TextView tv_bornDate;
    	TextView tv_classLocation;
    	TextView tv_classStudents;
    }
} 
