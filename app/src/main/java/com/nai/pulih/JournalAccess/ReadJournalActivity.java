package com.nai.pulih.JournalAccess;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.nai.pulih.JournalAccess.JournalDatabase;
import com.nai.pulih.JournalAccess.JournalEntry;
import com.nai.pulih.R;

import java.util.concurrent.Executors;

public class ReadJournalActivity extends AppCompatActivity {

    private TextView textDate, textTitle, textBody;
    private JournalDatabase db;
    private int journalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_journal);

        textDate = findViewById(R.id.textDate);
        textTitle = findViewById(R.id.textTitle);
        textBody = findViewById(R.id.textBody);

        db = JournalDatabase.getInstance(this);

        // Get journal ID from Intent
        journalId = getIntent().getIntExtra("journal_id", -1);
        if (journalId == -1) {
            Toast.makeText(this, "Invalid journal entry", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadJournal();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadJournal() {
        Executors.newSingleThreadExecutor().execute(() -> {
            JournalEntry entry = db.journalDao().getJournalById(journalId);

            if (entry == null) {
                runOnUiThread(() -> {
                    Toast.makeText(ReadJournalActivity.this, "Journal not found", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            runOnUiThread(() -> {
                textDate.setText(entry.date);
                textTitle.setText(entry.title);
                textBody.setText(entry.body);
            });
        });
    }
}
