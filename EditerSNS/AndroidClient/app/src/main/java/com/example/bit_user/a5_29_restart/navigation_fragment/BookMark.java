package com.example.bit_user.a5_29_restart.navigation_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit_user.a5_29_restart.R;

/**
 * Created by bituser on 2017-05-30.
 * 이 페이지는 네비게이션 바 - 책갈피에 해당하는 프래그먼트 입니다
 */

public class BookMark extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_mark_fragment,container,false);
    }
}
