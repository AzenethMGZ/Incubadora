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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.incubadora.datos.SingletonRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.incubadora.databinding.ActivityIndexBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class index extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityIndexBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setSupportActionBar(binding.appBarIndex.toolbar);
        men=(TextView) findViewById(R.id.token);
        Bundle mibun=this.getIntent().getExtras();
        if(mibun!=null) {
          String  token = mibun.getString("token");
          editor.putString("token",token);
            men.setText(token);
        }

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
            String token= "2|JenzAKkK0zL1zalaedYSUtghjfpradoyfpBK9Mom";

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
}