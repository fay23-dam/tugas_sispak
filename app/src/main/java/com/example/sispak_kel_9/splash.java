package com.example.sispak_kel_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 5000; // Durasi splash screen dalam milidetik
    private ProgressBar progressBar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar); // Mendapatkan referensi ProgressBar dari layout
        progressBar.setIndeterminate(true); // Mengatur ProgressBar sebagai indeterminate (berputar)

        handler = new Handler();

        // Handler untuk menangani jeda dan pindah ke MainActivity setelah waktu tertentu
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk beralih ke MainActivity
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);

                // Menutup aktivitas splash screen agar tidak dapat kembali
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
