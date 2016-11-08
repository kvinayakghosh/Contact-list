package layout;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinya.contactlistapp.ContactData;
import com.example.vinya.contactlistapp.ContactProfileAdapter;
import com.example.vinya.contactlistapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactProfileFragment extends Fragment {
    private Activity myActivity;
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


 /*   TextView name_display_text,phone_display_text;
    ArrayList<ContactData>relation_list=new ArrayList<>();
    String name_to_display;
    String num_to_display;
    ListView relationlist; */
    public ContactProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_profile, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = getActivity();

        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            Log.d("Bundle","BUNDLE NOT NULL");
            nameSelected=bundle.getString("name");
            System.out.println(nameSelected);
            numberSelected=bundle.getString("number");
            relatedList=(ArrayList<ContactData>)bundle.getSerializable("rel_list");
        }
        else{
            Log.d("Bundle","BUNDLE IS NULL");
        }

        nameDetails = (TextView) myActivity.findViewById(R.id.nameView2);
        phoneNumber = (TextView) myActivity.findViewById(R.id.numberView2);
        name = (TextView) myActivity.findViewById(R.id.nameTextView2);
        phone = (TextView) myActivity.findViewById(R.id.numberTextView2);
        relation = (TextView) myActivity.findViewById(R.id.relationTextView2);
        relationList = (ListView) myActivity.findViewById(R.id.listView32);

        nameDetails.setText(nameSelected);
        phoneNumber.setText(numberSelected);
        ListAdapter rel_list_adapter = new ContactProfileAdapter(getActivity(), relatedList);
        relationList.setAdapter(rel_list_adapter);

        relationList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> list,
                                            View row,
                                            int index,
                                            long rowID) {
                        ContactData tem_contact = relatedList.get(index);
                        String name = tem_contact.getConName();
                        String number = tem_contact.getPhNumber();
                        ArrayList<ContactData> rel = tem_contact.getRelation();

                        ContactProfileFragment frag = new ContactProfileFragment();
                        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Bundle bun = new Bundle();
                        bun.putString("name", name);
                        bun.putString("number", number);
                        bun.putSerializable("rel_list", rel);
                        transaction.replace(R.id.frame, frag);
                        transaction.addToBackStack(null);
                        frag.setArguments(bun);
                        transaction.commit();
                    }
                }
        );

    }

}


