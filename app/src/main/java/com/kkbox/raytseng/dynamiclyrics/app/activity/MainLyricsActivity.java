package com.kkbox.raytseng.dynamiclyrics.app.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class MainLyricsActivity extends ActionBarActivity {

    private MediaPlayer mediaPlayer;
    private Uri fileUri;
    private ListView lyricsListView;
    private LyricsAdapter lyricsAdapter;
    private ArrayList<Lyrics> lyricsData;

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

        // TODO - Get lyrics data from local assets
        String totalLyrics = loadJSONFromAsset();
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(totalLyrics);
            JSONArray array = jsonObj.getJSONArray("lyrics");

            for(int index = 0 ; index < array.length() ; index++) {
                JSONObject singleLine = array.getJSONObject(index);

                String content = singleLine.getString("content");
                String startTime = singleLine.getString("startTime");
                String endTime = singleLine.getString("endTime");


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
                // TODO - seek to select position


            }
        });

    }


    private void initPlayer() {
        mediaPlayer = new MediaPlayer();

        fileUri = Uri.parse(getString(R.string.not_your_kind_people));

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, fileUri);
            mediaPlayer.setLooping(true);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                }
            });

            mediaPlayer.prepareAsync();

        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (IllegalArgumentException ile) {
            ile.printStackTrace();

        }

    }

    public String loadJSONFromAsset() {
        String json = "";

        try {
            InputStream is = getAssets().open("lyrics.json");
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


}
