package com.example.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class SearchTypes extends AppCompatActivity {

    private Button home,room,stair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_types);

        UI_LOAD();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SearchTypes.this,SearchActivity.class);
                it.putExtra("Mode","home");
                startActivity(it);
            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SearchTypes.this,SearchActivity.class);
                it.putExtra("Mode","room");
                startActivity(it);
            }
        });

        stair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SearchTypes.this,SearchActivity.class);
                it.putExtra("Mode","stair");
                startActivity(it);
            }
        });

    }

    private void UI_LOAD(){
        home = (Button) findViewById(R.id.homeBtn);
        room = (Button) findViewById(R.id.roomBtn);
        stair = (Button) findViewById(R.id.stairBtn);
    }


}
