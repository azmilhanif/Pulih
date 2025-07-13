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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.nav_therapist);
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//            if (id == R.id.nav_home) {
//                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0,0);
//                return true;
//            } else if (id == R.id.nav_therapist) {
//                // Already on therapist, do nothing
//                return true;
//            } else if (id == R.id.nav_self_care) {
//                Toast.makeText(MainActivity2.this, "Self Care clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            } else if (id == R.id.nav_Journal) {
//                Toast.makeText(MainActivity2.this, "Journal clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//            return false;
//        });

    }
}
