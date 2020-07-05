package com.mobileclient.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by user on 2020/3/11.
 */

public class ToastUtil {
    private static Toast toast;
    private static Toast itoast;
    private  static View toastview;
    public static  void  putMessage(Context context, String message){
        if (toast==null){
            toast = Toast.makeText(context,message, Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(message);
        }
        toast.show();
    }
}
