package com.example.vinya.contactlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vinya on 10/26/2016.
 */
public class ContactProfileAdapter
    extends ArrayAdapter<ContactData>
    {
        public ContactProfileAdapter(Context context, ArrayList<ContactData> resource) {
            super(context, R.layout.contact_profile_layout, resource);
        }

        int index;
        @Override
        public View getView(final int index, View line, final ViewGroup parent) {

            LayoutInflater minflater = LayoutInflater.from(getContext());
            line = minflater.inflate(R.layout.contact_profile_layout, parent, false);

            String name=getItem(index).getConName();
            TextView contact_text=(TextView)line.findViewById(R.id.contactName);
            contact_text.setText(name);

            return line;
        }


    }

