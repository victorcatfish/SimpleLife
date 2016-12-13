package com.victor.vhealth.ui.fragment.medicine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.BodyInfo;
import com.victor.vhealth.domain.BodyInfoList;
import com.victor.vhealth.domain.DepartmentInfo;
import com.victor.vhealth.domain.DepartmentList;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.BodyProtocol;
import com.victor.vhealth.protocol.DepartmentPrototol;
import com.victor.vhealth.ui.activity.ClassifySearchActivity;
import com.victor.vhealth.base.ClassifySearchBaseFragment;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 疾病信息主页的菜单(根据身体部位和科室搜索疾病)fragment
 * Created by Victor on 2016/11/24.
 */
public class DiseaseMenuFragment extends ContentBaseFragment implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg_disease_menu_second)
    private RadioGroup mRgMenuSecond;
    @ViewInject(R.id.gv_disease_menu_third)
    private GridView mGvMenuThird;
    @ViewInject(R.id.rg_disease_menu_first)
    private RadioGroup mRgMenuFirst;
    @ViewInject(R.id.rb_disease_body)
    private RadioButton mRbBodyMenu;
    @ViewInject(R.id.rb_disease_department)
    private RadioButton mRbDepartmentMenu;

    private List<BodyInfoList.BodyClassify> mBodyClassifyList;
    private View mView;
    private List<DepartmentList.DepartmentClassify> mDepartmentClassifies;
    private BodyAdapter mBodyAdapter;
    private DepartMentAdapter mDepartmentAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        BodyProtocol bodyProtocol = new BodyProtocol(Constant.URL.MEDICINE_BODY);
        DepartmentPrototol departmentPrototol = new DepartmentPrototol(Constant.URL.MEDICINE_DEPARTMENT);
        try {
            mDepartmentClassifies = departmentPrototol.loadData(1);
            mBodyClassifyList = bodyProtocol.loadData(1);
            return checkState(mBodyClassifyList);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    @Override
    protected View initSuccessView() {

        mView = View.inflate(UIUtils.getContext(), R.layout.layout_disease_menu, null);
        ViewUtils.inject(this, mView);

        mGvMenuThird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mRgMenuFirst.getCheckedRadioButtonId() == R.id.rb_disease_body) {
                    // 根据body来搜索疾病信息
                    Intent intent = new Intent(UIUtils.getContext(), ClassifySearchActivity.class);
                    intent.putExtra(ContentBaseFragment.DATA_ID, mBodyAdapter.mDatas.get(position).id);
                    intent.putExtra(ClassifySearchActivity.SEARCH_CLASSIFY_NAME, mBodyAdapter.mDatas.get(position).name);
                    intent.putExtra(ClassifySearchActivity.SEARCH_CLASSIFY_TYPE, Constant.URL.MEDICINE_DISEASE);
                    intent.putExtra(ClassifySearchBaseFragment.SEARCH_CLASSIFY_KEY, Constant.URL.MEDICINE_BODY);
                    startActivity(intent);
                } else {
                    // 根据科室来搜索疾病信息
                    Intent intent = new Intent(UIUtils.getContext(), ClassifySearchActivity.class);
                    intent.putExtra(ContentBaseFragment.DATA_ID, mDepartmentAdapter.mDatas.get(position).id);
                    intent.putExtra(ClassifySearchActivity.SEARCH_CLASSIFY_NAME,
                            mDepartmentAdapter.mDatas.get(position).name);
                    intent.putExtra(ClassifySearchActivity.SEARCH_CLASSIFY_TYPE, Constant.URL.MEDICINE_DISEASE);
                    intent.putExtra(ClassifySearchBaseFragment.SEARCH_CLASSIFY_KEY, Constant.URL.MEDICINE_DEPARTMENT);
                    startActivity(intent);
                }
                ((DiseaseFragment)getParentFragment()).hideMenu();
            }
        });

        switchBodyMenu();

        initListener();

        return mView;
    }

    private void switchBodyMenu() {
        mRgMenuSecond.removeAllViews();
        for (int i = 0; i < mBodyClassifyList.size(); i++) {
            String name = mBodyClassifyList.get(i).name;

            RadioButton radioButton = getRadioButton(name);
            radioButton.setTag(i);
            if (i == 0) {
                radioButton.setBackgroundResource(R.drawable.disease_menu_selected);
                addThirdBodyMenu(0);
            }
            mRgMenuSecond.addView(radioButton);
        }

    }

    private void switchDepartmentMenu() {
        mRgMenuSecond.removeAllViews();
        for (int i = 0; i < mDepartmentClassifies.size(); i++) {

            String name = mDepartmentClassifies.get(i).name;

            if (mDepartmentClassifies.get(i).id == 23) {
                continue;
            }

            RadioButton radioButton = getRadioButton(name);
            radioButton.setTag(i);
            if (i == 0) {
                radioButton.setBackgroundResource(R.drawable.disease_menu_selected);
                addThirdDepartmentMemu(0);
            }
            mRgMenuSecond.addView(radioButton);
        }

    }

    @NonNull
    private RadioButton getRadioButton(String name) {
        RadioButton radioButton = new RadioButton(UIUtils.getContext());
        Bitmap a = null;
        radioButton.setButtonDrawable(new BitmapDrawable(a));
        radioButton.setBackgroundColor(Color.WHITE);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setText(name);
        radioButton.setTextColor(Color.rgb(10, 10, 10));
        radioButton.setPadding(0, UIUtils.dip2px(5), 0, UIUtils.dip2px(5));
        radioButton.setTextSize(14);

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, UIUtils.dip2px(1), 0, 0);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    /**
     * 显示身体部位的三级菜单
     */
    private void addThirdBodyMenu(int position) {
        List<BodyInfo> bodyInfos = mBodyClassifyList.get(position).places;
        mBodyAdapter = new BodyAdapter(bodyInfos);
        mGvMenuThird.setAdapter(mBodyAdapter);
    }

    private void addThirdDepartmentMemu(int postion) {
        List<DepartmentInfo> departmentInfos = mDepartmentClassifies.get(postion).departments;
        mDepartmentAdapter = new DepartMentAdapter(departmentInfos);
        mGvMenuThird.setAdapter(mDepartmentAdapter);
    }

    private void initListener() {
        mRgMenuFirst.setOnCheckedChangeListener(this);
        mRgMenuSecond.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_disease_menu_first:
                if (checkedId == R.id.rb_disease_body) {
                    mRbBodyMenu.setBackgroundResource(R.drawable.disease_menu_selected);
                    mRbDepartmentMenu.setBackgroundColor(Color.WHITE);
                    mRbDepartmentMenu.setTextColor(Color.rgb(190, 0, 1));
                    switchBodyMenu();
                } else {
                    mRbDepartmentMenu.setBackgroundResource(R.drawable.disease_menu_selected);
                    mRbBodyMenu.setBackgroundColor(Color.WHITE);
                    mRbBodyMenu.setTextColor(Color.rgb(190, 0, 1));
                    switchDepartmentMenu();
                }
                break;
            case R.id.rg_disease_menu_second:
                RadioButton radioButton = (RadioButton) mView.findViewById(checkedId);
                int postion = 0;
                if (radioButton != null) {
                    postion = (int) radioButton.getTag();
                }
                for (int i = 0; i < group.getChildCount(); i++) {
                    group.getChildAt(i).setBackgroundColor(Color.WHITE);
                    if (i == postion) {
                        group.getChildAt(i).setBackgroundResource(R.drawable.disease_menu_selected);
                    }
                }
                if (mRgMenuFirst.getCheckedRadioButtonId() == R.id.rb_disease_body) {
                    addThirdBodyMenu(postion);
                } else if (mRgMenuFirst.getCheckedRadioButtonId() == R.id.rb_disease_department) {
                    addThirdDepartmentMemu(postion);
                }
                break;
        }
    }


    class DepartMentAdapter extends DiseaseMenuAdapter<DepartmentInfo> {

        public List<DepartmentInfo> mDatas;

        public DepartMentAdapter(List<DepartmentInfo> datas) {
            super(datas);
            mDatas = datas;
        }

        @Override
        public String getName(int position) {
            return mDatas.get(position).name;
        }
    }


    class BodyAdapter extends DiseaseMenuAdapter<BodyInfo> {

        public List<BodyInfo> mDatas;

        public BodyAdapter(List<BodyInfo> datas) {
            super(datas);
            mDatas = datas;
        }

        @Override
        public String getName(int position) {
            return mDatas.get(position).name;
        }
    }


    private abstract class DiseaseMenuAdapter<T> extends BaseAdapter {

        private List<T> mDatas;

        public DiseaseMenuAdapter(List<T> datas) {
            mDatas = datas;
        }

        @Override
        public int getCount() {
            if (mDatas != null && mDatas.size() > 0) {
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public T getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tvMenu;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_disease_menu_third, null);
                tvMenu = (TextView) convertView.findViewById(R.id.tv_disease_third_menu);
                convertView.setTag(tvMenu);
            } else {
                tvMenu = (TextView) convertView.getTag();
            }

            tvMenu.setText(getName(position));

            return convertView;
        }

        public abstract String getName(int position);
    }
}
