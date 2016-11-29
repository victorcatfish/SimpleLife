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
import com.victor.vhealth.base.KeyWordSearchBaseFragment;
import com.victor.vhealth.factory.CustomFragmentFactory;

public class KeywordSearchActivity extends AppCompatActivity {

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    private KeyWordSearchBaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_search);

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

        Intent intent = getIntent();
        String classifyKey = intent.getStringExtra(KeyWordSearchBaseFragment.KEYWORD_SEARCH_CLASSIFY);
        String keyWord = intent.getStringExtra(KeyWordSearchBaseFragment.KEY_WORD);
        mFragment = CustomFragmentFactory.createKeywordSearchFragment(classifyKey, keyWord);
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
