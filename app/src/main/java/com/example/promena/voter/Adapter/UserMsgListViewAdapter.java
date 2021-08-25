package com.example.promena.voter.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetUserMsgData;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserMsgListViewAdapter extends BaseAdapter
{
    public ArrayList<GetUserMsgData> feesPaidList;
    Activity activity;
    Preferences pref;
    public UserMsgListViewAdapter(Activity activity, ArrayList<GetUserMsgData> feesPaidList) {
        super();
        this.activity = activity;
        this.feesPaidList = feesPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return feesPaidList.size();
    }

    @Override
    public GetUserMsgData getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView Name_tv;
        TextView MsgData_tv;
        TextView time_tv;
        TextView userId_tv;
        ImageView image_iv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        pref = new Preferences(activity);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pref.set(Constants.fragment_position,"2");
        pref.commit();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.getmsglist_adapter, null);
            holder = new ViewHolder();

            holder.Name_tv = convertView.findViewById(R.id.Name_tv);
            holder.MsgData_tv = convertView.findViewById(R.id.MsgData_tv);
            holder.time_tv = convertView.findViewById(R.id.time_tv);
            holder.image_iv = convertView.findViewById(R.id.image_iv);
            holder.userId_tv = convertView.findViewById(R.id.userId_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GetUserMsgData item = feesPaidList.get(position);

        if(item.getUserName()!=null) {
            holder.Name_tv.setText(item.getUserName());
        }
        if(item.getMessage()!=null) {
            holder.MsgData_tv.setText(item.getMessage());
        }

        if(!item.getSentDate().equals(null)) {
            holder.time_tv.setText(item.getSentDate());
        }
        if(!item.getUserId().equals(null)) {
            holder.userId_tv.setText(String.valueOf(item.getUserId()));
        }
        if(!item.getUserImage().equals(null)) {
            Picasso.with(activity).load(Uri.parse(item.getUserImage())).into(holder.image_iv);
            //    holder.image_iv.setImageURI(Uri.parse(item.getUserImage()));
        }else{

        }

        return convertView;
    }


/*
    public void filter(String charText, ArrayList<GetUserMsgData> feesList, String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0)
                {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (ClusterSummaryList wp : feesList) {

                        if(selectedCollegeName == null)
                        {
                            if ((wp.getEmployeeName()!=null && wp.getEmployeeName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataAll()!=null && wp.getDataAll().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataProcess()!=null && wp.getDataProcess().toLowerCase().contains(charText.toLowerCase())) || ( wp.getDataRejected()!=null && wp.getDataRejected().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getEmployeeName()!=null && wp.getEmployeeName().equals(selectedCollegeName)) && ((wp.getEmployeeName()!=null && wp.getEmployeeName().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataAll()!=null && wp.getDataAll().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataProcess()!=null && wp.getDataProcess().toLowerCase().contains(charText.toLowerCase())) || (wp.getDataRejected()!=null && wp.getDataRejected().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
*/
}
