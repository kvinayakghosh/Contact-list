package com.example.vinya.contactlistapp;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import layout.AddContactFragment;
import layout.ContactProfileFragment;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements AddContactFragment.get_updated_list {

    ListView lView;
    Context context = MainActivity.this;
    ArrayList<ContactData> myList = new ArrayList<ContactData>();
    Button add;
    Button delete;
    public static final int CODE = 111;

    @Override
    public void onPause() {
        super.onPause(); // always call super
        System.out.println("paused");
    }
    @Override
    public void onResume() {
        super.onResume(); // always call super
        System.out.println("resumed");
    }
    @Override
    public void onStop() {
        super.onStop(); // always call super
    }
    @Override
    public void onStart() {
        // always call super
        super.onStart();
        System.out.println("started");
    }
    @Override
    public void onRestart() {
        super.onRestart(); // always call super
        System.out.println("restarted");
    }
    @Override
    public void onDestroy() {
        super.onDestroy(); // always call super
        android.os.Debug.stopMethodTracing();

        System.out.println("destroyed");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate");

        //Code to load the custom layout with the 'Contact List' title
        LinearLayout custom_title = (LinearLayout) findViewById(R.id.custom_listBar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_contact_list);


        lView = (ListView) findViewById(R.id.contactListView);
        add = (Button) findViewById(R.id.AddNew);
        delete = (Button) findViewById(R.id.Delete);

        if(savedInstanceState!=null)
        {
            myList=(ArrayList<ContactData>)savedInstanceState.getSerializable("list");
            ListAdapter custom= new CustomAdapter(this, myList);
            lView.setAdapter(custom);
            Log.d("adapter set","lView set");
            Log.d("saved instance state", "It is" + savedInstanceState);

        }
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Toast.makeText(MainActivity.this, myList.get(position).getConName().toString() + " selected", Toast.LENGTH_SHORT).show();
                    landscapeContactProfile(position);
                }
                else{
                    Toast.makeText(MainActivity.this, myList.get(position).getConName().toString() + " selected", Toast.LENGTH_SHORT).show();
                    contactProfile(position);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addNew();
            }
        });


    }



    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        Log.d("onSaveInstanceState","onSaveInstanceState called");
        super.onSaveInstanceState(outState);
        outState.putSerializable("list",myList);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CODE) {

            myList = (ArrayList<ContactData>) intent.getSerializableExtra("contact_list");

            System.out.println("After adding in portraitview");
            for(int i=0;i<myList.size();i++)
            {
                ContactData contact=myList.get(i);
                System.out.println(contact.getConName());
            }

            ListAdapter Custom = new CustomAdapter(this, myList);
            lView.setAdapter(Custom);
        }

    }




    public void contactProfile(int position) {
        Intent intent = new Intent(this, ContactProfile.class);
        ContactData c1=myList.get(position);
        ArrayList<ContactData>relation;
        relation=c1.getRelation();
        intent.putExtra("relation_list",relation);


        String cName=myList.get(position).getConName();
        String phoneNum=myList.get(position).getPhNumber();

        intent.putExtra("name", cName);
        intent.putExtra("number", phoneNum);

        startActivity(intent);
    }


public void landscapeContactProfile(int position){
    if(myList.size()>0) {
        ContactData tem_contact = myList.get(position);
        String name = tem_contact.getConName();
        String number = tem_contact.getPhNumber();
        ArrayList<ContactData> rel = tem_contact.getRelation();

        ContactProfileFragment frag = new ContactProfileFragment();
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("number", number);
        b.putSerializable("rel_list", rel);
        transaction.replace(R.id.frame, frag);
        transaction.addToBackStack(null);
        frag.setArguments(b);
        transaction.commit();
    }

}

    public void updated_list(ArrayList<ContactData> list)
    {
        myList=list;

        System.out.println("After adding in landscapeview");
        for(int i=0;i<myList.size();i++)
        {
            ContactData contact=myList.get(i);
            System.out.println(contact.getConName());
        }
        ListAdapter CustomAdapter = new CustomAdapter(this, myList);
        lView.setAdapter(CustomAdapter);
    }


    public void addNew() {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AddContactFragment frag=new AddContactFragment();
            android.app.FragmentTransaction transaction=getFragmentManager().beginTransaction();
            Bundle bun=new Bundle();
            bun.putSerializable("contact_list",myList);
            transaction.replace(R.id.frame,frag);
            transaction.addToBackStack(null);
            frag.setArguments(bun);
            transaction.commit();
        }
        else {
            Intent intent = new Intent(this, ContactDetails.class);
            intent.putExtra("contact_list", myList);
            startActivityForResult(intent, CODE);
        }
    }

    public void deleteContact(View view) {


        for(int i=0;i<myList.size();i++)
        {
            if(myList.get(i).getForDeletion()==true)
            {
                for(int j=0;j<myList.size();j++)
                {
                    update_relation(myList.get(j),myList.get(i));
                }
                myList.remove(i);
            }
        }

        System.out.println("After deleting in portraitview");
        for(int i=0;i<myList.size();i++)
        {
            ContactData contact=myList.get(i);
            System.out.println(contact.getConName());
        }
        ListAdapter CustomAdapter = new CustomAdapter(this, myList);
        lView.setAdapter(CustomAdapter);
    }


    public void update_relation(ContactData c1,ContactData c2)
    {
        for(int j=0;j<c2.getRelation().size();j++)
        {

            c1.remove_relation(c2);
        }
    }




    public void putInSharedPref()
    {
        SharedPreferences sharedPref = getSharedPreferences("contactlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();

        for(int i = 0; i<myList.size();i++)
        {
            edit.putString("name"+i,myList.get(i).getConName().toString());
            edit.putString("number"+i,myList.get(i).getPhNumber().toString());

//            edit.putStringSet("relation"+i,myList.get(i).getRelation().toString());
        }
        edit.commit();
        //edit.putString("name",nameDetails.getText().toString());
        //edit.putString("phone",phoneNumber.getText().toString());*/

    }
    public void loadFromSharedPref() {

        SharedPreferences sharedPref = getSharedPreferences("contactlist",Context.MODE_PRIVATE);
        int j = sharedPref.getAll().size();
        Log.d("The elements", "It is: "+ j);
        for(int i = 0; i < sharedPref.getAll().size(); i++) {

        String cName = sharedPref.getString("name" + i,"");
        String phNo = sharedPref.getString("phone" + i,"");
            Log.d("contact number:","we've reached position " + i + " with name: "+ cName + " and phone number: " + phNo);
        ContactData cData = new ContactData();
        cData.setConName(cName);
        cData.setPhNumber(phNo);
        myList.add(cData);
        }
    }


}
