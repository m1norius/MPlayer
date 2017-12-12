package com.minorius.makakaplayer.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minorius.makakaplayer.App;
import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.adapter.pojo.Song;
import com.minorius.makakaplayer.adapter.viewholder.SongViewHolder;
import com.minorius.makakaplayer.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by minorius on 20.11.17.
 */

public class SongAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<Song> list;

    public SongAdapter(ArrayList<Song> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(list.get(position));

        TextView textView = holder.itemView.findViewById(R.id.id_txt_song);
        TextView duration = holder.itemView.findViewById(R.id.id_txt_song_duration);
        textView.setTextColor(App.selectedSound == position && App.currentArtistWhichPlayed == App.selectedPositionArtistByClick ? Color.parseColor("#A60000") : Color.parseColor("#BFBFBF"));
        duration.setTextColor(App.selectedSound == position && App.currentArtistWhichPlayed == App.selectedPositionArtistByClick ? Color.parseColor("#A60000") : Color.parseColor("#BFBFBF"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
