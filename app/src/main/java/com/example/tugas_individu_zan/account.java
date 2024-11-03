package com.example.tugas_individu_zan;

import android.app.DatePickerDialog; // Pastikan ini diimpor
import android.content.SharedPreferences; // Tambahkan impor ini
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class account extends AppCompatActivity {
    TextView username, email, no_telp, gender, ttl;
    RatingBar ulasan;
    ImageButton ttl_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username_account);
        email = findViewById(R.id.email_account);
        no_telp = findViewById(R.id.handphone_account);
        gender = findViewById(R.id.gender_account);
        ttl = findViewById(R.id.user_lahir_account);
        ulasan = findViewById(R.id.ulasan_account);
        ttl_user = findViewById(R.id.t_calendar);

        // Ambil data dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String usernameData = sharedPreferences.getString("username", "");
        String emailData = sharedPreferences.getString("email", "");
        String noTelpData = sharedPreferences.getString("no_telp", "");
        String genderData = sharedPreferences.getString("gender", "");
        String birthdate = sharedPreferences.getString("birthdate", ""); // Ambil tanggal lahir

        // Set data ke TextView
        username.setText(usernameData);
        email.setText(emailData);
        no_telp.setText(noTelpData);
        gender.setText(genderData);
        ttl.setText(birthdate); // Tampilkan tanggal lahir

        // Setup DatePicker dan RatingBar
        ttl_user.setOnClickListener(v -> {
            // Munculkan DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    account.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Update TextView ttl dengan tanggal yang dipilih
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        ttl.setText(selectedDate);

                        // Simpan tanggal ke SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("birthdate", selectedDate);
                        editor.apply(); // Simpan tanggal
                    },
                    2000, 0, 1); // Default ke 1 Januari 2000

            datePickerDialog.show();
        });

        // Cek apakah rating sudah diberikan
        float savedRating = sharedPreferences.getFloat("rating", 0);
        if (savedRating > 0) {
            ulasan.setRating(savedRating);
            ulasan.setIsIndicator(true); // Cegah perubahan rating
        }

        ulasan.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                // Munculkan toast dan cegah perubahan rating
                Toast.makeText(account.this, "Terima kasih atas penilaian anda", Toast.LENGTH_SHORT).show();

                // Simpan rating di SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("rating", rating);
                editor.apply(); // Simpan rating

                // Cegah perubahan rating lagi
                ulasan.setIsIndicator(true);
            }
        });
    }
}
