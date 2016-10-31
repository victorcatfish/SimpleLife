package com.victor.simplelife.adapter;

import android.content.Context;

import com.victor.simplelife.base.BaseHolder;
import com.victor.simplelife.base.CustomBaseAdapter;
import com.victor.simplelife.domain.HealthInfo;
import com.victor.simplelife.holder.NewsHolder;

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
