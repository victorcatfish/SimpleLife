package com.victor.vhealth.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.factory.CustomFragmentFactory;
import com.victor.vhealth.ui.fragment.DetailCommenFragment;
import com.victor.vhealth.ui.fragment.detail.AskDetailFragment;
import com.victor.vhealth.ui.fragment.detail.BookDetailFragment;
import com.victor.vhealth.ui.fragment.detail.KnowledgeDetailFragment;
import com.victor.vhealth.ui.fragment.detail.NewsDetailFragment;
import com.victor.vhealth.util.UIUtils;

/**资讯详情页面
 * Created by Victor on 2016/7/9.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String CLASSIFY_KEY = "classify_key";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;
    private ContentBaseFragment mFragment;
    private Menu mMenu;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ViewUtils.inject(this);

        initFragment();
        initToolbar();
    }

    /**初始化详情页的toolbar*/
    private void initToolbar() {
        if (mFragment instanceof NewsDetailFragment) {
            mToolbar.setTitle("资讯详情");
        } else if (mFragment instanceof KnowledgeDetailFragment) {
            mToolbar.setTitle("知识详情");
        } else if (mFragment instanceof AskDetailFragment) {
            mToolbar.setTitle("问答详情");
        } else if (mFragment instanceof BookDetailFragment) {
            mToolbar.setTitle("图书详情");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void hideMenu() {
        if(null != mMenu){
            for (int i = 0; i < mMenu.size(); i++){
                mMenu.getItem(i).setVisible(false);
                mMenu.getItem(i).setEnabled(false);
            }
        }
    }

    private void initFragment() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(ContentBaseFragment.DATA_ID, -1);
        String classifyKey = intent.getStringExtra(CLASSIFY_KEY);
        mFragment = CustomFragmentFactory.createDetailFragment(classifyKey, id);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_detail_content, mFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        if (mFragment instanceof BookDetailFragment) {
            menu.findItem(R.id.text_size).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mFragment instanceof DetailCommenFragment) {
                    if (mFragment instanceof BookDetailFragment) {
                        finish();
                    } else {
                        DetailCommenFragment fragment = (DetailCommenFragment) mFragment;
                        fragment.goBack();
                    }
                } else {
                    finish();
                }
                break;
            case R.id.text_size:
                showTextSizeDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**展示设置字体大小的对话框*/
    private void showTextSizeDialog() {
        // TODO 弹出正文字大小体设置对话框
        UIUtils.showShortToast(this, "设置文字大小");
    }

}
