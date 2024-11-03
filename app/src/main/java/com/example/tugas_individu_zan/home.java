package com.example.tugas_individu_zan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {
    ImageButton gambar_toko, gambar_account;
    String username, email, no_telp, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil data dari Intent yang dikirim oleh halaman sign_in
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        no_telp = intent.getStringExtra("no_telp");
        gender = intent.getStringExtra("gender");

        gambar_toko = findViewById(R.id.t_home_toko);
        gambar_account = findViewById(R.id.taccount);

        // Jika ImageButton diklik, pindah ke halaman account dengan membawa data
        gambar_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, toko.class);
                startActivity(intent);
            }
        });

        gambar_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, account.class);
                startActivity(intent);
            }
        });
    }
}
