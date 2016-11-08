package layout;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinya.contactlistapp.AddListAdapter;
import com.example.vinya.contactlistapp.ContactData;
import com.example.vinya.contactlistapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContactFragment extends android.app.Fragment {


    private Activity myActivity;
    private EditText nameDetails;
    private EditText phoneNumber;
    private ListView relationList;
    private Button addPerson;
    private TextView name;
    private TextView phone;
    private TextView relation;
    public ArrayList<ContactData> myList = new ArrayList<ContactData>();

    public boolean l = false;
    get_updated_list new_list;

    public interface get_updated_list{
        public void updated_list(ArrayList<ContactData> list);
    }



    @SuppressWarnings("deprecation")
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                new_list = (get_updated_list) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString());
            }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_contact, container, false);
        addPerson = (Button)v.findViewById(R.id.addPerson);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameDetails.getText().toString();
                String num = phoneNumber.getText().toString();
                ContactData contact = new ContactData();
                contact.setConName(name);
                contact.setPhNumber(num);

                for (int i = 0; i < myList.size(); i++) {
                    if (myList.get(i).getSelected() == true) {
                        ContactData c = myList.get(i);
                        c.setSelected(false);
                        addToRelation(contact, c);
                    }
                }
                nameDetails.setText("");
                phoneNumber.setText("");
                myList.add(contact);
                new_list.updated_list(myList);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myActivity = getActivity();

        Bundle bun=getArguments();
        if(bun!=null)
        {
            myList=(ArrayList<ContactData>) bun.getSerializable("contact_list");

            for(int i=0;i<myList.size();i++)
            {
                ContactData contact=myList.get(i);
                System.out.println(contact.getConName());
            }

        }
        else{
            Log.d("Bundle","Bundle is null");
        }
        nameDetails = (EditText) myActivity.findViewById(R.id.nameText);
        phoneNumber = (EditText) myActivity.findViewById(R.id.numberText);
        name = (TextView) myActivity.findViewById(R.id.contactNameView);
        phone = (TextView) myActivity.findViewById(R.id.contactNumberView);
        relation = (TextView) myActivity.findViewById(R.id.relationView);
        relationList = (ListView) myActivity.findViewById(R.id.listView2);


        ListAdapter addcontactlist = new AddListAdapter(getActivity(), myList);
        Log.d("Activity","The activity called is "+getActivity());
        relationList.setAdapter(addcontactlist);

    }




    public void addToRelation(ContactData c1, ContactData c2) {
        ArrayList<ContactData> rel_list = c1.getRelation();

        for (int j = 0; j < rel_list.size(); j++) {
            if (rel_list.get(j).getConName().equals(c2.getConName()) || c1.getConName().equals(c2.getConName())) {
                return;
            }
        }
        c1.add_relation(c2);
        c2.add_relation(c1);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        }
        else{
            setRetainInstance(true);
        }
    }



}





