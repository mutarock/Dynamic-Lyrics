package com.kkbox.raytseng.dynamiclyrics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kkbox.raytseng.dynamiclyrics.model.Lyrics;

import java.util.ArrayList;

/**
 * Created by raytseng on 2/4/15.
 */
public class LyricsAdapter extends BaseAdapter {

    private ListView mListView;
    private ArrayList<Lyrics> mLyricsList;
    private LayoutInflater mInflater = null;


    public LyricsAdapter(Context context, ArrayList<Lyrics> lyrics) {
        this.mInflater = LayoutInflater.from(context);
        this.mLyricsList = lyrics;
    }

    @Override
    public int getCount() {
        return mLyricsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    // ViewHolder for list view adapter
    public class ViewHolder {
        TextView tvLyrics;

        public ViewHolder() {

        }

    }


}
