package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Edite_Dash extends AppCompatActivity {

    private EditText Edite_fname , Edite_Lname ,Edite_City ,Edite_Dob, Edite_Nic ,Edite_Contact ,Edite_email;
    private Button Edite_save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edite__dash);

        uiload();

        //set fire base
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //get uid
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                Edite_fname.setText(userProfile.getUpload_Fname());
                Edite_Lname.setText(userProfile.getUpload_Lname());
                Edite_City.setText(userProfile.getUpload_City());
                Edite_Dob.setText(userProfile.getUpload_Dob());
                Edite_Nic.setText(userProfile.getUpload_Nic());
                Edite_Contact.setText(userProfile.getUpload_City());
                Edite_email.setText(userProfile.getUpload_Email());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile_Edite_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        Edite_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = Edite_fname.getText().toString();
                String lname = Edite_Lname.getText().toString();
                String city = Edite_City.getText().toString();
                String nic = Edite_Nic.getText().toString();
                String dob = Edite_Dob.getText().toString();
                String contact = Edite_Contact.getText().toString();

                UserProfile userProfile = new UserProfile(fname,lname,city,nic,dob,contact);

                databaseReference.setValue(userProfile);

                finish();
                startActivity(new Intent(Profile_Edite_Dash.this,Profile_Dash.class));
            }
        });

    }

   private void uiload()
    {
        Edite_fname =(EditText)findViewById(R.id.et_edite_fname);
        Edite_Lname =(EditText)findViewById(R.id.et_edite_lname);
        Edite_City =(EditText)findViewById(R.id.et_edite_City);
        Edite_Nic =(EditText)findViewById(R.id.et_edite_nic);
        Edite_Dob =(EditText)findViewById(R.id.txtdob);
        Edite_Contact =(EditText)findViewById(R.id.et_edite_ContactNum);
        Edite_email=(EditText)findViewById(R.id.et_edite_email);

        Edite_save=(Button)findViewById(R.id.btnsubmit2);
    }

}
