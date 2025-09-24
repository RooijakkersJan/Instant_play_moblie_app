package com.Adv.developer.instantmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Adv.developer.instantmobile.R;
import com.Adv.developer.instantmobile.Model.SchdPlaylist;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {

    ArrayList<SchdPlaylist> SongsModal;
    Context context;
    Typeface font;
    Typeface fontBold;



    public PlaylistAdapter(Context context, ArrayList<SchdPlaylist> SongsModal) {
        this.context = context;
        this.SongsModal = SongsModal;
        fontBold = Typeface.createFromAsset(this.context.getAssets(),this.context.getString(R.string.century_font_bold));
        font = Typeface.createFromAsset(this.context.getAssets(),this.context.getString(R.string.century_font));

    }

    @Override
    public int getCount() {
        return SongsModal.size();
    }

    @Override
    public Object getItem(int position) {
        return SongsModal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder {

        public TextView textview1,textview2;
        public ImageView img1;
        RelativeLayout rlBackground;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            // get layout from list_item.xml ( Defined Below )
            convertView = inflater.inflate(R.layout.playlist_layout, null);

            holder = new ViewHolder();
            holder.textview1 = (TextView) convertView.findViewById(R.id.playlistitle);
            holder.textview2 = (TextView) convertView.findViewById(R.id.info);

            holder.img1=(ImageView) convertView.findViewById(R.id.music_icon);

            holder.rlBackground = (RelativeLayout) convertView.findViewById(R.id.background);

            holder.textview1.setTypeface(fontBold);
            holder.textview1.setTextColor(Color.BLACK);
            holder.textview2.setTextColor(Color.BLACK);

            convertView.setTag(holder);

        } else {
            holder = (PlaylistAdapter.ViewHolder) convertView.getTag();
        }

        final SchdPlaylist modal = (SchdPlaylist) getItem(position);

        holder.textview1.setText(modal.getPlaylistname());
        holder.textview2.setText("StartTime - "+modal.getStartdate()+"      "+"EndTime - "+modal.getEnddate());

        holder.rlBackground.setBackgroundColor(Color.TRANSPARENT);
        return convertView;
    }




}
