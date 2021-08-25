package com.example.promena.voter.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Feedback_Module.Fedback_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Feedback_Module.Feedback_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback_Fragment extends Fragment implements View.OnClickListener {

    EditText ed_subject,ed_feedback,ed_voterid,ed_mobile;
    Button btn_submit;
    Preferences pref;
    String date;
    CustomLoader loader;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.feedback_fragment, container, false);

        getid();
        setlistner();
        pref.set(Constants.fragment_position,"0");
        pref.commit();
        Calendar c = Calendar.getInstance();
       // Calendar c = Calendar.getInstance();
      //  System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        date = simpleDateFormat.format(c.getTime());

// Now formattedDate have current date/time
       // date = simpleDateFormat.format(formattedDate);
        return v;
    }

    private void getid() {
      pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
      ed_subject = (EditText)v.findViewById(R.id.ed_subject);
      ed_feedback = (EditText)v.findViewById(R.id.ed_message);
      btn_submit = (Button)v.findViewById(R.id.btn_submit);
      ed_voterid = (EditText)v.findViewById(R.id.ed_voterid);
      ed_mobile = (EditText)v.findViewById(R.id.ed_mobile);
    }

    private void setlistner() {
    btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.btn_submit:
             if (!ed_subject.getText().toString().trim().equals("") && !ed_feedback.getText().toString().trim().equals("")){
                 if (ed_voterid.getText().toString().trim().equals("") && ed_mobile.getText().toString().trim().equals("")){
                     ed_voterid.setText("");
                     ed_mobile.setText("");
                     Feedback_Api();
                 }
                 else {
                     Feedback_Api();
                 }

             }
             else {
                 Toast.makeText(getContext(), "Subject And Feddback Are Mandatory", Toast.LENGTH_SHORT).show();
             }
             break;
     }
    }
//========================= Feddback Api ===========================================//
    private void Feedback_Api() {
        loader.show();
        loader.setCancelable(false);
        String token = pref.get(Constants.sessing_id);
        Fedback_Input fedback_input = new Fedback_Input();
        fedback_input.ID = 0;
        fedback_input.VoterID = ed_voterid.getText().toString().trim();
        fedback_input.UserID  = 0;
        fedback_input.Mobile = ed_mobile.getText().toString().trim();
        fedback_input.Subject = ed_subject.getText().toString().trim();
        fedback_input.FeedBack1 = ed_feedback.getText().toString().trim();
        fedback_input.Name = pref.get(Constants.username);
        fedback_input.FeedBackDateTime = date;
        Call<Feedback_Response> feedback_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .feedback_response(token,fedback_input);
        feedback_responseCall.enqueue(new Callback<Feedback_Response>() {
            @Override
            public void onResponse(Call<Feedback_Response> call, Response<Feedback_Response> response) {
                if (response.isSuccessful()){
                    Feedback_Response feedback_response = response.body();
                    if (feedback_response.Code.equals("1")){
                        loader.cancel();
                        Intent intent = new Intent(getContext(),DashBoard_Activity.class);
                        startActivity(intent);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), feedback_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
