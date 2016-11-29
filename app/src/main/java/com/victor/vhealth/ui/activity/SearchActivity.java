package com.victor.vhealth.ui.activity;

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

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_CALSSIFY_TYPE = "search_classify_type";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    private ContentBaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ViewUtils.inject(this);
        initFragment();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle("搜索结果");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initFragment() {

        String urlKey = getIntent().getStringExtra(SEARCH_CALSSIFY_TYPE);
        String searchKey = getIntent().getStringExtra(ClassifySearchBaseFragment.SEARCH_CLASSIFY_KEY);
        int id = getIntent().getIntExtra(ContentBaseFragment.DATA_ID, -1);
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
