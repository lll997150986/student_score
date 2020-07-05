package com.mobileclient.activity.custom;

/**
 * Created by user on 2020/6/28.
 */

public interface HttpCallBackHandler {
    void onFinish(String result);

    void onError(String e);
}
