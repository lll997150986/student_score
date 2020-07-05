package com.mobileclient.activity.teacher;

import android.app.ProgressDialog;
import android.content.Context;


public class MyProgressDialog extends ProgressDialog {

	public MyProgressDialog(Context context) {
		super(context);
	}
	public static MyProgressDialog getInstance(Context context)
	{
		MyProgressDialog dialog=new MyProgressDialog(context);
			dialog.setMessage("请稍候...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
		return dialog;
	}

}
