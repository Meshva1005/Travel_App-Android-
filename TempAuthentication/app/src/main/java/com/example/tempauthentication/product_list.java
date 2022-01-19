package com.example.tempauthentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class product_list extends AppCompatActivity {
    private List<travel> travelDb = new ArrayList<>();

    private RecyclerView rView;
    private RecyclerView.Adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        travel bloodRed = new travel(R.drawable.cn,"Toronto","CN Tower");
        travel GreenMoon = new travel(R.drawable.newyork,"New York","NY City");
        travel NameBoy = new travel(R.drawable.bridge,"Rio","Bridge");
        travel lon = new travel(R.drawable.london,"London","City Eye");
        travel taj = new travel(R.drawable.taj,"Agra","Taj Mahal");




        travelDb.add(bloodRed);
        travelDb.add(GreenMoon);
        travelDb.add(NameBoy);
        travelDb.add((lon));
        travelDb.add(taj);
        adapter= new travelAdapter(travelDb);

        rView = findViewById(R.id.rView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rView.setLayoutManager(manager);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(this, 1));
        rView.setAdapter(adapter);
    }
}