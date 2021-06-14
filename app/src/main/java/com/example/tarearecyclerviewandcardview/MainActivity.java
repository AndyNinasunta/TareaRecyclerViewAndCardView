package com.example.tarearecyclerviewandcardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tarearecyclerviewandcardview.Adapter.RecyclerAdapter;
import com.example.tarearecyclerviewandcardview.Model.Revista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRevista;
    private RecyclerAdapter adapterRevista;


    private EditText edtxtId;
    private ImageView imgFind;
    private RequestQueue requestQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtxtId=(EditText) findViewById(R.id.dtxTypeId);
        imgFind=(ImageButton) findViewById(R.id.imgBtnFind);





        recyclerViewRevista=(RecyclerView) findViewById(R.id.reclyclerRevistas);
        recyclerViewRevista.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRevista.setItemAnimator(new DefaultItemAnimator());




        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCover();

            }
        });
    }







    private void searchCover(){

        String url="https://revistas.uteq.edu.ec/ws/issues.php?j_id="+edtxtId.getText().toString();
        JsonArrayRequest requestJson=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        showCoverText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error al conectarse:"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue= Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    };

    private void showCoverText(JSONArray jArray){
        List<Revista> revistas= new ArrayList<>();
        for(int i=0;i<jArray.length();i++){
            try{
                JSONObject objectJson=new JSONObject(jArray.get(i).toString());

                revistas.add(new Revista(objectJson.getString("title"),objectJson.getString("cover"),objectJson.getString("volume"),objectJson.getString("year"),objectJson.getString("number")));

            }
            catch (JSONException e){
                Toast.makeText(this,"Error al cargar lista: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        adapterRevista=new RecyclerAdapter(MainActivity.this,revistas);

        int id = R.anim.layout_animation_down_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),
                id);
        recyclerViewRevista.setLayoutAnimation(animation);

        recyclerViewRevista.setAdapter(adapterRevista);
    };

}