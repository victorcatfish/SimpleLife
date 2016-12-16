package com.victor.vhealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.HospitalHolder;
import com.victor.vhealth.protocol.HospitalProtocol;
import com.victor.vhealth.ui.activity.MapActivity;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/** 医院门诊的ListAdapter
 * Created by Victor on 2016/12/14.
 */
public class HospitalAdapter extends CustomBaseAdapter<HospitalInfo> {

    private List<HospitalInfo> mDatas;
    private HospitalProtocol mProtocol;


    public HospitalAdapter(List<HospitalInfo> datas, Context context, HospitalProtocol protocol) {
        super(datas, context);
        mDatas = datas;
        mProtocol = protocol;
    }

    @Override
    protected BaseHolder<HospitalInfo> getHolderInstance() {
        return new HospitalHolder();
    }


    @Override
    protected List<HospitalInfo> onLoadMore() throws Exception {
        return mProtocol.loadData(mDatas.size() / 20 + 1);
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UIUtils.getContext(), MapActivity.class);
        HospitalInfo info = mDatas.get(position);
        int hospitalId = (int) info.id;
        intent.putExtra(MapActivity.X_COORDINATE, info.x);
        intent.putExtra(MapActivity.Y_COORDINATE, info.y);
        intent.putExtra(MapActivity.HOSPITAL_NAME, info.name);
        intent.putExtra(ContentBaseFragment.DATA_ID, hospitalId);
        intent.putExtra(MapActivity.MAP_CLASSIFY_KEY, Constant.URL.MEDICINE_HOSPTIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }
}
