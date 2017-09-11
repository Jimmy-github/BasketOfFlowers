package com.bagelplay.basketofflowers.utils;

import android.app.Dialog;
import android.content.Context;

import com.bagelplay.basketofflowers.R;
import com.bagelplay.basketofflowers.view.LoadDialog;

/**
 * Created by zhangtianjie on 2017/9/11.
 */

public class Util {

    public static Dialog getProgressDialog(Context context) {

        Dialog dialog = new LoadDialog(context, R.style.dialog);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;

    }
}
