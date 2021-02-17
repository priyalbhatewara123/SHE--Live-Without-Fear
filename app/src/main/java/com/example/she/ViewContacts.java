package com.example.she;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ViewContacts extends AppCompatActivity implements ContactListAdaptor.ContactItemInterface {

    ListView listView;
    DatabaseHandler db;
    ArrayList<Pair<String, String>> contactList;
    ContactListAdaptor contactListAdaptor;
    SearchView searchView;
    TextInputLayout et_name, et_number;


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
    public void editOptionSelected(final String phoneNumber, String name) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewContacts.this);

        LayoutInflater inflater = ViewContacts.this.getLayoutInflater();
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.edit_contact, null);
        et_number = viewGroup.findViewById(R.id.et_edit_number);
        et_name = viewGroup.findViewById(R.id.et_edit_name);
        et_number.getEditText().setText(phoneNumber);
        et_name.getEditText().setText(name);

        builder.setView(viewGroup)
                .setPositiveButton("Update", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPhone() & checkName() && isCheckContactExist(alertDialog)) {
                    db.updateContact(phoneNumber, et_name.getEditText().getText().toString(), et_number.getEditText().getText().toString());
                    Toast.makeText(ViewContacts.this, "Contact Updated", Toast.LENGTH_SHORT).show();
                    updateListView();
                    alertDialog.dismiss();
                }
            }
        });
    }

    public boolean checkName() {
        String name = et_name.getEditText().getText().toString().trim();
        if (name.length() == 0) {
            et_name.setError("Invalid Name");
            et_name.requestFocus();
            return false;
        }
        et_name.setErrorEnabled(false);
        return true;
    }

    public boolean checkPhone() {
        String number = et_number.getEditText().getText().toString().trim();
        if (number.length() != 10) {
            et_number.setError("Invalid Number");
            et_number.requestFocus();
            return false;
        } else {
            et_number.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isCheckContactExist(AlertDialog alertDialog) {
        String name = et_name.getEditText().getText().toString().trim();
        String number = et_number.getEditText().getText().toString().trim();

        if (db.getContact(number) == 0 || db.getName(name) == 0) {
            return true;
        }
        Toast.makeText(ViewContacts.this, "Contact Updated", Toast.LENGTH_SHORT).show();
        alertDialog.dismiss();
        return false;
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
