package com.kkbox.raytseng.dynamiclyrics.app.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kkbox.raytseng.dynamiclyrics.R;
import com.kkbox.raytseng.dynamiclyrics.app.adapter.LyricsAdapter;
import com.kkbox.raytseng.dynamiclyrics.model.Lyrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainLyricsActivity extends ActionBarActivity {

    private MediaPlayer mediaPlayer;
    private ListView lyricsListView;
    private LyricsAdapter lyricsAdapter;
    private ArrayList<Lyrics> lyricsData;
    private Timer mTimer;
    private int lastItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lyrics);

        initLyrics();
        initUIComponent();
        initPlayer();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_lyrics, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initLyrics() {
        lyricsData = new ArrayList<Lyrics>();

        String totalLyrics = loadJSONFromAsset("lyrics.json");
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(totalLyrics);
            JSONArray array = jsonObj.getJSONArray("lyrics");

            for(int index = 0 ; index < array.length() ; index++) {
                JSONObject singleLine = array.getJSONObject(index);

                String content = singleLine.getString("content");
                int startTime = convertTimeFormatToSeconds(singleLine.getString("startTime"));
                int endTime = convertTimeFormatToSeconds(singleLine.getString("endTime"));

                Lyrics lyrics = new Lyrics(content, startTime, endTime);
                lyricsData.add(lyrics);


            }

        }catch (JSONException je) {
            je.printStackTrace();

        }


    }


    private void initUIComponent() {
        lyricsListView = (ListView) findViewById(R.id.lyrics_listview);
        lyricsAdapter = new LyricsAdapter(this, lyricsData);
        lyricsListView.setAdapter(lyricsAdapter);
        lyricsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Lyrics lyrics = lyricsData.get(position);
                if (!lyrics.isEmptyLine()) {
                    lyricsData.get(lastItemIndex).setFocus(false);
                    mediaPlayer.seekTo(lyrics.getStartTime() * 1000);
                }

            }
        });

    }


    private void initPlayer() {
        mediaPlayer = new MediaPlayer();

        Uri fileUri = Uri.parse(getString(R.string.not_your_kind_people));

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, fileUri);
            mediaPlayer.setLooping(true);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    initTimerForLyrics();

                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setListViewToSelectIndex(0);

                }
            });

            mediaPlayer.prepareAsync();

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }

    }


    private String loadJSONFromAsset(String fileName) {
        String json = "";

        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return json;

        }

        return json;
    }


    private int convertTimeFormatToSeconds(String timeFormat) {

        String[] tokens = timeFormat.split(":");

        int seconds = 0;
        int exponent = tokens.length-1;
        for(int index = tokens.length-1 ; index >= 0 ; index--) {
            seconds += Integer.parseInt(tokens[index]) * (int)(Math.pow(60, exponent-index));
        }

        return seconds;

    }


    private void initTimerForLyrics() {
        mTimer = new Timer();
        mTimer.schedule(myTask, 0 , 500);

    }


    private void setListViewToSelectIndex(final int itemIndex) {
        lyricsListView.post(new Runnable() {
            @Override
            public void run() {
                lyricsListView.smoothScrollToPositionFromTop(itemIndex, lyricsListView.getHeight() / 2);
            }
        });

    }


    private TimerTask myTask = new TimerTask() {

        @Override
        public void run() {
            handler.obtainMessage(0).sendToTarget();
        }

    };


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int currentTime = mediaPlayer.getCurrentPosition() / 1000;
            int currentItemIndex = lastItemIndex;

            for (int i = 1 ; i < lyricsData.size() ; i++) {
                if (currentTime >= lyricsData.get(i).getStartTime() && currentTime <= lyricsData.get(i).getEndTime() && !lyricsData.get(i).isEmptyLine()) {
                    lyricsData.get(i).setFocus(true);
                    currentItemIndex = i;

                    break;

                } else {
                    lyricsData.get(i).setFocus(false);

                }
            }

            if (lastItemIndex != currentItemIndex) {
                lyricsAdapter.notifyDataSetChanged();
                setListViewToSelectIndex(currentItemIndex);
                lastItemIndex = currentItemIndex;

            }
        }

    };


}
