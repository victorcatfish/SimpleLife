package com.victor.vhealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.DiseaseHolder;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/** 疾病信息列表适配器
 * Created by Victor on 2016/11/24.
 */
public class DiseaseAapter extends CustomBaseAdapter<DiseaseInfo> {

    List<DiseaseInfo> mDatas;

    public DiseaseAapter(List<DiseaseInfo> datas, Context context) {
        super(datas, context);
        mDatas = datas;
    }

    @Override
    protected BaseHolder<DiseaseInfo> getHolderInstance() {
        return new DiseaseHolder();
    }

    @Override
    protected boolean needLoadMore() {
        return false;
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onNormalItemClick(parent, view, position, id);

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra(ContentBaseFragment.DATA_ID, (int)mDatas.get(position).id);
        intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DISEASE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}
