package com.bagelplay.basketofflowers.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bagelplay.basketofflowers.Config;
import com.bagelplay.basketofflowers.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;
import static android.R.attr.widgetCategory;
import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by zhangtianjie on 2017/9/10.
 */

public class FlowerMoveView extends RelativeLayout {
    private ImageView mIvBasket, mIvBasketFlower1, mIvBasketFlower2, mIvBasketFlower3,
            mIvFlower1, mIvFlower2, mIvFlower3;

    private List<ImageView> mBasketFlowers;

    private int mIvFlower1Width, mIvFlower1Height,mIvFlower1MarginLeft,mIvFlower1MarginTop;

    private int WIDTH, HEIGHT;


    private Handler timeHandler;

    private final int flower1messageDown = 1000,flower1messageUp = 1001, flower2messageDown = 2000,flower2messageUp = 2001, flower3messageDown = 3000,
            flower3messageUp = 3001;


    public FlowerMoveView(Context context) {
        super(context);
    }

    public FlowerMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WIDTH = Config.widthPixels;
        HEIGHT = Config.heightPixels;

        LayoutInflater.from(context).inflate(R.layout.flower_move_view_layout, this, true);



        intUI();

        initFlowerSize();

        initBasketFlowers();



        initTimeHandler();



    }



    private boolean isInFlowerRange(int mIvFlowerWidth, int mIvFlowerHeight,int mIvFlowerMarginLeft,int mIvFlowerMarginTop, int x,int y) {



        if(x<mIvFlowerMarginLeft+mIvFlowerWidth && x>mIvFlowerMarginLeft && y<mIvFlowerMarginTop+mIvFlowerHeight && y>mIvFlowerMarginTop){

            return true;
        }

        return false;
    }

    private void initTimeHandler() {
        timeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case flower1messageDown:
                        flowerSetPosition(msg, mIvFlower1, mIvFlower1Width, mIvFlower1Height,flower1messageDown);
                        break;
                    case  flower1messageUp:
                        recoverFlowerPosition(mIvFlower1,mIvFlower1Width, mIvFlower1Height,mIvFlower1MarginLeft ,mIvFlower1MarginTop);
                        break;

                }

                super.handleMessage(msg);
            }
        };

    }

    private void flowerSetPosition(Message msg, ImageView mIvFlower, int mIvFlowerWidth, int mIvFlowerHeight,int message) {
        int x = msg.getData().getInt("x");
        int y = msg.getData().getInt("y");

        if (x < 0) {
            x = 0;
        } else if (x > WIDTH) {
            x = WIDTH;
        }
        if (y < 0) {
            y = 0;
        } else if (y > HEIGHT) {
            y = HEIGHT;
        }


        IVTouch(mIvFlower, mIvFlowerWidth, mIvFlowerHeight
                , x, y,message);
    }

    private void recoverFlowerPosition(ImageView mIvFlower,int mIvFlowerWidth, int mIvFlowerHeight,int leftMargin ,int topMargin) {

        LayoutParams flowerLayout = new LayoutParams(
                mIvFlowerWidth, mIvFlowerHeight);


        flowerLayout.leftMargin = leftMargin;
        flowerLayout.topMargin = topMargin;

        mIvFlower.setLayoutParams(flowerLayout);
    }
    public void initBasketFlowers() {
        mBasketFlowers = new ArrayList<>();
        mBasketFlowers.add(mIvBasketFlower1);
        mBasketFlowers.add(mIvBasketFlower2);
        mBasketFlowers.add(mIvBasketFlower3);

        for (int i = 0; i < mBasketFlowers.size(); i++) {
            mBasketFlowers.get(i).setVisibility(GONE);
        }

    }

    private void intUI() {
        mIvBasket = findViewById(R.id.iv_basket);
        mIvBasketFlower1 = findViewById(R.id.iv_basket_flower1);
        mIvBasketFlower2 = findViewById(R.id.iv_basket_flower2);
        mIvBasketFlower3 = findViewById(R.id.iv_basket_flower3);

        mIvFlower1 = findViewById(R.id.iv_flower1);
        mIvFlower2 = findViewById(R.id.iv_flower2);
        mIvFlower3 = findViewById(R.id.iv_flower3);






    }

    private void initFlowerSize() {
        mIvFlower1Width = getResources().getDimensionPixelSize(R.dimen.size_334);
        mIvFlower1Height = getResources().getDimensionPixelSize(R.dimen.size_318);
        mIvFlower1MarginLeft=getResources().getDimensionPixelSize(R.dimen.size_60);
        mIvFlower1MarginTop=getResources().getDimensionPixelSize(R.dimen.size_350);


    }


    int sendMessage;


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {




        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

               if(isInFlowerRange(mIvFlower1Width, mIvFlower1Height,mIvFlower1MarginLeft,mIvFlower1MarginTop,x,y)){
                   sendMessage= flower1messageDown;
                   Log.d("test","true");
               }
                break;
            case MotionEvent.ACTION_MOVE:
                Message msg = new Message();

                msg.what = sendMessage;

                Bundle b = new Bundle();
                b.putInt("x", (int) motionEvent.getX());
                b.putInt("y", (int) motionEvent.getY());


                msg.setData(b);

                timeHandler.sendMessage(msg);
                break;

            case MotionEvent.ACTION_UP:
              if(sendMessage==flower1messageDown){
                  sendMessage=flower1messageUp;
              }

                Message msgup = new Message();

                msgup.what = sendMessage;

                Bundle b_up = new Bundle();
                b_up.putInt("x", (int) motionEvent.getX());
                b_up.putInt("y", (int) motionEvent.getY());

                msgup.setData(b_up);

                timeHandler.sendMessage(msgup);
                break;

        }

        return true;


    }


    private void IVTouch(ImageView mIvFlower, int mIvFlowerWidth, int mIvFlowerHeight, int x, int y,int message) {
        LayoutParams flowerLayout = new LayoutParams(
                mIvFlowerWidth, mIvFlowerHeight);

        int leftMargin = x - mIvFlowerWidth / 2;

        if (leftMargin < 0) {
            leftMargin = 0;
        } else if (leftMargin > WIDTH - mIvFlowerWidth) {
            leftMargin = WIDTH - mIvFlowerWidth;

        }

        int topMargin = y - mIvFlowerHeight / 2;

        if (topMargin < 0) {
            topMargin = 0;
        } else if (topMargin > HEIGHT - mIvFlowerHeight) {
            topMargin = HEIGHT - mIvFlowerHeight;

        }


        flowerLayout.leftMargin = leftMargin;
        flowerLayout.topMargin = topMargin;

        Log.d("test", x + " " + leftMargin + "###" + y + " " + topMargin);

        mIvFlower.setLayoutParams(flowerLayout);

        mIvFlower.bringToFront();

        timeHandler.removeMessages(message);

    }

    public FlowerMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
