package com.example.promena.voter.Activity.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.promena.voter.Adapter.ChatAdapter;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.ChatMessage;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetChatMessage;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.GetUserMsgData;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module.SendMsgResponse;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response;
import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search.VoterId_Search_Response_Data;
import com.example.promena.voter.Api_Modules.Retrofit.ApiClient;
import com.example.promena.voter.Api_Modules.Retrofit.ApiInterface;
import com.example.promena.voter.Common.Constants;
import com.example.promena.voter.Common.CustomLoader;
import com.example.promena.voter.Common.Preferences;
import com.example.promena.voter.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    String messageText;

    CustomLoader loader;
    Preferences pref;
    private ArrayList<ChatMessage> chatHistory;
    private ChatAdapter adapter;
    ChatMessage[] class_chatModules_arrayobj;
    ArrayList<ChatMessage> class_chatModulesobj=new ArrayList<>();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);

        pref = new Preferences(ChatActivity.this);
        loader = new CustomLoader(ChatActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        pref.set(Constants.fragment_position,"2");
        pref.commit();
        GetMessagesAPI();


        // starts the chat inside a container
        //ChatUI.getInstance().openConversationsListFragment(getChildFragmentManager(), R.id.container);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText = messageET.getText().toString();
                sendMessageAPI(messageText);
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

            }
        });
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ArrayList<ChatMessage> msg_final=new ArrayList<>();

        boolean stud_manag = false;

        Log.e("tag","class_chatModulesobj.size=="+class_chatModulesobj.size());
        for (int j=0;j<class_chatModulesobj.size();j++){
            String Msg = class_chatModulesobj.get(j).getMessage();
            stud_manag = class_chatModulesobj.get(j).getSenderSide();
            String sentDate = class_chatModulesobj.get(j).getSentDate();
       /*     studentName = class_chatModulesobj.get(j).getSenderUserId();
            replyTime = class_chatModulesobj.get(j).getReceiverUserId();
            projectStatus = class_chatModulesobj.get(j).getSentStatus();
            */
            ChatMessage msg = new ChatMessage();
            msg.setId(j);
            msg.setSenderSide(stud_manag);
            msg.setMessage(Msg);
            msg.setSentDate(sentDate);
         //   msg.setProjectStatus(projectStatus);
            chatHistory.add(msg);
        }

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }

    }
    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }
    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void GetMessagesAPI() {

        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        Log.e("tag","chatActivity token=="+tokekn.trim());
        ApiInterface userService;
        userService = ApiClient.getClient().create(ApiInterface.class);

        Integer pageIndex=0;
        Integer pageSize=100;
       // Integer userId=20;

        retrofit2.Call call = userService.getMessage(tokekn, Integer.valueOf(userId),pageIndex,pageSize);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                Log.e("response_chatdata:", "" + String.valueOf(new Gson().toJson(response)));
                loader.cancel();
                GetChatMessage user_object;


                try {
                    //  Log.e("response", response.body().toString());
                    user_object = new GetChatMessage();
                    user_object = (GetChatMessage) response.body();
                    // String x=response.body().toString();

                    Log.e("response chatdata:", "" + new Gson().toJson(response));
                    Log.e("tag","chat data=="+user_object.getCode());
                    if(user_object.getCode().equals(1)){
                        List<ChatMessage> chatMessage=new ArrayList<>();
                        chatMessage=user_object.getData().getData();

                      //  ChatMessage innerObj = new ChatMessage();
                        ChatMessage chatMessage1;
                        class_chatModules_arrayobj = new ChatMessage[chatMessage.size()];
                        class_chatModulesobj.clear();

                        Integer Id,ReceiverUserId,SenderUserId;
                        String Msg,SentDate;
                        Boolean SenderSide;
                        for(int i=0;i<chatMessage.size();i++) {
                            /*innerObj.setId(chatMessage.get(i).getId());
                            innerObj.setMessage(chatMessage.get(i).getMessage());
                            innerObj.setReceiverUserId(chatMessage.get(i).getReceiverUserId());
                            innerObj.setSenderUserId(chatMessage.get(i).getSenderUserId());
                            innerObj.setSenderSide(chatMessage.get(i).getSenderSide());
                            innerObj.setSentDate(chatMessage.get(i).getSentDate());*/

                            Id =chatMessage.get(i).getId();
                            Msg=chatMessage.get(i).getMessage();
                            ReceiverUserId=chatMessage.get(i).getReceiverUserId();
                            SenderUserId=chatMessage.get(i).getSenderUserId();
                            SentDate=chatMessage.get(i).getSentDate();
                            SenderSide=chatMessage.get(i).getSenderSide();

                            chatMessage1= new ChatMessage(Id,Msg,ReceiverUserId,SenderUserId,SentDate,SenderSide);
                            Log.e("tag","chat_response_data="+chatMessage.get(i).getMessage());
                          //  class_chatModules_arrayobj[i] = innerObj;
                            class_chatModulesobj.add(chatMessage1);
                        }


                        loadDummyHistory();
                       /* adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
                        messagesContainer.setAdapter(adapter);

                        Log.e("tag","class_chatModulesobj="+class_chatModulesobj.size());
                        for(int i=0; i<class_chatModulesobj.size(); i++) {
                            ChatMessage message = class_chatModulesobj.get(i);
                            displayMessage(message);
                        }*/

                    }else{
                        Log.e("tag","chat_response_data= else");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loader.cancel();
                Toast.makeText(ChatActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessageAPI(String msg) {

        loader.show();
        loader.setCancelable(false);
        String tokekn = pref.get(Constants.sessing_id);
        Log.e("tag","token=="+tokekn.trim());
        ApiInterface userService;
        userService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call call = userService.sendMessage(tokekn, Integer.valueOf(userId),msg);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                Log.e("response sentdata:", "" + String.valueOf(new Gson().toJson(response)));
                loader.cancel();
                SendMsgResponse user_object;


                try {
                    //  Log.e("response", response.body().toString());

                    user_object = (SendMsgResponse) response.body();
                    // String x=response.body().toString();

                    Log.e("response sentdata:", "" + new Gson().toJson(response));
                    Log.e("tag","Send data=="+user_object.getCode());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loader.cancel();
                Toast.makeText(ChatActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}