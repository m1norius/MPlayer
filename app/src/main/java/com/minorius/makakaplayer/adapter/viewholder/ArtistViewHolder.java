package com.minorius.makakaplayer.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.minorius.makakaplayer.App;
import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.base.BaseViewHolder;
import com.minorius.makakaplayer.modul.artist.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by minorius on 20.11.17.
 */

public class ArtistViewHolder extends BaseViewHolder<String> {

    @BindView(R.id.id_txt_artist_title) TextView artist;
    private String data;

    public ArtistViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_artist);
    }

    @Override
    protected void bindData(String data) {
        this.data = data;
        artist.setText(data);
    }

    @OnClick
    public void onClick(){
        ((MainActivity) getContext()).showSongs(data);
        App.selectedPositionArtistByClick = getAdapterPosition();
    }
}
