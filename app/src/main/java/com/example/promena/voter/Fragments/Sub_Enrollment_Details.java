package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;

public class Sub_Enrollment_Details extends Fragment implements View.OnClickListener {

    Preferences pref;
    RoundedImageView rv_profile;
    LinearLayout lv_address,lv_count;
    TextView tv_edit,tv_delete,tv_add,tv_viewall;
    TextView tv_enrollmentid,tv_assembly,tv_ward,tv_name,tv_gender,tv_mailid,tv_mobile,tv_address,tv_voterid,tv_count,tv_booth;


    View v;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.details_screen, container, false);


        getid();
        setlistner();

        pref.set(Constants.fragment_position,"5");
        pref.commit();

        Picasso.with(getContext()).load(pref.get(Constants.se_Photo)).into(rv_profile);
        tv_enrollmentid.setText(": "+pref.get(Constants.se_ID));
        tv_assembly.setText(": "+pref.get(Constants.Assemblyname));
        tv_ward.setText(": "+pref.get(Constants.Wardname));
        tv_name.setText(": "+pref.get(Constants.se_Name));
        tv_gender.setText(": "+pref.get(Constants.se_Gender));
        tv_mailid.setText(": "+pref.get(Constants.se_EmailID));
        tv_mobile.setText(": "+pref.get(Constants.se_Mobile));
        tv_voterid.setText(": "+pref.get(Constants.se_VoterID));
        tv_booth.setText(": "+pref.get(Constants.Booth_name));
        lv_count.setVisibility(View.GONE);
        lv_address.setVisibility(View.GONE);
        tv_viewall.setVisibility(View.GONE);
        tv_add.setVisibility(View.GONE);
        return v;
    }


    private void getid() {
        pref = new Preferences(getContext());
        rv_profile = (RoundedImageView)v.findViewById(R.id.enrollpic);
        tv_edit = (TextView)v.findViewById(R.id.e_edit_user);
        tv_delete = (TextView)v.findViewById(R.id.e_delete);
        tv_enrollmentid = (TextView)v.findViewById(R.id.erollment_id);
        tv_assembly = (TextView) v.findViewById(R.id.e_assembly);
        tv_ward = (TextView)v.findViewById(R.id.e_ward);
        tv_name = (TextView) v.findViewById(R.id.e_name);
        tv_gender = (TextView) v.findViewById(R.id.e_gender);
        tv_mailid = (TextView)v.findViewById(R.id.e_mail);
        tv_mobile = (TextView)v.findViewById(R.id.e_mobile);
        tv_address = (TextView)v.findViewById(R.id.e_address);
        tv_voterid = (TextView)v.findViewById(R.id.e_voter);
        tv_count = (TextView)v.findViewById(R.id.e_members);
        tv_add = (TextView) v.findViewById(R.id.e_add_member);
        tv_viewall = (TextView)v.findViewById(R.id.e_viewall);
        lv_address = (LinearLayout)v.findViewById(R.id.address_area);
        lv_count = (LinearLayout)v.findViewById(R.id.count_area);
        tv_booth = (TextView)v.findViewById(R.id.e_booth);

    }

    private void setlistner() {
        tv_edit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.e_edit_user:
              pref.set(Constants.se_update,"1");
              pref.commit();
              ((DashBoard_Activity)getContext()).displayView(10);
              break;
      }
    }
}
