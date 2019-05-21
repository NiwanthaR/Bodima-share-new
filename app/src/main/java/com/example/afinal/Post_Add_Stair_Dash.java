package com.example.afinal;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Post_Add_Stair_Dash extends AppCompatActivity {

    private ImageView Stair_image;
    private Button submit_stairdetails , goback_menu , set_stair_location;

    private EditText S_number,S_street,S_city,S_stairno,S_norooms,S_nobathroom ,S_parkin, S_staircase , S_fernituredis , S_keymoneyfee ,S_mounthlyfee, S_rentperoid , S_discription;
    private RadioGroup Radio_Roomgroup , Radio_Bathrommgroup , Radio_Kitchengroup , Radio_Garagegroup , Radio_Staircasegroup , Radio_Fernituregroup , Radio_Watergroup , Radio_Garbagegroup , Radio_gatewallgroup , Radio_Keymoneygroup ;

    private RadioButton s_roombutton , s_bathroombutton , s_kitchenbutton , s_garagebutton , s_staircasebutton , s_ferniturebuton , s_waterbutton , s_garbagebutton , s_gatewallbutton , s_keymoneybutton;

    private String s_nomber ,s_road ,s_city , s_stair , s_rooms, s_roomcondition , s_bathroom, s_bathroomtype, s_kitchen , s_garage, s_parkingslot , s_staircasenumber, s_staircasetype, s_fernitures, s_ferniturediss , s_water , s_garbage ,s_gatewall , s_keymoney , s_keymoneyfee , s_mounthlyfee , s_rentperiod , s_discription;
    public static  String Stair_latitude , Stair_longitude;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    Uri imagepath ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__add__stair__dash);



    }

    private void UI_load()
    {
        //--------------------------------Edittext-Load---------------------------------------------
        S_number = (EditText)findViewById(R.id.et_s_number);
        S_street = (EditText)findViewById(R.id.et_s_road);
        S_city = (EditText)findViewById(R.id.et_s_city);
        S_stairno = (EditText)findViewById(R.id.et_s_stairno);
        S_norooms = (EditText)findViewById(R.id.et_s_noroom);
        S_nobathroom = (EditText)findViewById(R.id.et_s_nobathroom);
        S_parkin = (EditText)findViewById(R.id.et_s_noofparking);
        S_staircase = (EditText)findViewById(R.id.et_s_noofstaircase);
        S_fernituredis = (EditText)findViewById(R.id.et_s_fernituredis);
        S_keymoneyfee = (EditText)findViewById(R.id.et_S_Keymoneyfee);
        S_mounthlyfee = (EditText)findViewById(R.id.et_S_Mounthfee);
        S_rentperoid = (EditText)findViewById(R.id.et_S_rentperiod);
        S_discription = (EditText)findViewById(R.id.et_S_discription);

        //--------------------------------Radio Group-----------------------------------------------

        Radio_Roomgroup = (RadioGroup)findViewById(R.id.radio_s_room_condition);
        Radio_Bathrommgroup = (RadioGroup)findViewById(R.id.radio_s_bathroom_type);
        Radio_Kitchengroup = (RadioGroup)findViewById(R.id.radio_s_kitchen);
        Radio_Garagegroup = (RadioGroup)findViewById(R.id.radio_s_garage);
        Radio_Staircasegroup = (RadioGroup)findViewById(R.id.radio_s_staircasetype);
        Radio_Fernituregroup = (RadioGroup)findViewById(R.id.radio_s_Farnichar);
        Radio_Watergroup = (RadioGroup)findViewById(R.id.radio_s_water);
        Radio_Garbagegroup = (RadioGroup)findViewById(R.id.radio_s_garbage);
        Radio_gatewallgroup = (RadioGroup)findViewById(R.id.radio_s_gateandwall);
        Radio_Keymoneygroup = (RadioGroup)findViewById(R.id.radio_s_keymoney);

        //-------------------------------image------------------------------------------------------
        Stair_image = (ImageView)findViewById(R.id.stair_image);

        //--------------------------------Button----------------------------------------------------
        submit_stairdetails = (Button)findViewById(R.id.btn_s_submitstair);
        goback_menu = (Button)findViewById(R.id.btn_s_backtomenu);
        set_stair_location = (Button)findViewById(R.id.btn_s_locations);



    }


    public void Read_Roomtype() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Roomgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_roombutton = (RadioButton) findViewById(selectedId);

        s_roomcondition = String.valueOf(s_roombutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_Bathroomtype() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Bathrommgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_bathroombutton = (RadioButton) findViewById(selectedId);

        s_bathroomtype = String.valueOf(s_bathroombutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }




    public void Read_kitchen() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Kitchengroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_kitchenbutton = (RadioButton) findViewById(selectedId);

        s_kitchen = String.valueOf(s_kitchenbutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_garage() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Garagegroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_garagebutton = (RadioButton) findViewById(selectedId);

        s_garage = String.valueOf(s_garagebutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_Staircase_type() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Staircasegroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_staircasebutton = (RadioButton) findViewById(selectedId);

        s_staircasetype = String.valueOf(s_staircasebutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_Ferniture_supply() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Fernituregroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_ferniturebuton = (RadioButton) findViewById(selectedId);

        s_fernitures = String.valueOf(s_ferniturebuton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_water() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Watergroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_waterbutton = (RadioButton) findViewById(selectedId);

        s_water = String.valueOf(s_waterbutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_garbage() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Garbagegroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_garbagebutton = (RadioButton) findViewById(selectedId);

        s_garbage = String.valueOf(s_garbagebutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_gateandwall() {

        // get selected radio button from radioGroup
        int selectedId = Radio_gatewallgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_gatewallbutton= (RadioButton) findViewById(selectedId);

        s_gatewall = String.valueOf(s_gatewallbutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_keymoney() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Keymoneygroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        s_keymoneybutton= (RadioButton) findViewById(selectedId);

        s_keymoney = String.valueOf(s_keymoneybutton.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

}
