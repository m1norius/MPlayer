package com.minorius.makakaplayer.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by minorius on 29.08.2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private Context context;

    public BaseViewHolder(ViewGroup parent, @LayoutRes int id) {
        super(LayoutInflater.from(parent.getContext()).inflate(id, parent, false));
        context = itemView.getContext();
        ButterKnife.bind(this,itemView);

    }

    protected Context getContext(){
        return context;
    }

    public void bind(Object object){
        T t = (T) object;
        bindData(t);
    }

    protected abstract void bindData(T data);
}
