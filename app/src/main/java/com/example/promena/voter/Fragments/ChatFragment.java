package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.promena.voter.R;

public class ChatFragment extends Fragment /*implements MyPresenceListener */{

//    private MyPresenceHandler myPresenceHandler;

    public ChatFragment() {
    }


    /**
     * Returns a new instance of this fragment
     */

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();

        return fragment;
    }
    private Button sendBtn;
    private EditText messageET;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myPresenceHandler = ChatManager.getInstance().getMyPresenceHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_chat, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        return rootView;
    }

}