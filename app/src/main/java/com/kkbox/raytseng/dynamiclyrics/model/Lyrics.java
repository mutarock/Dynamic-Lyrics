package com.kkbox.raytseng.dynamiclyrics.model;

/**
 * Created by raytseng on 2/4/15.
 */
public class Lyrics {

    private String lyric;
    private int startTime;
    private int endTime;
    private boolean isFocus;
    private boolean isEmptyLine;

    public Lyrics(String lyric, int startTime, int endTime) {

        this.lyric = lyric;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isFocus = false;

        if (startTime == 0 && endTime == 0) {
            isEmptyLine = true;
        } else {
            isEmptyLine = false;
        }

    }

    public String getLyric() {
        return lyric;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public boolean isEmptyLine() {
        return isEmptyLine;
    }

}
