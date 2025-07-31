package com.nai.pulih;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Find views
        MaterialCardView openChatbot = findViewById(R.id.card1);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // "Send Message" button toast
        findViewById(R.id.button).setOnClickListener(view ->
                Toast.makeText(this, "Send Message clicked!", Toast.LENGTH_SHORT).show()
        );

        // When card1 is clicked
        openChatbot.setOnClickListener(v -> {
            // Open chatbot activity
            startActivity(new Intent(MainActivity.this, chatWithAI.class));
            // Highlight the "Therapist" tab in bottom navigation
            bottomNav.setSelectedItemId(R.id.nav_therapist);
        });

        // Handle bottom navigation item clicks
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_therapist) {
                Intent intent = new Intent(MainActivity.this, chatWithAI.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.nav_home) {
                // Already on home, do nothing or refresh
                return true;
            } else if (id == R.id.nav_self_care) {
                Toast.makeText(this, "Self-care selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_Journal) {
                Intent intent = new Intent(MainActivity.this, JournalActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });
    }
}
