package com.minorius.makakaplayer.adapter.pojo;

import java.io.Serializable;

/**
 * Created by minorius on 17.11.17.
 */

public class Song implements Serializable{

    private String title;
    private String artist;
    private String data;
    private long duration;

    public Song(String title, String artist, String data, long duration) {
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getData() {
        return data;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", data='" + data + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
