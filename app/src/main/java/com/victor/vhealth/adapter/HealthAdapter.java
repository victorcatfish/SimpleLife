package com.victor.vhealth.adapter;

import android.content.Context;

import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.HealthInfo;
import com.victor.vhealth.holder.NewsHolder;

import java.util.List;

/**
 * Created by Victor on 2016/7/6.
 */
public class HealthAdapter extends CustomBaseAdapter<HealthInfo> {

    public HealthAdapter(List<HealthInfo> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected BaseHolder<HealthInfo> getHolderInstance() {
        return new NewsHolder();
    }
}
