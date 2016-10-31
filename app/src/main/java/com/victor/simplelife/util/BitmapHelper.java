package com.victor.simplelife.util;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/** BitmapUtils的工厂类
 * Created by Victor on 2016/6/22.
 */
public class BitmapHelper {

    public static BitmapUtils sBitmapUtils;

    static {
        sBitmapUtils = new BitmapUtils(UIUtils.getContext());
    }

    public static <T extends View> void display(T container, String uri) {
        sBitmapUtils.display(container, uri);
    }
}
