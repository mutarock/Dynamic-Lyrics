package com.kkbox.raytseng.dynamiclyrics.model;

/**
 * Created by raytseng on 2/4/15.
 */
public class Lyrics {

    public String lyric;
    public int startTime;
    public int endTime;
    public boolean focus;

    public Lyrics(String lyric, int startTime, int endTime) {

        this.lyric = lyric;
        this.startTime = startTime;
        this.endTime = endTime;
        this.focus = false;

    }


}
