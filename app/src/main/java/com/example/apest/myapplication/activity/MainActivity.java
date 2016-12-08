package com.example.apest.myapplication.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.apest.myapplication.adapter.MyListAdapter;
import com.example.apest.myapplication.R;
import com.example.apest.myapplication.service.DataService;
import com.example.apest.myapplication.service.FakeDataService;

public class MainActivity extends AppCompatActivity {

    private static final int SEARCH_ACTIVITY = 1;

    DataService dataSource = new FakeDataService();
    MyListAdapter myListAdapter = new MyListAdapter(this, dataSource);
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(myListAdapter);
        myListView.setOnItemClickListener(myListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
                Intent i = new Intent(this, SearchActivity.class);
                startActivityForResult(i,SEARCH_ACTIVITY);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case SEARCH_ACTIVITY:
                switch (resultCode)
                {
                    case SearchActivity.SEARCH:
                    {
                        if(data != null)
                        {
                            myListAdapter.setSearchString(data.getStringExtra("SEARCH"));
                            myListAdapter.notifyDataSetChanged();
                        }
                    }
                }
        }
    }
}
