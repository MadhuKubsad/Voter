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
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Request.Register_Input;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Register_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Update_Profile_Fragment extends Fragment implements View.OnClickListener {
    Button btn_update;
    EditText ed_user,ed_gender,ed_mobile,ed_mail;
    RoundedImageView roundedImageView;
    ImageView iv_button;
    Preferences pref;
    CustomLoader loader;
    int assid;
    int sssid;
    int warid;
    String encodedString = "";
    private static String[] PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.update_profile_fragment, container, false);

        getid();
        setlistner();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        pref.set(Constants.fragment_position,"0");
        pref.commit();

        if (!pref.get(Constants.username).equals("")){
            ed_user.setText(pref.get(Constants.username));
        }
        if (!pref.get(Constants.phonenumber).equals("")){
            ed_mobile.setText(pref.get(Constants.phonenumber));
        }
        if (!pref.get(Constants.user_mail).equals("")){
            ed_mail.setText(pref.get(Constants.user_mail));
        }
        if (!pref.get(Constants.user_gender).equals("")){
            ed_gender.setText(pref.get(Constants.user_gender));
        }
        if (!pref.get(Constants.userprofile).equals(""))
        {
            Picasso.with(getContext()).load(pref.get(Constants.userprofile)).into(roundedImageView);
        }
        if (!pref.get(Constants.AssemblyID).equals("")){
            assid = Integer.parseInt(pref.get(Constants.AssemblyID));
        }
        if (!pref.get(Constants.WardID).equals("")){
              warid = Integer.parseInt(pref.get(Constants.WardID));
        }
        if (!pref.get(Constants.Booth_id).equals("")){
            sssid = Integer.parseInt(pref.get(Constants.Booth_id));
        }

        return v;
    }

    private void getid() {
      pref = new Preferences(getContext());
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
      ed_user = (EditText)v.findViewById(R.id.ed_username);
      ed_mail = (EditText)v.findViewById(R.id.ed_mail);
      ed_mobile = (EditText)v.findViewById(R.id.ed_mobile);
      ed_gender = (EditText)v.findViewById(R.id.ed_gender);
      roundedImageView = (RoundedImageView)v.findViewById(R.id.user_image);
      iv_button = (ImageView)v.findViewById(R.id.camera);
      btn_update = (Button)v.findViewById(R.id.btn_update);
    }

    private void setlistner() {
     roundedImageView.setOnClickListener(this);
     iv_button.setOnClickListener(this);
     btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.user_image:
             showcameradialoge();
             break;
         case R.id.camera:

             showcameradialoge();

             break;
         case R.id.btn_update:
             Hit_Update();
             break;
     }
    }


    //======================Camera Gallery Pop up screen =======================================================//
    private void showcameradialoge() {
        int REQUEST_PERMISSIONS = 1;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(),PERMISSIONS,REQUEST_PERMISSIONS);
            return;
        }
        else {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                roundedImageView.setImageURI(resultUri);
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


    //==========================Update Profile Api=======================================================//
    private void Hit_Update() {
        loader.show();
        loader.setCancelable(false);
        Register_Input register_input = new Register_Input();
        String token = pref.get(Constants.sessing_id);
        register_input.ID = 0;
        register_input.UserName = ed_user.getText().toString().trim();
        register_input.EmailID = ed_mail.getText().toString().trim();
        register_input.Gender = ed_gender.getText().toString().trim();
        register_input.Mobile = ed_mobile.getText().toString().trim();
        register_input.Password = null;
        register_input.Photo = encodedString;
        register_input.RegisterDateTime = "";
        register_input.Approval = true;
        register_input.ApprovedBy = 0;
        register_input.AccessToken =null;
        register_input.AssemblyID = assid;
        register_input.WardID = warid;
        register_input.BoothNo = pref.get(Constants.Booth_name);
        register_input.BoothID = sssid;
        Call<Register_Response> register_responseCall = ApiClient.getClient().create(ApiInterface.class)
                .update_response(token,register_input);
        register_responseCall.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()){
                    Register_Response register_response = response.body();
                    if (register_response.Code.equals("1")){
                        loader.cancel();
                       Intent intent = new Intent(getContext(),DashBoard_Activity.class);
                       startActivity(intent);
                    }
                    else {
                        loader.cancel();
                        Toast.makeText(getContext(), register_response.Message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loader.cancel();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                loader.cancel();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
