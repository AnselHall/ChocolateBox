package com.laibatour.chocolatebox.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.apkfuns.logutils.LogUtils;
import com.laibatour.chocolatebox.R;
import com.laibatour.chocolatebox.utils.LogUtil;

/**
 * splash界面，
 * 1、查看网络是否可用
 * 可用：访问网络
 * 不可用：不访问网络。提示网络不可用
 * 2、查看是否有更新
 * 3、
 */
public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }, 3000);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("onKeyDown is activity");
        System.out.println(keyCode + "");
        if(keyCode == KeyEvent.KEYCODE_BACK){

            handler.removeCallbacksAndMessages(null);
            System.out.println("KeyEvent.KEYCODE_BACK");
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
