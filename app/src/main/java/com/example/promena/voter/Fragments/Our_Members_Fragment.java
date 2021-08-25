package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Adapter.Members_Adapter;
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

public class Our_Members_Fragment extends Fragment implements View.OnClickListener {
    Preferences pref;
    Members_Adapter adapter;
    RecyclerView rv_members;
    CustomLoader loader;
    FloatingActionButton add_new;
    ArrayList<HashMap<String,String>> members_data = new ArrayList<HashMap<String, String>>();
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.our_members_fragment, container, false);
        getid();
        setlistner();


        pref.set(Constants.fragment_position,"0");
        pref.commit();

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
     rv_members = (RecyclerView)v.findViewById(R.id.members_data);
     add_new = (FloatingActionButton)v.findViewById(R.id.add_new);
    }

    private void setlistner() {
       add_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.add_new:
              pref.set(Constants.e_update,"0");
              pref.commit();
              ((DashBoard_Activity)getActivity()).displayView(7);
              break;
      }
    }
//================ call members data ============================================//
    private void Hit_membersApi() {
        members_data.clear();
        loader.show();
        loader.setCancelable(false);
        String token = pref.get(Constants.sessing_id);
        Call<Members_Response> members_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .members_response(token);
        members_responseCall.enqueue(new Callback<Members_Response>() {
            @Override
            public void onResponse(Call<Members_Response> call, Response<Members_Response> response) {
                if (response.isSuccessful()){
                    Members_Response members_response = response.body();
                    if (members_response.Code.equals("1")){
                        for (int i=0; i<members_response.Data.size(); i++){
                            Members_List members_list = members_response.Data.get(i);
                           int count = members_list.SubEnrollments.size();
                            HashMap<String,String>map = new HashMap<String, String>();
                            map.put("ID",members_list.ID);
                            map.put("AssemblyID",members_list.AssemblyID);
                            map.put("WardID",members_list.WardID);
                            //map.put("EnrollmentID",members_list.EnrollmentID);
                            map.put("Name",members_list.Name);
                            map.put("Gender",members_list.Gender);
                            map.put("EmailID",members_list.EmailID);
                            map.put("Mobile",members_list.Mobile);
                            map.put("Photo",members_list.Photo);
                            map.put("VoterID",members_list.VoterID);
                            map.put("UserID",members_list.UserID);
                            map.put("Address",members_list.Address);
                            map.put("BoothNo",members_list.BoothNo);
                            map.put("RegisterDateTime",members_list.RegisterDateTime);
                            map.put("Count", String.valueOf(count));
                           members_data.add(map);
                        }
                        adapter = new Members_Adapter(getContext(), members_data);
                        LinearLayoutManager horizontalLayout
                                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_members.setLayoutManager(horizontalLayout);
                        rv_members.setAdapter(adapter);
                        loader.cancel();
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "Check data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Members_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
