package com.victor.vhealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.DrugHotRecentHolder;
import com.victor.vhealth.holder.DrugSearchHolder;
import com.victor.vhealth.protocol.DrugProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/** 药物信息的List适配器
 * Created by Victor on 2016/11/20.
 */
public class DrugAdapter extends CustomBaseAdapter<DrugInfo> {

    private static final int HOT_RECENT_COUNTS = 12;

    private List<DrugInfo> mDatas;
    private ListType mType;
    private DrugProtocol mProtocol;

    public enum ListType {
        GRID_VIEW_TYPE,
        LIST_VIEW_TYPE
    }

    public DrugAdapter(List<DrugInfo> datas, Context context, ListType type, DrugProtocol protocol) {
        super(datas, context);
        mDatas = datas;
        mType = type;
        mProtocol = protocol;
    }

    @Override
    public int getCount() {
        if (mType == ListType.GRID_VIEW_TYPE) {
            if (mDatas != null && mDatas.size() > 0) {
                if (mDatas.size() > HOT_RECENT_COUNTS) {
                    return HOT_RECENT_COUNTS;
                } else {
                    return mDatas.size();
                }
            }
        } else if (mType == ListType.LIST_VIEW_TYPE) {
            if (mDatas != null && mDatas.size() > 0) {
                return mDatas.size();
            }
        }
        return 0;
    }

    @Override
    protected boolean needLoadMore() {
        if (mType == ListType.GRID_VIEW_TYPE) {
            return false;
        }
        return true;
    }

    @Override
    protected BaseHolder<DrugInfo> getHolderInstance() {
        if (mType == ListType.GRID_VIEW_TYPE) {
            return new DrugHotRecentHolder();
        } else {
            return new DrugSearchHolder();
        }
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra(ContentBaseFragment.DATA_ID, (int) mDatas.get(position).id);
        // 放入url里面的关键词，用于区分是药物、医院信息等……
        intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DRUG);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }

    @Override
    protected List<DrugInfo> onLoadMore() throws Exception {
        return mProtocol.loadData(mDatas.size() / 20 + 1);
    }
}
