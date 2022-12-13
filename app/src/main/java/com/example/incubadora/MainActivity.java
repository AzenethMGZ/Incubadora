package com.example.incubadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.incubadora.datos.SingletonRequest;
import com.example.incubadora.modelos.login;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {


    private RequestQueue Queue;
    EditText email;
    EditText password;
    String tokenusu=" ";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String llave = "sesion";
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.iniciarsesion).setOnClickListener(this::logini);
        findViewById(R.id.botonregis).setOnClickListener(this::regis);
        email = findViewById(R.id.correoini);
        password = findViewById(R.id.passwordinicio);
        preferences = this.getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editor = preferences.edit();
        if(sesion()){
            startActivity(new Intent(this, index.class));
        }
    }

    private boolean sesion(){
        return this.preferences.getBoolean(llave, false);
    }



    private void logini(View view) {
        String url = "https://escenario.space/api/v1/user/logIn";

        JSONObject jo = new JSONObject();

        try {
            jo.put("email","" + email.getText());
            jo.put("password","" + password.getText());
        } catch (JSONException e) {
        }
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.index.class);
        JsonObjectRequest carta1 = new JsonObjectRequest(Request.Method.POST, url,jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                login lo = gson.fromJson(response.toString(), login.class);
                tokenusu = lo.getToken();
                check=true;
                editor.putBoolean(llave,check);
                editor.putString("tokens",tokenusu);
                editor.apply();
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Bundle mibun=new Bundle();
                mibun.putString("token",tokenusu);
                intent.putExtras(mibun);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        SingletonRequest.getInstance(this).addToRequestQue(carta1);

    }


    private void regis(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.Registro.class);
        startActivity(intent);
    }
}