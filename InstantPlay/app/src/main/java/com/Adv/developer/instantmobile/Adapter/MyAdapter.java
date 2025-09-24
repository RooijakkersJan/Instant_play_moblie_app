package com.Adv.developer.instantmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.Adv.developer.instantmobile.R;
import com.Adv.developer.instantmobile.Model.Tokeninfo;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Tokeninfo> {
    private Context mContext;
    private ArrayList<Tokeninfo> listState;
    private MyAdapter myAdapter;
    private boolean isFromView = false;

    public MyAdapter(Context context, int resource, List<Tokeninfo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<Tokeninfo>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.mTextView.setText(listState.get(position).getToken() + "-" + listState.get(position).getlocinfo() + "-" + listState.get(position).getTokeninfo());
        holder.mTextView.setSelected(true);
        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;
        holder.mCheckBox.setVisibility(View.VISIBLE);

        holder.mCheckBox.setTag(position);


        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    myAdapter.notifyDataSetChanged();

                    if (isChecked) {

                        if (listState.get(position).getTokeninfo().equals("All Locations")) {
                            for (int i = 0; i < listState.size(); i++) {
                                listState.get(i).setSelected(true);
                                myAdapter.notifyDataSetChanged();

                            }

                        }
                        else
                        {
                            for(int i=0;i<listState.size();i++)
                            {
                                if(position==i)
                                {

                                }
                                else
                                {
                                    listState.get(i).setSelected(false);
                                    myAdapter.notifyDataSetChanged();
                                    holder.mCheckBox.setChecked(false);                                }
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    } else {
                        if (listState.get(position).getTokeninfo().equals("All Locations")) {

                            for (int i = 0; i < listState.size(); i++) {
                                listState.get(i).setSelected(false);
                                myAdapter.notifyDataSetChanged();
                            }


                        }
                        else
                        {

                        }
                        myAdapter.notifyDataSetChanged();
                        if (!listState.get(position).getTokeninfo().equals("All Locations")) {
                            for (int i = 0; i < listState.size(); i++) {
                                if (listState.get(i).getTokeninfo().equals("All Locations")) {
                                    listState.get(i).setSelected(false);
                                    myAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                        myAdapter.notifyDataSetChanged();

                    }

                }

            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}