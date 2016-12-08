package com.example.apest.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apest.myapplication.R;

public class SearchActivity extends AppCompatActivity {

    EditText etxtSearch;
    Button searchButton;
    Button cancelButton;

    public static final int SEARCH=1;
    public static final int CANCEL =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etxtSearch = (EditText) findViewById(R.id.etxtSearch);
        searchButton = (Button) findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String searchString = etxtSearch.getText().toString();
                i.putExtra("SEARCH",searchString);
                setResult(SEARCH, i);
                finish();
            }
        });

        cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CANCEL);
                finish();
            }
        });
    }
}
