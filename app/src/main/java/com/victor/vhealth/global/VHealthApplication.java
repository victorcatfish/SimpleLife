package com.victor.vhealth.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by Victor on 2016/7/4.
 */
public class VHealthApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext =  getApplicationContext();
        mHandler = new Handler();
        // onCreate方法运行在主线程，所以得到的id是主线程id
        mMainThreadId = Process.myTid();
    }
}
