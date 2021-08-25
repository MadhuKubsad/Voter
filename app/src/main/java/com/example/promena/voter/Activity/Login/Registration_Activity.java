package com.example.promena.voter.Activity.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Adapter.Dropdown_Adapter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Register_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Register_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Assembly_Data;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Assembly_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Booth_Datas;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Booth_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Ward_Data;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas.Ward_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration_Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_regiseter;
    Preferences pref;
    CustomLoader loader;
    EditText ed_mobile,ed_password,ed_user,ed_mail,ed_confirmpassword;
    TextView ed_gender;
    Dropdown_Adapter adapter,adapter2,adapter3;
    Spinner sp_gender,sp_assembly,sp_ward,sp_booth;
    ArrayList<HashMap<String,String>>assembly_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>>ward_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>>booth_list = new ArrayList<HashMap<String, String>>();
    ImageView iv_confirm;
    int assembly = -5;
    int ward = -5;
    String boothh="123";
    int booth = -5;
    String gender = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getid();
        setListner();

        final List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Male");
        spinnerArray.add("Female");
        spinnerArray.add("Others");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                Registration_Activity.this,
                android.R.layout.simple_spinner_item,
                spinnerArray);
        sp_gender.setAdapter(adapter);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //priority_type = i;
              gender = spinnerArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hit_assembly_api();

    }

    private void getid() {
        pref = new Preferences(getApplicationContext());
        ed_user = (EditText)  findViewById(R.id.ed_username);
        ed_mobile = (EditText)  findViewById(R.id.ed_mobile);
        ed_mail = (EditText)  findViewById(R.id.ed_mail);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        ed_gender = (TextView)  findViewById(R.id.ed_gender);
        ed_password = (EditText)  findViewById(R.id.ed_password);
        btn_regiseter = (Button) findViewById(R.id.login);
        ed_confirmpassword = (EditText)findViewById(R.id.ed_passwordconfirm);
        iv_confirm = (ImageView)findViewById(R.id.match);
        sp_gender  = (Spinner)findViewById(R.id.gender_spinner);
        sp_assembly = (Spinner)findViewById(R.id.assembley_spinner);
        sp_ward = (Spinner)findViewById(R.id.ward_spinner);
        sp_booth = (Spinner)findViewById(R.id.booth_spinner);
    }

    private void setListner() {
        btn_regiseter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                if (ed_user.getText().toString().trim().equals("") && ed_password.getText().toString().trim().equals("") && ed_mail.getText().toString().trim().equals("")
                        && ed_mobile.getText().toString().trim().equals("") && assembly==-5 && ward==-5
                        && gender.equals("")
                        && ed_confirmpassword.getText().toString().trim().equals("")){
                    Toast.makeText(this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (ed_confirmpassword.getText().toString().trim().equals(ed_password.getText().toString().trim())){

                        iv_confirm.setVisibility(View.VISIBLE);

                        if (Utils.isNetworkConnectedMainThred(getApplication())){
                            hit_register_api();
                        }
                        else {
                            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Password Miss match", Toast.LENGTH_SHORT).show();
                    }


                }
                break;
        }
    }
//================================ Register Api ===========================================//
    private void hit_register_api() {
        loader.show();
        loader.setCancelable(false);
        Register_Input register_input = new Register_Input();
        register_input.ID = 0;
        register_input.AssemblyID = assembly;
        register_input.WardID = ward;
        register_input.BoothID = booth;
        register_input.BoothNo = boothh;
        register_input.UserName = ed_user.getText().toString().trim();
        register_input.EmailID = ed_mail.getText().toString().trim();
        register_input.Gender = gender;
        register_input.Mobile = ed_mobile.getText().toString().trim();
        register_input.Password = ed_password.getText().toString().trim();
        register_input.Photo = null;
        register_input.RegisterDateTime = "";
        register_input.Approval = true;
        register_input.ApprovedBy = 0;
        register_input.AccessToken = "";
        Call<Register_Response> register_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .register_response(register_input);
        register_responseCall.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()){
                    Register_Response register_response = response.body();
                    if (register_response.Code.equals("1")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Activity.this);

                        builder.setTitle("Done");
                        builder.setMessage(register_response.Message.trim()+" Login Now  !");
                        builder.setCancelable(false);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do something when user want to exit the app
                                // Let allow the system to handle the event, such as exit th

                              finish();
                              Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                              startActivity(intent);
                              loader.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(Registration_Activity.this, register_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(Registration_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(Registration_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

//======================= Hit_Assembly Api =====================================//
    private void hit_assembly_api() {
        loader.show();
        loader.setCancelable(false);
        assembly_list.clear();
        Call<Assembly_Response> assembly_responseCall = ApiClient.getClient().create(ApiInterface.class).assembly_response();
        assembly_responseCall.enqueue(new Callback<Assembly_Response>() {
            @Override
            public void onResponse(Call<Assembly_Response> call, Response<Assembly_Response> response) {
                if (response.isSuccessful()){
                    Assembly_Response assembly_response = response.body();
                    if (assembly_response.Code.equals("1")){
                       for (int i=0; i<assembly_response.Data.size(); i++){
                           Assembly_Data assembly_data = assembly_response.Data.get(i);
                           HashMap<String,String> map = new HashMap<String, String>();
                           map.put("Text",assembly_data.Text);
                           map.put("Value",assembly_data.Value);
                           assembly_list.add(map);
                       }
                       loader.cancel();
                    adapter = new Dropdown_Adapter(getApplicationContext(),assembly_list);
                    sp_assembly.setAdapter(adapter);
                    sp_assembly.setSelection(0);

                        sp_assembly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                assembly = Integer.parseInt(assembly_list.get(i).get("Value"));
                                hit_ward_api(assembly_list.get(i).get("Value"));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(Registration_Activity.this, assembly_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(Registration_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Assembly_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(Registration_Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//================ Wards Data By Assembly Id ================================================//
    private void hit_ward_api(String value) {
        loader.show();
        loader.setCancelable(false);
        ward_list.clear();
      String url = "api/Data/GetWardList/"+value;
      Call<Ward_Response> ward_responseCall = ApiClient.getClient().create(ApiInterface.class).ward_response(url);
      ward_responseCall.enqueue(new Callback<Ward_Response>() {
          @Override
          public void onResponse(Call<Ward_Response> call, Response<Ward_Response> response) {
              if (response.isSuccessful()){
                  Ward_Response ward_response = response.body();
                  if (ward_response.Code.equals("1")){
                      for (int i=0; i<ward_response.Data.size(); i++){
                          Ward_Data ward_data = ward_response.Data.get(i);
                          HashMap<String,String> map = new HashMap<String, String>();
                          map.put("Text",ward_data.Text);
                          map.put("Value",ward_data.Value);
                          ward_list.add(map);
                      }
                      loader.cancel();
                      adapter2 = new Dropdown_Adapter(getApplicationContext(),ward_list);
                      sp_ward.setAdapter(adapter2);

                      sp_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            //  Toast.makeText(Registration_Activity.this, ward_list.get(i).get("Value"), Toast.LENGTH_SHORT).show();
                              ward = Integer.parseInt(ward_list.get(i).get("Value"));
                              hit_booth_api(ward);
                          }

                          @Override
                          public void onNothingSelected(AdapterView<?> adapterView) {

                          }
                      });

                  }
                  else {
                      loader.cancel();
                      Toast.makeText(Registration_Activity.this, ward_response.Message, Toast.LENGTH_SHORT).show();
                  }
              }
              else {
                  loader.cancel();
                  Toast.makeText(Registration_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onFailure(Call<Ward_Response> call, Throwable t) {
              loader.cancel();
              Toast.makeText(Registration_Activity.this, t.toString(), Toast.LENGTH_SHORT).show();

          }
      });
    }
//======================Booth Api ==============================================//
    private void hit_booth_api(int ward) {
        loader.show();
        loader.setCancelable(false);
        booth_list.clear();
        String url = "api/Data/GetBoothList/"+ward;
        Call<Booth_Response>booth_responseCall = ApiClient.getClient().create(ApiInterface.class).booth_response(url);
        booth_responseCall.enqueue(new Callback<Booth_Response>() {
            @Override
            public void onResponse(Call<Booth_Response> call, Response<Booth_Response> response) {
                if (response.isSuccessful()){
                    Booth_Response booth_response = response.body();
                    if (booth_response.Code.equals("1")){
                         for (int i=0; i<booth_response.Data.size(); i++){
                             Booth_Datas booth_datas = booth_response.Data.get(i);
                             HashMap<String,String> map = new HashMap<String, String>();
                             map.put("Value",booth_datas.Value);
                             map.put("Text",booth_datas.Text);
                             booth_list.add(map);
                         }
                         loader.cancel();
                        adapter3 = new Dropdown_Adapter(getApplicationContext(),booth_list);
                         sp_booth.setAdapter(adapter3);
                         sp_booth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                             @Override
                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 boothh = booth_list.get(position).get("Text");
                                 booth = Integer.parseInt(booth_list.get(position).get("Value"));
                             }

                             @Override
                             public void onNothingSelected(AdapterView<?> parent) {

                             }
                         });

                    }
                    else {
                        loader.cancel();
                        Toast.makeText(Registration_Activity.this, booth_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(Registration_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Booth_Response> call, Throwable t) {
             loader.cancel();
                Toast.makeText(Registration_Activity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
