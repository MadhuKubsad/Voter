package com.example.promena.voter.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.promena.voter.Activity.Login.Registration_Activity;
import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Adapter.Sub_Members_Adpter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_member_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response_Data;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Enrollment_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Members_List;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Common.Utils;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Add_Enrollment_Fragment extends Fragment implements View.OnClickListener {
   Preferences pref;
    EditText ed_user,ed_mobile,ed_mailID,ed_address,ed_people,ed_voter;
    Button btn_add,btn_update;
    RoundedImageView rm_user;
    ImageView iv_camera,iv_voterid_search;
    CustomLoader loader;
    String encodedString = "";
    String gender = "abc";
    Spinner sp_gender;
    String boothid,booth_name;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.add_enrollment_fragment, container, false);

        getid();
        setlistner();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pref.set(Constants.fragment_position,"2");
        pref.commit();


        final List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Male");
        spinnerArray.add("Female");
        spinnerArray.add("Others");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
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

        /*ed_voter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            ;
            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = ed_voter.getText().toString().toLowerCase(Locale.getDefault());
                Search_Voter();
                //Search_VoterId(text);
            }
        });*/


        if (pref.get(Constants.e_update).equals("1")){
            btn_update.setVisibility(View.VISIBLE);
            btn_add.setVisibility(View.GONE);
            Picasso.with(getContext()).load(pref.get(Constants.e_Photo)).into(rm_user);
            ed_user.setText(pref.get(Constants.e_Name));
            ed_mobile.setText(pref.get(Constants.e_Mobile));
            ed_mailID .setText(pref.get(Constants.e_EmailID));

            ed_address.setText(pref.get(Constants.e_address));
            ed_people.setText(pref.get(Constants.e_count));
            ed_voter.setText(pref.get(Constants.e_VoterID));
            booth_name = pref.get(Constants.Booth_name);
            boothid = pref.get(Constants.Booth_id);
           if (pref.get(Constants.e_Gender).equals("Male")){
               sp_gender.setSelection(0);
           }
           else if (pref.get(Constants.e_Gender).equals("Female")){
               sp_gender.setSelection(1);
           }
           else {
               sp_gender.setSelection(2);
           }
        }
        else {
            btn_update.setVisibility(View.GONE);
            btn_add.setVisibility(View.VISIBLE);
            booth_name = "0";
            boothid = "0";
        }

        return v;
    }

    private void getid() {
     pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
     ed_user = (EditText)v.findViewById(R.id.ed_username);
     ed_mobile = (EditText)v.findViewById(R.id.ed_mobile);
     ed_mailID = (EditText)v.findViewById(R.id.ed_mail);
     ed_address = (EditText)v.findViewById(R.id.ed_address);
     ed_people = (EditText)v.findViewById(R.id.ed_members);
     btn_add = (Button)v.findViewById(R.id.add_button);
     rm_user = (RoundedImageView)v.findViewById(R.id.user_image);
     iv_camera = (ImageView)v.findViewById(R.id.camera);
     ed_voter = (EditText)v.findViewById(R.id.ed_voter);
     btn_update = (Button)v.findViewById(R.id.update_button);
     sp_gender = (Spinner)v.findViewById(R.id.gender);
     iv_voterid_search= (ImageView) v.findViewById(R.id.iv_voterid_search);
    }

    private void setlistner() {
      btn_add.setOnClickListener(this);
      rm_user.setOnClickListener(this);
      iv_camera.setOnClickListener(this);
      btn_update.setOnClickListener(this);
      iv_voterid_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.add_button:
             check_data();
             break;

         case R.id.user_image:
             open_popup();
             break;

         case R.id.camera:
             open_popup();
             break;

         case R.id.update_button:
             update_info();
             break;

         case R.id.iv_voterid_search:
             Search_Voter();
             break;
     }
    }
//================== Check for the Update fields ========================================//
    private void update_info() {

        if (!ed_user.getText().toString().trim().equals("") && !ed_mobile.getText().toString().trim().equals("")
                && !ed_address.getText().toString().trim().equals("")
                && !ed_voter.getText().toString().trim().equals("")){
            if (Utils.isNetworkConnectedMainThred(getContext())){
               update();
            }
            else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }

    }

//================= PopUp menu =========================================================//

    private void open_popup() {
        int REQUEST_PERMISSIONS = 1;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions((Activity) getContext(), PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        } else {

            CropImage.activity()
                    .start(getContext(), this);


        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                rm_user.setImageURI(resultUri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                encodedString = getEncoded64ImageStringFromBitmap(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        }

    //=============================checking all the datas ============================================//
    private void check_data() {
        if (!ed_user.getText().toString().trim().equals("") && !ed_mobile.getText().toString().trim().equals("")

                && !ed_address.getText().toString().trim().equals("")
                && !ed_voter.getText().toString().trim().equals("")){
            if (Utils.isNetworkConnectedMainThred(getContext())){
                Update_enroll_api();
            }
            else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }
    }
//====================== Update Enroll Api =======================================================//
    private void Update_enroll_api() {
        int members;
        if (ed_people.getText().toString().equals("")){
              members = 0;
        }
        else {
            members = Integer.parseInt(ed_people.getText().toString().trim());
        }
        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        Add_Member_Input add_member_input = new Add_Member_Input();
        add_member_input.ID = 0;
        add_member_input.AssemblyID = 0;
        add_member_input.WardID = 0;
        add_member_input.BoothID = boothid;
        add_member_input.BoothNo = booth_name;
        add_member_input.Name = ed_user.getText().toString().trim();
        add_member_input.EmailID = ed_mailID.getText().toString().trim();
        add_member_input.Mobile = ed_mobile.getText().toString().trim();
        add_member_input.Gender = gender;
        add_member_input.Address = ed_address.getText().toString().trim();
        add_member_input.AdultFamilyMembers = members;
        add_member_input.VoterID = ed_voter.getText().toString().trim();
        add_member_input.UserID = 0;
        add_member_input.Photo = encodedString;
        add_member_input.RegisterDateTime = null;
        Call<Add_member_Response> add_member_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .add_member_response(tokekn,add_member_input);
        add_member_responseCall.enqueue(new Callback<Add_member_Response>() {
            @Override
            public void onResponse(Call<Add_member_Response> call, Response<Add_member_Response> response) {
                if (response.isSuccessful()){
                    Add_member_Response add_member_response = response.body();
                    if (add_member_response.Code.equals("1")){
                        Toast.makeText(getContext(),add_member_response.Message,Toast.LENGTH_SHORT).show();
                        loader.cancel();
                        ((DashBoard_Activity)getActivity()).displayView(3);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), add_member_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_member_Response> call, Throwable t) {
           loader.cancel();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //================ Update The Current Member Api ==================================================//

    private void update() {
        int members;
        if (ed_people.getText().toString().equals("")){
            members = 0;
        }
        else {
            members = Integer.parseInt(ed_people.getText().toString().trim());
        }
        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        Add_Member_Input update_member_input = new Add_Member_Input();
        update_member_input.ID = Integer.parseInt(pref.get(Constants.e_ID));
        update_member_input.AssemblyID = Integer.parseInt(pref.get(Constants.AssemblyID));
        update_member_input.WardID = Integer.parseInt(pref.get(Constants.WardID));
        update_member_input.Name = ed_user.getText().toString().trim();
        update_member_input.EmailID = ed_mailID.getText().toString().trim();
        update_member_input.Mobile = ed_mobile.getText().toString().trim();
        update_member_input.Gender = gender;
        update_member_input.Address = ed_address.getText().toString().trim();
        update_member_input.AdultFamilyMembers = members;
        update_member_input.VoterID = ed_voter.getText().toString().trim();
        update_member_input.UserID = Integer.parseInt(pref.get(Constants.e_userid));
        update_member_input.Photo = encodedString;
        update_member_input.RegisterDateTime = pref.get(Constants.e_Date);
       Call<Add_member_Response> update_member_responseCall = ApiClient.getClient().create(ApiInterface.class)
               .update_member_response(tokekn,update_member_input);
       update_member_responseCall.enqueue(new Callback<Add_member_Response>() {
           @Override
           public void onResponse(Call<Add_member_Response> call, Response<Add_member_Response> response) {
               if (response.isSuccessful()){

                   Add_member_Response update_member_response = response.body();
                   if (update_member_response.Code.equals("1")){
                       Toast.makeText(getContext(),update_member_response.Message,Toast.LENGTH_SHORT).show();
                       loader.cancel();
                       ((DashBoard_Activity)getActivity()).displayView(3);
                   }
                   else {
                       loader.cancel();
                       Toast.makeText(getContext(), update_member_response.Message, Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   loader.cancel();
                   Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Add_member_Response> call, Throwable t) {
               loader.cancel();
               Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    //================= VoterID Search Member Details API ===================================================//

    private void Search_Voter() {

        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        Log.e("tag","token=="+tokekn.trim());
        ApiInterface userService;
        userService = ApiClient.getClient().create(ApiInterface.class);
        String voterId = ed_voter.getText().toString().toLowerCase(Locale.getDefault());

        retrofit2.Call call = userService.getVotersDetails(tokekn, voterId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                Log.e("responseuserdata:", "" + String.valueOf(new Gson().toJson(response)));
                loader.cancel();
                VoterId_Search_Response user_object;


                try {
                    //  Log.e("response", response.body().toString());
                    user_object = new VoterId_Search_Response();
                    user_object = (VoterId_Search_Response) response.body();
                    // String x=response.body().toString();

                    Log.e("response userdata:", "" + new Gson().toJson(response));
                    Log.e("tag","user data=="+user_object.getCode());
                    if(user_object.getCode().equals(1)){
                        VoterId_Search_Response_Data voterId_search_response_data=new VoterId_Search_Response_Data();
                        voterId_search_response_data=user_object.getData();
                        Log.e("tag","voterId_search_response_data="+voterId_search_response_data.getVoterName());

                        ed_user.setText(voterId_search_response_data.getVoterName());
                        ed_mobile.setText(voterId_search_response_data.getContact());
                      //  ed_mailID .setText(pref.get(Constants.e_EmailID));

                        ed_address.setText(voterId_search_response_data.getAddress());
                     //   ed_people.setText(voterId_search_response_data.getFamilyCode());
                     //   ed_voter.setText(pref.get(Constants.e_VoterID));
                       // booth_name = pref.get(Constants.Booth_name);
                        //boothid = pref.get(Constants.Booth_id);
                        if (voterId_search_response_data.getSex().equals("M")){
                            sp_gender.setSelection(0);
                        }
                        else if (pref.get(Constants.e_Gender).equals("F")){
                            sp_gender.setSelection(1);
                        }
                        else {
                            sp_gender.setSelection(2);
                        }
                    }else{
                        Log.e("tag","voterId_search_response_data= else");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
