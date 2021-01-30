package com.example.she;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText e6,e7;
    Button b;
    TextView t,forgetPassTv;
    FirebaseAuth fb;
    String logname , logpassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e6 = findViewById(R.id.et6);
        e7 = findViewById(R.id.et7);
        b = findViewById(R.id.b2);
        t = findViewById(R.id.tv6);
        forgetPassTv = findViewById(R.id.forget_password_tv);
        progressBar = findViewById(R.id.pb2);
        fb = FirebaseAuth.getInstance();
        ActionCodeSettings actionCodeSettings;

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Registration.class));
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = e6.getText().toString().trim();
                String password = e7.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    e6.setError("Email is Required.");
                    e6.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    e7.setError("Password is Required");
                    e7.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

               fb.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this,MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(login.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        forgetPassTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = e6.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    e6.setError("Email is Required.");
                    e6.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fb.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getApplicationContext(),
                                    "Reset password link sent to your email",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        else
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't find your email",
                                    Toast.LENGTH_SHORT)
                                    .show();

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

    }
}
