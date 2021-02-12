package com.example.she;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SafeBrowsingResponse;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class EditContactDetails extends AppCompatActivity {

    EditText name, phoneNumber;
    Button update, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_details);

        name = findViewById(R.id.et_edit_name);
        phoneNumber = findViewById(R.id.et_edit_contactNumber);
        update = findViewById(R.id.btn_edit_update);
        cancel = findViewById(R.id.btn_edit_cancel);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = name.getText().toString().trim();
                String newPhoneNumber = phoneNumber.getText().toString();
                String oldPhoneNumber = getIntent().getStringExtra("oldPhoneNumber");

                if(checkFields()){
                    DatabaseHandler databaseHandler = new DatabaseHandler(EditContactDetails.this);
                    databaseHandler.updateContact(oldPhoneNumber, contactName, newPhoneNumber);

                    Intent intent = new Intent(EditContactDetails.this, ViewContacts.class);
                    // set the new task and clear flags
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContactDetails.this, ViewContacts.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean checkFields(){
        String _name = name.getText().toString().trim();
        String _number = phoneNumber.getText().toString().trim();

        if(_name.length() == 0){
            name.setError("Invalid Name");
            return false;
        } else if(_number.length() != 10){
            phoneNumber.setError("Invalid Number");
            return false;
        } else {
            return true;
        }
    }
}