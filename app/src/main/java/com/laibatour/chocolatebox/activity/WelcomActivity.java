package com.laibatour.chocolatebox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.laibatour.chocolatebox.R;
import com.laibatour.chocolatebox.utils.CacheUtil;
import com.laibatour.chocolatebox.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomActivity extends AppCompatActivity implements View.OnClickListener {
    //定义一个常量，用于记录欢迎页是否观察完毕，点击了start按钮
    public static final String IS_WELCOME_OPENED = "is_guide_opened";

    private List<ImageView> images;
    private LinearLayout ll_guide_points;
    private ImageView redPoint;
    private Button bt_guide_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isWelcomOpened = CacheUtil.getBoolean(this, WelcomActivity.IS_WELCOME_OPENED, false);
        //欢迎页打开过了，不用再次打开，要进入splash界面
        if(isWelcomOpened){
            //如果不是第一次进入应用，那么首先进入的要是splash界面
            startActivity(new Intent(this,SplashActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_welcom);
            init();
        }
    }

    private void init() {
        ViewPager vp_guide_bg = (ViewPager) findViewById(R.id.vp_guide_bg);
        ll_guide_points = (LinearLayout) findViewById(R.id.ll_guide_points);
        redPoint = (ImageView) findViewById(R.id.iv_guide_redPoint);
        bt_guide_start = (Button) findViewById(R.id.bt_guide_start);
        bt_guide_start.setOnClickListener(this);
        // 初始化数据
        initData();
        // 设置数据适配器
        vp_guide_bg.setAdapter(new MyPagerAdapter());
        // 监听ViewPager的滑动事件
        vp_guide_bg.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initData() {
        int[] imageIds = new int[] { R.mipmap.welcome_image_one, R.mipmap.welcome_image_two,
                R.mipmap.welcome_image_three };
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);

            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.guide_point_normal);
            int pointDp2Px = CommonUtil.dp2px(this, 10);// dp转px
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointDp2Px, pointDp2Px);
            if (i != 0) {
                params.leftMargin = pointDp2Px;
            }
            point.setLayoutParams(params);
            ll_guide_points.addView(point);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
//			 System.out.println(position + ":positionOffset:" +
//			 positionOffset+":positionOffsetPixels:" + positionOffsetPixels);
            // 红点移动的距离
            int redPointX = (int) (CommonUtil.dp2px(WelcomActivity.this, 20) * (positionOffset+position));// 红点移动的距离
            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) redPoint
                    .getLayoutParams();
            params.leftMargin = redPointX;
            redPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if(position==images.size()-1){
                bt_guide_start.setVisibility(View.VISIBLE);
            }else{
                bt_guide_start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = images.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onClick(View v) {
        //欢迎页展示完毕，记录状态，下次就不用再进入欢迎页了
        CacheUtil.putBoolean(this, WelcomActivity.IS_WELCOME_OPENED, true);
        //进入主界面
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
