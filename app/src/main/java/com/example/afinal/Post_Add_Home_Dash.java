package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Post_Add_Home_Dash extends AppCompatActivity {

    private Button get_location , go_back , submit_data;
    private EditText U_address , U_city , U_province , U_stair , U_rooms , U_bathroom , U_parking , U_keymoneyfee , U_mounthfee , U_renttime , U_discription ;

    private RadioGroup radioSexGroup , radiogarageGroup , radiowaterGroup , radiogarbageGroup , radiogateandwallGroup , radiokeymoneyGroup ;
    private RadioButton r_SexButton , r_garageButton , r_waterButton , r_garbageButton , r_gateandwallButton , r_keymoneyButton;

    private FirebaseAuth firebaseAuth;

    private String h_address , h_city , h_province , h_stair , h_rooms , h_bathroom , h_kitchen , h_garage , h_parking , h_water , h_garbage , h_gateandwall , h_keymoney , h_keymoneyfee , h_mounthlyfee , h_renttime , h_discription , h_ownername ;
    public static  String Home_latitude , Home_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__add__home__dash);

        firebaseAuth = FirebaseAuth.getInstance();

        //------------------------------------------------------------------------------------------------------------
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                h_ownername =(userProfile.getUpload_Fname()+" "+userProfile.getUpload_Lname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Post_Add_Home_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


        //-------------------------------------------------------------------------------------------------------------

        uiload();

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Post_Add_Home_Dash.this,"Name is "+h_ownername,Toast.LENGTH_SHORT).show();
            }
        });


        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Add_Home_Dash.this,Set_Current_Location.class));
            }
        });


        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Read_kitchen();
                Read_garage();
                Read_water();
                Read_garbage();
                Read_gateandwall();
                Read_keymoney();

                if (checkisfull() == true)
                {
                    sendhousedata();
                    Toast.makeText(Post_Add_Home_Dash.this,"Your Post added Succesfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Post_Add_Home_Dash.this,LoginUserDashboard.class));
                }

            }
        });


    }


    private void uiload()
    {
        get_location =(Button)findViewById(R.id.btn_locations);
        go_back =(Button)findViewById(R.id.btn_backtomenu);
        submit_data =(Button)findViewById(R.id.btn_submitshome);

        U_address =(EditText) findViewById(R.id.et_h_address);
        U_city =(EditText) findViewById(R.id.et_h_city);
        U_province =(EditText) findViewById(R.id.et_h_province);
        U_stair =(EditText) findViewById(R.id.et_h_stare);
        U_rooms =(EditText) findViewById(R.id.et_h_rooms);
        U_bathroom =(EditText) findViewById(R.id.et_h_bathroom);
        U_parking =(EditText) findViewById(R.id.et_h_parking);
        U_keymoneyfee =(EditText) findViewById(R.id.et_h_Keymoneyfee);
        U_mounthfee =(EditText) findViewById(R.id.et_h_Mounthfee);
        U_renttime =(EditText) findViewById(R.id.et_h_rentperiod);
        U_discription =(EditText) findViewById(R.id.et_h_discription);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radiogarageGroup = (RadioGroup) findViewById(R.id.radiogarage);
        radiowaterGroup  = (RadioGroup) findViewById(R.id.radiowater);
        radiogarbageGroup = (RadioGroup) findViewById(R.id.radiogarbage);
        radiogateandwallGroup =(RadioGroup) findViewById(R.id.radiogateandwall);
        radiokeymoneyGroup =(RadioGroup) findViewById(R.id.radiokeymoney);
    }

    public void Read_kitchen() {

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                r_SexButton = (RadioButton) findViewById(selectedId);

                h_kitchen = String.valueOf(r_SexButton.getText());
                //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_garage() {

        // get selected radio button from radioGroup
        int selectedId = radiogarageGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        r_garageButton = (RadioButton) findViewById(selectedId);

        h_garage = String.valueOf(r_garageButton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_water() {

        // get selected radio button from radioGroup
        int selectedId = radiowaterGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        r_waterButton = (RadioButton) findViewById(selectedId);

        h_water = String.valueOf(r_waterButton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_garbage() {

        // get selected radio button from radioGroup
        int selectedId = radiogarbageGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        r_garbageButton = (RadioButton) findViewById(selectedId);

        h_garbage = String.valueOf(r_garbageButton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_gateandwall() {

        // get selected radio button from radioGroup
        int selectedId = radiogateandwallGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
         r_gateandwallButton= (RadioButton) findViewById(selectedId);

        h_gateandwall = String.valueOf(r_gateandwallButton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_keymoney() {

        // get selected radio button from radioGroup
        int selectedId = radiokeymoneyGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        r_keymoneyButton= (RadioButton) findViewById(selectedId);

        h_keymoney = String.valueOf(r_keymoneyButton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    private Boolean checkisfull()
    {
        boolean result = false;

        h_address = U_address.getText().toString();
        h_city = U_city.getText().toString();
        h_province = U_province.getText().toString();
        h_stair = U_stair.getText().toString();
        h_rooms =U_rooms.getText().toString();
        h_bathroom = U_bathroom.getText().toString();
        h_parking = U_parking.getText().toString();
        h_keymoneyfee = U_keymoneyfee.getText().toString();
        h_mounthlyfee = U_mounthfee.getText().toString();
        h_renttime = U_renttime.getText().toString();
        h_discription = U_discription.getText().toString();


        if (h_address.isEmpty() || h_city.isEmpty() || h_province.isEmpty() || h_stair.isEmpty() || h_rooms.isEmpty() || h_bathroom.isEmpty() || h_parking.isEmpty() || h_keymoneyfee.isEmpty() || h_mounthlyfee.isEmpty())
        {
            Toast.makeText(Post_Add_Home_Dash.this,"Some Fields Empty",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else
        {
            result =  true;
        }

        return result;
    }

    private void sendhousedata()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String child = h_ownername;
        DatabaseReference homepost = firebaseDatabase.getReference().child("User_Home_Post").child(child);

        User_add_Homepost user_add_homepost = new User_add_Homepost(h_address,h_city,h_province,h_stair,h_rooms,h_bathroom,h_kitchen,h_garage,h_parking,h_water,h_garbage,h_gateandwall,h_keymoney,h_keymoneyfee,h_mounthlyfee,h_renttime,h_discription,Home_latitude,Home_longitude);
        homepost.setValue(user_add_homepost);
    }



}
