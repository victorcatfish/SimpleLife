package com.victor.vhealth.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.factory.CustomFragmentFactory;
import com.victor.vhealth.ui.fragment.medicine.DiseaseFragment;
import com.victor.vhealth.ui.fragment.medicine.DrugFragment;
import com.victor.vhealth.ui.fragment.medicine.MedicineFragment;

/**
 * 医疗列表activity
 */
public class MedicineActivity extends AppCompatActivity {

    public static final String MEDICINE_CALSSIFY_KEY = "medicine_classify_key";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    private Menu mMenu;
    private MedicineFragment mFragment;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        ViewUtils.inject(this);
        initFragment();
        initToolbar();
    }


    /**初始化详情页的toolbar*/
    private void initToolbar() {
        if (mFragment instanceof DrugFragment) {
            mToolbar.setTitle("药品");
        } else if ( mFragment instanceof DiseaseFragment) {
            mToolbar.setTitle("疾病");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    private void initFragment() {
        String classifyKey = getIntent().getStringExtra(MEDICINE_CALSSIFY_KEY);
        mFragment = CustomFragmentFactory.createMedicineFragment(classifyKey);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_medicine_content, mFragment);
        ft.commitAllowingStateLoss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mFragment instanceof DiseaseFragment) {
                    if (((DiseaseFragment)mFragment).isMenuState) {
                        ((DiseaseFragment)mFragment).hideMenu();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mFragment instanceof DiseaseFragment) {
            if (((DiseaseFragment)mFragment).isMenuState) {
                ((DiseaseFragment)mFragment).hideMenu();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
