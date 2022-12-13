package com.example.incubadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class visi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi);
        findViewById(R.id.visinuevo).setOnClickListener(this::agregarvisi);
    }
    private void agregarvisi(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.agregarvisi.class);
        startActivity(intent);
    }
}