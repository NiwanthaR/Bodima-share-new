package com.example.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button Logout;
    private Button goProfile;
    private Button goPost;
    private Button view_ad;
    //firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiload();
        //firebase
        firebaseAuth=FirebaseAuth.getInstance();


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,Login_Dash.class));
            }
        });

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this,Profile_Dash.class));
            }
        });

        goPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this,Post_Dash.class));
            }
        });



    }

    private void uiload(){
        Logout=(Button)findViewById(R.id.btnlogout);
        goProfile=(Button)findViewById(R.id.btngoprofile);
        goPost=(Button)findViewById(R.id.btngoPost);
        view_ad=(Button)findViewById(R.id.btnViewad);
    }
}
