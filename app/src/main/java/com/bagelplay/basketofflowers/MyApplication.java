package com.bagelplay.basketofflowers;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by zhangtianjie on 2017/8/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((WindowManager) MyApplication.this.getSystemService(Activity.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);

        Config.widthPixels = displayMetrics.widthPixels;
        Config.heightPixels =displayMetrics.heightPixels;
        super.onCreate();
    }
}
