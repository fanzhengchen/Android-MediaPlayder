package com.example.administrator.mediaplayer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/4.
 */
public class Adapter extends BaseAdapter {

    private ArrayList<String> menu;

    public Adapter(ArrayList<String> menu) {
        this.menu = menu;
    }

    @Override
    public int getCount() {
        return null == menu ? 0 : menu.size();
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
        View view = View.inflate(parent.getContext(), R.layout.cell, null);
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        TextView textView = (TextView) view.findViewById(R.id.text_info);
        textView.setText(menu.get(position));
        return view;
    }
}
