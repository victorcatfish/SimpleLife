package com.victor.vhealth.factory;

import com.victor.vhealth.manager.ThreadPoolProxy;

/**
 * Created by Victor on 2016/7/5.
 */
public class ThreadPoolFactory {

    static ThreadPoolProxy sThreadPool;

    public static ThreadPoolProxy getThreadPool() {
        if (sThreadPool == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sThreadPool == null) {
                    sThreadPool = new ThreadPoolProxy(5, 5, 3000);
                }
            }
        }
        return sThreadPool;
    }
}
