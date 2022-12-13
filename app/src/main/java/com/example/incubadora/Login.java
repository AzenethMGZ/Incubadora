package com.example.incubadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent bien = new Intent(this , MainActivity.class);

        long total = 6000;


        new CountDownTimer(total, 1000) {

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                startActivity(bien);
                finish();
            }
        }.start();
    }

}