package com.example.promena.voter.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Adapter.Notification_adapter;
import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Todo_Adapter  extends RecyclerView.Adapter<Todo_Adapter.MyViewHolder> {

    Context context;

    ArrayList<HashMap<String, String>> alist;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_title,tv_date,tv_details;
        CheckBox chk_box;
        ImageView iv_cmplt,iv_priority;



        public MyViewHolder(View itemView) {
            super(itemView);

            chk_box = (CheckBox) itemView.findViewById(R.id.check_box);
            tv_date =  (TextView)itemView.findViewById(R.id.date_time);
            tv_title = (TextView)itemView.findViewById(R.id.todo_details);
            tv_details = (TextView)itemView.findViewById(R.id.tiltle);
            iv_cmplt = (ImageView)itemView.findViewById(R.id.complete);
            iv_priority = (ImageView)itemView.findViewById(R.id.priority_img);

        }
    }

    public Todo_Adapter(Context context, ArrayList<HashMap<String,String>> alist){
        this.context = context;
        this.alist = alist;
    }

    @Override
    public Todo_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.to_do_adapter, viewGroup, false);

        return new Todo_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Todo_Adapter.MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_details.setText(alist.get(i).get("Task"));
        myViewHolder.tv_title.setText(alist.get(i).get("TaskTitle"));
       String datetime = alist.get(i).get("TaskDateTime");
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

        String status  = alist.get(i).get("Status");

        if (status.equals("false")){
            myViewHolder.chk_box.setVisibility(View.VISIBLE );
            myViewHolder.iv_cmplt.setVisibility(View.GONE);
        }
        else if(status.equals("true")){

            myViewHolder.chk_box.setVisibility(View.GONE);
            myViewHolder.iv_cmplt.setVisibility(View.VISIBLE);
        }
        else {
            myViewHolder.chk_box.setVisibility(View.VISIBLE );
            myViewHolder.iv_cmplt.setVisibility(View.GONE);
        }
        String priority = alist.get(i).get("Priority");
        if (priority.equals("0")) {
            myViewHolder.iv_priority.setVisibility(View.GONE);
        }
        else if (priority.equals("1")){
            myViewHolder.iv_priority.setVisibility(View.VISIBLE);
            myViewHolder.iv_priority.setImageResource(R.drawable.medium_priority);
        }
        else if (priority.equals("2")){
            myViewHolder.iv_priority.setVisibility(View.VISIBLE);
            myViewHolder.iv_priority.setImageResource(R.drawable.high_priority);
        }

        myViewHolder.chk_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String task,date,topic;
                String id = alist.get(i).get("ID");
                task = alist.get(i).get("Task");
                date = alist.get(i).get("TaskDateTime");
                topic = alist.get(i).get("TaskTitle");
                alist.remove(i);
                hit_update_api(task,date,id,topic);
            }
        });

        //====================On TExt click====================================================//
          myViewHolder.tv_details.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  final Dialog dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                  dialog.setCancelable(true);
                  dialog.setContentView(R.layout.pop_details_todo);
                  dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                  dialog.show();
                  TextView tv_date,tv_details;
                  tv_date = (TextView) dialog.findViewById(R.id.date_time);
                  tv_details = (TextView)dialog.findViewById(R.id.details);
                  String datetime = alist.get(i).get("TaskDateTime");
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
                  tv_details.setText(alist.get(i).get("Task"));
              }
          });
    }
//========================Update Api ==============================================//
    private void hit_update_api(String task, String date, String id, String topic) {
       Preferences pref = new Preferences(context);
        String token = pref.get(Constants.sessing_id);
        Create_Data_Input create_data_input = new Create_Data_Input();
        create_data_input.ID = Integer.parseInt(id);
        create_data_input.Priority = 0;
        create_data_input.TaskTitle = topic;
        create_data_input.Status = true;
        create_data_input.Task = task;
        create_data_input.TaskDateTime = date;
        create_data_input.UserID = 0;
        Call<Create_Data_Response> create_data_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .create_data_responsee(token,create_data_input);
        create_data_responseCall.enqueue(new Callback<Create_Data_Response>() {
            @Override
            public void onResponse(Call<Create_Data_Response> call, Response<Create_Data_Response> response) {
                if (response.isSuccessful()){
                    Create_Data_Response create_data_response = response.body();
                    if (create_data_response.Code.equals("1")){
                        ((DashBoard_Activity)context).displayView(4);

                    }
                    else {
                        Toast.makeText(context,create_data_response.Message,Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(context,"Data Issues",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Create_Data_Response> call, Throwable t) {
                Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return alist.size();
    }
}
