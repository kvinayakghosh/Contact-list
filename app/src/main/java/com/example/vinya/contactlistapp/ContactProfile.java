package com.example.vinya.contactlistapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vinya on 10/15/2016.
 */
public class ContactProfile extends AppCompatActivity {

    private TextView nameDetails;
    private TextView phoneNumber;
    private ListView relationList;
    private TextView name;
    private TextView phone;
    private TextView relation;
    ArrayList<ContactData> relatedList = new ArrayList<ContactData>();
    int position;
    String nameSelected;
    String numberSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_profile);
        Intent intent = getIntent();

        //Code to load the custom layout with the 'Contact Profile' title
        LinearLayout custom_title = (LinearLayout) findViewById(R.id.custom_profileBar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_contact_profile);


        nameDetails = (TextView) findViewById(R.id.nameView);
        phoneNumber = (TextView) findViewById(R.id.numberView);
        name = (TextView) findViewById(R.id.nameTextView);
        phone = (TextView) findViewById(R.id.numberTextView);
        relation = (TextView) findViewById(R.id.relationTextView);
        relationList = (ListView) findViewById(R.id.listView3);

        relatedList=(ArrayList<ContactData>)intent.getSerializableExtra("relation_list");
        nameSelected=intent.getStringExtra("name");
        numberSelected=intent.getStringExtra("number");
        nameDetails.setText(nameSelected);
        phoneNumber.setText(numberSelected);

        ListAdapter relateList=new ContactProfileAdapter(this,relatedList);
        relationList.setAdapter(relateList);

        relationList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {

                        Toast.makeText(getApplicationContext(), relatedList.get(position).getConName().toString()+" selected", Toast.LENGTH_SHORT).show();
                        contactProfile(position);
                    }
                }
        );
    }

    public void contactProfile(int index) {
        Intent intent = new Intent(this, ContactProfile.class);

        String name = relatedList.get(index).getConName();
        String number = relatedList.get(index).getPhNumber();

        intent.putExtra("name", name);
        intent.putExtra("number", number);

        ArrayList<ContactData> relation=relatedList.get(index).getRelation();

        intent.putExtra("relation_list", relation);

        startActivity(intent);
    }

}