package com.example.promena.voter.Fragments;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.ChatMainActivity;
import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.DashBoard_Module.DashBoard_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Synchronize_Module.Synchronize_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Synchronize_Module.Synchronize_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.Extras.GetLocation;
import com.example.promena.voter.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard_Fragment extends Fragment implements View.OnClickListener {

    Preferences pref;
    TextView tv_members,tv_today,tv_completed,tv_pending;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION
            ,Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int REQUEST_PERMISSIONS = 1;
    GetLocation location;
    double lat,lng;
    CustomLoader loader;
    String local_address,date;
    int start = 0;
    RelativeLayout rv_view,rv_add,rv_pending,rv_complete;
    FloatingActionButton fa_chatBox;
    int end=0;

    View v;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        int resource = R.layout.dashboard_fragment;
        v = inflater.inflate(resource, container, false);

        getid();
        setlistner();
        pref.set(Constants.fragment_position,"");
        pref.commit();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions((Activity) getContext(),PERMISSIONS,REQUEST_PERMISSIONS);

        }


        location = new GetLocation(getContext());
        lat = location.getLatitude();
        lng = location.getLongitude();
        if (lat==0.0 && lng==0.0){
            if (Utils.isNetworkConnectedMainThred(getContext())){

                hit_Dashboard_api();

            }
            else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        }
        else {



            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                local_address = address;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Calendar c = Calendar.getInstance();
        // Calendar c = Calendar.getInstance();
        //  System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = simpleDateFormat.format(c.getTime());
        hit_synchronize_api();
        hit_Dashboard_api();




        return v;
    }

    private void getid() {

        pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tv_members = (TextView) v.findViewById(R.id.totalmember);
        tv_today = (TextView) v.findViewById(R.id.todaymember);
        tv_completed = (TextView) v.findViewById(R.id.totalcompleted);
        tv_pending = (TextView) v.findViewById(R.id.pending);
        rv_view = (RelativeLayout)v.findViewById(R.id.total);
        rv_add = (RelativeLayout)v.findViewById(R.id.addclients);
        rv_complete = (RelativeLayout)v.findViewById(R.id.taskcomplete);
        rv_pending = (RelativeLayout)v.findViewById(R.id.taskpending);
        fa_chatBox = (FloatingActionButton)v.findViewById(R.id.fa_chatBox);
    }

    private void setlistner() {
        rv_view.setOnClickListener(this);
        rv_add.setOnClickListener(this);
        rv_complete.setOnClickListener(this);
        rv_pending.setOnClickListener(this);
        fa_chatBox.setOnClickListener(this);
    }

    //====================== Dash Board Api ============================================//
    private void hit_Dashboard_api() {
        String token = pref.get(Constants.sessing_id);
       Call<DashBoard_Response> dashBoard_responseCall = ApiClient.getClient().create(ApiInterface.class)
               .dashboard_response(token);
       dashBoard_responseCall.enqueue(new Callback<DashBoard_Response>() {
           @Override
           public void onResponse(Call<DashBoard_Response> call, Response<DashBoard_Response> response) {
               if (response.isSuccessful()){
                   DashBoard_Response dashBoard_response = response.body();
                   if (dashBoard_response.Code.equals("1")){


                       end = Integer.parseInt(dashBoard_response.Data.TotalRegistrationsl);
                       call_count1(start,end);
                       end = Integer.parseInt(dashBoard_response.Data.TodaysRegistrations);
                       call_count2(start,end);
                       end = Integer.parseInt(dashBoard_response.Data.TotalTasksCompleted);
                       call_count3(start,end);
                       end = Integer.parseInt(dashBoard_response.Data.TotalTasksPending);
                       call_count4(start,end);
                   }
                   else {
                       Toast.makeText(getContext(), dashBoard_response.Message, Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<DashBoard_Response> call, Throwable t) {
               Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
           }
       });

    }

    //=================================== Synchronize Api ========================================//


    private void hit_synchronize_api() {
        loader.show();
        loader.setCancelable(false);
        String token = pref.get(Constants.sessing_id);
        Synchronize_Input synchronize_input = new Synchronize_Input();
        synchronize_input.ID = 0;
        synchronize_input.UserID = 0;
        synchronize_input.LogDate = date;
        synchronize_input.Lat = String.valueOf(lat);
        synchronize_input.Long = String.valueOf(lng);
        synchronize_input.Address = local_address;
        Call<Synchronize_Response> synchronize_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .synchronize_response(token,synchronize_input);
        synchronize_responseCall.enqueue(new Callback<Synchronize_Response>() {
            @Override
            public void onResponse(Call<Synchronize_Response> call, Response<Synchronize_Response> response) {
                if (response.isSuccessful()){
                    Synchronize_Response synchronize_response = response.body();
                    if (synchronize_response.Code.equals("1")){
                        loader.cancel();
                        Toast.makeText(getContext(), synchronize_response.Message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), synchronize_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "Data Connection Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Synchronize_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void call_count1(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_members.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
    private void call_count2(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_today.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
    private void call_count3(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_completed.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
    private void call_count4(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_pending.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
//==================== Onclick ===================================//
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.total:
                ((DashBoard_Activity)getContext()).displayView(3);
                break;
            case R.id.addclients:
                pref.set(Constants.e_update,"0");
                pref.commit();
                ((DashBoard_Activity)getContext()).displayView(7);
                break;
            case R.id.taskcomplete:
                ((DashBoard_Activity)getContext()).displayView(9);
                break;
            case R.id.taskpending:
                ((DashBoard_Activity)getContext()).displayView(4);
                break;
            case R.id.fa_chatBox:
                ((DashBoard_Activity)getContext()).displayView(15);

                /*Intent intent1 = new Intent(getContext().getApplicationContext(), ChatMainActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.fa_chatNew:
                ((DashBoard_Activity)getContext()).displayView(16);
                break;
        }
    }
}
