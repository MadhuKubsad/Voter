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
import com.example.promena.voter.Adapter.Todo_Adapter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Todo_List;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module.Todo_Response;
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

public class To_Do_Fragment extends Fragment implements View.OnClickListener {

    Preferences pref;
    RecyclerView rv_todo_data;
    FloatingActionButton add_new;
    Todo_Adapter adapter;
    CustomLoader loader;
    ArrayList<HashMap<String,String>> to_do_list = new ArrayList<HashMap<String, String>>();
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.to_do_fragment, container, false);

        getid();
        setlistner();


        pref.set(Constants.fragment_position,"0");
        pref.commit();

        if (Utils.isNetworkConnectedMainThred(getActivity())){
            to_do_list.clear();
            hit_todo_api();
        }
        else {
            Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void getid() {
      pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
      rv_todo_data = (RecyclerView)v.findViewById(R.id.todo_data);
      add_new = (FloatingActionButton)v.findViewById(R.id.add_new);
    }

    private void setlistner() {
     add_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
  switch (view.getId()){
      case R.id.add_new:
          ((DashBoard_Activity)getActivity()).displayView(8);
          break;
  }
    }

//============================== To do Api ====================================================//
    private void hit_todo_api() {
        loader.show();
        loader.setCancelable(false);
     String token = pref.get(Constants.sessing_id);
        Call<Todo_Response> todo_responseCall = ApiClient.getClient().create(ApiInterface.class).todo_response(token);
        todo_responseCall.enqueue(new Callback<Todo_Response>() {
            @Override
            public void onResponse(Call<Todo_Response> call, Response<Todo_Response> response) {
                if (response.isSuccessful()){
                    Todo_Response todo_response = response.body();
                    if (todo_response.Code.equals("1")){
                      for (int i =0; i<todo_response.Data.size(); i++){
                          Todo_List todo_list = todo_response.Data.get(i);
                          HashMap<String,String> map = new HashMap<String, String>();
                          map.put("ID",todo_list.ID);
                          map.put("TaskTitle",todo_list.TaskTitle);
                          map.put("Task",todo_list.Task);
                          map.put("TaskDateTime",todo_list.TaskDateTime);
                          map.put("Priority",todo_list.Priority);
                          map.put("UserID",todo_list.UserID);
                          map.put("Status",todo_list.Status);
                          to_do_list.add(map);
                      }
                        adapter = new Todo_Adapter(getContext(), to_do_list);
                        LinearLayoutManager horizontalLayout
                                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv_todo_data.setLayoutManager(horizontalLayout);
                        rv_todo_data.setAdapter(adapter);
                        loader.cancel();
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Todo_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
