package com.minorius.makakaplayer.modul.artist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.adapter.ArtistAdapter;
import com.minorius.makakaplayer.adapter.pojo.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by minorius on 20.11.17.
 */

public class ArtistListFragment extends Fragment {

    private TreeMap<String, ArrayList<Song>> map;
    private ArtistAdapter artistAdapter;
    public static Fragment newInstance(TreeMap<String, ArrayList<Song>> map) {

        Bundle arguments = new Bundle();
        arguments.putSerializable("MAP", map);

        ArtistListFragment fragment = new ArtistListFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        map = (TreeMap<String, ArrayList<Song>>) bundle.getSerializable("MAP");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = getActivity().findViewById(R.id.id_recycler_artist);

        LinearLayoutManager layoutManager = new LinearLayoutManager((getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<String> artistsList = new ArrayList<>();
        artistsList.addAll(map.keySet());

        artistAdapter = new ArtistAdapter(artistsList);
        recyclerView.setAdapter(artistAdapter);

    }
}
