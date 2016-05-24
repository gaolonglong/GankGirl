package com.app.gaolonglong.gankgirl.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.gaolonglong.gankgirl.R;
import com.app.gaolonglong.gankgirl.util.ImageUtil;
import com.app.gaolonglong.gankgirl.util.ShareUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by gaohailong on 2016/5/18.
 */
public class MeiZhiActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ImageView image;
    private String desc;
    private String url;
    private PhotoViewAttacher attacher;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizhi_layout);

        init();

    }

    private void init() {
        //获取传过来的数据
        getData();
        //初始化
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        desc = intent.getStringExtra("desc");
        url = intent.getStringExtra("url");
    }

    private void initView() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle(desc);
        //ImageView
        image = (ImageView) findViewById(R.id.image_meizhi);
        attacher = new PhotoViewAttacher(image);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        image.setImageBitmap(resource);
                        attacher.update();
                        bitmap = resource;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meizhi,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareUtil.shareImage(this,ImageUtil.saveImage(this,url,bitmap,image,"share"));
                break;
            case R.id.action_save:
                ImageUtil.saveImage(this,url,bitmap,image,"save");
                break;
            case R.id.action_click_me:
                Snackbar.make(image,"萝莉赛高.. ヾ (o ° ω ° O ) ノ",Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
