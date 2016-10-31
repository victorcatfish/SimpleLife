package com.victor.simplelife.base;

import android.view.View;

/**ViewHolder的基类
 * Created by Victor on 2016/7/5.
 */
public abstract class BaseHolder<T> {

    public View mHolderView;
    private T mData;

    public BaseHolder() {
        mHolderView = initView();
        mHolderView.setTag(this);
    }

    /**初始化HolderView视图 构造方法里面会直接调用
     * @return
     */
    protected abstract View initView();

    /**设置数据刷新界面
     * @param data
     */
    public void setDataAndRefreshView(T data) {
        this.mData = data;
        refreshView(data);
    }

    /**刷新UI (在外界设置数据的时候 setDataAndRefreshView(T data))
     * @param data 需要设置显示的数据
     */
    protected abstract void refreshView(T data);
}
