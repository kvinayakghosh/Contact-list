package com.example.vinya.contactlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vinya on 10/26/2016.
 */
public class AddListAdapter extends ArrayAdapter<ContactData> {
    public AddListAdapter(Context context, ArrayList<ContactData> resource) {
        super(context, R.layout.add_list_layout, resource);
    }

    int index;
    CheckBox checkBox;
    TextView contactName;

    @Override
    public View getView(final int index, View line, final ViewGroup parent) {

        LayoutInflater minflater = LayoutInflater.from(getContext());
        line = minflater.inflate(R.layout.add_list_layout, parent, false);
        checkBox=(CheckBox)line.findViewById(R.id.checkedBox);

        String cName=getItem(index).getConName();
        System.out.println(cName+" is adding to adapter");
        contactName=(TextView)line.findViewById(R.id.contactView);
        contactName.setText(cName);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb=(CheckBox)v.findViewById(R.id.checkedBox);
                if(cb.isChecked())
                {
                    getItem(index).setSelected(true);
                }
            }
        });
        return line;
    }


}