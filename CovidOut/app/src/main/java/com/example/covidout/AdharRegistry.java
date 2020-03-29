package com.example.covidout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdharRegistry extends AppCompatActivity {

    EditText adharNumber;
    Button validate;
    Button add;
    private DatabaseReference mDatabase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Boolean isAvailable;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhar_registry);

        adharNumber = findViewById(R.id.adharNumber);
        validate = findViewById(R.id.validate);
        add = findViewById(R.id.add);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("AdharNumbers");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.bringToFront();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adharNumber.getText().toString().equals("")){
                    adharNumber.setError("Please enter number");
                }else{
                    isAvailable=false;
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseFirestore.getInstance().collection("AdharNumbers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot documentSnapshot:task.getResult()){
                                    //System.out.println(documentSnapshot.getData().equals(adharNumber));
                                    if(documentSnapshot.getData().get("no").equals(adharNumber.getText().toString())){
                                        isAvailable = true;
                                    }
                                }
                                if (isAvailable){
                                    adharNumber.setError("No. already registered");
                                    progressBar.setVisibility(View.INVISIBLE);
                                }else{
                                    Map<String,String> map = new HashMap<>();
                                    map.put("no",adharNumber.getText().toString());
                                    DocumentReference ref = db.collection("AdharNumbers").document();
                                    String myId = ref.getId();
                                    System.out.println("DOCUMENT ID: "+myId);
                                    db.collection("AdharNumbers").document(myId).set(map);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    adharNumber.setText(null);
                                    adharNumber.setError(null);
                                    new AlertDialog.Builder(AdharRegistry.this).setMessage("Data Saved").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                                }
                            }
                        }
                    });
                }



            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adharNumber.getText().toString().equals("")){
                    adharNumber.setError("Please enter number");
                }else{
                    isAvailable= false;
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseFirestore.getInstance().collection("AdharNumbers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (DocumentSnapshot documentSnapshot:task.getResult()){
                                    //System.out.println(documentSnapshot.getData().equals(adharNumber));
                                    if(documentSnapshot.getData().get("no").equals(adharNumber.getText().toString())){
                                        isAvailable = true;
                                    }
                                }
                                if (isAvailable == true){
                                    adharNumber.setError("No. already registered");
                                }
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });
                }

            }
        });

    }
}


//
