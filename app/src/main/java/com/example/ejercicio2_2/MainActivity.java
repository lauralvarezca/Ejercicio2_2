package com.example.ejercicio2_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejercicio2_2.Procesos.Procesos;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView txtlista;
    Button btnconsultar;
    RequestQueue requestQueue;
    List<String> argDatos = new ArrayList<>();
    private static final String URL = "https://jsonplaceholder.typicode.com/todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtlista = (ListView) findViewById(R.id.txtLista);
        btnconsultar = (Button) findViewById(R.id.btnconsultar);
        requestQueue = Volley.newRequestQueue(this);
        btnconsultar.setOnClickListener(this::onClick);

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = response.getJSONObject(i);
                                Procesos obj = new Procesos();
                                obj.setUserId(Integer.parseInt(objeto.get("userId").toString()));
                                obj.setId(Integer.parseInt(objeto.get("id").toString()));
                                obj.setTitle(objeto.get("title").toString());
                                obj.setBodycompleted(objeto.get("completed").toString());
                                argDatos.add("UserId: "+obj.getUserId() + "\nID: " + obj.getId() + "\nTitle: " + obj.getTitle() + "\nCompleted: " + obj.getBodycompleted());
                                ArrayAdapter<String> adp = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, argDatos);
                                txtlista.setAdapter(adp);
                            } catch (JSONException e) {
                                message(e.getMessage());
                            }
                        }
                    }
                },
                error -> message(error.getMessage())
        );
        requestQueue.add(jsonRequest);
    }
    private void onClick(View view) {
        Intent ventana = new Intent(getApplicationContext(), ActivityConsulta.class);
        startActivity(ventana);
        finish();
    }

    public void message(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
