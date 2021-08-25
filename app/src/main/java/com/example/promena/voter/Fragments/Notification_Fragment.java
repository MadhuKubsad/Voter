package com.example.promena.voter.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.promena.voter.Activity.Adapter.Notification_adapter;
import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Notification.Notification_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Notification.Notification_ResponseBody;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Fragment extends Fragment implements View.OnClickListener {

    Preferences pref;
    Notification_adapter adapter;
    RecyclerView rv_notification_list;
    ArrayList<HashMap<String,String>> notification_list =  new ArrayList<HashMap<String, String>>();
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION
    ,Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int REQUEST_PERMISSIONS = 1;
    GetLocation location;
    double lat,lng;
    CustomLoader loader;
    String local_address,date;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.notification_fragment, container, false);

        getid();
        setlistner();
        pref.set(Constants.nn_count,"0");
        pref.commit();
        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
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

                hit_notification();

            }
            else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        }
        else {


            //get the geo location address
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

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



        pref.set(Constants.fragment_position,"0");
        pref.commit();
        if (Utils.isNetworkConnectedMainThred(getContext())){

            hit_notification();

        }
        else {
            Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
        }
        return v;
    }


    private void getid() {
     pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
     rv_notification_list = (RecyclerView)v.findViewById(R.id.notification_data);
    }

    private void setlistner() {

    }
    @Override
    public void onClick(View view) {

    }

    //==========================Notification List =================================================//
    private void hit_notification() {
        loader.show();
        loader.setCancelable(false);
        notification_list.clear();
          String token = pref.get(Constants.sessing_id);
        Call<Notification_Response> notification_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .notification_data(token);
        notification_responseCall.enqueue(new Callback<Notification_Response>() {
            @Override
            public void onResponse(Call<Notification_Response> call, Response<Notification_Response> response) {

                if (response.isSuccessful()){
                    Notification_Response notification_response = response.body();
                    if (notification_response.Code.equals("1")){
                        for (int i=0; i<notification_response.Data.size(); i++){
                            Notification_ResponseBody notification_responseBody = notification_response.Data.get(i);
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("ID",notification_responseBody.ID);
                            map.put("Link",notification_responseBody.Link);
                            map.put("Text",notification_responseBody.Text);
                            map.put("NotificationDate",notification_responseBody.NotificationDate);
                            notification_list.add(map);
                        }
                        adapter = new Notification_adapter(getContext(),notification_list);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                        rv_notification_list.setLayoutManager(gridLayoutManager);
                        rv_notification_list.setAdapter(adapter);
                        loader.cancel();
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), notification_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "Check all data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notification_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

//========================= Fetch All Call Logs ======================================//

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

}
