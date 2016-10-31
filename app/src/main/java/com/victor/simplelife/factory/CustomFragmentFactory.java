package com.victor.simplelife.factory;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;

import com.victor.simplelife.base.ContentBaseFragment;
import com.victor.simplelife.global.Constant;
import com.victor.simplelife.ui.fragment.detail.AskDetailFragment;
import com.victor.simplelife.ui.fragment.detail.BookDetailFragment;
import com.victor.simplelife.ui.fragment.detail.KnowledgeDetailFragment;
import com.victor.simplelife.ui.fragment.detail.NewsDetailFragment;
import com.victor.simplelife.ui.fragment.health.AskFragment;
import com.victor.simplelife.ui.fragment.health.BookFragment;
import com.victor.simplelife.ui.fragment.health.KnowledgeFragment;
import com.victor.simplelife.ui.fragment.health.NewsFragment;
import com.victor.simplelife.ui.fragment.life.CookbookFragment;
import com.victor.simplelife.ui.fragment.life.FoodFragment;
import com.victor.simplelife.ui.fragment.life.TopFragment;

/**自定义fragment工厂类
 * Created by Victor on 2016/7/5.
 */
public class CustomFragmentFactory {

    static SparseArrayCompat<NewsFragment> sNewsFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<KnowledgeFragment> sKnowledgeFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<AskFragment> sAskFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<BookFragment> sBookFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<TopFragment> sTopFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<FoodFragment> sFoodFragments = new SparseArrayCompat<>();
    static SparseArrayCompat<CookbookFragment> sCookBookFragments = new SparseArrayCompat<>();

    public static ContentBaseFragment createContentFragment(String key, int id) {
        ContentBaseFragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt(ContentBaseFragment.DATA_ID, id);
        if (key.equals(Constant.URL.HEALTH_NEWS)) {
            fragment = sNewsFragments.get(id);
            if (fragment == null) {
                fragment = new NewsFragment();
                fragment.setArguments(bundle);
                sNewsFragments.put(id, (NewsFragment) fragment);
            }
        } else if (key.equals(Constant.URL.HEALTH_KNOWLEDGE)) {
            fragment = sKnowledgeFragments.get(id);
            if (fragment == null) {
                fragment = new KnowledgeFragment();
                fragment.setArguments(bundle);
                sKnowledgeFragments.put(id, (KnowledgeFragment) fragment);
            }
        } else if (key.equals(Constant.URL.HEALTH_ASK)) {
            fragment = sAskFragments.get(id);
            if (fragment == null) {
                fragment = new AskFragment();
                fragment.setArguments(bundle);
                sAskFragments.put(id, (AskFragment) fragment);
            }
        } else if (key.equals(Constant.URL.HEALTH_BOOK)) {
            fragment = sBookFragments.get(id);
            if (fragment == null) {
                fragment = new BookFragment();
                fragment.setArguments(bundle);
                sBookFragments.put(id, (BookFragment) fragment);
            }
        } else if (key.equals(Constant.URL.LIFE_TOP)) {
            fragment = sTopFragments.get(id);
            if (fragment == null) {
                fragment = new TopFragment();
                fragment.setArguments(bundle);
                sTopFragments.put(id, (TopFragment) fragment);
            }
        } else if (key.equals(Constant.URL.LIFE_FOOD)) {
            fragment = sFoodFragments.get(id);
            if (fragment == null) {
                fragment = new FoodFragment();
                fragment.setArguments(bundle);
                sFoodFragments.put(id, (FoodFragment) fragment);
            }
        } else if (key.equals(Constant.URL.LIFE_COOKBOOK)) {
            fragment = sCookBookFragments.get(id);
            if (fragment == null) {
                fragment = new CookbookFragment();
                fragment.setArguments(bundle);
                sCookBookFragments.put(id, (CookbookFragment) fragment);
            }
        }
        return fragment;
    }

    public static ContentBaseFragment createDetailFragment(String key, int id) {
        ContentBaseFragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt(ContentBaseFragment.DATA_ID, id);
        if (key.equals(Constant.URL.HEALTH_NEWS)) {
            fragment = new NewsDetailFragment();
            fragment.setArguments(bundle);
        }else if (key.equals(Constant.URL.HEALTH_KNOWLEDGE)) {
            fragment = new KnowledgeDetailFragment();
            fragment.setArguments(bundle);
        }else if (key.equals(Constant.URL.HEALTH_ASK)) {
            fragment = new AskDetailFragment();
            fragment.setArguments(bundle);
        }else if (key.equals(Constant.URL.HEALTH_BOOK)) {
            fragment = new BookDetailFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }

}
