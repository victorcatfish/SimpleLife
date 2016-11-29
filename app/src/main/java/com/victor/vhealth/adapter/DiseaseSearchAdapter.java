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
import com.victor.vhealth.holder.DiseaseSearchHolder;
import com.victor.vhealth.protocol.DiseaseProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/** 疾病信息搜索结果的适配器
 * Created by Victor on 2016/11/25.
 */
public class DiseaseSearchAdapter extends CustomBaseAdapter<DiseaseInfo> {

    private List<DiseaseInfo> mDatas;
    private DiseaseProtocol mProtocol;

    public DiseaseSearchAdapter(List<DiseaseInfo> datas, Context context, DiseaseProtocol protocol) {
        super(datas, context);
        mDatas = datas;
        mProtocol = protocol;
    }

    @Override
    protected BaseHolder<DiseaseInfo> getHolderInstance() {
        return new DiseaseSearchHolder();
    }

    @Override
    protected List<DiseaseInfo> onLoadMore() throws Exception {
        return mProtocol.loadData(mDatas.size() / 20 + 1);
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra(ContentBaseFragment.DATA_ID, (int)mDatas.get(position).id);
        intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DISEASE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}
