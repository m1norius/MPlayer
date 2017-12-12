package com.minorius.makakaplayer.modul.artist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minorius.makakaplayer.App;
import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.adapter.ArtistAdapter;
import com.minorius.makakaplayer.adapter.SongAdapter;
import com.minorius.makakaplayer.adapter.pojo.Song;
import com.minorius.makakaplayer.modul.artist.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by minorius on 20.11.17.
 */

public class SongListFragment extends Fragment {

    private ArrayList<Song> list;
    private SongAdapter songAdapter;
    public static Fragment newInstance(ArrayList<Song> map) {

        Bundle arguments = new Bundle();
        arguments.putSerializable("LIST", map);

        SongListFragment fragment = new SongListFragment();
        fragment.setArguments(arguments);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        list = (ArrayList<Song>) bundle.getSerializable("LIST");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.id_recycler_song);

        LinearLayoutManager layoutManager = new LinearLayoutManager((getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        songAdapter = new SongAdapter(list);
        recyclerView.setAdapter(songAdapter);
    }

    public void notifyRecycler(){
        App.myLog("+" +songAdapter);
        songAdapter.notifyDataSetChanged();
    }
}
