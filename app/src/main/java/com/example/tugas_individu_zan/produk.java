package com.example.tugas_individu_zan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class produk extends AppCompatActivity {
    EditText jmlh_barang_sawit, alamat_sawit;
    Spinner pembayaran_sawit;
    Button pesan_sawit;
    Switch garansi_sawit;
    TextView total_harga_sawit;

    // Harga satuan produk
    final int hargaSatuan = 200;
    int totalHarga = 0;
    boolean garansiAktif = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produk);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        jmlh_barang_sawit = findViewById(R.id.jumlah_barang_bibit_sawit);
        alamat_sawit = findViewById(R.id.alamat);
        pembayaran_sawit = findViewById(R.id.metode_pembayaran_sawit);
        pesan_sawit = findViewById(R.id.t_produk_pesan);
        garansi_sawit = findViewById(R.id.switch_garansi);
        total_harga_sawit = findViewById(R.id.harga_total_bibit_sawit);

        // Set metode pembayaran
        String[] metode = {"DANA", "BCA", "Qris"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, metode);
        pembayaran_sawit.setAdapter(adapter);

        // Perhitungan total harga saat jumlah barang diinputkan
        jmlh_barang_sawit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hitungTotalHarga();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Tambah biaya garansi saat Switch diaktifkan
        garansi_sawit.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            garansiAktif = isChecked;
            hitungTotalHarga();
        });

        // Saat tombol pesan ditekan
        pesan_sawit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validasi input
                if (jmlh_barang_sawit.getText().toString().isEmpty() || alamat_sawit.getText().toString().isEmpty()) {
                    Toast.makeText(produk.this, "Anda belum melengkapi pemesanan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(produk.this, "Terima kasih telah melakukan pemesanan", Toast.LENGTH_SHORT).show();
                    // Berpindah ke halaman Home
                    Intent intent = new Intent(produk.this, home.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Fungsi untuk menghitung total harga
    private void hitungTotalHarga() {
        int jumlahBarang = 0;
        if (!jmlh_barang_sawit.getText().toString().isEmpty()) {
            jumlahBarang = Integer.parseInt(jmlh_barang_sawit.getText().toString());
        }

        totalHarga = jumlahBarang * hargaSatuan;

        // Jika garansi diaktifkan, tambahkan Rp 1.000
        if (garansiAktif) {
            totalHarga += 1000;
        }

        // Update TextView total harga
        total_harga_sawit.setText("Rp " + totalHarga);
    }
}
