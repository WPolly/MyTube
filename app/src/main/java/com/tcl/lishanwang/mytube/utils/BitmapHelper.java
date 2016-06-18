package com.tcl.lishanwang.mytube.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by xiaoshan on 2016/5/24.
 * 15:32
 */
public class BitmapHelper {

    public static void display(ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) return;
        Picasso.with(UIUtils.getContext()).load(url).into(iv);
    }
}
