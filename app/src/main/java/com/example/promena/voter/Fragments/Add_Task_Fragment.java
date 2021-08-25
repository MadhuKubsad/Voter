package com.example.promena.voter.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Create_Data_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Task_Fragment extends Fragment implements View.OnClickListener {

    Preferences pref;
    TextView ed_priority;
    Spinner sp_priority;
    TextView ed_date,ed_time;
    EditText ed_task,ed_description;
    CustomLoader loader;
    Button btn_add;
    final Calendar c = Calendar.getInstance(TimeZone.getDefault());
    int myear = c.get(Calendar.YEAR);
    int mmonth = c.get(Calendar.MONTH)+1;
    String usertype;
    int priority_type;
    int mday = c.get(Calendar.DAY_OF_MONTH);
    String DateTime;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.add_task_fragment, container, false);

        getid();
        setlistner();


        pref.set(Constants.fragment_position,"4");
        pref.commit();

        final List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Low");
        spinnerArray.add("Medium");
        spinnerArray.add("High");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                spinnerArray);
        sp_priority.setAdapter(adapter);
        sp_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority_type = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    private void getid() {
    pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    ed_date = (TextView) v.findViewById(R.id.ed_date);
    ed_time = (TextView) v.findViewById(R.id.ed_time);
    ed_priority = (TextView)v.findViewById(R.id.ed_priority);
    btn_add = (Button)v.findViewById(R.id.button_ok);
    sp_priority = (Spinner)v.findViewById(R.id.spinner_data);
    ed_task = (EditText)v.findViewById(R.id.ed_wt_done);
    ed_description = (EditText)v.findViewById(R.id.ed_details);
    }

    private void setlistner() {
       btn_add.setOnClickListener(this);
       ed_date.setOnClickListener(this);
       ed_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.button_ok:
             if (!ed_time.getText().toString().equals("") && !ed_date.getText().toString().equals("")
                     && !sp_priority.getSelectedItem().equals("") && !ed_task.getText().toString().trim().equals("")
             && !ed_description.getText().toString().trim().equals("")){

                 String datetime = ed_date.getText().toString().trim()+" "+ed_time.getText().toString().trim();
                 String pattern;
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                 SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd h:mm a");
                 try {
                     Date d = input.parse(datetime);
                     DateTime = simpleDateFormat.format(d);
                 } catch (ParseException ex) {
                     Log.v("Exception", ex.getLocalizedMessage());
                 }

                 hit_add_todo_api();
             }
             else {
                 Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
             }
             break;
         case R.id.ed_date:
             callcallender1();
             break;
         case R.id.ed_time:
             timedata();
             break;
     }
    }


    private void callcallender1() {
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ed_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


            }
        };
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), datePickerListener,
                mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void timedata(){
        TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {

                String time = hour+":"+minute;
                // String s = "12:18:00";
                DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
                Date d = null;
                try {
                    d = f1.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat f2 = new SimpleDateFormat("h:mm a");
                //  ; // "12:18am"
                ed_time.setText( f2.format(d).toLowerCase());
              //  timeee = f2.format(d).toLowerCase();


            }
        };
        Calendar c = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),timePickerListener,
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE)+5,false);
        timePickerDialog.show();

    }

    //============================ hit to do Api ========================================//
    private void hit_add_todo_api() {
        loader.show();
        loader.setCancelable(false);
        String token = pref.get(Constants.sessing_id);
        Create_Data_Input create_data_input = new Create_Data_Input();
        create_data_input.ID = 0;
        create_data_input.Priority = priority_type;
        create_data_input.Status = false;
        create_data_input.TaskTitle = ed_task.getText().toString().trim();
        create_data_input.Task = ed_description.getText().toString().trim();
        create_data_input.TaskDateTime = DateTime;
        create_data_input.UserID = 0;
        Call<Create_Data_Response> create_data_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .create_data_response(token,create_data_input);
        create_data_responseCall.enqueue(new Callback<Create_Data_Response>() {
            @Override
            public void onResponse(Call<Create_Data_Response> call, Response<Create_Data_Response> response) {
                if (response.isSuccessful()){
                    Create_Data_Response create_data_response = response.body();
                    if (create_data_response.Code.equals("1")){
                        loader.cancel();
                        ((DashBoard_Activity)getActivity()).displayView(4);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(),create_data_response.Message,Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(),"Data Issues",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Create_Data_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
