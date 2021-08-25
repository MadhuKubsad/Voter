package com.example.promena.voter.Activity.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promena.voter.Activity.Login.Login_Activity;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Register_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.User_Module.User_Response;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Extras.GetLocation;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.Fragments.Add_Enrollment_Fragment;
import com.example.promena.voter.Fragments.Add_SubEnrollment_Fragment;
import com.example.promena.voter.Fragments.Add_Task_Fragment;
import com.example.promena.voter.Fragments.Change_Password_Frgament;
import com.example.promena.voter.Fragments.ChatFragment;
import com.example.promena.voter.Fragments.Completed_Todo_List;
import com.example.promena.voter.Fragments.DashBoard_Fragment;
import com.example.promena.voter.Fragments.Details_Fragment;
import com.example.promena.voter.Fragments.Feedback_Fragment;
import com.example.promena.voter.Fragments.ChatHomeFragment;
import com.example.promena.voter.Fragments.NewChatFragment;
import com.example.promena.voter.Fragments.Notification_Fragment;
import com.example.promena.voter.Fragments.Our_Members_Fragment;
import com.example.promena.voter.Fragments.Sub_Enrollment_Details;
import com.example.promena.voter.Fragments.Sub_Enrollment_List_Fragment;
import com.example.promena.voter.Fragments.To_Do_Fragment;
import com.example.promena.voter.Fragments.Update_Profile_Fragment;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard_Activity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    RelativeLayout rlSideList;
    Preferences pref;
    GetLocation location;
    Handler mHandler;
    RoundedImageView rv_user_logo;
    double lat,lng;
    String local_address,date;
    ImageView iv_menu,iv_notification,iv_notify;
    TextView tv_headder,tv_username;
    private static String[] PERMISSIONS = {Manifest.permission.GET_ACCOUNTS};
     RelativeLayout rl_home,rl_profile,rl_password,rl_member,rl_todo_list,rl_feedback,rl_logout,rl_complted;
    int REQUEST_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getid();
        setListner();

        if (pref.get(Constants.nn_count).equals("1")){
            iv_notify.setVisibility(View.VISIBLE);
        }

        tv_headder.setText("National Congress");
        hit_user_data_api();
        if (!pref.get(Constants.username).equals("")){
            tv_username.setText(pref.get(Constants.username));
        }
        if (!pref.get(Constants.userprofile).equals("")){
            Picasso.with(getApplicationContext()).load(pref.get(Constants.userprofile)).into(rv_user_logo);
        }
        loadFragment(new DashBoard_Fragment());


       // hit_calllogs();

    }



    private void getid() {
        mHandler = new Handler();
        location = new GetLocation(getApplicationContext());
        pref = new Preferences(getApplicationContext());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_menu = (ImageView) findViewById(R.id.menu_list);
        rlSideList = (RelativeLayout) findViewById(R.id.side_list);
        tv_headder = (TextView)findViewById(R.id.tv_headder);
        //side menu items
        rl_home = (RelativeLayout)findViewById(R.id.rl_home);
        rl_profile = (RelativeLayout)findViewById(R.id.rl_profile);
        rl_password = (RelativeLayout)findViewById(R.id.rl_password);
        rl_member = (RelativeLayout)findViewById(R.id.rl_members);
        rl_todo_list = (RelativeLayout)findViewById(R.id.rl_todo);
        rl_feedback = (RelativeLayout)findViewById(R.id.rl_feedback);
        rl_logout = (RelativeLayout)findViewById(R.id.rl_logout);
        rl_complted = (RelativeLayout)findViewById(R.id.rl_cmp_todo);
        tv_username = (TextView)findViewById(R.id.tv_name);
        iv_notification = (ImageView)findViewById(R.id.bell_icon);
        rv_user_logo = (RoundedImageView)findViewById(R.id.iv_provider);
        iv_notify = (ImageView)findViewById(R.id.notify);

    }

    private void setListner() {
        iv_menu.setOnClickListener(this);
        rl_home.setOnClickListener(this);
        rl_profile.setOnClickListener(this);
        rl_password.setOnClickListener(this);
        rl_member.setOnClickListener(this);
        rl_todo_list.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_logout.setOnClickListener(this);
        rl_complted.setOnClickListener(this);
        iv_notification.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_list:
                opendrawer();
                break;
            case R.id.rl_home:
                displayView(0);
                break;
            case R.id.rl_profile:
                displayView(1);
                break;
            case R.id.rl_password:
                displayView(2);
                break;
            case R.id.rl_members:
                displayView(3);
                break;
            case R.id.rl_todo:
                displayView(4);
                break;
            case R.id.rl_feedback:
                displayView(5);
                break;
            case R.id.rl_logout:
                displayView(6);
                break;
            case R.id.rl_cmp_todo:
                displayView(9);
                break;
            case R.id.bell_icon:
              displayView(14);
                break;
            /*case R.id.fa_chatBox:
                displayView(15);
                break;*/
        }
    }

    private void opendrawer() {
        mDrawerLayout.openDrawer(rlSideList);
    }

    //close drawer navigation
    public void closeDrawer() {

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mDrawerLayout.closeDrawer(rlSideList);
            }
        }, 100);
    }

    private void loadFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    //=============displaying view================================================//
    public void displayView(int position) {
        switch (position) {
            case 0:
               Intent intent = new Intent(getApplicationContext(),DashBoard_Activity.class);
               startActivity(intent);
                break;
            case 1:
                closeDrawer();
                tv_headder.setText("User Profile");
                loadFragment(new Update_Profile_Fragment());
                break;
            case 2: closeDrawer();
                tv_headder.setText("Change Password");
                loadFragment(new Change_Password_Frgament());
                break;
            case 3:
                closeDrawer();
                tv_headder.setText("Our Clients");
                loadFragment(new Our_Members_Fragment());
                break;
            case 4:
                closeDrawer();
                tv_headder.setText("To-Do-List");
                loadFragment(new To_Do_Fragment());
                break;
            case 5:
                closeDrawer();
                tv_headder.setText("Feedback");
                loadFragment(new Feedback_Fragment());
                break;
            case 6:
                closeDrawer();
                popupLogout();
                break;
            case 7:
                closeDrawer();
                tv_headder.setText("Add New Client");
                loadFragment(new Add_Enrollment_Fragment());
                break;
            case 8:
                closeDrawer();
                tv_headder.setText("Add New Task");
                loadFragment(new Add_Task_Fragment());
                break;
            case 9:
                closeDrawer();
                tv_headder.setText("Task Completed");
                loadFragment(new Completed_Todo_List());
                break;
            case 10:
                closeDrawer();
                tv_headder.setText("Sub Client Adding");
                loadFragment(new Add_SubEnrollment_Fragment());
                break;
            case 11:
                closeDrawer();
                tv_headder.setText("Sub Client List");
                loadFragment(new Sub_Enrollment_List_Fragment());
                break;
            case 12:
                closeDrawer();
                tv_headder.setText(" Client Details");
                loadFragment(new Details_Fragment());
                break;
            case 13:
                closeDrawer();
                tv_headder.setText(" Client Details");
                loadFragment(new Sub_Enrollment_Details());
                break;
            case 14:
                closeDrawer();
                tv_headder.setText(" Notifications");
                loadFragment(new Notification_Fragment());
                break;
            case 15:
                closeDrawer();
                tv_headder.setText(" Chat Box");
                loadFragment(new ChatHomeFragment());
                break;
            case 16:
                closeDrawer();
                tv_headder.setText(" Contact List");
                loadFragment(new NewChatFragment());
                break;
        }
    }


    //logout popup
    private void popupLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard_Activity.this);

        builder.setTitle("Please confirm");
        builder.setMessage("Do you want to Logout from app ?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when user want to exit the app
                // Let allow the system to handle the event, such as exit th

               hit_logou_api();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });  AlertDialog dialog = builder.create();

        dialog.show();


    }

    //==========================log out api==========================================//

    private void hit_logou_api() {
        String token = pref.get(Constants.sessing_id);
        Call<Register_Response> register_responseCall = ApiClient.getClient().create(ApiInterface.class).setdata(token);
        register_responseCall.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()){
                    Register_Response register_response = response.body();
                    if (register_response.Code.equals("1")){
                        pref.set(Constants.sessing_id,null);
                        pref.commit();
                        Intent intent = new Intent(DashBoard_Activity.this,Login_Activity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(DashBoard_Activity.this, register_response.Message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DashBoard_Activity.this,Login_Activity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(DashBoard_Activity.this, "No response", Toast.LENGTH_SHORT).show();finish();
                    Intent intent = new Intent(DashBoard_Activity.this,Login_Activity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                Toast.makeText(DashBoard_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //=================Back Button Press Operations===================================================//
    @Override
    public void onBackPressed() {

        if (pref.get(Constants.fragment_position).equals("1") || pref.get(Constants.fragment_position).equals("")) {


            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard_Activity.this);

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

        else if (pref.get(Constants.fragment_position).equals("0")){
            displayView(0);
        }
        else if (pref.get(Constants.fragment_position).equals("2")){
            displayView(3);
        }
        else if (pref.get(Constants.fragment_position).equals("3")){
            displayView(3);
        }
        else if (pref.get(Constants.fragment_position).equals("4")){
            displayView(4);
        }
        else if (pref.get(Constants.fragment_position).equals("5")){
            displayView(11);
        }
    }


//=========================== User Data Api ==========================================================//
    private void hit_user_data_api() {
    String token = pref.get(Constants.sessing_id);
    Call<User_Response>user_responseCall = ApiClient.getClient().create(ApiInterface.class).user_response(token);
    user_responseCall.enqueue(new Callback<User_Response>() {
        @Override
        public void onResponse(Call<User_Response> call, Response<User_Response> response) {
            if (response.isSuccessful()){
                User_Response user_response = response.body();
                if (user_response.Code.equals("1")){
                       pref.set(Constants.username,user_response.Data.UserName);
                    tv_username.setText(pref.get(Constants.username));
                        pref.set(Constants.user_gender,user_response.Data.Gender);
                        pref.set(Constants.user_mail,user_response.Data.EmailID);
                        pref.set(Constants.userprofile,user_response.Data.Photo);
                    Picasso.with(getApplicationContext()).load(user_response.Data.Photo).into(rv_user_logo);
                        pref.set(Constants.AssemblyID,user_response.Data.AssemblyID);
                        pref.set(Constants.WardID,user_response.Data.WardID);
                        pref.set(Constants.Booth_id,user_response.Data.BoothID);
                        pref.set(Constants.Booth_name,user_response.Data.BoothNo);
                        pref.commit();

                }
                else {
                    Toast.makeText(DashBoard_Activity.this, user_response.Message, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard_Activity.this);

                    builder.setTitle("Error");
                    builder.setMessage("You Logged in to another device, Login again ?");
                    builder.setCancelable(false);

                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Do something when user want to exit the app
                            // Let allow the system to handle the event, such as exit th

                            pref.set(Constants.sessing_id,null);
                            pref.commit();
                            finish();
                            Intent intent = new Intent(DashBoard_Activity.this,Login_Activity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }

            }
            else {
                Toast.makeText(DashBoard_Activity.this, "Problem with datas", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<User_Response> call, Throwable t) {
            Toast.makeText(DashBoard_Activity.this, "connection Failed", Toast.LENGTH_SHORT).show();
        }
    });
    }


    //===================================== Send Location data ===========================//

    private void hit_synchronize_api() {

    }

}
