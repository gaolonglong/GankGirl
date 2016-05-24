package com.app.gaolonglong.gankgirl.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.app.gaolonglong.gankgirl.R;
import com.app.gaolonglong.gankgirl.adapter.GanHuoAdapter;
import com.app.gaolonglong.gankgirl.adapter.MeiZhiAdapter;
import com.app.gaolonglong.gankgirl.model.GanHuo;
import com.app.gaolonglong.gankgirl.retrofit.GankRetrofit;
import com.app.gaolonglong.gankgirl.retrofit.GankService;
import com.app.gaolonglong.gankgirl.view.activity.GanHuoActivity;
import com.app.gaolonglong.gankgirl.view.activity.MeiZhiActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 2016/5/16.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener {

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<GanHuo.Result> ganHuoList;
    private GanHuoAdapter ganHuoAdapter;
    private MeiZhiAdapter meiZhiAdapter;

    private boolean isNetWork = true;
    private String title;
    private int page = 1;
    private Handler handler = new Handler();
    //iOSList;androidList;welfareList;videoList;

    public static MainFragment getInstance(String title){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ganHuoList = new ArrayList<>();
        noWIFILayout = (LinearLayout) view.findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);

        if (title.equals("福利")){
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            meiZhiAdapter = new MeiZhiAdapter(getContext());

            dealWithAdapter(meiZhiAdapter);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ganHuoAdapter = new GanHuoAdapter(getContext());
            //recyclerView.setAdapterWithProgress(ganHuoAdapter);
            dealWithAdapter(ganHuoAdapter);
        }

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<GanHuo.Result> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Snackbar.make(recyclerView,adapter.getItem(position).getDesc(), Snackbar.LENGTH_SHORT).show();
                if (title.equals("福利")){
                    Intent intent = new Intent(getContext(), MeiZhiActivity.class);
                    jumpActivity(intent,adapter,position);
                }else {
                    Intent intent = new Intent(getContext(), GanHuoActivity.class);
                    jumpActivity(intent,adapter,position);
                }
            }
        });
    }

    private void jumpActivity(Intent intent,RecyclerArrayAdapter<GanHuo.Result> adapter,int position) {
        intent.putExtra("desc",adapter.getItem(position).getDesc());
        intent.putExtra("url",adapter.getItem(position).getUrl());
        startActivity(intent);
    }

    private void getData(String type,int count,int page) {
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGanHuo(type,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuo>() {
                    @Override
                    public void onCompleted() {
                        Log.e("666","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        noWIFILayout.setVisibility(View.VISIBLE);
                        Snackbar.make(recyclerView,"NO WIFI，不能愉快的看妹纸啦..",Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        ganHuoList = ganHuo.getResults();
                        if (title.equals("福利")){
                            meiZhiAdapter.addAll(ganHuoList);
                        }else {
                            ganHuoAdapter.addAll(ganHuoList);
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")){
                    meiZhiAdapter.clear();
                    getData("福利",20,1);
                }else{
                    ganHuoAdapter.clear();
                    if (title.equals("Android")){
                        getData("Android",20,1);
                    }else if (title.equals("iOS")){
                        getData("iOS",20,1);
                    }
                    else if (title.equals("休息视频")){
                        getData("休息视频",20,1);
                    }
                }
                page = 2;
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (title.equals("福利")){
                    getData("福利",20,page);
                }else if (title.equals("Android")){
                    getData("Android",20,page);
                }else if (title.equals("iOS")){
                    getData("iOS",20,page);
                }
                else if (title.equals("休息视频")){
                    getData("休息视频",20,page);
                }
                page++;
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
