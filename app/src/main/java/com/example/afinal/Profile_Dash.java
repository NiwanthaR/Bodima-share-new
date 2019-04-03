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
    private TextView name ,city , nic,dob,contact,email;
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
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                name.setText(userProfile.getFirstName()+" "+userProfile.getLastName());
//                city.setText(userProfile.getCity());
//               nic.setText(userProfile.getNIC());
//                dob.setText(userProfile.getDOB());
//                contact.setText(userProfile.getContact());
//                email.setText(userProfile.getEmail());

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

        name=(TextView)findViewById(R.id.name);
        city=(TextView)findViewById(R.id.city);
        nic=(TextView)findViewById(R.id.nic);
        dob=(TextView)findViewById(R.id.dob);
        contact=(TextView)findViewById(R.id.contact);
        email=(TextView)findViewById(R.id.email);

        ProfileEdite=(Button) findViewById(R.id.btnprofileedit);

    }
}
