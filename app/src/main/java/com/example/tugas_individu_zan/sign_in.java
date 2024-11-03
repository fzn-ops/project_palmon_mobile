package com.example.tugas_individu_zan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sign_in extends AppCompatActivity {
    EditText username_sign, email_sign, password_sign, no_hp_sign;
    Spinner no_telp_awal;
    Button submit_sign;
    RadioGroup group_gender;
    RadioButton g_lelaki, g_perempuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username_sign = findViewById(R.id.sign_username);
        email_sign = findViewById(R.id.sign_email);
        password_sign = findViewById(R.id.sign_password);
        no_hp_sign = findViewById(R.id.sign_no);
        no_telp_awal = findViewById(R.id.Spinner_telp_awal);
        submit_sign = findViewById(R.id.btn_sign);
        group_gender = findViewById(R.id.group_gender);
        g_lelaki = findViewById(R.id.g_laki_laki);
        g_perempuan = findViewById(R.id.g_perempuan);

        // Array untuk awalan nomor telepon
        String[] noAwal = {"+62", "+12", "+1", "+44", "+91"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, noAwal);
        no_telp_awal.setAdapter(adapter);

        submit_sign.setOnClickListener(v -> {
            // Validasi jika semua form diisi dan jenis kelamin dipilih
            if (username_sign.getText().toString().isEmpty() ||
                    email_sign.getText().toString().isEmpty() ||
                    password_sign.getText().toString().isEmpty() ||
                    no_hp_sign.getText().toString().isEmpty() ||
                    group_gender.getCheckedRadioButtonId() == -1) { // Cek apakah gender dipilih

                // toast jika ada data yang belum diisi
                Toast.makeText(sign_in.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
            } else {
                // Mendapatkan gender yang dipilih
                int selectedGenderId = group_gender.getCheckedRadioButtonId();
                RadioButton selectedGender = findViewById(selectedGenderId);
                String gender = selectedGender.getText().toString();

                // menggabungkan nomor telepon dari spinner dan EditText
                String noTelp = no_telp_awal.getSelectedItem().toString() + no_hp_sign.getText().toString();

                // menyimpan data ke SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username_sign.getText().toString());
                editor.putString("email", email_sign.getText().toString());
                editor.putString("no_telp", noTelp);
                editor.putString("gender", gender);
                editor.apply(); // Simpan data

                // Berpindah ke halaman Home
                Intent intent = new Intent(sign_in.this, home.class);
                startActivity(intent);
            }
        });
    }
}
