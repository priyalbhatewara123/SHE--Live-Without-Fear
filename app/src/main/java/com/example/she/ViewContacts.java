package com.example.she;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity implements ContactListAdaptor.ContactItemInterface {

    ListView listView;
    DatabaseHandler db;
    ArrayList<Pair<String, String>> contactList;
    ContactListAdaptor contactListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        updateListView();
    }

    @Override
    public void editOptionSelected(String phoneNumber) {
        Intent intent = new Intent(ViewContacts.this, EditContactDetails.class);
        intent.putExtra("oldPhoneNumber", phoneNumber);
        startActivity(intent);
        finish();
    }

    @Override
    public void deleteOptionSelected(final String phoneNumber) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.ic_delete_24)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewContacts.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                        db = new DatabaseHandler(ViewContacts.this);
                        db.deleteContact(phoneNumber);
                        updateListView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    //updates the list view
    public void updateListView() {
        listView = findViewById(R.id.list_view);
        db = new DatabaseHandler(this);
        contactList = new ArrayList<>();
        Cursor cursor = db.getAllContacts();

        if (cursor.getCount() == 0) {
            contactListAdaptor.clear();
            contactListAdaptor.notifyDataSetChanged();
            Toast.makeText(this, "No contacts added!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Pair<String, String> contact = new Pair<>(cursor.getString(1), cursor.getString(2));
                contactList.add(contact);
                contactListAdaptor = new ContactListAdaptor(this, contactList, this);
                listView.setAdapter(contactListAdaptor);
            }
        }
    }
}