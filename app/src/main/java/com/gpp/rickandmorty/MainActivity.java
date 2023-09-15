package com.gpp.rickandmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String url_next_page;
    int next_page = 2;

    RecyclerAdapter adapterRecycler;

    private EditText editTextSearch;

    Boolean alive = false, dead = false, unknown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewResults);

        editTextSearch = findViewById(R.id.editTextSearch);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        CheckBox checkBoxFilter1 = findViewById(R.id.checkBoxFilter1);
        CheckBox checkBoxFilter2 = findViewById(R.id.checkBoxFilter2);
        CheckBox checkBoxFilter3 = findViewById(R.id.checkBoxFilter3);

        //Carga de datos inicial
        ArrayList<MyCharacter> characters = new ArrayList<>();
        getData(characters);
        adapterRecycler = new RecyclerAdapter(this, characters);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                // Si el último elemento visible es el último de la lista
                if (lastVisibleItem == totalItemCount - 1) {
                    loadNextPage();

                }
            }
        });

        // Manejo del botón de búsqueda
        buttonSearch.setOnClickListener(v -> searchData());

        checkBoxFilter1.setOnClickListener(view -> {
            alive = !alive;
            dead = false;
            unknown = false;
            checkBoxFilter2.setChecked(false);
            checkBoxFilter3.setChecked(false);
            searchData();
        });

        checkBoxFilter2.setOnClickListener(view -> {
            dead = !dead;
            alive = false;
            unknown = false;
            checkBoxFilter1.setChecked(false);
            checkBoxFilter3.setChecked(false);
            searchData();
        });

        checkBoxFilter3.setOnClickListener(view -> {
            unknown = !unknown;
            alive = false;
            dead = false;
            checkBoxFilter1.setChecked(false);
            checkBoxFilter2.setChecked(false);
            searchData();
        });


    }

    private void loadNextPage() {
        ArrayList<MyCharacter> characters = new ArrayList<>();
        String vl_statusFilter = getStatusFilter();
        url_next_page = "https://rickandmortyapi.com/api/character?page="+ next_page +vl_statusFilter;
        String vl_name = editTextSearch.getText().toString();
        if(vl_name.compareTo("") != 0)
            url_next_page = "https://rickandmortyapi.com/api/character?page="+ next_page +"&name="+vl_name+vl_statusFilter;
        next_page++;

        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.GET, url_next_page, null, response -> {
            try {
                JSONArray arrayJSON = response.getJSONArray("results");
                Log.v("respuesta",arrayJSON.toString());

                for (int i=0;i<arrayJSON.length();i++){

                    JSONObject resultado = arrayJSON.getJSONObject(i);
                    String name = (String) resultado.get("name");
                    String status = (String) resultado.get("status");
                    String species = (String) resultado.get("species");
                    String gender = (String) resultado.get("gender");
                    String type = (String) resultado.get("type");
                    String image = (String) resultado.get("image");
                    MyCharacter p = new MyCharacter(name,status,species,type,gender,image);
                    characters.add(p);
                }
                adapterRecycler.addData(characters);
                adapterRecycler.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionJSON);

    }

    private void getData(final ArrayList<MyCharacter> lista) {
        String url = "https://rickandmortyapi.com/api/character";
        url_next_page = "https://rickandmortyapi.com/api/character?page="+ next_page;
        next_page++;

        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray arrayJSON = response.getJSONArray("results");
                Log.v("respuesta",arrayJSON.toString());

                for (int i=0;i<arrayJSON.length();i++){

                    JSONObject resultado = arrayJSON.getJSONObject(i);
                    String name = (String) resultado.get("name");
                    String status = (String) resultado.get("status");
                    String species = (String) resultado.get("species");
                    String gender = (String) resultado.get("gender");
                    String type = (String) resultado.get("type");
                    String image = (String) resultado.get("image");
                    MyCharacter p = new MyCharacter(name,status,species,type,gender,image);
                    lista.add(p);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionJSON);
    }


    private void searchData() {
        String vl_statusFilter = getStatusFilter();
        String vl_name = editTextSearch.getText().toString();
        String url = "https://rickandmortyapi.com/api/character/?name="+vl_name+vl_statusFilter;
        next_page = 2;
        if(vl_name.compareTo("") != 0)
            url_next_page = "https://rickandmortyapi.com/api/character?page="+ next_page +"&name="+vl_name+vl_statusFilter;
        next_page++;

        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray arrayJSON = response.getJSONArray("results");
                Log.v("respuesta:",arrayJSON.toString());
                ArrayList<MyCharacter> characters = new ArrayList<>();

                for (int i=0;i<arrayJSON.length();i++){

                    JSONObject resultado = arrayJSON.getJSONObject(i);
                    String name = (String) resultado.get("name");
                    String status = (String) resultado.get("status");
                    String species = (String) resultado.get("species");
                    String gender = (String) resultado.get("gender");
                    String type = (String) resultado.get("type");
                    String image = (String) resultado.get("image");
                    MyCharacter p = new MyCharacter(name,status,species,type,gender,image);
                    characters.add(p);
                }

                adapterRecycler.deleteData();
                adapterRecycler.addData(characters);
                adapterRecycler.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticionJSON);
    }

    private String getStatusFilter() {
        String vl_status_filter = "";

        if(alive){
            vl_status_filter += "&status=alive";
        }
        if(dead){
            vl_status_filter += "&status=dead";
        }
        if(unknown){
            vl_status_filter += "&status=unknown";
        }

        return vl_status_filter;
    }


}