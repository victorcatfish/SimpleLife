package com.victor.simplelife.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.victor.simplelife.util.UIUtils;

/**ListView 工厂类
 * Created by Victor on 2016/7/5.
 */
public class AblistViewFactory {

    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setFastScrollEnabled(true);
        return listView;
    }

    public static GridView create3RowGridView() {
        GridView gridView = new GridView(UIUtils.getContext());
        gridView.setNumColumns(3);
        gridView.setCacheColorHint(Color.TRANSPARENT);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setFastScrollEnabled(true);
        return gridView;
    }
}
