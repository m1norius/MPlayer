package com.minorius.makakaplayer.modul.artist;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.minorius.makakaplayer.App;
import com.minorius.makakaplayer.R;
import com.minorius.makakaplayer.adapter.pojo.Song;

import com.minorius.makakaplayer.adapter.viewholder.Communicator;
import com.minorius.makakaplayer.base.BaseActivity;
import com.minorius.makakaplayer.base.FragmentNavigator;
import com.minorius.makakaplayer.di.component.DaggerPlayerComponent;
import com.minorius.makakaplayer.modul.artist.fragment.ArtistListFragment;
import com.minorius.makakaplayer.modul.artist.fragment.SongListFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements Communicator {

    @Inject MediaPlayer mediaPlayer;

    private TreeMap<String, ArrayList<Song>> map = new TreeMap<>();

    private FragmentNavigator fragmentNavigator;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<Song> allArtistSongs = new ArrayList<>();

    private String selectedArtistNameByClick;
    private String selectedArtistWhichPlayed = "";
    private Handler mHandler = new Handler();
    private int count = 0;
    private SongListFragment fragment;

    private int playedArtistPosition;


    @BindView(R.id.id_txt_artist_text)  TextView artistTextView;
    @BindView(R.id.id_frame_title)      RelativeLayout titleFrame;

    @BindView(R.id.id_btn_next)         ImageButton nextBtn;
    @BindView(R.id.id_btn_pause)        ImageButton pauseBtn;
    @BindView(R.id.id_btn_prev)         ImageButton prevBtn;

    @BindView(R.id.id_txt_current_song) TextView currentSongTxt;

    @BindView(R.id.id_seek_bar)         SeekBar seekBar;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        getMusic();

        DaggerPlayerComponent.create().inject(MainActivity.this);

        fragmentNavigator = new FragmentNavigator(R.id.id_frame, fragmentManager);
        fragmentNavigator.addFragment(ArtistListFragment.newInstance(map), false);

        titleFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();

                if (fragmentManager.getBackStackEntryCount() == 0 && fragment != null){
                    selectedArtistNameByClick = artistTextView.getText().toString();
                    App.selectedPositionArtistByClick = playedArtistPosition;
                    fragmentNavigator.replaceFragment(fragment, true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(duration);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pauseBtn.setBackgroundResource(R.drawable.ic_play_btn);
                }else {
                    mediaPlayer.start();
                    pauseBtn.setBackgroundResource(R.drawable.ic_pause_btn);
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count - 2;
                App.selectedSound = App.selectedSound - 2;
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(duration);
            }
        });


    }

    @Override
    public void showSongs(String artist) {
        this.selectedArtistNameByClick = artist;

        //если выбран артист уже играет то кидаем во фрейм старый фрагемнт
        // (чтобы у нас был старый адаптер для переключения бекграунда играющей слудующей песни)
        if (selectedArtistWhichPlayed.equals(artist)){
            fragmentNavigator.replaceFragment(fragment, true);
            //иначе кидаем во фрейм новый фрагмент
        }else {
            fragmentNavigator.replaceFragment(SongListFragment.newInstance(map.get(artist)), true);
        }
    }


    public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if(songCursor != null && songCursor.moveToFirst()) {

            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do {

                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentData = songCursor.getString(songData);
                long currentDuration = songCursor.getLong(songDuration);

                if (map.containsKey(currentArtist)){
                    map.get(currentArtist).add(new Song(currentTitle, currentArtist, currentData, currentDuration));
                }else {
                    ArrayList<Song> songs = new ArrayList<>();
                    songs.add(new Song(currentTitle, currentArtist, currentData, currentDuration));
                    map.put(currentArtist, songs);
                }

            } while(songCursor.moveToNext());
        }
    }


    @Override
    public void play(final String url) {

        pauseBtn.setBackgroundResource(R.drawable.ic_pause_btn);
        //получаем фрагмент того листа в котором нажато програть
        fragment = (SongListFragment) fragmentManager.findFragmentById(R.id.id_frame);

        //Если мы в play значит пользователь нажал кноку играть
        //Ложим в !!!selectedArtistWhichPlayed!!! имя артиста чей лист запущен !!!selectedArtistNameByClick!!!
        selectedArtistWhichPlayed = selectedArtistNameByClick;
        
        allArtistSongs = map.get(selectedArtistWhichPlayed);

        //задать текст в верхний тайтл
        artistTextView.setText(selectedArtistWhichPlayed);


        //получаем позицию артиста который играет
        playedArtistPosition = App.selectedPositionArtistByClick;

        count = 0;
        final List<Song> currentSongPlusNextAllSongs = new ArrayList<>();

        for (int i = 0; i < allArtistSongs.size(); i++){
            if(allArtistSongs.get(i).getData().equals(url)){
                for (int k = i; k < allArtistSongs.size(); k++){
                    currentSongPlusNextAllSongs.add(allArtistSongs.get(k));
                }
            }
        }

        currentSongTxt.setText(currentSongPlusNextAllSongs.get(count).getTitle());

        initSeekBar((int) currentSongPlusNextAllSongs.get(count).getDuration());

        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(currentSongPlusNextAllSongs.get(count).getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    count = count+1;

                    String nextTrack = "";

                    if (count < currentSongPlusNextAllSongs.size()){
                        if (count < 0){
                            count = 0;
                        }

                        if(App.selectedSound < (allArtistSongs.size() -  currentSongPlusNextAllSongs.size())){
                            App.selectedSound = allArtistSongs.size() -  currentSongPlusNextAllSongs.size();
                            App.selectedSound--;
                        }
                        nextTrack = currentSongPlusNextAllSongs.get(count).getData();

                        initSeekBar((int) currentSongPlusNextAllSongs.get(count).getDuration());

                        currentSongTxt.setText(currentSongPlusNextAllSongs.get(count).getTitle());
                        App.selectedSound++;
                        fragment.notifyRecycler();
                    }

                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(nextTrack);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSeekBar(int max){

        seekBar.setMax(max/1000);

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }


}
