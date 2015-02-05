package com.kkbox.raytseng.dynamiclyrics.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kkbox.raytseng.dynamiclyrics.R;
import com.kkbox.raytseng.dynamiclyrics.model.Lyrics;

import java.util.ArrayList;

/**
 * Created by raytseng on 2/4/15.
 */
public class LyricsAdapter extends BaseAdapter {

    private ArrayList<Lyrics> mLyricsList;
    private LayoutInflater mInflater = null;
    private Context mContext;


    public LyricsAdapter(Context context, ArrayList<Lyrics> lyrics) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLyricsList = lyrics;
    }

    @Override
    public int getCount() {
        return mLyricsList.size();

    }

    @Override
    public Object getItem(int position) {
        return mLyricsList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lyrics_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvLyrics = (TextView) convertView.findViewById(R.id.lyrics_tv);
            convertView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tvLyrics.setText(mLyricsList.get(position).getLyric());

        if (mLyricsList.get(position).isFocus()) {
            holder.tvLyrics.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else {
            holder.tvLyrics.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        if (position == 0) {
            holder.tvLyrics.setTextColor(mContext.getResources().getColor(R.color.red));
        }

        return convertView;
    }


    // ViewHolder for list view adapter
    public class ViewHolder {
        TextView tvLyrics;

        public ViewHolder() {}

    }


}
