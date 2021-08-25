package com.example.promena.voter.Activity.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Login_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Login_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_register,tv_forgot;
    Button btn_login;
    Preferences pref;
    CustomLoader loader;
    EditText ed_mobile,ed_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getid();
        setListner();

        if (!pref.get(Constants.sessing_id).equals("")){
          Intent intent = new Intent(getApplicationContext(),DashBoard_Activity.class);
          startActivity(intent);
        }

    }


    private void getid() {
        pref = new Preferences(getApplicationContext());
        //loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        loader = new CustomLoader(Login_Activity.this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tv_register = (TextView) findViewById(R.id.new_account);
        btn_login = (Button) findViewById(R.id.login);
        ed_mobile = (EditText)findViewById(R.id.ed_mobile);
        ed_password = (EditText)findViewById(R.id.ed_password);
        tv_forgot = (TextView)findViewById(R.id.forgot);
    }

    private void setListner() {
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_account:
                Intent intent = new Intent(getApplicationContext(), Registration_Activity.class);
                startActivity(intent);
                break;
            case R.id.login:
                if (ed_mobile.getText().toString().trim().equals("") && ed_password.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"All Datas are Mandatory",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Utils.isNetworkConnectedMainThred(getApplicationContext())) {
                       // loader.show();
                       // loader.setCancelable(false);
                        hit_login_api();
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.forgot:
                Intent intent1 = new Intent(getApplicationContext(),Forgot_Password_Activity.class);
                startActivity(intent1);
                break;
        }
    }

    //==============================Login Api======================================================//
    private void hit_login_api() {
        loader.show();
        loader.setCancelable(false);
        Login_Input login_input = new Login_Input();
        login_input.Mobile = ed_mobile.getText().toString().trim();
        login_input.Password = ed_password.getText().toString().trim();
        Call<Login_Response> login_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .login_response(login_input);
        login_responseCall.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if (response.isSuccessful()){
                    Login_Response login_response = response.body();
                    if (login_response.Code.equals("1")){
                           pref.set(Constants.sessing_id,login_response.Data.Token);
                           pref.set(Constants.phonenumber,ed_mobile.getText().toString().trim());
                           pref.set(Constants.username,login_response.Data.EmployeeName);
                           pref.set(Constants.userprofile,login_response.Data.Image);
                           pref.set(Constants.Assemblyname,login_response.Data.Assembly);
                           pref.set(Constants.Wardname,login_response.Data.Ward);
                           pref.commit();
                           finish();
                           loader.cancel();
                           Intent intent = new Intent(getApplicationContext(),DashBoard_Activity.class);
                           startActivity(intent);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(Login_Activity.this, login_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(Login_Activity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(Login_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {


            AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);

            builder.setTitle("Please confirm");
            builder.setMessage("Do you want to exit the app ?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do something when user want to exit the app
                    // Let allow the system to handle the event, such as exit th

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    //a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();
        }
}
