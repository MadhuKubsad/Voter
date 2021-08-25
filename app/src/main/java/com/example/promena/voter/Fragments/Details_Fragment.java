package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.promena.voter.Activity.Main.DashBoard_Activity;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.Extras.RoundedImageView;
import com.example.promena.voter.R;
import com.squareup.picasso.Picasso;

public class Details_Fragment extends Fragment implements View.OnClickListener {

    Preferences pref;
    RoundedImageView rv_profile;
    TextView tv_edit,tv_delete,tv_add,tv_viewall;
    TextView tv_enrollmentid,tv_assembly,tv_ward,tv_name,tv_gender,tv_mailid,tv_mobile,tv_address,tv_voterid,tv_count,tv_boothname;

    View v;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.details_screen, container, false);

        getid();
        setlistner();

        pref.set(Constants.fragment_position,"2");
        pref.commit();

        Picasso.with(getContext()).load(pref.get(Constants.e_Photo)).into(rv_profile);
        tv_enrollmentid.setText(": "+pref.get(Constants.e_ID));
        tv_assembly.setText(": "+pref.get(Constants.Assemblyname));
        tv_ward.setText(": "+pref.get(Constants.Wardname));
        tv_name.setText(": "+pref.get(Constants.e_Name));
        tv_gender.setText(": "+pref.get(Constants.e_Gender));
        tv_mailid.setText(": "+pref.get(Constants.e_EmailID));
        tv_mobile.setText(": "+pref.get(Constants.e_Mobile));
        tv_address.setText(": "+pref.get(Constants.e_address));
        tv_voterid.setText(": "+pref.get(Constants.e_VoterID));
        tv_boothname.setText(": "+pref.get(Constants.Booth_name));
        tv_count.setText(": "+pref.get(Constants.e_count));

        if (pref.get(Constants.e_count).equals("0")){
            tv_viewall.setVisibility(View.GONE);
        }
        else {
            tv_viewall.setVisibility(View.VISIBLE);
        }

        return v ;
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
        tv_boothname = (TextView)v.findViewById(R.id.e_booth);
    }

    private void setlistner() {
        tv_edit.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_viewall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.e_edit_user:
                pref.set(Constants.e_update,"1");
                pref.commit();
                ((DashBoard_Activity)getActivity()).displayView(7);
                break;
            case R.id.e_delete:
                break;
            case R.id.e_add_member:
                pref.set(Constants.se_update,"0");
                pref.commit();
                ((DashBoard_Activity)getContext()).displayView(10);
                break;
            case R.id.e_viewall:
                ((DashBoard_Activity)getContext()).displayView(11);
                break;
        }
    }
}
