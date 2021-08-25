package com.example.promena.voter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.promena.voter.R;

public class NewChatFragment extends Fragment /*implements MyPresenceListener */{

//    private MyPresenceHandler myPresenceHandler;

    public NewChatFragment() {
    }


    /**
     * Returns a new instance of this fragment
     */

    public static NewChatFragment newInstance() {
        NewChatFragment fragment = new NewChatFragment();

        return fragment;
    }
    private Button sendBtn;
    private EditText messageET;
    ListView list;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myPresenceHandler = ChatManager.getInstance().getMyPresenceHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_chatnew, container, false);

        list = (ListView) rootView.findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                R.layout.listview, mobileArray);
        list.setAdapter(adapter);

        setHasOptionsMenu(false); // disable fragment option menu

        return rootView;
    }

}