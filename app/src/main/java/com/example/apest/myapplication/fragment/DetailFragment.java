package com.example.apest.myapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apest.myapplication.R;
import com.example.apest.myapplication.model.PetType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PET_ID =  "id";
    private static final String PET_NAME = "name";
    private static final String PET_AGE = "age";
    private static final String PET_TYPE = "type";

    private int petId;
    private String petName;
    private int petAge;
    private PetType petType;

    EditText etxtName;
    EditText etxtAge;
    Spinner spType;

    List<String> spList;



    private OnDetailFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance(int id,String name, int age, PetType type) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(PET_ID,id);
        args.putString(PET_NAME, name);
        args.putInt(PET_AGE, age);
        args.putSerializable(PET_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petId = getArguments().getInt(PET_ID);
            petName = getArguments().getString(PET_NAME);
            petAge = getArguments().getInt(PET_AGE);
            petType = (PetType) getArguments().get(PET_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_detail,null);
        etxtName = (EditText) v.findViewById(R.id.etxtName);
        etxtName.setText(petName);
        etxtAge = (EditText) v.findViewById(R.id.etxtAge);
        etxtAge.setText(petAge+"");
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((String)spType.getSelectedItem())
                {
                    case "Parrot":
                        petType = PetType.PARROT;
                        break;
                    case "Cat":
                        petType = PetType.CAT;
                        break;
                    case "Dog":
                        petType =PetType.DOG;
                        break;
                    default :
                        petType = PetType.UNKNOWN;
                }
                mListener.onSaveButtonClick(petId,etxtName.getText().toString(), Integer.parseInt(etxtAge.getText().toString()),petType);
            }
        });

        Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteButtonClick(petId);
            }
        });

        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelButtonClick();
            }
        });

        spType = (Spinner)v.findViewById(R.id.spType);
        spList = new ArrayList<>();
        spList.add("unknown");
        spList.add("Dog");
        spList.add("Cat");
        spList.add("Parrot");

        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,spList);
        spType.setAdapter( spAdapter );
        switch (petType)
        {
            case CAT:
                spType.setSelection(2);
                break;
            case DOG:
                spType.setSelection(1);
                break;
            case PARROT:
                spType.setSelection(3);
                break;
                default:
                    spType.setSelection(0);
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnDetailFragmentInteractionListener {
        void onSaveButtonClick(int id,String name, int Age, PetType petType);
        void onDeleteButtonClick(int id);
        void onCancelButtonClick();
    }
}
