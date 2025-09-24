package com.Adv.developer.instantmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Adv.developer.instantmobile.Activity.HomeActivity;
import com.Adv.developer.instantmobile.R;
import com.Adv.developer.instantmobile.Model.Songs;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    ArrayList<Songs> SongsModal;
    int checked=0;
    Context context;
    Typeface font;
    Typeface fontBold;



    public SongAdapter(Context context, ArrayList<Songs> SongsModal) {
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

        public TextView textview1,textview2,textview3,textview4;
        public ImageView img1,img2,img3;
        public CheckBox chk;
        RelativeLayout rlBackground;
        LinearLayout l1;

    }

   /* @Override
    public boolean isEnabled(int position) {
        return false;
    }*/




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            // get layout from list_item.xml ( Defined Below )
            convertView = inflater.inflate(R.layout.song_list_item, null);

            holder = new ViewHolder();
            holder.textview1 = (TextView) convertView.findViewById(R.id.songtitle);
            holder.textview3=(TextView) convertView.findViewById(R.id.listsongduration);
            holder.textview2 = (TextView) convertView.findViewById(R.id.songArtist);
            holder.img1=(ImageView) convertView.findViewById(R.id.music_icon);
            holder.img2=(ImageView) convertView.findViewById(R.id.playinlistselection);
            holder.img3=(ImageView) convertView.findViewById(R.id.stopinstant);

            holder.chk=(CheckBox) convertView.findViewById(R.id.repeatchk);

            holder.rlBackground = (RelativeLayout) convertView.findViewById(R.id.background);
            holder.l1=(LinearLayout) convertView.findViewById(R.id.songlayouttitle);

            holder.textview1.setTypeface(fontBold);
            holder.textview2.setTypeface(font);
            holder.textview3.setTypeface(font);
            holder.textview1.setTextColor(Color.BLACK);
            holder.textview2.setTextColor(Color.BLACK);
            holder.textview3.setTextColor(Color.BLACK);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Songs modal = (Songs)getItem(position);

        holder.textview1.setText(modal.getTitle());
        //holder.textview1.setPaintFlags( holder.textview1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.textview2.setText(modal.getAr_Name());
        
        if(modal.getPlaylistCat().equals("Ads"))
        {
            holder.img3.setVisibility(View.GONE);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT,0.75f);
            holder.l1.setLayoutParams(params2);

        }
        int count = (modal.getSerialNo()+"").length();
        if(count==1)
        {
            holder.textview3.setText("S.no -"+"00"+String.valueOf(modal.getSerialNo()));

        }
        else if(count==2)
        {
            holder.textview3.setText("S.no -"+"0"+String.valueOf(modal.getSerialNo()));

        }
        else
        {
            holder.textview3.setText("S.no -"+String.valueOf(modal.getSerialNo()));

        }
        holder.chk.setChecked(SongsModal.get(position).getSelected());
        holder.chk.setTag(position);
        holder.chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SongsModal.get(position).getSelected()){
                    SongsModal.get(position).setSelected(false);
                    SongsModal.get(position).setCheckValue(0);
                }
                else {

                    for (int i=0;i<SongsModal.size();i++)
                    {
                        if(i==position)
                        {
                            SongsModal.get(position).setSelected(true);
                            SongsModal.get(position).setCheckValue(1);
                            holder.chk.setChecked(SongsModal.get(position).getSelected());
                            notifyDataSetChanged();
                           // Toast.makeText(context,"equalo",Toast.LENGTH_LONG).show();

                           // break;
                        }
                        else
                        {
                            if(SongsModal.get(i).getSelected()){
                                SongsModal.get(i).setSelected(false);
                                SongsModal.get(i).setCheckValue(0);
                                holder.chk.setChecked(SongsModal.get(i).getSelected());
                                notifyDataSetChanged();
                            }
                        }
                    }

                }

            }
        });



        holder.rlBackground.setBackgroundColor(Color.TRANSPARENT);

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ((HomeActivity)context).instantplaysong(position,SongsModal.get(position).getCheckvalue(),"play");

            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ((HomeActivity)context).instantplaysong(position,SongsModal.get(position).getCheckvalue(),"Stop");

            }
        });

        holder.textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

               // ((HomeActivity)context).previewsong(position);

            }
        });
        return convertView;
    }






}
