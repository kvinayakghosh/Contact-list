package com.example.vinya.contactlistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vinya on 10/15/2016.
 */
//Create a custom adapter that extends an ArrayAdapter

public class CustomAdapter extends ArrayAdapter<ContactData> {
    public CustomAdapter(Context context, ArrayList<ContactData> resource) {
        super(context, R.layout.custom_layout, resource);
    }

    //int index;
    CheckBox checkBox;

    @Override
    public View getView(final int position, View row, final ViewGroup parent) {

        LayoutInflater minflater = LayoutInflater.from(getContext());
        row = minflater.inflate(R.layout.custom_layout, parent, false);
        checkBox=(CheckBox)row.findViewById(R.id.checkBox);

        String name=getItem(position).getConName();
        TextView contact_text=(TextView)row.findViewById(R.id.contactView);
        contact_text.setText(name);
//done
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb=(CheckBox)v.findViewById(R.id.checkBox);
                if(cb.isChecked())
                {
                    getItem(position).setForDeletion();
                }
            }
        });
        return row;
    }


}