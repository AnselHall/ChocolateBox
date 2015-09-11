package com.laibatour.chocolatebox.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.laibatour.chocolatebox.R;
import com.laibatour.chocolatebox.constant.ImageUrl;
import com.laibatour.chocolatebox.utils.ToastUtil;
import com.laibatour.chocolatebox.utils.http.OkHttpClientManager;
import com.squareup.okhttp.OkHttpClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int[] imageIds;
    private LinearLayout main_ll_dots;
    private ViewPager main_vp;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            main_vp.setCurrentItem(main_vp.getCurrentItem() + 1, true);
            handler.sendEmptyMessageDelayed(0, 5000);
        }
    };
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.icon);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        setupDrawerContent(mNavigationView);

        initViewPager();
    }
    public void test(View view){
        startActivity(new Intent(this, TestActivity.class));
    }

    private void initViewPager() {
        imageIds = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};
        main_ll_dots = (LinearLayout) findViewById(R.id.main_ll_dots);

        initDot();

        main_vp = ((ViewPager) findViewById(R.id.main_vp));

        main_vp.setAdapter(new MyPagerAdapter());

        main_vp.setCurrentItem(imageIds.length * 10000);

        updateDescAndDot();

        handler.sendEmptyMessageDelayed(0, 5000);

        main_vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                }
                return false;
            }
        });

        main_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateDescAndDot();
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    /**
     * 动态添加点
     */
    private void initDot() {
        for (int i = 0; i < imageIds.length; i++) {
            View view = new View(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            params.leftMargin = (i == 0 ? 0 : 20);//给除了第一个点之外的点都加上marginLeft
            view.setLayoutParams(params);//设置宽高

            view.setBackgroundResource(R.drawable.selector_dot);//设置背景图片

            main_ll_dots.addView(view);
        }
    }

    /**
     * 根据当前page来显示不同的文字和点
     */
    private void updateDescAndDot() {
        int currentPage = main_vp.getCurrentItem() % imageIds.length;
        // tv_desc.setText(list.get(currentPage).getDesc());

        //更新点
        //遍历所有的点，当点的位置和currentPage相当的时候，则设置为可用，否则是禁用
        for (int i = 0; i < main_ll_dots.getChildCount(); i++) {
            main_ll_dots.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        ToastUtil.showToast(MainActivity.this, menuItem.getTitle().toString());
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                break;
            case R.id.menu_search:
                ToastUtil.showToast(MainActivity.this,"menu_search");
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
            case R.id.menu_selectcity:
                ToastUtil.showToast(MainActivity.this, item.getTitle().toString());
                startActivity(new Intent(MainActivity.this,CityActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

                mDrawerLayout.closeDrawers();
                return true;
            }
            long curTims = System.currentTimeMillis();
            //如果两次点击返回键的时间不大于2秒 弹出toast ，小于2秒退出程序
            if (curTims - firstTime > 2000) {
                ToastUtil.showToast(MainActivity.this, "再按一次退出程序");
                firstTime = curTims;
            }else {
                // TODO Auto-generated method stub
                Intent exit = new Intent(Intent.ACTION_MAIN);
                //CATEGORY_HOME:This is the home activity, that is the first activity that is displayed when the device boots.
                //Constant Value: "android.intent.category.HOME"

                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
                System.exit(0);
                //这里不需要执行父类的点击事件，所以直接return
            }
                return true;


            /*//弹出确定退出对话框
            new AlertDialog.Builder(this)
                    .setTitle("退出")
                    .setMessage("确定退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent exit = new Intent(Intent.ACTION_MAIN);
                            exit.addCategory(Intent.CATEGORY_HOME);
                            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(exit);
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    })
                    .show();
            //这里不需要执行父类的点击事件，所以直接return
            return true;*/
        }
        //继续执行父类的其他点击事件
        return super.onKeyDown(keyCode, event);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //Log.e("current position =====", position + "");
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(imageIds[position % imageIds.length]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
