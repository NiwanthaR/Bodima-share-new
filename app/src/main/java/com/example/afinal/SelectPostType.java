package com.example.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectPostType extends AppCompatActivity {

    private Button btnHome;
    private Button btnStare;
    private Button btnRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_post_type);


        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OpenAddHome();
            }
        });

        btnStare = (Button) findViewById(R.id.btnStare);
        btnStare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddStare();
            }
        });

        btnRoom = (Button) findViewById(R.id.btnRoom);
        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddRoom();
            }
        });
    }

    private void OpenAddHome() {
        Intent intent = new Intent(this, Post_Add_Home_Dash.class);
        startActivity(intent);
    }

    private void OpenAddStare() {
        Intent intent = new Intent(this, CallbackMap_Dash.class);
        startActivity(intent);
    }

    private void OpenAddRoom() {
        Intent intent = new Intent(this, Post_Dash.class);
        startActivity(intent);
    }

}
