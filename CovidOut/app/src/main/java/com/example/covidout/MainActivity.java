package com.example.covidout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText LoginId;
    EditText Password;
    Button LogInButton;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginId = findViewById(R.id.LoginId);
        Password =findViewById(R.id.Password);
        LogInButton = findViewById(R.id.LogInButton);
        mAuth = FirebaseAuth.getInstance();
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoginId.getText().toString().equals("")){
                    LoginId.setError("Please Enter ID");
                }else if (Password.getText().toString().equals("")){
                    Password.setError("Please Enter Password");
                }else{
                    String loginId = LoginId.getText().toString();
                    String password = Password.getText().toString();
                    mAuth.signInWithEmailAndPassword(loginId, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                //Toast.makeText(MainActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(MainActivity.this).setMessage("ID or Password Incorrect").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                            } else {
                                //Toast.makeText(MainActivity.this, "sucessfull", Toast.LENGTH_SHORT).show();


                                startActivity(new Intent(MainActivity.this, AdharRegistry.class));
                            }
                        }
                    });
                }


            }
        });



    }



}
