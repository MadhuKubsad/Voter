package com.example.promena.voter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.promena.voter.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Dropdown_Adapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> alist;
    public static ArrayList<HashMap<String, String>> checked_Lissta;
    LayoutInflater inflter;



    public Dropdown_Adapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
        this.context = applicationContext;
        this.alist=alist;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.drop_down_adapter, null);


        checked_Lissta = new ArrayList<HashMap<String, String>>();
        String loc_id;
        //  final CheckBox cbname = (CheckBox)convertView.findViewById(R.id.cb_location);
        TextView texname = (TextView)convertView.findViewById(R.id.id_name);
        texname.setText(alist.get(position).get("Text"));

        return convertView;
    }
}
