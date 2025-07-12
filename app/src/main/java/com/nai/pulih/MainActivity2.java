package com.nai.pulih;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nai.pulih.MainActivity;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Navigate to MainActivity
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_therapist) {
                    Toast.makeText(MainActivity2.this, "Therapist clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_selfcare) {
                    Toast.makeText(MainActivity2.this, "Self Care clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_journal) {
                    Toast.makeText(MainActivity2.this, "Journal clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });
    }
}
