package com.minorius.makakaplayer.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minorius.makakaplayer.App;
import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.adapter.SongAdapter;
import com.minorius.makakaplayer.adapter.pojo.Song;
import com.minorius.makakaplayer.base.BaseViewHolder;
import com.minorius.makakaplayer.modul.artist.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by minorius on 20.11.17.
 */

public class SongViewHolder extends BaseViewHolder<Song> {

    @BindView(R.id.id_txt_song)             TextView song;
    @BindView(R.id.id_txt_song_duration)    TextView duration;

    private Song data;
    private SongAdapter songAdapter;

    public SongViewHolder(ViewGroup parent, SongAdapter songAdapter) {
        super(parent, R.layout.item_song);
        this.songAdapter = songAdapter;
    }

    @Override
    protected void bindData(Song data) {
        this.data = data;
        song.setText(data.getTitle());

        String time = String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(data.getDuration()),
                TimeUnit.MILLISECONDS.toSeconds(data.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(data.getDuration()))
        );

        duration.setText(time);
    }

    @OnClick
    public void onClick(){
        if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
        ((MainActivity) getContext()).play(data.getData());

        App.selectedSound = getAdapterPosition();
        App.currentArtistWhichPlayed = App.selectedPositionArtistByClick;
        songAdapter.notifyItemChanged(App.selectedSound);

        songAdapter.notifyDataSetChanged();
    }
}
