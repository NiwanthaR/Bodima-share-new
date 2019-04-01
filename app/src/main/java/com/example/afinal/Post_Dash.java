package com.example.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_Dash extends AppCompatActivity {

    private EditText Pno , Pcity , Paddress , Prentfee , Proom ,Pbathroom ,Pdiscription;
    private Button Add_Post;
    private Button Get_location;
    private ImageView User_addpic;

    private FirebaseAuth firebaseAuth;

    String uno,ucity,uaddress,urentfee,uroom,ubathroom,udiscription;
    String Latitiude , Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__dash);

        firebaseAuth = FirebaseAuth.getInstance();

        Uiload();

        Add_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostdetails();
                Toast.makeText(Post_Dash.this,"Post Uploaded",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Post_Dash.this,MainActivity.class));
            }
        });

        Get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Dash.this,Map_Dash.class));
            }
        });


    }

    //get Lat Lan
    public void setLatLan(double La ,double Lo)
    {

        this.Latitiude= String.valueOf(La).toString();
        this.Longitude= String.valueOf(Lo).toString();

    }

    private void Uiload(){

        Pno=(EditText)findViewById(R.id.etPno);
        Pcity=(EditText)findViewById(R.id.etPcity);
        Paddress=(EditText)findViewById(R.id.etPaddress);
        Prentfee=(EditText)findViewById(R.id.etPrentfee);
        Proom=(EditText)findViewById(R.id.etProoms);
        Pbathroom=(EditText)findViewById(R.id.etPbathroom);
        Pdiscription=(EditText)findViewById(R.id.etPdiscription);

        Add_Post=(Button)findViewById(R.id.btnAddPost);
        Get_location=(Button)findViewById(R.id.btngoMap);

        User_addpic=(ImageView)findViewById(R.id.imgaddpic);
    }

    //Send Profile Data
    private void sendPostdetails(){

        uno = Pno.getText().toString();
        ucity = Pcity.getText().toString();
        uaddress = Paddress.getText().toString();
        urentfee = Prentfee.getText().toString();
        uroom = Proom.getText().toString();
        ubathroom = Pbathroom.getText().toString();
        udiscription = Pdiscription.getText().toString();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mypost = firebaseDatabase.getReference(firebaseAuth.getUid());

        UserAddPost userAddPost = new UserAddPost(uno,ucity,uaddress,urentfee,ubathroom,ubathroom,udiscription,Latitiude,Longitude);
        mypost.setValue(userAddPost);
    }
}
