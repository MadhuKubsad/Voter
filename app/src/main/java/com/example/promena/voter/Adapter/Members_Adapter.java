package com.example.promena.voter.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.promena.voter.Activity.Adapter.Notification_adapter;
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

public class Members_Adapter extends RecyclerView.Adapter<Members_Adapter.MyViewHolder> {

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

    public Members_Adapter(Context context, ArrayList<HashMap<String,String>> alist){
        this.context = context;
        this.alist = alist;
    }

    @Override
    public Members_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.our_member_adapter, viewGroup, false);

        return new Members_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Members_Adapter.MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_details.setText(alist.get(i).get("Name"));
          String image = alist.get(i).get("Photo");
        String countt = alist.get(i).get("Count");
        if (!countt.equals("0")){
            myViewHolder.tv_members.setVisibility(View.VISIBLE);
            if (countt.equals("1")){
                myViewHolder.tv_members.setText(countt+" Client added");
            }
            else {
                myViewHolder.tv_members.setText(countt+" Clients added");
            }

        }
        else {
            myViewHolder.tv_members.setVisibility(View.GONE);
        }

          if (!TextUtils.isEmpty(image)){
              Picasso.with(context).load(alist.get(i).get("Photo")).into(myViewHolder.rm_pic);

          }

        String datetime = alist.get(i).get("RegisterDateTime");
        String DateTime;



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
            public void onClick(View view) {
                Preferences pref = new Preferences(context);
                pref.set(Constants.e_ID,alist.get(i).get("ID"));
                final String count = alist.get(i).get("Count");
                pref.set(Constants.e_Name,alist.get(i).get("Name"));
                pref.set(Constants.e_count,count);
                pref.set(Constants.e_AssemblyID,alist.get(i).get("AssemblyID"));
                pref.set(Constants.e_WardID,alist.get(i).get("WardID"));
                pref.set(Constants.e_Name,alist.get(i).get("Name"));
                pref.set(Constants.e_Gender,alist.get(i).get("Gender"));
                pref.set(Constants.e_EmailID,alist.get(i).get("EmailID"));
                pref.set(Constants.e_Mobile,alist.get(i).get("Mobile"));
                pref.set(Constants.e_Photo,alist.get(i).get("Photo"));
                pref.set(Constants.e_address,alist.get(i).get("Address"));
                pref.set(Constants.e_VoterID,alist.get(i).get("VoterID"));
                pref.set(Constants.e_Date,alist.get(i).get("RegisterDateTime"));
                pref.set(Constants.e_userid,alist.get(i).get("UserID"));
                pref.set(Constants.Booth_name,alist.get(i).get("BoothNo"));
                pref.commit();

                ((DashBoard_Activity)context).displayView(12);

          /*  Dialog dialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.details_screen);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);*/

             /*   AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");
                builder.setMessage("Add Sub Enrollment for the member ?");
                builder.setCancelable(true);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                if (!count.equals("0")){

                    builder.setNegativeButton("See All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });
                }


                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return alist.size();
    }
}
