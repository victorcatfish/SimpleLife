package com.victor.simplelife.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.victor.simplelife.R;

/**
 * Created by Victor on 2016/7/10.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        initView();
        if (hasToolBar()) {
            initToolBar();
        }
        initData();
    }

    /**判断是否需要ToolBar 默认返回true*/
    private boolean hasToolBar() {
        return true;
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected abstract void initView();

    protected void initToolBar(){
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            int titleRes = getToolbarTitle();
            if (titleRes != -1) {
                mToolbar.setTitle(titleRes);
            }
            if (showBackButton()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                if (hasSlidingMenu()) {
                    // 初始化侧边栏的开关
                    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
                    // 设置开关监听
                    mDrawerLayout.addDrawerListener(mDrawerToggle);
                    // 将侧边栏和开关关联起来
                    mDrawerLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mDrawerToggle.syncState();
                        }
                    });
                    initSlidingMenu();
                }
            } else {
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                getSupportActionBar().setDisplayUseLogoEnabled(false);
            }
        }
    }

    protected int getToolbarTitle() {
        return -1;
    }

    protected void initSlidingMenu() {

    }

    /**关闭抽屉*/
    public void closedDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDrawerLayout.closeDrawers();
                }
            }, 200);
        }
    }

    protected boolean hasSlidingMenu() {
        return false;
    }

    protected boolean showBackButton() {
        return false;
    }

    protected void initData(){}
}
