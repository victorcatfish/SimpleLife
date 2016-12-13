package com.victor.vhealth.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.factory.CustomFragmentFactory;
import com.victor.vhealth.base.ClassifySearchBaseFragment;

public class ClassifySearchActivity extends AppCompatActivity {

    public static final String SEARCH_CLASSIFY_TYPE = "search_classify_type";
    public static final String SEARCH_CLASSIFY_NAME = "search_classify_name";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    private ContentBaseFragment mFragment;
    private String mKeyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ViewUtils.inject(this);
        initFragment();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(mKeyName + "相关疾病");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initFragment() {

        Intent intent = getIntent();
        String urlKey = intent.getStringExtra(SEARCH_CLASSIFY_TYPE);
        String searchKey = intent.getStringExtra(ClassifySearchBaseFragment.SEARCH_CLASSIFY_KEY);
        mKeyName = intent.getStringExtra(SEARCH_CLASSIFY_NAME);
        int id = intent.getIntExtra(ContentBaseFragment.DATA_ID, -1);
        mFragment = CustomFragmentFactory.createSearchFragment(urlKey,searchKey, id);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_search_content, mFragment);
        ft.commitAllowingStateLoss();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
