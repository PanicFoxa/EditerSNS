package com.example.bit_user.a5_29_restart;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by bit-user on 2017-07-20.
 */

public class gridData {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */
    // 아이콘
    public Drawable mIcon;

    // 제목
    public String mTitle;

    // 날짜
    public String mDate;

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator<gridData> ALPHA_COMPARATOR = new Comparator<gridData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(gridData mListDate_1, gridData mListDate_2) {
            return sCollator.compare(mListDate_1.mTitle, mListDate_2.mTitle);
        }
    };
}
