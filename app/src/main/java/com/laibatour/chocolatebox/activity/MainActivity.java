package com.laibatour.chocolatebox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.laibatour.chocolatebox.R;
import com.laibatour.chocolatebox.constant.ImageUrl;
import com.laibatour.chocolatebox.utils.http.OkHttpClientManager;
import com.squareup.okhttp.OkHttpClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_iv)
    ImageView main_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        OkHttpClientManager.getDisplayImageDelegate().displayImage(main_iv, ImageUrl.imageUrls[9] + "");
    }
}
