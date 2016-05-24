package com.app.gaolonglong.gankgirl.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.app.gaolonglong.gankgirl.model.GanHuo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by gaohailong on 2016/5/17.
 */
public class GanHuoAdapter extends RecyclerArrayAdapter<GanHuo.Result> {

    public GanHuoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }
}
