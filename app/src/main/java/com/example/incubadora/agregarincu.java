package com.example.incubadora;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AuthenticationRequiredException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.incubadora.datos.SingletonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class agregarincu extends AppCompatActivity {

    EditText code;
    EditText name;
    private RequestQueue Queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarincu);
        Queue = SingletonRequest.getInstance(agregarincu.this).getRequestQueue();
        findViewById(R.id.botonincuagregar).setOnClickListener(this::registrar);
        findViewById(R.id.botonincueliminar).setOnClickListener(this::eliminar);
        code = findViewById(R.id.codigoincu);
        name = findViewById(R.id.nameincu);

    }
    private void registrar(View view) {
        String url = "https://escenario.space/api/v1/incubator/addInc";
        JSONObject jo = new JSONObject();
        String token= "2|JenzAKkK0zL1zalaedYSUtghjfpradoyfpBK9Mom";
        try {
            jo.put("code","" + code.getText());
            jo.put("name","" +  name.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.index.class);
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
        }){
            @Override           //PARA PONER ESTO SE ESCRIBE    getHeaders
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }};
        SingletonRequest.getInstance(this).addToRequestQue(carta);

    }

    private void eliminar(View view) {
        String url = "https://escenario.space/api/v1/incubator/delete";
        JSONObject jo = new JSONObject();
        String token= "2|JenzAKkK0zL1zalaedYSUtghjfpradoyfpBK9Mom";

        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.visi.class);
        JsonObjectRequest carta = new JsonObjectRequest(Request.Method.DELETE, url, null,
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
        }){
            @Override           //PARA PONER ESTO SE ESCRIBE    getHeaders
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }};
        SingletonRequest.getInstance(this).addToRequestQue(carta);
    }
}