package com.victor.vhealth.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.ui.fragment.ClassifyFragment;
import com.victor.vhealth.ui.fragment.LeftMenuFragment;
import com.victor.vhealth.ui.fragment.medicine.MedicineIndexFragment;
import com.victor.vhealth.ui.fragment.pic.PicFragment;
import com.victor.vhealth.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    public static final String LEFT_MENU = "left_menu";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;

    @ViewInject(R.id.dl_drawer_layout)
    private DrawerLayout mDrawerLayout;

    @ViewInject(R.id.fth_health_bottom)
    private FragmentTabHost mHealthTabHost;
    @ViewInject(R.id.fth_life_bottom)
    private FragmentTabHost mLifeTabHost;
    @ViewInject(R.id.fl_content_main)
    private FrameLayout mflContentMain;
    private ActionBarDrawerToggle mDrawerToggle;

    private int[] mHealthTabIconIds = new int[]{R.drawable.selector_bottom_tab_news, R.drawable.selector_bottom_tab_knowledge,
            R.drawable.selector_bottom_tab_question, R.drawable.selector_bottom_tab_book};

    private int[] mLifeTabIconIds = new int[]{R.drawable.selector_bottom_tab_hot, R.drawable.selector_bottom_tab_food,
            R.drawable.selector_bottom_tab_cookbook};

    private String[] mHealthUrlKey = new String[]{Constant.URL.HEALTH_NEWS, Constant.URL.HEALTH_KNOWLEDGE,
            Constant.URL.HEALTH_ASK, Constant.URL.HEALTH_BOOK};

    private String[] mLifeUrlKey = new String[]{Constant.URL.LIFE_TOP, Constant.URL.LIFE_FOOD,
            Constant.URL.LIFE_COOKBOOK};
    private View mLifeView;
    private View mHealthView;
    private String[] mHealthTabTitles;
    private String[] mLifeTabTitles;
    private FragmentManager mFm;
    private boolean isLifeHostInited = false;
    private long mPreTime;
    private ChanelType mCurChanelType = ChanelType.HEALTH_CHANEL;
    public enum ChanelType {
        HEALTH_CHANEL,
        MEDICINE_CHANEL,
        LIFE_CHANEL,
        PIC_CHANLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initView();
    }

    /**初始化布局*/
    private void initView() {
        initToolBar();
        initLeftMenuFragment();
        // 初始化Tab(默认初始化健康页面)
        initMainContent(ChanelType.HEALTH_CHANEL);
    }

    /**初始化侧边栏*/
    private void initLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_left_menu_root, new LeftMenuFragment(), LEFT_MENU);
        transaction.commit();
    }

    /**初始化ToolBar*/
    private void initToolBar() {
        // 设置标题
        mToolbar.setTitle(R.string.app_name);
        // 添加ActionBar支持
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
    }

    /**关闭抽屉*/
    public void closedDrawer() {
        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawers();
            }
        }, 200);
    }

    /** 初始化内容(Fragment和Tab)
     * @param type 需要进行初始化内容对应频道的类型
     */
    public void initMainContent(ChanelType type) {
        if (mflContentMain.getChildCount() > 0) {
            mflContentMain.removeAllViews();
        }
        mFm = getSupportFragmentManager();
        switch (type) {
            case HEALTH_CHANEL:
                // 初始化健康频道的TabHost
                mHealthView = View.inflate(this, R.layout.layout_health_pager, null);
                ViewUtils.inject(this, mHealthView);
                mHealthTabTitles = UIUtils.getStringArray(R.array.bottom_tab_health);
                mHealthTabHost.setup(this, mFm, R.id.fl_health_content);
                for (int i = 0; i < mHealthTabTitles.length; i++) {
                    TabHost.TabSpec tab = mHealthTabHost.newTabSpec(mHealthTabTitles[i]);
                    // 初始化tab的标签视图
                    View indicator = View.inflate(this, R.layout.layout_bottom_tab, null);
                    TextView textView = (TextView) indicator.findViewById(R.id.tv_tab_title);
                    ImageView tabIcon = (ImageView) indicator.findViewById(R.id.iv_tab_icon);
                    tabIcon.setImageDrawable(UIUtils.getDrawable(mHealthTabIconIds[i]));
                    textView.setText(mHealthTabTitles[i]);
                    // 设置tab标签视图
                    tab.setIndicator(indicator);
                    // 给fragment传入分类URL的关键字
                    Bundle bundle = new Bundle();
                    bundle.putString(ClassifyFragment.URL_KEY, mHealthUrlKey[i]);
                    bundle.putString(ClassifyFragment.CHANNEL_TAG, Constant.HEALTH_CHANNEL);
                    mHealthTabHost.addTab(tab, ClassifyFragment.class, bundle);
                }
                mflContentMain.addView(mHealthView);
                break;
            case MEDICINE_CHANEL:
                break;
            case LIFE_CHANEL:
                // 初始化生活频道的TabHost
                mLifeView = View.inflate(this, R.layout.layout_life_pager, null);
                ViewUtils.inject(this, mLifeView);
                mLifeTabTitles = UIUtils.getStringArray(R.array.bottom_tab_life);
                mLifeTabHost.setup(this, mFm, R.id.fl_life_content);
                for (int i = 0; i < mLifeTabTitles.length; i++) {
                    TabHost.TabSpec tab = mLifeTabHost.newTabSpec(mLifeTabTitles[i]);
                    // 初始化tab的标签视图
                    View indicator = View.inflate(this, R.layout.layout_bottom_tab, null);
                    TextView textView = (TextView) indicator.findViewById(R.id.tv_tab_title);
                    ImageView tabIcon = (ImageView) indicator.findViewById(R.id.iv_tab_icon);
                    tabIcon.setImageDrawable(UIUtils.getDrawable(mLifeTabIconIds[i]));
                    textView.setText(mLifeTabTitles[i]);
                    // 设置tab标签视图
                    tab.setIndicator(indicator);
                    // 给fragment传入分类URL的关键字
                    Bundle bundle = new Bundle();
                    bundle.putString(ClassifyFragment.URL_KEY, mLifeUrlKey[i]);
                    bundle.putString(ClassifyFragment.CHANNEL_TAG, Constant.LIFE_CHANNEL);
                    mLifeTabHost.addTab(tab, ClassifyFragment.class, bundle);
                    isLifeHostInited = true;
                }
                mflContentMain.addView(mLifeView);
                break;
            case PIC_CHANLE:
                break;
        }
    }

    /** 切换显示频道
     * @param type 需要显示频道的类型
     */
    public void switchChanel(ChanelType type) {
        if (mflContentMain.getChildCount() > 0) {
            mflContentMain.removeAllViews();
        }
        String tag = null;
        FragmentTransaction ft = mFm.beginTransaction();
        switch (type) {
            case HEALTH_CHANEL:
                tag = mHealthTabHost.getCurrentTabTag();
                if (tag.equals(mHealthTabTitles[0])) {
                    Fragment fragment = mFm.findFragmentByTag(tag);
                    if (fragment != null) {
                        ft.attach(fragment);
                        ft.commit();
                        mFm.executePendingTransactions();
                    }
                }
                mflContentMain.addView(mHealthView);
                mCurChanelType = ChanelType.HEALTH_CHANEL;
                break;
            case MEDICINE_CHANEL:
                ft.replace(R.id.fl_content_main, new MedicineIndexFragment());
                ft.commit();
                mCurChanelType = ChanelType.MEDICINE_CHANEL;
                break;
            case LIFE_CHANEL:
                // 判断生活频道是否初始化了，第一次切换到生活频道的时候先进行初始化
                if (isLifeHostInited) {
                    // 如果之前是在该频道首个page离开页面，切换回来，系统不会主动加载fragment页面，
                    // 让其手动加载一次页面，否则否则无法显示fragment的界面
                    tag = mLifeTabHost.getCurrentTabTag();
                    if (tag.equals(mLifeTabTitles[0])) {
                        Fragment fragment = mFm.findFragmentByTag(tag);
                        if (fragment != null) {
                            ft.attach(fragment);
                            ft.commit();
                            mFm.executePendingTransactions();
                        }
                    }
                } else {
                    initMainContent(ChanelType.LIFE_CHANEL);
                    return;
                }
                mflContentMain.addView(mLifeView);
                mCurChanelType = ChanelType.LIFE_CHANEL;
                break;
            case PIC_CHANLE:
                ft.replace(R.id.fl_content_main, new PicFragment());
                ft.commit();
                mCurChanelType = ChanelType.PIC_CHANLE;
                break;
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (mCurChanelType != ChanelType.HEALTH_CHANEL && mCurChanelType != ChanelType.LIFE_CHANEL) {
            menu.findItem(R.id.ab_search).setVisible(false);
        } else
        {
            menu.findItem(R.id.ab_search).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_search:
                Toast.makeText(this, "搜素", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**通过标签获得依附在Activity上面的Fragment
     * @param tag Fragment的标签
     * @return Fragment
     */
    public Fragment getFragmentByTag(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(tag);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mPreTime > 1500) {
            UIUtils.showShortToast(UIUtils.getContext(), "再按一次，退出应用");
            mPreTime = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}
