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

    private EditText Edite_name , Edite_email , Edite_nic;
    private Button Edite_save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edite__dash);

//        uiload();

        //set fire base
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();

        //get uid
//        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                Edite_name.setText(userProfile.getUserupname());
//                Edite_email.setText(userProfile.getUserupemai());
//                Edite_nic.setText(userProfile.getUserupnic());

//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(Profile_Edite_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
//            }
//        });

//        Edite_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                String name = Edite_name.getText().toString();
//                String email = Edite_email.getText().toString();
//                String nic = Edite_email.getText().toString();

//                UserProfile userProfile = new UserProfile(name,email,nic);

//                databaseReference.setValue(userProfile);

//                finish();
//                startActivity(new Intent(Profile_Edite_Dash.this,Profile_Dash.class));
//            }
//        });
//
//    }

//    private void uiload()
//    {
//        Edite_name=(EditText)findViewById(R.id.edite_name);
//        Edite_email=(EditText)findViewById(R.id.edite_email);
//        Edite_nic=(EditText)findViewById(R.id.edite_nic);
//        Edite_save=(Button)findViewById(R.id.btn_edite_save);
    }

}
