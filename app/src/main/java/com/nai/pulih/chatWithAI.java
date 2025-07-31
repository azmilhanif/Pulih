package com.nai.pulih;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.ArrayList;

public class chatWithAI extends AppCompatActivity {

    private DialogflowHelper dialogflowHelper;
    private EditText promptTf;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_ai);

        // Set up chat UI
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

        // ✅ Bottom Navigation Setup
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_therapist); // Highlight current tab

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(chatWithAI.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_Journal) {
                startActivity(new Intent(chatWithAI.this, JournalActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_self_care) {
                Toast.makeText(this, "Self-care selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_therapist) {
                // Already on this tab
                return true;
            }
            return false;
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
                runOnUiThread(() -> addMessageToChat(responseText, Message.BOT));
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> addMessageToChat("Error: " + e.getMessage(), Message.BOT));
            }
        });
    }
}
