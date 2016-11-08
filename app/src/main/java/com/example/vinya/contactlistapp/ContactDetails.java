package com.example.vinya.contactlistapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vinya on 10/15/2016.
 */
public class ContactDetails extends AppCompatActivity {

    private EditText nameDetails;
    private EditText phoneNumber;
    private ListView relationList;
    private Button addPerson;
    private TextView name;
    private TextView phone;
    private TextView relation;
    public ArrayList<ContactData> myList = new ArrayList<ContactData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        Intent intent = getIntent();

        //Code to load the custom layout with the 'Contact Details' title
        LinearLayout custom_title = (LinearLayout) findViewById(R.id.custom_detailsBar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_contact_details);


        nameDetails = (EditText) findViewById(R.id.nameText);
        phoneNumber = (EditText) findViewById(R.id.numberText);
        addPerson = (Button) findViewById(R.id.addPerson);
        name = (TextView) findViewById(R.id.contactNameView);
        phone = (TextView) findViewById(R.id.contactNumberView);
        relation = (TextView) findViewById(R.id.relationView);
        relationList = (ListView) findViewById(R.id.listView2);
        myList = (ArrayList) intent.getSerializableExtra("contact_list");
        AddListAdapter addnew= new AddListAdapter(this, myList);
        relationList.setAdapter(addnew);
    }



    public void addPerson(View view) {
        ContactData cData = new ContactData();
        cData.setConName(nameDetails.getText().toString());
        cData.setPhNumber(phoneNumber.getText().toString());
        nameDetails.setText("");
        phoneNumber.setText("");
        Toast.makeText(this, "Contact added!", Toast.LENGTH_LONG).show();
        myList.add(cData);
        if(myList.size()>0) {
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).getSelected() == true) {
                    ContactData c = myList.get(i);
                    c.setSelected(false);
                    addToRelation(cData, c);
                }
            }
        }

        Intent intent = new Intent();
        intent.putExtra("contact_list", myList);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addToRelation(ContactData c1,ContactData c2) {
        ArrayList<ContactData> related_list = c1.getRelation();

        for (int j = 0; j < related_list.size(); j++) {
            if (related_list.get(j).getConName().equals(c2.getConName())||c1.getConName().equals(c2.getConName())) {
                return;
            }
        }
        c1.add_relation(c2);
        c2.add_relation(c1);
        System.out.println(c2.getConName());


    }
}
