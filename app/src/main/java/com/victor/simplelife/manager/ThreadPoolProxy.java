package com.victor.simplelife.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 2016/7/5.
 */
public class ThreadPoolProxy {

    ThreadPoolExecutor mExecutor;
    int mCorePoolSize;
    int mMaxPoolSize;
    long mKeepAliveTime;

    public ThreadPoolProxy(int corePoolSize, int maxPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaxPoolSize = maxPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    private ThreadPoolExecutor getThreadPoolExecutor() {
        if (mExecutor == null) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null) {
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaxPoolSize, mKeepAliveTime,
                            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return mExecutor;
    }

    /**执行任务*/
    public void execute(Runnable task) {
        getThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**移除任务*/
    public void removeTask(Runnable task) {
        getThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
