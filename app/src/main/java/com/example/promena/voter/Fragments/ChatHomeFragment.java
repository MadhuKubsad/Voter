package com.example.promena.voter.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.ChatActivity;
import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Adapter.UserMsgListViewAdapter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetUserMessages;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetUserMsgData;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.DashBoard_Module.DashBoard_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHomeFragment extends Fragment {


    /**
     * Returns a new instance of this fragment.
     */
    public static ChatHomeFragment newInstance() {
        ChatHomeFragment fragment = new ChatHomeFragment();
        return fragment;
    }
    ListView list;
    FloatingActionButton fa_chatNew;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    String[] lastArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    String[] timeArray= {"3:12","4:10","5:20","6:10",
            "7:20","8:10","7:20","4:30"};

    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title,adapter,adapter2;
    HashMap<String, String> map1, map2;

    Preferences pref;
    UserMsgListViewAdapter userMsgListViewAdapter;
    private ArrayList<GetUserMsgData> getUserMsgDataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_home, container, false);
        pref = new Preferences(getContext());

        list = (ListView) rootView.findViewById(R.id.listView1);
        fa_chatNew = (FloatingActionButton) rootView.findViewById(R.id.fa_chatNew);
       // setlistner();

        getUserMsgDataList =new ArrayList<GetUserMsgData>();
        //  userMsgListViewAdapter = new UserMsgListViewAdapter(getActivity(), getUserMsgDataList);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pref.set(Constants.fragment_position,"2");
        pref.commit();

        hit_Dashboard_api();


      /*  mylist_title = new ArrayList<HashMap<String, String>>();
        map1 = new HashMap<String, String>();

        // map1.put("slno", "Lead Id");
        map1.put("one", " Pooja");
        map1.put("two", " Last Message");
        map1.put("three","3:40PM");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.activity_listview,
                    new String[]{ "one", "two","three"}, new int[]{
                    R.id.label, R.id.text, R.id.time});
            list.setAdapter(adapter_title);
        } catch (Exception e) {

        }*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object o = list.getItemAtPosition(position);
                Log.i("Tag","o=="+o.toString());
                Log.i("Tag","position=="+position);
                String UserId = ((TextView) view.findViewById(R.id.userId_tv)).getText().toString();
                Log.e("Tag","UserId=="+UserId);

                Intent i= new Intent(getContext(), ChatActivity.class);
                i.putExtra("userId",UserId);
                startActivity(i);
            }

        });

        fa_chatNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoard_Activity)getContext()).displayView(16);
            }
        });

        return rootView;
    }

    private void hit_Dashboard_api() {
        String token = pref.get(Constants.sessing_id);
        Call<GetUserMessages> dashBoard_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .getUsersMessage(token);
        Log.e("tag","token="+token);
        //Log.e("TAG", "request getusermsg : " + new Gson().toJson(dashBoard_responseCall));

        dashBoard_responseCall.enqueue(new Callback<GetUserMessages>() {
            @Override
            public void onResponse(Call<GetUserMessages> call, Response<GetUserMessages> response) {

                if (response.isSuccessful()){
                    Log.e("TAG", "response getusermsg: " + new Gson().toJson(response));

                    GetUserMessages dashBoard_response = response.body();
                    if (dashBoard_response.getCode().equals(1)){

                        Log.e("tag","response="+dashBoard_response.message);
                        String LastMsg = null,userName = null,userImage= null,sentDate= null;
                        Integer userId = null,senderUserId= null,countMsg= null,receiverUserId= null;
                        Boolean sentStatus = null;
                        GetUserMsgData getUserMsgData = null;
                        for (int i=0;i<dashBoard_response.data.size();i++){
                            LastMsg=dashBoard_response.data.get(i).getMessage();
                            userName=dashBoard_response.data.get(i).getUserName();
                            userImage=dashBoard_response.data.get(i).getUserImage();
                            countMsg=dashBoard_response.data.get(i).getCount();
                            userId=dashBoard_response.data.get(i).getUserId();
                            senderUserId=dashBoard_response.data.get(i).getSenderUserId();
                            receiverUserId=dashBoard_response.data.get(i).getReceiverUserId();
                            sentStatus=dashBoard_response.data.get(i).getSentStatus();
                            sentDate=dashBoard_response.data.get(i).getSentDate();
                            if(userImage==null){
                                userImage="";
                            }
                            getUserMsgData = new GetUserMsgData(LastMsg,userId,userName,userImage,senderUserId,receiverUserId,sentStatus,countMsg,sentDate);
                            getUserMsgDataList.add(getUserMsgData);
                        }

                        userMsgListViewAdapter = new UserMsgListViewAdapter(getActivity(), getUserMsgDataList);
                        list.setAdapter(userMsgListViewAdapter);

                    }
                    else {
                        Log.e("tag","msg=="+dashBoard_response.message);
                        Toast.makeText(getContext(), dashBoard_response.message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.e("tag","msg=="+ response.message());

                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserMessages> call, Throwable t) {
                Log.e("tag","msg=="+ t.toString());

                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}