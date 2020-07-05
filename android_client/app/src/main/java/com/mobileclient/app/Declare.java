package com.mobileclient.app;
 
import java.io.File;

import android.app.Application;
import android.content.Context;
/**
 * 启动Application时，系统会创建一个PID，即进程ID，所有的Activity都会在此进程上运行。那么我们在Application创建的时候初始化全局变量，
 * 同一个应用的所有Activity都可以取到这些全局变量的值，换句话说，我们在某一个Activity中改变了这些全局变量的值，
 * 那么在同一个应用的其他Activity中值就会改变。
 * 在Android中，可以通过继承Application类来实现应用程序级的全局变量，这种全局变量方法相对静态类更有保障，直到应用的所有Activity全部被destory掉之后才会被释放掉。
 */
import com.mobileclient.util.HttpUtil;

public class Declare extends Application {

	
	@Override
	public void onCreate() {
		super.onCreate(); 
//		CrashHandler crashHandler = CrashHandler.getInstance();
//	    crashHandler.init(getApplicationContext());
	    context = this.getApplicationContext(); 
	    File path = new File(HttpUtil.FILE_PATH);
	    if(!path.exists()) path.mkdirs();
	}
	 
	public static Context context;
	 
	
	private int id;
    private String userName;
    private String identify; //用户身份
    
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	} 
	
	
}
