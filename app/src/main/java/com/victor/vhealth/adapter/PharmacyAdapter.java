package com.victor.vhealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.PharmacyInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.PharmacyHolder;
import com.victor.vhealth.protocol.PharmacyProtocol;
import com.victor.vhealth.ui.activity.MapActivity;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/** 药店药房的ListAdapter
 * Created by Victor on 2016/12/14.
 */
public class PharmacyAdapter extends CustomBaseAdapter<PharmacyInfo> {

    private List<PharmacyInfo> mDatas;
    private PharmacyProtocol mProtocol;


    public PharmacyAdapter(List<PharmacyInfo> datas, Context context, PharmacyProtocol protocol) {
        super(datas, context);
        mDatas = datas;
        mProtocol = protocol;
    }

    @Override
    protected BaseHolder<PharmacyInfo> getHolderInstance() {
        return new PharmacyHolder();
    }


    @Override
    protected List<PharmacyInfo> onLoadMore() throws Exception {
        return mProtocol.loadData(mDatas.size() / 20 + 1);
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UIUtils.getContext(), MapActivity.class);
        PharmacyInfo info = mDatas.get(position);
        int hospitalId = (int) info.id;
        intent.putExtra(MapActivity.X_COORDINATE, info.x);
        intent.putExtra(MapActivity.Y_COORDINATE, info.y);
        intent.putExtra(MapActivity.HOSPITAL_NAME, info.name);
        intent.putExtra(ContentBaseFragment.DATA_ID, hospitalId);
        intent.putExtra(MapActivity.MAP_CLASSIFY_KEY, Constant.URL.MEDICINE_PHARMACY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}
