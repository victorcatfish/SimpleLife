package com.victor.vhealth.factory;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;

import com.victor.vhealth.base.ClassifySearchBaseFragment;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.KeyWordSearchBaseFragment;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.ui.fragment.detail.AskDetailFragment;
import com.victor.vhealth.ui.fragment.detail.BookDetailFragment;
import com.victor.vhealth.ui.fragment.detail.CompanyDetailFragment;
import com.victor.vhealth.ui.fragment.detail.CookDetailFragment;
import com.victor.vhealth.ui.fragment.detail.DiseaseDetailFragment;
import com.victor.vhealth.ui.fragment.detail.DrugDetailFragment;
import com.victor.vhealth.ui.fragment.detail.FoodDetailFragment;
import com.victor.vhealth.ui.fragment.detail.HospitalDetailFragment;
import com.victor.vhealth.ui.fragment.detail.KnowledgeDetailFragment;
import com.victor.vhealth.ui.fragment.detail.NewsDetailFragment;
import com.victor.vhealth.ui.fragment.detail.PharmacyDetailFragment;
import com.victor.vhealth.ui.fragment.detail.TopDetailFragment;
import com.victor.vhealth.ui.fragment.health.AskFragment;
import com.victor.vhealth.ui.fragment.health.BookFragment;
import com.victor.vhealth.ui.fragment.health.KnowledgeFragment;
import com.victor.vhealth.ui.fragment.health.NewsFragment;
import com.victor.vhealth.ui.fragment.life.CookbookFragment;
import com.victor.vhealth.ui.fragment.life.FoodFragment;
import com.victor.vhealth.ui.fragment.life.TopFragment;
import com.victor.vhealth.ui.fragment.medicine.CompanyFragment;
import com.victor.vhealth.ui.fragment.medicine.DiseaseFragment;
import com.victor.vhealth.ui.fragment.medicine.DrugFragment;
import com.victor.vhealth.ui.fragment.medicine.HospitalFragment;
import com.victor.vhealth.ui.fragment.medicine.MedicineFragment;
import com.victor.vhealth.ui.fragment.medicine.PharmacyFragment;
import com.victor.vhealth.ui.fragment.pic.PicFragment;
import com.victor.vhealth.ui.fragment.search.DiseaseClassifySearchFragment;
import com.victor.vhealth.ui.fragment.search.DiseaseKeywordSearchFragment;
import com.victor.vhealth.ui.fragment.search.DrugKeywordSearchFragment;
import com.victor.vhealth.ui.fragment.search.DrugSearchFragment;

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
    static SparseArrayCompat<PicFragment> sPicFragments = new SparseArrayCompat<>();

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
        } else if (key.equals(Constant.URL.PIC)) {
            fragment = sPicFragments.get(id);
            if (fragment == null) {
                fragment = new PicFragment();
                fragment.setArguments(bundle);
                sPicFragments.put(id, (PicFragment)fragment);
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
        } else if (key.equals(Constant.URL.MEDICINE_DRUG)) {
            fragment = new DrugDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.MEDICINE_DISEASE)) {
            fragment = new DiseaseDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.LIFE_TOP)) {
            fragment = new TopDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.LIFE_FOOD)) {
            fragment = new FoodDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.LIFE_COOKBOOK)) {
            fragment = new CookDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.MEDICINE_HOSPTIAL)) {
            fragment = new HospitalDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.MEDICINE_PHARMACY)) {
            fragment = new PharmacyDetailFragment();
            fragment.setArguments(bundle);
        } else if (key.equals(Constant.URL.MEDICINE_COMPANY)) {
            fragment = new CompanyDetailFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }


    public static MedicineFragment createMedicineFragment (String key) {
        MedicineFragment fragment = null;
        if (key.equals(Constant.URL.MEDICINE_DRUG)) {
            fragment = new DrugFragment();
        } else if (key.equals(Constant.URL.MEDICINE_DISEASE)) {
            fragment = new DiseaseFragment();
        } else if (key.equals(Constant.URL.MEDICINE_HOSPTIAL)) {
            fragment = new HospitalFragment();
        } else if (key.equals(Constant.URL.MEDICINE_PHARMACY)) {
            fragment = new PharmacyFragment();
        } else if (key.equals(Constant.URL.MEDICINE_COMPANY)) {
            fragment = new CompanyFragment();
        }

        return fragment;
    }


    public static ContentBaseFragment createSearchFragment(String urlKey, String searchKey, int id) {
        ContentBaseFragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt(ContentBaseFragment.DATA_ID, id);
        if (searchKey != null) {
            bundle.putString(ClassifySearchBaseFragment.SEARCH_CLASSIFY_KEY, searchKey);
        }
        if (urlKey.equals(Constant.URL.MEDICINE_DISEASE)) {
            fragment = new DiseaseClassifySearchFragment();
            fragment.setArguments(bundle);
        }else if (urlKey.equals(Constant.URL.MEDICINE_DRUG)) {
            fragment = new DrugSearchFragment();
            fragment.setArguments(bundle);
        }

        return fragment;
    }


    public static KeyWordSearchBaseFragment createKeywordSearchFragment(String classifyKey, String keyword) {
        KeyWordSearchBaseFragment fragment = null;
        Bundle bundle = new Bundle();
        if (classifyKey != null) {
            bundle.putString(KeyWordSearchBaseFragment.KEYWORD_SEARCH_CLASSIFY, classifyKey);
        }
        if (keyword != null) {
            bundle.putString(KeyWordSearchBaseFragment.KEY_WORD, keyword);
        }

        if (Constant.SearchKeyword.DRUG.equals(classifyKey)) {
            fragment = new DrugKeywordSearchFragment();
            fragment.setArguments(bundle);
        } else if (Constant.SearchKeyword.DISEASE.equals(classifyKey)) {
            fragment = new DiseaseKeywordSearchFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }

}
