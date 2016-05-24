package com.app.gaolonglong.gankgirl.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.gaolonglong.gankgirl.R;
import com.app.gaolonglong.gankgirl.util.ShareUtil;

/**
 * Created by Admin on 2016/5/22.
 */
public class AboutApp extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("关于App");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share_app){
            ShareUtil.shareText(this,"分享一个很赞的妹纸App，赶紧去和妹子愉快的玩耍吧.. ヾ (o ° ω ° O ) ノ https://github.com/gaolonglong/GankGirl");
        }
        return super.onOptionsItemSelected(item);
    }

}
