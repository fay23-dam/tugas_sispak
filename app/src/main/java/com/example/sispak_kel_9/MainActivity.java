package com.example.sispak_kel_9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tentang(View view) {
        Intent intent = new Intent(MainActivity.this, tentang.class);
        startActivity(intent);
    }
    public void daftar_penyakit(View view) {
        Intent intent = new Intent(MainActivity.this, daftar_penyakit.class);
        startActivity(intent);
    }
    public void deteksi(View view) {
        Intent intent = new Intent(MainActivity.this, deteksi.class);
        startActivity(intent);
    }

}