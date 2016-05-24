package com.app.gaolonglong.gankgirl.retrofit;

import com.app.gaolonglong.gankgirl.model.GanHuo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by gaohailong on 2016/5/17.
 */
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
