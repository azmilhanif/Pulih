package com.nai.pulih;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class chatWithAI extends AppCompatActivity {

    private DialogflowHelper dialogflowHelper;
    private EditText promptTf;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_with_ai);

        promptTf = findViewById(R.id.messageInput);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        dialogflowHelper = new DialogflowHelper(this);

        findViewById(R.id.sendButton).setOnClickListener(v -> {
            String message = promptTf.getText().toString().trim();
            if (!message.isEmpty()) {
                addMessageToChat(message, Message.USER);
                promptTf.setText("");
                sendMessageToDialogflow(message);
            }
        });
    }

    private void addMessageToChat(String text, int sender) {
        messageList.add(new Message(text, sender));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    private void sendMessageToDialogflow(String message) {
        dialogflowHelper.detectIntent(message, new DialogflowHelper.DialogflowCallback() {
            @Override
            public void onResponse(String responseText) {
                runOnUiThread(() -> {
                    addMessageToChat(responseText, Message.BOT);
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    addMessageToChat("Error: " + e.getMessage(), Message.BOT);
                });
            }
        });
    }
}
