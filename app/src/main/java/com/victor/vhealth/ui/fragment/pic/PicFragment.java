package com.victor.vhealth.ui.fragment.pic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.vhealth.R;

/** 美图部分的Fragment
 * Created by Victor on 2016/11/17.
 */
public class PicFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        return view;
    }
}
