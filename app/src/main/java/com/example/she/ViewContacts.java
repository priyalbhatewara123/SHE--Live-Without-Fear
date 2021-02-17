package com.example.she;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity implements ContactListAdaptor.ContactItemInterface {

    ListView listView;
    DatabaseHandler db;
    ArrayList<Pair<String, String>> contactList;
    ContactListAdaptor contactListAdaptor;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        searchView = findViewById(R.id.search_bar);
        contactList = new ArrayList<>();
        db = new DatabaseHandler(ViewContacts.this);

        //searching feature
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        updateListView();
    }

    @Override
    public void editOptionSelected(String phoneNumber) {
        Toast.makeText(this, "Edit coming soon", Toast.LENGTH_SHORT).show();
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
            }
            contactListAdaptor = new ContactListAdaptor(this, contactList, this);
            listView.setAdapter(contactListAdaptor);
        }
    }

    public void search(String keyword) {
        Cursor cursor = db.searchContacts(keyword);
        if (cursor.getCount() == 0) {
            Pair<String, String> contact = new Pair<>("No Such Contact Found", null);
            contactListAdaptor.clear();
            contactList.add(contact);
            contactListAdaptor = new ContactListAdaptor(ViewContacts.this, contactList, null);
            listView.setAdapter(contactListAdaptor);
        } else {
            contactListAdaptor.clear();
            while (cursor.moveToNext()) {
                Pair<String, String> contact = new Pair<>(cursor.getString(1), cursor.getString(2));
                contactList.add(contact);
            }
            contactListAdaptor = new ContactListAdaptor(ViewContacts.this, contactList, ViewContacts.this);
            listView.setAdapter(contactListAdaptor);
        }
    }
}
