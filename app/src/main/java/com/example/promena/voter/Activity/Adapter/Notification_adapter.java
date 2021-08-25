package com.example.promena.voter.Activity.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.MyViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> alist;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_name,tv_date;
        RelativeLayout rv_block;



        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_notification);
            tv_date = (TextView)itemView.findViewById(R.id.datetime);
            rv_block = (RelativeLayout)itemView.findViewById(R.id.notification_block);

        }
    }

    public Notification_adapter(Context context, ArrayList<HashMap<String,String>> alist){
        this.context = context;
        this.alist = alist;
    }

    @Override
    public Notification_adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_adapter, viewGroup, false);

        return new Notification_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Notification_adapter.MyViewHolder myViewHolder, final int i) {

    myViewHolder.tv_name.setText(alist.get(i).get("Text"));
        String datetime = alist.get(i).get("NotificationDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String formattedTime = output.format(d);
        myViewHolder.tv_date.setText(formattedTime);

        myViewHolder.rv_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.pop_details_todo);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
                TextView tv_date,tv_details;
                tv_date = (TextView) dialog.findViewById(R.id.date_time);
                tv_details = (TextView)dialog.findViewById(R.id.details);
                String datetime = alist.get(i).get("NotificationDate");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedTime = output.format(d);
                tv_date.setText("Date And Time: "+formattedTime);
                tv_details.setText(alist.get(i).get("Text"));
            }
        });

    }
    @Override
    public int getItemCount() {
        return alist.size();
    }
}
