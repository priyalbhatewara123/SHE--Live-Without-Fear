package com.example.she;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity {
    ListView listView;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        listView = findViewById(R.id.list_view);
        db = new DatabaseHandler(this);
        ArrayList list = new ArrayList();
        Cursor cursor = db.getAllContacts();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No contacts added!", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(listAdapter);
            }
        }
    }
}
