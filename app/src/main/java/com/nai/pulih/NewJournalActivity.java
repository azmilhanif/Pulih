package com.nai.pulih;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nai.pulih.JournalAccess.JournalDatabase;
import com.nai.pulih.JournalAccess.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class NewJournalActivity extends AppCompatActivity {

    private TextView todayDateTv;
    private EditText titleInput, bodyInput;
    private Button saveBtn;

    private JournalDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        todayDateTv = findViewById(R.id.todayDate);
        titleInput = findViewById(R.id.journalTitleInput);
        bodyInput = findViewById(R.id.journalBodyInput);
        saveBtn = findViewById(R.id.saveJournalBtn);

        // Show today's date in yyyy-MM-dd format
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        todayDateTv.setText("Date: " + today);

        db = JournalDatabase.getInstance(this);

        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String body = bodyInput.getText().toString().trim();

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Please enter both title and body", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save journal entry in background thread
            Executors.newSingleThreadExecutor().execute(() -> {
                JournalEntry entry = new JournalEntry();
                entry.title = title;
                entry.body = body;
                entry.date = today;

                db.journalDao().insert(entry);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Journal saved!", Toast.LENGTH_SHORT).show();
                    finish(); // close this screen and go back
                });
            });
        });
    }
}
