package com.example.she;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity {

    ListView listView;
    DatabaseHandler db;
    ArrayList<Pair<String,String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        listView = findViewById(R.id.list_view);
        db = new DatabaseHandler(this);
        contactList = new ArrayList<>();
        Cursor cursor = db.getAllContacts();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No contacts added!", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                Pair<String,String> contact = new Pair<>(cursor.getString(1),cursor.getString(2));
                contactList.add(contact);
                ContactListAdaptor contactListAdaptor = new ContactListAdaptor(this,contactList);
                listView.setAdapter(contactListAdaptor);
            }
        }
    }
}
