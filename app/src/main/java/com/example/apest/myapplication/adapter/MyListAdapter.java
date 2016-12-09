package com.example.apest.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apest.myapplication.R;
import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.service.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apest on 07/12/2016.
 */

public class MyListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    DataService dataService;
    Context context;


    String searchString="";

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public MyListAdapter(Context context, DataService service) {
        this.context = context;
        this.dataService = service;
    }

    /**
     * Filter pets names by searchString
     * @return ArrayList with pets where pet's name contains searchString
     */
    private ArrayList<Pet> getSearchedList()
    {
        ArrayList<Pet> list = new ArrayList<>();

        for (int i = 0 ; i < dataService.getAvailableCount(); i++)
        {
            Pet p = dataService.getItemAt(i);
            if (p.getName().toLowerCase().contains(searchString.toLowerCase()))
            {
                list.add(p);
            }
        }
        return list;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return getSearchedList().size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return getSearchedList().get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return getSearchedList().get(position).getId();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pet item = getSearchedList().get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.logo);
        switch (item.getType()) {
            case CAT:
                imageView.setImageResource(R.mipmap.cat);
                break;
            case DOG:
                imageView.setImageResource(R.mipmap.dog);
                break;
            case PARROT:
                imageView.setImageResource(R.mipmap.parrot);
                break;
            default:
                imageView.setImageResource(R.mipmap.unknown);
                break;
        }
        imageView.setMaxHeight(128);


        TextView textView = (TextView)convertView.findViewById(R.id.textView);
        textView.setText(item.getName());
        TextView textView2 = (TextView)convertView.findViewById(R.id.textView2);
        textView2.setText(item.getAge()+"");

        return convertView;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"click ! <"+ dataService.getItemAt(position).getName() + ">",Toast.LENGTH_LONG).show();
    }
}
