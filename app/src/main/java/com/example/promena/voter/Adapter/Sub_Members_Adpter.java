package com.example.promena.voter.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Sub_Members_Adpter extends RecyclerView.Adapter<Sub_Members_Adpter.MyViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> alist;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_details,tv_date,tv_members;
        RoundedImageView rm_pic;
        RelativeLayout rv_banner;

        public MyViewHolder(View itemView) {
            super(itemView);

            rm_pic = (RoundedImageView) itemView.findViewById(R.id.user_pic);
            tv_date =  (TextView)itemView.findViewById(R.id.date_time);
            tv_details = (TextView)itemView.findViewById(R.id.user_name);
            rv_banner = (RelativeLayout)itemView.findViewById(R.id.click_data);
            tv_members = (TextView)itemView.findViewById(R.id.members);

        }
    }

    public Sub_Members_Adpter(Context context, ArrayList<HashMap<String,String>> alist){
        this.context = context;
        this.alist = alist;
    }

    @Override
    public Sub_Members_Adpter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.our_member_adapter, viewGroup, false);

        return new Sub_Members_Adpter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Sub_Members_Adpter.MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_details.setText(alist.get(i).get("Name"));
        String image = alist.get(i).get("Photo");
        String countt = alist.get(i).get("Count");
        myViewHolder.tv_members.setVisibility(View.GONE);
        String datetime = alist.get(i).get("RegisterDateTime");
        String DateTime;

        if (!TextUtils.isEmpty(image)){
            Picasso.with(context).load(alist.get(i).get("Photo")).into(myViewHolder.rm_pic);

        }



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        myViewHolder.tv_date.setText(formattedTime);

        myViewHolder.rv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences pref = new Preferences(context);
                pref.set(Constants.se_ID,alist.get(i).get("ID"));
                pref.set(Constants.se_Name,alist.get(i).get("Name"));
                pref.set(Constants.se_Name,alist.get(i).get("Name"));
                pref.set(Constants.se_Gender,alist.get(i).get("Gender"));
                pref.set(Constants.se_EmailID,alist.get(i).get("EmailID"));
                pref.set(Constants.se_Mobile,alist.get(i).get("Mobile"));
                pref.set(Constants.se_Photo,alist.get(i).get("Photo"));
                pref.set(Constants.se_VoterID,alist.get(i).get("VoterID"));
                pref.set(Constants.se_date,alist.get(i).get("RegisterDateTime"));
                pref.set(Constants.Booth_name,alist.get(i).get("BoothNo"));
                pref.commit();

                ((DashBoard_Activity)context).displayView(13);
            }
        });


    }
    @Override
    public int getItemCount() {
        return alist.size();
    }
}

