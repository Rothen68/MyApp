package com.example.apest.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apest.myapplication.R;
import com.example.apest.myapplication.adapter.MyListAdapter;
import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.service.DataService;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterFragment.OnMasterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterFragment extends Fragment implements ListView.OnItemClickListener {

    private static final String SEARCH_STRING = "SearchString";
    private String searchString;

    private ListView listView;
    private MyListAdapter adapter;
    private DataService datasource;


    private OnMasterFragmentInteractionListener mListener;

    public MasterFragment() {
        // Required empty public constructor
    }


    public void setSearchString(String search)
    {
        searchString = search;
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }
    }


    public static MasterFragment newInstance( String search) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_STRING, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchString = getArguments().getString(SEARCH_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate( R.layout.frag_list,null);
        listView = (ListView) v.findViewById(R.id.myListView);
        adapter = new MyListAdapter(getContext(),mListener.getFragmentDataSource());
        adapter.setSearchString(searchString);
        listView.setOnItemClickListener(this);

        listView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMasterFragmentInteractionListener) {
            mListener = (OnMasterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMasterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onDatasetChanged()
    {
        adapter.notifyDataSetChanged();
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
        mListener.onItemClick(((Pet)adapter.getItem(position)).getId());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMasterFragmentInteractionListener {
        void onItemClick(int itemId);
        DataService getFragmentDataSource();
    }
}
