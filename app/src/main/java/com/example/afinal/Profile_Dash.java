package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Dash extends AppCompatActivity {

    private ImageView Profilepic;
    private TextView Name ,City , Nic, Dob, Contact, Email;
    private Button ProfileEdite;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__dash);

        //load
        uiload();
        //set fire base
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //get uid
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
               Name.setText(userProfile.getUpload_Fname()+" "+userProfile.getUpload_Lname());
               City.setText(userProfile.getUpload_City());
               Nic.setText(userProfile.getUpload_Nic());
               Dob.setText(userProfile.getUpload_Dob());
               Contact.setText(userProfile.getUpload_Contact());
               Email.setText(userProfile.getUpload_Email());

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
           }
        });

        ProfileEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_Dash.this,Profile_Edite_Dash.class));
            }
        });
    }

    //assign
    private void uiload(){

        Name=(TextView)findViewById(R.id.tv_username);
        City=(TextView)findViewById(R.id.tv_city);
        Nic=(TextView)findViewById(R.id.tv_nic);
        Dob=(TextView)findViewById(R.id.tv_dob);
        Contact=(TextView)findViewById(R.id.tv_contact);
        Email=(TextView)findViewById(R.id.tv_email);

        ProfileEdite=(Button) findViewById(R.id.btnEditProfile);

   }
}
