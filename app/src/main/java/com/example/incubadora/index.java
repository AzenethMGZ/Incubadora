package com.example.incubadora;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.incubadora.datos.SingletonRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.example.incubadora.modelos.incubadora;
import com.example.incubadora.modelos.usersres;
import com.example.incubadora.modelos.response;
import com.example.incubadora.modelos.adaptadormiincu;
import com.example.incubadora.modelos.datarespnse;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.incubadora.databinding.ActivityIndexBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class index extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityIndexBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String  token;
    TextView name;
    TextView email;
    TextView men;
    adaptadormiincu adapter;
    List<response> data;
    List<datarespnse> incu;
    List<incubadora> namein;
    List<incubadora> code;

    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user();
        mQueue = SingletonRequest.getInstance(index.this).getRequestQueue();
        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setSupportActionBar(binding.appBarIndex.toolbar);
        name=(TextView) findViewById(R.id.nameuser);
        email=(TextView) findViewById(R.id.correouser);
        men=(TextView) findViewById(R.id.hola);
        Bundle mibun=this.getIntent().getExtras();
        if(mibun!=null){
            token = mibun.getString("token");
            email.setText("" + token);

        }

        editor.putString("token",token);
        editor.commit();
        data = new ArrayList<>();
        incu = new ArrayList<>();
        code = new ArrayList<>();
        namein = new ArrayList<>();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_index);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.vitantesmenu){
            Intent intent = new Intent(getApplicationContext(), com.example.incubadora.agregarincu.class);
            startActivity(intent);
        } else if(id==R.id.agregarvisi){
            Intent intent = new Intent(getApplicationContext(), com.example.incubadora.agregarvisi.class);
            startActivity(intent);
        }else if (id == R.id.cerrarsesionmenu) {
            editor.putBoolean("sesion", false);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), com.example.incubadora.MainActivity.class);
            startActivity(intent);
            String url = "https://escenario.space/api/v1/user/logOut";

            Intent intent2 = new Intent(getApplicationContext(), com.example.incubadora.MainActivity.class);
            JsonObjectRequest carta = new JsonObjectRequest(Request.Method.DELETE, url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            startActivity(intent2);

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


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_index);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void agregarincu(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.agregarincu.class);
        startActivity(intent);
    }
    private void agregarvisi(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.agregarvisi.class);
        startActivity(intent);
    }

    private void user() {
        String url = "https://escenario.space/api/v1/user/info";

        Intent intent = new Intent(getApplicationContext(), com.example.incubadora.visi.class);
        JsonObjectRequest carta = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        usersres num = gson.fromJson(String.valueOf(response), (Type) usersres.class);
                        name.setText("" + num.getName());
                        email.setText("" + num.getEmail());

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


    public void ganadores() {
        String urlApi = "https://ramiro.uttics.com/api/ganadores";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlApi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RecyclerView recyclerView = findViewById(R.id.incuadmin);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Gson gson = new Gson();
                response in = gson.fromJson(response.toString(), response.class);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(request);
    }


}