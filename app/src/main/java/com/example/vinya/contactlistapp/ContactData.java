package com.example.vinya.contactlistapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinya on 10/16/2016.
 */
public class ContactData implements Serializable {
    String conName;
    String phNumber;
    boolean isRelated = false;
    boolean toDelete = false;
    private ArrayList<ContactData> related = new ArrayList<>();


    //return the name of each contact
    public String getConName(){
        return conName;
    }

    //set the name for each contact
    public void setConName(String contact_name) {
        this.conName = contact_name;
    }

    //return the phone number for each contact
    public String getPhNumber(){
        return phNumber;
    }

    //set the phone number for each contact
    public void setPhNumber(String phone_number) {
        this.phNumber = phone_number;
    }

    public void setSelected(boolean val)
    {
        isRelated=val;
    }
    public boolean getSelected()
    {
        return isRelated;
    }
    public void setForDeletion()
    {
        toDelete=true;
    }
    public boolean getForDeletion()
    {
        return toDelete;
    }

    public void add_relation(ContactData c1)
    {
      related.add(c1);
    }

    public ArrayList<ContactData> getRelation()
    {
        return related;
    }

    public void remove_relation(ContactData c2)
    {
        for(int i=0;i<related.size();i++)
        {
            String name1=related.get(i).getConName();
            System.out.println(name1);

            String name2=c2.getConName();
            System.out.println(name2);
            if(name1.equals(name2))
            {
                System.out.println("removing "+c2.getConName());
                related.remove(i);
            }
        }

        for(int j=0;j<related.size();j++) {
            System.out.println("after update "+related.get(j).getConName());
        }
    }



}
