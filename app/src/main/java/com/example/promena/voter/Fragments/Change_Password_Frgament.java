package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Change_Password_Module.Change_Password_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Change_Password_Module.Change_Password_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Change_Password_Frgament extends Fragment implements View.OnClickListener {

    Preferences pref;
    EditText ed_current,ed_new,ed_confirm_new;
    Button btn_update;
    CustomLoader loader;


    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.change_password_fragment, container, false);

        getid();
        setlistner();

        pref.set(Constants.fragment_position,"0");
        pref.commit();

        return v;
    }

    private void getid() {

        pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        ed_current = (EditText)v.findViewById(R.id.ed_current);
        ed_new = (EditText)v.findViewById(R.id.ed_new_password);
        ed_confirm_new = (EditText)v.findViewById(R.id.ed_confirm);
        btn_update = (Button)v.findViewById(R.id.update);
    }

    private void setlistner() {
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.update:
             check_data();
             break;
     }
    }
//=========================== check datas ==================================================//
    private void check_data() {
        if (!ed_current.getText().toString().trim().equals("") && !ed_new.getText().toString().trim().equals("")
                && !ed_confirm_new.getText().toString().trim().equals("")){

            String newpasswd = ed_new.getText().toString().trim();
            String confrpasswd = ed_confirm_new.getText().toString().trim();
           if (newpasswd.equals(confrpasswd)){
               hit_changepassword();
           }
           else {
               Toast.makeText(getContext(),"Password Miss match",Toast.LENGTH_SHORT).show();
           }
        }
        else {

        }
    }
//============================= Change password Api ======================================//
    private void hit_changepassword() {
        loader.show();
        loader.setCancelable(false);

        String token = pref.get(Constants.sessing_id);
        Change_Password_Input change_password_input = new Change_Password_Input();
        change_password_input.CurrentPassword = ed_current.getText().toString().trim();
        change_password_input.NewPassword = ed_new.getText().toString().trim();
        Call<Change_Password_Response>change_password_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .change_password_response(token,change_password_input);
        change_password_responseCall.enqueue(new Callback<Change_Password_Response>() {
            @Override
            public void onResponse(Call<Change_Password_Response> call, Response<Change_Password_Response> response) {
                if(response.isSuccessful()){
                    Change_Password_Response change_password_response = response.body();
                    if (change_password_response.Code.equals("1")){
                        Toast.makeText(getContext(), change_password_response.Message, Toast.LENGTH_SHORT).show();
                        loader.cancel();
                        ((DashBoard_Activity)getActivity()).displayView(0);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), change_password_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), "Check Crediantials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Change_Password_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
