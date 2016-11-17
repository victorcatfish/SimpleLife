package com.victor.vhealth.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.BookInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**图书页面ViewHolder
 * Created by Victor on 2016/7/5.
 */
public class BookHolder extends BaseHolder<BookInfo> {

    @ViewInject(R.id.iv_book_cover)
    ImageView mivConver;
    @ViewInject(R.id.tv_book_name)
    TextView mTvName;
    @ViewInject(R.id.tv_book_author)
    TextView mTvAuthor;
    @ViewInject(R.id.tv_book_des)
    TextView mTvdes;
    @ViewInject(R.id.tv_book_read_time)
    TextView mTvReadCount;
    @ViewInject(R.id.btn_read)
    Button mStartBtnRead;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_book_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(BookInfo data) {
        mTvName.setText(data.name);
        mTvAuthor.setText(data.author);
        mTvdes.setText(data.summary);
        mTvReadCount.setText(data.count);
        BitmapHelper.display(mivConver, Constant.URL.IMG_BASE + data.img);

        mStartBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 开启图书阅读
                UIUtils.showShortToast(UIUtils.getContext(), "开始阅读");
            }
        });
    }

    /**格式化时间*/
    private String getStringTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String strTime = format.format(date);
        return strTime;
    }
}
