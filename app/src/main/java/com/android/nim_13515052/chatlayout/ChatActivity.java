package com.android.nim_13515052.chatlayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends ActionBarActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private int getFavorite;
    private int favoriteLink;
    private int preferenceLink;
    private int unpreferenceLink;
    private int favoriteTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        getFavorite = 0;
        favoriteLink = 0;
        preferenceLink = 0;
        unpreferenceLink = 0;
        favoriteTopic = 0;


        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                final String msg = messageET.getText().toString();
                messageET.setText("");

                displayMessage(chatMessage);

                (new Handler()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ChatMessage chatMessage = new ChatMessage();
                        chatMessage.setId(3);//dummy
                        if (msg.toLowerCase().matches("^hello(.*)")) {
                            chatMessage.setMessage("Hello, how can I help you?");
                        }
                        else if (msg.toLowerCase().matches("(.*)tell(.*)about pernikahan putri jokowi(.*)")) {
                            chatMessage.setMessage(getString(R.string.case1));

                            if(getFavorite == 0) {
                                (new Handler()).postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        ChatMessage chatMessage = new ChatMessage();
                                        chatMessage.setId(3);//dummy
                                        chatMessage.setMessage(getString(R.string.recommend));
                                        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                                        chatMessage.setMe(false);
                                        displayMessage(chatMessage);
                                        getFavorite = 1;
                                    }
                                }, 45000);
                            }
                        }
                        else if (msg.toLowerCase().matches("(.*)show(.*)popular(.*)")) {
                            chatMessage.setMessage(getString(R.string.case2));
                        }
                        else if (msg.toLowerCase().matches("(.*)yes(.*)") && getFavorite == 1) {
                            chatMessage.setMessage(getString(R.string.case3a));
                        }
                        else if (msg.toLowerCase().matches("(.*)more(.*)") && getFavorite == 1) {
                            chatMessage.setMessage(getString(R.string.case3b));
                            getFavorite = 0;
                        }
                        else if (msg.toLowerCase().matches("(.*)no(.*)") && getFavorite == 1) {
                            chatMessage.setMessage("OK then");
                            getFavorite = 0;
                        }
                        else if (msg.toLowerCase().matches("(.*)search(.*)about thor: ragnarok(.*)")) {
                            chatMessage.setMessage(getString(R.string.case4));
                        }
                        else if (msg.toLowerCase().matches("(.*)tell(.*)about timnas indonesia(.*)")) {
                            if (favoriteLink == 1) {
                                chatMessage.setMessage(getString(R.string.case6a));
                            }
                            else {
                                chatMessage.setMessage(getString(R.string.case6b));
                            }
                        }
                        else if (msg.toLowerCase().matches("(.*)prefer(.*)from detik.com")) {
                            chatMessage.setMessage(getString(R.string.case7b));
                            preferenceLink = 1;
                        }
                        else if (msg.toLowerCase().matches("(.*)tell(.*)about bandung(.*)")) {
                            if (preferenceLink == 0) {
                                chatMessage.setMessage(getString(R.string.case7a));
                            }
                            else {
                                chatMessage.setMessage(getString(R.string.case7c));
                            }
                        }
                        else if (msg.toLowerCase().matches("(.*)(block|)(.*)about sandiaga uno(.*)(block|)(.*)")) {
                            chatMessage.setMessage(getString(R.string.case8b));
                            unpreferenceLink = 1;
                        }
                        else if (msg.toLowerCase().matches("(.*)search(.*)about jakarta(.*)")) {
                            if (unpreferenceLink == 0) {
                                chatMessage.setMessage(getString(R.string.case8a));
                            }
                            else {
                                chatMessage.setMessage(getString(R.string.case8c));
                            }
                        }
                        else if (msg.toLowerCase().matches("(.*)like(.*)(about|) raisa(.*)")) {
                            chatMessage.setMessage(getString(R.string.case10c));
                            favoriteTopic = 1;
                        }
                        else if (msg.toLowerCase().matches("(.*)tell(.*)some news(.*)")) {
                            if(favoriteTopic == 0) {
                                chatMessage.setMessage(getString(R.string.case10b));
                            }
                            else {
                                chatMessage.setMessage(getString(R.string.case10a));
                            }
                        }
                        else if (msg.toLowerCase().matches("thank (u|you)(.*)")) {
                            chatMessage.setMessage("You're welcome :)");
                        }
                        else {
                            chatMessage.setMessage("I'm sorry, I can't respond to that");
                        }
                        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                        chatMessage.setMe(false);
                        displayMessage(chatMessage);
                    }
                }, 1000);
            }
        });


        (new Handler()).postDelayed(new Runnable() {

            @Override
            public void run() {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(3);//dummy
                chatMessage.setMessage(getString(R.string.case6c));
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(false);
                displayMessage(chatMessage);
            }
        }, 300000);
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage(getString(R.string.tutorial));
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How can I help you?");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }

    }

}