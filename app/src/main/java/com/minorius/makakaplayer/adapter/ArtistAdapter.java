package com.minorius.makakaplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.minorius.makakaplayer.adapter.viewholder.ArtistViewHolder;
import com.minorius.makakaplayer.base.BaseViewHolder;

import java.util.List;

/**
 * Created by minorius on 20.11.17.
 */

public class ArtistAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<String> strings;

    public ArtistAdapter(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }


}
