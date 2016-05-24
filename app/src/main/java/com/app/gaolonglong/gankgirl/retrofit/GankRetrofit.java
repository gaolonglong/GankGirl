package com.app.gaolonglong.gankgirl.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaohailong on 2016/5/17.
 */
public class GankRetrofit {

    private static final String GANHUO_API = "http://gank.io/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            synchronized (GankRetrofit.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(GANHUO_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
