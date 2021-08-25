package com.example.promena.voter.Activity.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Forgot_input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Register_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password_Activity extends AppCompatActivity  implements View.OnClickListener {
    Button btn_regiseter;
    Preferences pref;
    CustomLoader loader;
    EditText ed_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        getId();
        setListner();
    }

    private void getId() {
        ed_mail = (EditText)findViewById(R.id.ed_mail);
        btn_regiseter = (Button)findViewById(R.id.submit);
    }

    private void setListner() {
        btn_regiseter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if (ed_mail.getText().toString().trim().equals("")){
                    Toast.makeText(this, "enter the registered email id", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Utils.isNetworkConnectedMainThred(getApplication())){
                        hit_forgot_api();
                    }
                    else {
                        Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
//============================= Forgot Api =================================================//
    private void hit_forgot_api() {
        Forgot_input forgot_input = new Forgot_input();
        forgot_input.Email = ed_mail.getText().toString().trim();
        Call<Register_Response> register_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .register_response(forgot_input);
        register_responseCall.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()){
                    Register_Response register_response = response.body();
                    if (register_response.Code.equals("1")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Forgot_Password_Activity.this);

                        builder.setTitle("Done");
                        builder.setMessage(register_response.Message.trim()+"! Check Your Mail Id and Try to Login again");
                        builder.setCancelable(true);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do something when user want to exit the app
                                // Let allow the system to handle the event, such as exit th

                                finish();
                                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                    else {
                        Toast.makeText(Forgot_Password_Activity.this, "Give Valid Email id", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Forgot_Password_Activity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                Toast.makeText(Forgot_Password_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
