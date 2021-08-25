package com.example.promena.voter.Activity.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.crashlytics.android.Crashlytics;
import com.example.promena.voter.Activity.Login.Login_Activity;
import com.example.promena.voter.Activity.Login.Registration_Activity;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import io.fabric.sdk.android.Fabric;


public class Splash_Activity extends AppCompatActivity implements View.OnClickListener {

    private static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION,  android.Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA, Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET};
    private static final int READ_EXTERNAL_STORAGE= 1;
    private static final int WRITE_EXTERNAL_STORAGE= 1;
    private static int TIME_OUT = 4000;
    Intent intent;
    private final int MY_PERMISSIONS_REQUEST_CALL = 02;
    private String phone;
    private AlertDialog alertDialog;
    Button btn_login,btn_register;
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);
        FirebaseMessaging.getInstance().subscribeToTopic("Testing2");
    String    refreshedToken   = FirebaseInstanceId.getInstance().getToken();


        getPermission();



    }
    private static final int REQUEST_PERMISSIONS = 1;
    private void getPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                ||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                ||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                ||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)
                )


        {



            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       getid();
                        setListner();
                        if (pref.get(Constants.sessing_id).equals("")){
                            btn_register.setVisibility(View.VISIBLE);
                            btn_login.setVisibility(View.VISIBLE);
                            Animation myanim1;
                            myanim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_right);
                            btn_login.startAnimation(myanim1);
                            btn_register.startAnimation(myanim1);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(),DashBoard_Activity.class);
                            startActivity(intent);
                        }

                    }

                }, TIME_OUT);
                //Toast.makeText(Drawer_Activity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getid();
                        setListner();
                        if (pref.get(Constants.sessing_id).equals("")){
                            btn_register.setVisibility(View.VISIBLE);
                            btn_login.setVisibility(View.VISIBLE);
                            Animation myanim1;
                            myanim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_right);
                            btn_login.startAnimation(myanim1);
                            btn_register.startAnimation(myanim1);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(),DashBoard_Activity.class);
                            startActivity(intent);
                        }

                    }

                }, TIME_OUT);
                //Toast.makeText(Drawer_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        else  if (requestCode == READ_EXTRENAL_MEDIA_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {


            }
        }
        else   if (requestCode == READ_EXTERNAL_STORAGE) {
            // Log.i("Splash", "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
               /* if (PermissionUtil.verifyPermissions(grantResults)) {
                    // All required permissions have been granted, display contacts fragment.

                } else {
                    Log.i("Splash", "Contacts permissions were NOT granted.");
                }
*/
        }
        else    if (requestCode == WRITE_EXTERNAL_STORAGE) {
            //  Log.i("Splash", "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
               /* if (PermissionUtil.verifyPermissions(grantResults)) {
                    // All required permissions have been granted, display contacts fragment.

                } else {
                    Log.i("Splash", "Contacts permissions were NOT granted.");
                }
*/
        }

        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private static final int READ_EXTRENAL_MEDIA_PERMISSIONS_REQUEST = 1;

    private void getid() {
        pref = new Preferences(getApplicationContext());
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_signup);
    }

    private void setListner() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_signup:
                Intent intent1 = new Intent(getApplicationContext(),Registration_Activity.class);
                startActivity(intent1);
                break;
        }
    }
}
