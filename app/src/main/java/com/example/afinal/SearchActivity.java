package com.example.afinal;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        layout = findViewById(R.id.searchlayout);
        Intent it = getIntent();
        String mode = it.getStringExtra("Mode");

        if(mode.matches("home")){
            DatabaseReference data = FirebaseDatabase.getInstance().getReference("User_Room_Post");
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dps : dataSnapshot.getChildren()){
                        CardView cardView = new CardView(SearchActivity.this);
                        LinearLayout cardlayout = new LinearLayout(SearchActivity.this);
                        LinearLayout cardlayout2 = new LinearLayout(SearchActivity.this);
                        TextView nametxt = new TextView(SearchActivity.this);
                        TextView pricetxt = new TextView(SearchActivity.this);
                        TextView empty = new TextView(SearchActivity.this);
                        TextView empty2 = new TextView(SearchActivity.this);
                        TextView empty3 = new TextView(SearchActivity.this);
                        ImageView image = new ImageView(SearchActivity.this);

                        empty.setText(" ");
                        empty2.setText(" ");
                        empty3.setText(" ");

                        cardlayout.setOrientation(0);
                        cardlayout2.setOrientation(1);

                        //text view can be editable
                        nametxt.setTextSize(20);
                        nametxt.setPadding(15,0,0,0);

                        pricetxt.setTextSize(15);
                        pricetxt.setPadding(15,0,0,0);

                        Picasso.with(SearchActivity.this).load(dps.child("room_image_url").getValue().toString()).resize(300,250).into(image);

                        cardView.setRadius(52);
                        nametxt.setText("City - "+dps.child("room_city").getValue().toString());
                        pricetxt.setText("Rs - "+dps.child("room_mounthly_fee").getValue().toString());
                        final String nic = ""; //dps.child("room_description").getValue().toString();

                        cardlayout.addView(image);
                        cardlayout.addView(empty);
                        cardlayout2.addView(nametxt);
                        cardlayout2.addView(empty3);
                        cardlayout2.addView(pricetxt);
                        cardlayout.addView(cardlayout2);
                        cardView.addView(cardlayout);
                        layout.addView(cardView);
                        layout.addView(empty2);

                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(SearchActivity.this,SearchDetails.class);
                                it.putExtra("NIC",nic);
                                startActivity(it);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else if (mode.matches("room")){

        }else{

        }
    }
}
