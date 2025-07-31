package com.nai.pulih;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nai.pulih.JournalAccess.JournalAdapter;
import com.nai.pulih.JournalAccess.JournalDatabase;
import com.nai.pulih.JournalAccess.JournalEntry;

import java.util.List;
import java.util.concurrent.Executors;

public class JournalActivity extends AppCompatActivity {

    private RecyclerView journalRecyclerView;
    private JournalAdapter journalAdapter; // You need to create this adapter
    private JournalDatabase db;
    private FloatingActionButton addJournalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_layout); // Your coordinator layout with recycler + fab + bottom nav

        db = JournalDatabase.getInstance(this);

        journalRecyclerView = findViewById(R.id.journalRecyclerView);
        journalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        journalAdapter = new JournalAdapter();
        journalRecyclerView.setAdapter(journalAdapter);

        addJournalButton = findViewById(R.id.addJournalButton);
        addJournalButton.setOnClickListener(v -> {
            startActivity(new Intent(this, NewJournalActivity.class));
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_Journal);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(JournalActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_therapist) {
                startActivity(new Intent(JournalActivity.this, chatWithAI.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_self_care) {
                Toast.makeText(this, "Self-care selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_Journal) {
                return true;
            }
            return false;
        });

        loadJournals();
    }

    private void loadJournals() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<JournalEntry> entries = db.journalDao().getAllJournals();

            runOnUiThread(() -> {
                journalAdapter.setJournals(entries);
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJournals(); // Refresh list when returning from NewJournalActivity
    }
}
