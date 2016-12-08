package com.example.apest.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.apest.myapplication.adapter.MyListAdapter;
import com.example.apest.myapplication.R;
import com.example.apest.myapplication.service.DataService;
import com.example.apest.myapplication.service.FakeDataService;

public class MainActivity extends AppCompatActivity {

    DataService dataSource = new FakeDataService();
    MyListAdapter myListAdapter = new MyListAdapter(this,dataSource);
    ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListView = (ListView)findViewById(R.id.myListView);
        myListView.setAdapter(myListAdapter);
        myListView.setOnItemClickListener(myListAdapter);
    }
}
