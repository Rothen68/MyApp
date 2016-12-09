package com.example.apest.myapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.model.PetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apest on 09/12/2016.
 */

public class SQLDataService implements DataService{

    private MySQLHelper helper;

    private List<Pet> petList;

    public SQLDataService(Context context) {
        helper = new MySQLHelper(context);
    }


    private void loadPetList()
    {
        petList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String setTables = helper.PET_TABLE_NAME +
                " INNER JOIN " + helper.PET_TYPE_TABLE_NAME + " ON " +
                helper.PET_TABLE_NAME + "." + helper.FK_PET_TYPE + " = " +
                helper.PET_TYPE_TABLE_NAME + "." +helper.PET_TYPE_ID;
        qb.setTables(setTables);

        Cursor c = qb.query(db,null,null,null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int columnIndex = c.getColumnIndex(helper.PET_ID);
            int id = c.getInt(columnIndex);
            String name = c.getString(c.getColumnIndex(helper.PET_NAME));
            int age = c.getInt(c.getColumnIndex(helper.PET_AGE));
            String petTypeName = c.getString(c.getColumnIndex(helper.PET_TYPE_NAME));
            PetType petType = PetType.UNKNOWN;

            for(PetType t : PetType.values())
            {
                if(t.getLabel().equals(petTypeName))
                {
                    petType=t;
                }
            }

            Pet p = new Pet(id,petType, name, age );
            petList.add(p);
            c.moveToNext();
        }
    }

    private List<Pet> getPetList()
    {
        if(petList == null)
        {
            loadPetList();
        }
        return petList;
    }

    private void resetPetList()
    {
        petList = null;
    }

    @Override
    public int getAvailableCount() {


        return getPetList().size();
    }

    @Override
    public Pet getItemAt(int position) {
        return getPetList().get(position);
    }

    @Override
    public Pet getItemById(int id) {
        List<Pet> list = getPetList();
        for(int i = 0 ; i < list.size(); i++)
        {
            if(list.get(i).getId() == id)
            {
                return list.get(i);
            }
        }
        throw new Resources.NotFoundException("Pet " + id + " not found");
    }

    @Override
    public void deleteItemById(int id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int nbreRowAffected = db.delete(helper.PET_TABLE_NAME,helper.PET_ID + " = " + id,null);
        if(nbreRowAffected ==0) {
            throw new Resources.NotFoundException("Pet " + id + " not found");
        }
        resetPetList();
    }

    @Override
    public void setItemById(int petId, Pet pet) {
        SQLiteDatabase db = helper.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(helper.PET_NAME,pet.getName());
        values.put(helper.PET_AGE,pet.getAge());
        values.put(helper.FK_PET_TYPE,getPetTypeIdByPetTypeName(pet.getType().getLabel()));

        db.update(helper.PET_TABLE_NAME,values,helper.PET_ID + " = " + pet.getId(),null);
        resetPetList();
    }

    @Override
    public int addPet(Pet pet) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.PET_NAME,pet.getName());
        values.put(helper.PET_AGE,pet.getAge());
        values.put(helper.FK_PET_TYPE,getPetTypeIdByPetTypeName(pet.getType().getLabel()));

        db.insert(helper.PET_TABLE_NAME,null,values);

        resetPetList();
        return 0;
    }

    private int getPetTypeIdByPetTypeName(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.query(helper.PET_TYPE_TABLE_NAME,
                new String[]{helper.PET_TYPE_ID},
                helper.PET_TYPE_NAME + " = '" + name + "'" ,null,null,null,null
        );
        c.moveToFirst();
        if(c.getCount()!= 1)
        {
            throw new SQLiteException("pet type name not found, pet name : " + name);
        }
        int id = c.getInt(c.getColumnIndex(helper.PET_TYPE_ID));
        return id;
    }
}
