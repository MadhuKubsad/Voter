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

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.Add_member_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response_Data;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Add_Sub_Member_Response;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Add_SubEnrollment_Fragment extends Fragment implements View.OnClickListener {
    Preferences pref;
    EditText ed_user, ed_mobile, ed_mailID, ed_voter;
    Spinner sp_gender;
    Button btn_add, btn_updatee;
    CustomLoader loader;
    RoundedImageView rm_user;
    ImageView iv_camera,iv_voterid_search;
    private static final int SELECT_PICTURE = 1;
    public Uri fileUri;
    public Uri picUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Voter";
    public static final int MEDIA_TYPE_VIDEO = 2;
    String picturePath = "", filename = "", ext = "";
    String encodedString = "";
    String setPic = "";
    String gender;
    public static Bitmap bitmap;
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    String dtae;
    int PIC_CROP = 21;
    int REQUEST_CODE_HIGH_QUALITY_IMAGE = 22;
    private Uri mHighQualityImageUri = null;
    String boothid,booth_name;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.add_sub_enrollment, container, false);

        getid();
        setlistner();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pref.set(Constants.fragment_position, "3");
        pref.commit();

        Calendar c = Calendar.getInstance();
        // Calendar c = Calendar.getInstance();
        //  System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dtae = simpleDateFormat.format(c.getTime());

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

        if (pref.get(Constants.se_update).equals("1")) {
            Picasso.with(getContext()).load(pref.get(Constants.se_Photo)).into(rm_user);
            ed_user.setText(pref.get(Constants.se_Name));
            if (pref.get(Constants.se_Gender).equals("Male")) {
                sp_gender.setSelection(0);
            } else if (pref.get(Constants.se_Gender).equals("Female")) {
                sp_gender.setSelection(1);
            } else {
                sp_gender.setSelection(2);
            }
            ed_mobile.setText(pref.get(Constants.se_Mobile));
            ed_mailID.setText(pref.get(Constants.se_EmailID));
            ed_voter.setText(pref.get(Constants.se_VoterID));
            booth_name = pref.get(Constants.Booth_name);
            boothid = pref.get(Constants.Booth_id);
            btn_add.setVisibility(View.GONE);
            btn_updatee.setVisibility(View.VISIBLE);
        }
        else {
            btn_add.setVisibility(View.VISIBLE);
            btn_updatee.setVisibility(View.GONE);
            booth_name = "0";
            boothid = "0";
        }

        return v;
    }

    private void getid() {
        pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        ed_user = (EditText) v.findViewById(R.id.ed_username);
        ed_mobile = (EditText) v.findViewById(R.id.ed_mobile);
        ed_mailID = (EditText) v.findViewById(R.id.ed_mail);
        sp_gender = (Spinner) v.findViewById(R.id.gender);
        btn_add = (Button) v.findViewById(R.id.add_button);
        rm_user = (RoundedImageView) v.findViewById(R.id.user_image);
        iv_camera = (ImageView) v.findViewById(R.id.camera);
        ed_voter = (EditText) v.findViewById(R.id.ed_voter);
        btn_updatee = (Button)v.findViewById(R.id.ulpdate_button);
        iv_voterid_search = (ImageView) v.findViewById(R.id.iv_voterid_search);
    }

    private void setlistner() {
        btn_add.setOnClickListener(this);
        rm_user.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
      btn_updatee.setOnClickListener(this);
        iv_voterid_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                check_data();
                break;

            case R.id.user_image:
                open_popup();
                break;

            case R.id.camera:
                open_popup();
                break;
            case R.id.ulpdate_button:
                Update_Data();
                break;

            case R.id.iv_voterid_search:
                Search_Voter();
                break;
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

    //==================== Open Camera =======================================================//


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                && !ed_voter.getText().toString().trim().equals("")) {
            if (Utils.isNetworkConnectedMainThred(getContext())) {
                Update_enroll_api();
            } else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }
    }

    //====================== Update Enroll Api =======================================================//
    private void Update_enroll_api() {
        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        int enr_id = Integer.parseInt(pref.get(Constants.e_ID));
        Add_Sub_Member_Input add_member_input = new Add_Sub_Member_Input();
        add_member_input.ID = 0;
        add_member_input.EnrollmentID = enr_id;
        add_member_input.AssemblyID = 0;
        add_member_input.WardID = 0;
        add_member_input.BoothID = boothid;
        add_member_input.BoothNo = booth_name;
        add_member_input.Name = ed_user.getText().toString().trim();
        add_member_input.EmailID = ed_mailID.getText().toString().trim();
        add_member_input.Mobile = ed_mobile.getText().toString().trim();
        add_member_input.Gender = gender;
        add_member_input.VoterID = ed_voter.getText().toString().trim();
        add_member_input.UserID = 0;
        add_member_input.Photo = encodedString;
        add_member_input.RegisterDateTime = dtae;
        Call<Add_Sub_Member_Response> add_member_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .add_sub_response(tokekn, add_member_input);
        add_member_responseCall.enqueue(new Callback<Add_Sub_Member_Response>() {
            @Override
            public void onResponse(Call<Add_Sub_Member_Response> call, Response<Add_Sub_Member_Response> response) {
                if (response.isSuccessful()) {
                    Add_Sub_Member_Response add_sub_member_response = response.body();
                    if (add_sub_member_response.Code.equals("1")) {
                        Toast.makeText(getContext(), add_sub_member_response.Message, Toast.LENGTH_SHORT).show();
                        loader.cancel();
                        ((DashBoard_Activity) getActivity()).displayView(3);
                    } else {
                        loader.cancel();
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loader.cancel();
                    Toast.makeText(getContext(), "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Sub_Member_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //========================= Update Data Checking ==========================================//

    private void Update_Data() {
        if (!ed_user.getText().toString().trim().equals("") && !ed_mobile.getText().toString().trim().equals("")
                && !ed_voter.getText().toString().trim().equals("")) {
            if (Utils.isNetworkConnectedMainThred(getContext())) {
                Update();
            } else {
                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }
    }
//
    private void Update() {
     loader.show();
     loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        int enr_id = Integer.parseInt(pref.get(Constants.e_ID));
        Add_Sub_Member_Input update_sub_input = new Add_Sub_Member_Input();
        update_sub_input.ID = Integer.parseInt(pref.get(Constants.se_ID));
        update_sub_input.EnrollmentID = enr_id;
        update_sub_input.AssemblyID = Integer.parseInt(pref.get(Constants.e_AssemblyID));
        update_sub_input.WardID = Integer.parseInt(pref.get(Constants.e_WardID));
        update_sub_input.Name = ed_user.getText().toString().trim();
        update_sub_input.EmailID = ed_mailID.getText().toString().trim();
        update_sub_input.Mobile = ed_mobile.getText().toString().trim();
        update_sub_input.Gender = gender;
        update_sub_input.VoterID = ed_voter.getText().toString().trim();
        update_sub_input.UserID = Integer.parseInt(pref.get(Constants.e_userid));
        update_sub_input.Photo = encodedString;
        update_sub_input.RegisterDateTime = pref.get(Constants.se_date);
        Call<Add_Sub_Member_Response> update_sub_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .updtae_sub_response(tokekn,update_sub_input);
        update_sub_responseCall.enqueue(new Callback<Add_Sub_Member_Response>() {
            @Override
            public void onResponse(Call<Add_Sub_Member_Response> call, Response<Add_Sub_Member_Response> response) {
                if (response.isSuccessful()){
                    Add_Sub_Member_Response update_sub_response = response.body();
                    if (update_sub_response.Code.equals("1")){
                        Toast.makeText(getContext(), update_sub_response.Message, Toast.LENGTH_SHORT).show();
                        loader.cancel();
                        ((DashBoard_Activity) getActivity()).displayView(3);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), update_sub_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Sub_Member_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//========================== Search data by voterId API ==============================

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

                       // ed_address.setText(voterId_search_response_data.getAddress());
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