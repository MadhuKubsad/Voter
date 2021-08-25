package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Adapter.Members_Adapter;
import com.example.promena.voter.Adapter.Sub_Members_Adpter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Enrollment_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Members_List;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Members_List;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Members_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_Enrollment_List_Fragment extends Fragment implements View.OnClickListener {
    Preferences pref;
    Sub_Members_Adpter adapter;
    RecyclerView rv_members;
    CustomLoader loader;
    TextView tv_name;
    FloatingActionButton add_new;
    ArrayList<HashMap<String,String>> submembers_data = new ArrayList<HashMap<String, String>>();
    String id;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.sub_enrollment_data_fragment, container, false);
        getid();
        setlistner();


        pref.set(Constants.fragment_position,"3");
        pref.commit();

        tv_name.setText("Clients Under The "+pref.get(Constants.e_Name));
        id = pref.get(Constants.e_ID);

        if (Utils.isNetworkConnectedMainThred(getContext())){
            Hit_membersApi();
        }
        else {
            Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
        }
        return v;
    }


    private void getid() {
        pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        rv_members = (RecyclerView) v.findViewById(R.id.sub_enrollment);
        tv_name = (TextView)v.findViewById(R.id.name);
        add_new = (FloatingActionButton)v.findViewById(R.id.add_new);
    }

    private void setlistner() {
        add_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_new:
                ((DashBoard_Activity)getActivity()).displayView(7);
                break;
        }
    }
    //================ call members data ============================================//
    private void Hit_membersApi() {

        submembers_data.clear();
        loader.show();
        loader.setCancelable(false);
        String url = "api/SubEnrollments/GetSubEnrollmentList/"+id;
        String token = pref.get(Constants.sessing_id);
       Call<Sub_Enrollment_Response> sub_enrollment_responseCall = ApiClient.getClient().create(ApiInterface.class)
               .sub_enrollment_response(url,token);
       sub_enrollment_responseCall.enqueue(new Callback<Sub_Enrollment_Response>() {
           @Override
           public void onResponse(Call<Sub_Enrollment_Response> call, Response<Sub_Enrollment_Response> response) {
               if (response.isSuccessful()){
                   Sub_Enrollment_Response sub_enrollment_response = response.body();
                   if (sub_enrollment_response.Code.equals("1")){
                       for (int i=0; i<sub_enrollment_response.Data.size(); i++){
                           Sub_Members_List sub_members_list = sub_enrollment_response.Data.get(i);
                           HashMap<String,String> map = new HashMap<String, String>();
                           map.put("ID", String.valueOf(sub_members_list.ID));
                           //map.put("EnrollmentID",members_list.EnrollmentID);
                           map.put("Name",sub_members_list.Name);
                           map.put("Gender",sub_members_list.Gender);
                           map.put("EmailID",sub_members_list.EmailID);
                           map.put("Mobile",sub_members_list.Mobile);
                           map.put("Photo",sub_members_list.Photo);
                           map.put("VoterID",sub_members_list.VoterID);
                           map.put("UserID",sub_members_list.UserID);
                           map.put("BoothNo",sub_members_list.BoothNo);
                           map.put("RegisterDateTime",sub_members_list.RegisterDateTime);
                           map.put("Count", "0");
                           submembers_data.add(map);
                       }
                       adapter = new Sub_Members_Adpter(getContext(), submembers_data);
                       LinearLayoutManager horizontalLayout
                               = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                       rv_members.setLayoutManager(horizontalLayout);
                       rv_members.setAdapter(adapter);
                       loader.cancel();
                       loader.cancel();
                   }
                   else {
                       loader.cancel();
                       Toast.makeText(getContext(), sub_enrollment_response.Message, Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   loader.cancel();
                   Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Sub_Enrollment_Response> call, Throwable t) {

               loader.cancel();
               Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
           }
       });
    }
}