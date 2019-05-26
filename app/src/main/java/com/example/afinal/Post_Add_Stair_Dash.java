package com.example.afinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Post_Add_Stair_Dash extends AppCompatActivity {

    private ImageView Stair_image;
    private Button submit_stairdetails , goback_menu , set_stair_location;

    private EditText S_number,S_street,S_city,S_floorno,S_norooms,S_nobathroom ,S_parkin, S_staircase , S_fernituredis , S_keymoneyfee ,S_mounthlyfee, S_rentperoid , S_discription;
    private RadioGroup Radio_Roomgroup , Radio_Bathrommgroup , Radio_Kitchengroup , Radio_Garagegroup , Radio_Staircasegroup , Radio_Fernituregroup , Radio_Watergroup , Radio_Garbagegroup , Radio_gatewallgroup , Radio_Keymoneygroup ;

    private RadioButton s_roombutton , s_bathroombutton , s_kitchenbutton , s_garagebutton , s_staircasebutton , s_ferniturebuton , s_waterbutton , s_garbagebutton , s_gatewallbutton , s_keymoneybutton;

    private String s_nomber ,s_road ,s_city , s_floor , s_rooms, s_roomcondition , s_bathroom, s_bathroomtype, s_kitchen , s_garage, s_parkingslot , s_staircasenumber, s_staircasetype, s_fernitures, s_ferniturediss , s_water , s_garbage ,s_gatewall , s_keymoney , s_keymoneyfee , s_mounthlyfee , s_rentperiod , s_discription , s_owner_Nic,image_url;
    public static  String Stair_latitude , Stair_longitude;

    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123;
    Uri imagepath ;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null)
        {
            imagepath = data.getData();

            //-----------------------------------------------new------------------------------------

            storageReference = firebaseStorage.getReference();
            final StorageReference imageRefarance = storageReference.child("Room advertise").child(s_owner_Nic);

            imageRefarance.putFile(imagepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageRefarance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //HashMap<String,String> hashMap = new HashMap<>();
                            //hashMap.put(image_url, String.valueOf(uri));

                            image_url = String.valueOf(uri);

                            //Toast.makeText(Post_Add_Room_Dash.this,"ok...!!"+image_url,Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });





            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                Stair_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__add__stair__dash);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //----------------------------------------------------part of load image---------------------------------------------------------

        storageReference = firebaseStorage.getReference();
        //StorageReference myref = storageReference.child("Home advertisement").child(h_ownername);

        //-----------------------------------------uiload-------------------------------------------
        UI_load();


        //-----------------------------------------part of load owner-------------------------------
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                s_owner_Nic =(userProfile.getUpload_Nic());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Post_Add_Stair_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        //----------------------------------------submit button-------------------------------------
        submit_stairdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Read_Roomtype();
                Read_Bathroomtype();
                Read_kitchen();
                Read_garage();
                Read_Staircase_type();
                Read_Ferniture_supply();
                Read_water();
                Read_garbage();
                Read_gateandwall();
                Read_keymoney();

                if (check_isfull() == true)
                {
                    sendstairdata();
                    Toast.makeText(Post_Add_Stair_Dash.this,"Your Post added Succesfull",Toast.LENGTH_SHORT).show();
                    clear_all();
                    startActivity(new Intent(Post_Add_Stair_Dash.this,LoginUserDashboard.class));
                }

            }
        });

        //---------------------------------------location button-------------------------------------
        set_stair_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Add_Stair_Dash.this,Set_Current_Location.class));
            }
        });

        //---------------------------------------back_button-----------------------------------------
        goback_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Add_Stair_Dash.this,SelectPostType.class));
            }
        });


        //----------------------------------------Image part-----------------------------------------
        Stair_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });


    }

    private boolean check_isfull()
    {
        boolean result = false;

        s_nomber = S_number.getText().toString();
        s_road = S_street.getText().toString();
        s_city = S_city.getText().toString();
        s_floor = S_floorno.getText().toString();
        s_rooms = S_norooms.getText().toString();
        s_bathroom = S_nobathroom.getText().toString();
        s_parkingslot = S_parkin.getText().toString();
        s_staircasenumber = S_staircase.getText().toString();
        s_ferniturediss = S_fernituredis.getText().toString();
        s_keymoneyfee = S_keymoneyfee.getText().toString();
        s_mounthlyfee = S_mounthlyfee.getText().toString();
        s_rentperiod = S_rentperoid.getText().toString();
        s_discription = S_discription.getText().toString();

        if (s_nomber.isEmpty() || s_road.isEmpty() || s_city.isEmpty() || s_floor.isEmpty() || s_rooms.isEmpty() || s_bathroom.isEmpty() || s_parkingslot.isEmpty() || s_staircasenumber.isEmpty() || s_ferniturediss.isEmpty() || s_keymoneyfee.isEmpty() || s_mounthlyfee.isEmpty() || s_rentperiod.isEmpty() || s_discription.isEmpty())
        {
            Toast.makeText(Post_Add_Stair_Dash.this,"Some Fields Empty",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else
        {
            result =  true;
        }

        return result;
    }

    private void sendstairdata()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String child = s_owner_Nic;
        DatabaseReference stairpost = firebaseDatabase.getReference().child("User_Stair_Post").child(child);

        User_Add_Stair user_add_stair = new User_Add_Stair(s_nomber,s_road,s_city,s_floor,s_rooms,s_roomcondition,s_bathroom,s_bathroomtype,s_kitchen,s_garage,s_parkingslot,s_staircasenumber,s_staircasetype,s_fernitures,s_ferniturediss,s_water,s_garbage,s_gatewall,s_keymoney,s_keymoneyfee,s_mounthlyfee,s_rentperiod,s_discription,Stair_latitude,Stair_longitude,s_owner_Nic,image_url);
        stairpost.setValue(user_add_stair);


        //User_add_Homepost user_add_homepost = new User_add_Homepost(h_address,h_city,h_province,h_stair,h_rooms,h_bathroom,h_kitchen,h_garage,h_parking,h_water,h_garbage,h_gateandwall,h_keymoney,h_keymoneyfee,h_mounthlyfee,h_renttime,h_discription,Home_latitude,Home_longitude);
        //homepost.setValue(user_add_homepost);



        //----------------------------------------------------image part---------------------------------------------------

        StorageReference imageRefarance = storageReference.child("Stair advertise").child(s_owner_Nic);
        UploadTask uploadTask = imageRefarance.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Post_Add_Stair_Dash.this,"Image upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Post_Add_Stair_Dash.this,"Image upload successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void UI_load()
    {
        //--------------------------------Edittext-Load---------------------------------------------
        S_number = (EditText)findViewById(R.id.et_s_number);
        S_street = (EditText)findViewById(R.id.et_s_road);
        S_city = (EditText)findViewById(R.id.et_s_city);
        S_floorno = (EditText)findViewById(R.id.et_s_nofloor);
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

    private void clear_all()
    {
        S_number.setText("");
        S_street.setText("");
        S_city.setText("");
        S_floorno.setText("");
        S_norooms.setText("");
        S_nobathroom.setText("");
        S_parkin.setText("");
        S_staircase.setText("");
        S_fernituredis.setText("");
        S_keymoneyfee.setText("");
        S_mounthlyfee.setText("");
        S_rentperoid.setText("");
        S_discription.setText("");
    }



}
