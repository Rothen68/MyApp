package com.example.apest.myapplication.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.apest.myapplication.R;

/**
 * Created by apest on 09/12/2016.
 */

public class CustomAlertDialog extends DialogFragment {

    private static final String PET_ID = "PetId";
    private static final String PET_NAME = "PetName";

    private int petId;
    private String petName;
    private OnCustomAlertDialogInteractionListener mListener;

    public static CustomAlertDialog newInstance(int id, String name) {
        CustomAlertDialog fragment = new CustomAlertDialog();
        Bundle args = new Bundle();
        args.putInt(PET_ID,id);
        args.putString(PET_NAME,name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            petId = getArguments().getInt(PET_ID);
            petName = getArguments().getString(PET_NAME);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_item)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDeleteClick(petId);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCancelClick();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnCustomAlertDialogInteractionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnCustomAlertDialogInteractionListener");
        }
    }



    public interface OnCustomAlertDialogInteractionListener
    {
        void onDeleteClick(int petId);
        void onCancelClick();
    }

}
