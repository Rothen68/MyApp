package com.example.apest.myapplication.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.apest.myapplication.R;
import com.example.apest.myapplication.fragment.CustomAlertDialog;
import com.example.apest.myapplication.fragment.DetailFragment;
import com.example.apest.myapplication.fragment.MasterFragment;
import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.model.PetType;
import com.example.apest.myapplication.service.DataService;
import com.example.apest.myapplication.service.FakeDataService;

import javax.sql.DataSource;


public class MainActivity extends AppCompatActivity implements MasterFragment.OnMasterFragmentInteractionListener,
        DetailFragment.OnDetailFragmentInteractionListener,
        CustomAlertDialog.OnCustomAlertDialogInteractionListener{

    private static final int SEARCH_ACTIVITY = 1;

    DataService dataSource ;

    String searchString="";

    int selectedPetId=-1;

    FloatingActionButton btnAddPet;

    MasterFragment masterFragment;
    DetailFragment detailFragment;

    public DataService getDataSource()
    {
        return dataSource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masterFragment = MasterFragment.newInstance(searchString);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.fragMasterLayout,masterFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        ft.addToBackStack("Master");

        ft.commit();

        btnAddPet = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPetId = dataSource.addPet(new Pet(0, PetType.UNKNOWN, "New Pet",0));
                masterFragment.onDatasetChanged();
            }
        });

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
            case R.id.web:
                Intent j = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.fr/search?q=pet"));
                startActivity(j);
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
                            searchString=data.getStringExtra("SEARCH");
                            masterFragment.setSearchString(searchString);
                        }
                    }
                }
        }
    }

    @Override
    public void onItemClick(int itemId) {
        selectedPetId = itemId;
        ViewGroup vg = (ViewGroup) findViewById(R.id.fragDetailLayout);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Pet p = dataSource.getItemById(itemId);
        detailFragment = DetailFragment.newInstance(p.getId(),p.getName(),p.getAge(),p.getType());

        if(vg.getVisibility() == View.GONE)
        {
            ft.replace(R.id.fragMasterLayout, detailFragment);
            ft.addToBackStack("Detail");
        }
        else
        {
            ft.replace(R.id.fragDetailLayout,detailFragment);
        }
        ft.commit();
    }

    @Override
    public DataService getFragmentDataSource() {
        if(dataSource == null)
        {
            dataSource = new FakeDataService();
        }

        return dataSource;
    }

    @Override
    public void onSaveButtonClick(int id, String name, int age, PetType type) {
        Pet p = dataSource.getItemById(id);
        p.setName(name);
        p.setAge(age);
        p.setType(type);
        dataSource.setItemById(id,p);
        removeDetailFragment();

    }

    @Override
    public void onDeleteButtonClick(int id) {
        CustomAlertDialog alertDialog = CustomAlertDialog.newInstance(selectedPetId,dataSource.getItemById(selectedPetId).getName());
        alertDialog.show(getFragmentManager(),"Delete");
    }

    @Override
    public void onCancelButtonClick() {
        removeDetailFragment();
    }

    private void removeDetailFragment()
    {
        selectedPetId=-1;
        ViewGroup vg = (ViewGroup) findViewById(R.id.fragDetailLayout);

        if(vg.getVisibility() == View.GONE) //portrait
        {
            onBackPressed();
        }
        else
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(detailFragment);
            ft.commit();
        }
    }

    @Override
    public void onDeleteClick(int petId) {
        dataSource.deleteItemById(petId);
        masterFragment.onDatasetChanged();
        removeDetailFragment();
    }

    @Override
    public void onCancelClick() {

    }
}
