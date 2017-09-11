package com.bagelplay.basketofflowers;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bagelplay.basketofflowers.utils.Util;
import com.bagelplay.basketofflowers.view.FlowerMoveView;
import com.bagelplay.basketofflowers.view.VideoView;

public class MainActivity extends AppCompatActivity {


    VideoView mVideoView;
    FlowerMoveView mFlowerMoveView;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

        dialog = Util.getProgressDialog(this);
        dialog.show();

        setVvListener();


        mFlowerMoveView.setOnFlowerMoveViewFinishListener(new FlowerMoveView.OnFlowerMoveViewFinishListener() {
            @Override
            public void onFinish() {


                mProgressHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFlowerMoveView.setVisibility(View.GONE);
                        mVideoView.start();
                    }
                },1000);

            }
        });

        mVideoView.setVideoPath("http://112.126.81.84/video/topClass3.mp4");

    }

    private Handler mProgressHandler = new Handler();
    private int pose = 0;
    private int videoLength;
    Runnable updateThread = new Runnable() {
        @Override
        public void run() {

            pose = mVideoView.getCurrentPosition();


            if (getTime(pose)) {

                showInteract();


            } else {

                mProgressHandler.postDelayed(updateThread, 1000);

            }

        }

    };

    private void showInteract() {

        mVideoView.pause();
        mProgressHandler.removeCallbacks(updateThread);

        mFlowerMoveView.setVisibility(View.VISIBLE);



    }


    private Boolean getTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;


        if (hour == 0 && minute == 3 && second == 38) {

            return true;
        }


        return false;

    }

    private void setVvListener() {
        // 异常
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mVideoView.stopPlayback();

                return false;
            }
        });
        // 准备OK
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {


                mVideoView.start();
                videoLength = mVideoView.getDuration();
                mProgressHandler.post(updateThread);

            }
        });

        // 播放完成
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 判断是否播放节目的下一段，获取下一个节目的媒体列表

                mVideoView.stopPlayback();


            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (mp.MEDIA_INFO_BUFFERING_START == what) {

                    dialog.show();

                } else {
                    // else if (mp.MEDIA_INFO_BUFFERING_END == what) {

                    dialog.dismiss();

                    // }
                }

                return true;
            }
        });

        mVideoView.setOnPlayingBufferCacheListener(new MediaPlayer.OnBufferingUpdateListener() {

            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });

        mVideoView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {

            }
        });

    }

    private void initView() {

        mVideoView = (VideoView) findViewById(R.id.videoview);
        mFlowerMoveView = (FlowerMoveView) findViewById(R.id.flower_move_view);
    }


}
