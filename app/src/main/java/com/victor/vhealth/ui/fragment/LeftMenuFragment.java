package com.victor.vhealth.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.ui.activity.MainActivity;
import com.victor.vhealth.util.UIUtils;

/**左侧滑菜单fragment
 * Created by Victor on 2016/7/1.
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.ll_menu_item_health)
    private LinearLayout mllHealth;
    @ViewInject(R.id.ll_menu_item_life)
    private LinearLayout mllLife;
    @ViewInject(R.id.ll_menu_item_setting)
    private LinearLayout mllSetting;
    @ViewInject(R.id.ll_menu_item_exit)
    private LinearLayout mLLExit;
    @ViewInject(R.id.ll_menu_item_medicine)
    private LinearLayout mLlMedicine;
    @ViewInject(R.id.ll_menu_item_pic)
    private LinearLayout mLlPic;

    private MainActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_left_menu, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    protected void initListener() {
        mllHealth.setOnClickListener(this);
        mllLife.setOnClickListener(this);
        mLlMedicine.setOnClickListener(this);
        mLlPic.setOnClickListener(this);
        mllSetting.setOnClickListener(this);
        mLLExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) mActivity;
        switch (v.getId()) {
            case R.id.ll_menu_item_health:
                // 切换到健康频道视图
                mainActivity.switchChanel(MainActivity.ChanelType.HEALTH_CHANEL);
//                mainActivity.initTab(false);
                break;
            case R.id.ll_menu_item_life:
                // 切换到生活频道视图
                mainActivity.switchChanel(MainActivity.ChanelType.LIFE_CHANEL);
//                mainActivity.initTab(true);
                break;
            case R.id.ll_menu_item_pic:
                mainActivity.switchChanel(MainActivity.ChanelType.PIC_CHANLE);
                break;
            case R.id.ll_menu_item_setting:
            UIUtils.showShortToast(getContext(), "设置");
            break;
            case R.id.ll_menu_item_medicine:
            mainActivity.switchChanel(MainActivity.ChanelType.MEDICINE_CHANEL);
            break;
            case R.id.ll_menu_item_exit:
                quit();
                break;
        }
        mainActivity.closedDrawer();
    }

    /**退出应用程式*/
    private void quit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
