package com.example.incubadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.incubadora.datos.SingletonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    private RequestQueue Queue;
    EditText name;
    EditText email;
    EditText password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Queue = SingletonRequest.getInstance(Registro.this).getRequestQueue();
        findViewById(R.id.registrar).setOnClickListener(this::registrar);
        name = findViewById(R.id.usuario);
        email = findViewById(R.id.correoregis);
        password = findViewById(R.id.passwordregis);

    }

    private void registrar(View view) {
        String url = "https://escenario.space/api/v1/user/register";
        JSONObject jo = new JSONObject();
        try {
            jo.put("name","" + name.getText());
            jo.put("email","" + email.getText());
            jo.put("password","" + password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.MainActivity.class);
        JsonObjectRequest carta = new JsonObjectRequest(Request.Method.POST, url, jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(carta);

    }
}