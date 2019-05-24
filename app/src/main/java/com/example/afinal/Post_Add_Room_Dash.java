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
import java.util.HashMap;

public class Post_Add_Room_Dash extends AppCompatActivity {

    private ImageView Room_Image;
    private Button submit_roomdetails , goback_menu , set_room_location;

    private EditText R_nomber,R_street,R_city,R_ferniture_diss,R_neatroot_distance,R_keymoney_fee,R_mounthly_fee,R_rentperiod,R_description;
    private RadioGroup Radio_Roomtype,Radio_Room_Condition,Radio_Member,Radio_Bathroom,Radio_Ferniture,Radio_Parkin,Radio_Keymoney;

    private RadioButton R_roomtype,R_roomcondition,R_roommember,R_bathroom,R_ferniture,R_parkig,R_keymonry;

    private String Rm_nomber,Rm_street,Rm_city,Rm_roomtype,Rm_roomcondition,Rm_member,Rm_bathroom,Rm_ferniture,Rm_ferniturediss,Rm_parking,Rm_near_distance,Rm_keymoney,Rm_keymoneyfee,Rm_mounthlyfee,Rm_period,Rm_disscription,r_owner_Nic,image_url;
    public static  String Room_latitude , Room_longitude;

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
            final StorageReference imageRefarance = storageReference.child("Room advertise").child(r_owner_Nic);

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


            //--------------------------------------------------------------------------------------
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                Room_Image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__add__room__dash);

        //-----------------------------------firebase part------------------------------------------
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //-----------------------------------storage------------------------------------------------
        storageReference = firebaseStorage.getReference();
        //StorageReference myref = storageReference.child("Home advertisement").child(h_ownername);

        //-----------------------------------------uiload-------------------------------------------
        UI_Load();

        //-----------------------------------------part of load owner-------------------------------
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                r_owner_Nic =(userProfile.getUpload_Nic());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Post_Add_Room_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        //------------------------------------------submit_button-----------------------------------
        submit_roomdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Read_Roomtype();
                Read_Roomcondition();
                Read_RoomMember();
                Read_RoomBathroom();
                Read_RoomFerniture();
                Read_RoomParking();
                Read_RoomKeymoney();

                if (check_isfull() == true)
                {
                    sendroomdetails();
                    Toast.makeText(Post_Add_Room_Dash.this,"Your Post added Succesfull",Toast.LENGTH_SHORT).show();
                    Clear_all();
                    startActivity(new Intent(Post_Add_Room_Dash.this,LoginUserDashboard.class));
                }

            }
        });

        //----------------------------------------goback button-------------------------------------
        goback_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Add_Room_Dash.this,SelectPostType.class));
            }
        });

        //-----------------------------------------location button-----------------------------------
        set_room_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Post_Add_Room_Dash.this,Set_Current_Location.class));
            }
        });


        //----------------------------------------Image part-----------------------------------------
        Room_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });


    }

    private void sendroomdetails()
    {
        //-----------------------------------details------------------------------------------------
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String child = r_owner_Nic;
        DatabaseReference roompost = firebaseDatabase.getReference().child("User_Room_Post").child(child);

        User_Add_Roompost user_add_roompost = new User_Add_Roompost(Rm_nomber,Rm_street,Rm_city,Rm_roomtype,Rm_roomcondition,Rm_member,Rm_bathroom,Rm_ferniture,Rm_ferniturediss,Rm_parking,Rm_near_distance,Rm_keymoney,Rm_keymoneyfee,Rm_mounthlyfee,Rm_period,Rm_disscription,Room_latitude,Room_longitude,image_url);
        roompost.setValue(user_add_roompost);


        //----------------------------------------------------image part---------------------------------------------------

        StorageReference imageRefarance = storageReference.child("Room advertise").child(r_owner_Nic);
        UploadTask uploadTask = imageRefarance.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Post_Add_Room_Dash.this,"Image upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Post_Add_Room_Dash.this,"Image upload successfully",Toast.LENGTH_SHORT).show();
            }
        });


    }




    private boolean check_isfull()
    {
        boolean result = false;

        Rm_nomber = R_nomber.getText().toString();
        Rm_street = R_street.getText().toString();
        Rm_city = R_city.getText().toString();
        Rm_ferniturediss = R_ferniture_diss.getText().toString();
        Rm_near_distance = R_neatroot_distance.getText().toString();
        Rm_keymoneyfee = R_keymoney_fee.getText().toString();
        Rm_mounthlyfee = R_mounthly_fee.getText().toString();
        Rm_period = R_rentperiod.getText().toString();
        Rm_disscription = R_description.getText().toString();

        if (Rm_nomber.isEmpty() || Rm_street.isEmpty() || Rm_city.isEmpty() || Rm_ferniturediss.isEmpty() || Rm_near_distance.isEmpty() ||Rm_keymoneyfee.isEmpty() ||Rm_mounthlyfee.isEmpty() ||Rm_period.isEmpty() || Rm_disscription.isEmpty())
        {
            Toast.makeText(Post_Add_Room_Dash.this,"Some Fields Empty",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else
        {
            result = true;
        }

        return result;
    }



    private void UI_Load()
    {
        Room_Image = (ImageView)findViewById(R.id.room_img);

        submit_roomdetails = (Button)findViewById(R.id.btn_r_submitroom);
        goback_menu = (Button)findViewById(R.id.btn_r_backtomenu);
        set_room_location = (Button)findViewById(R.id.btn_r_locations);

        R_nomber=(EditText)findViewById(R.id.et_r_number);
        R_street=(EditText)findViewById(R.id.et_r_street);
        R_city=(EditText)findViewById(R.id.et_r_city);
        R_ferniture_diss=(EditText)findViewById(R.id.et_r_ferniture_dis);
        R_neatroot_distance=(EditText)findViewById(R.id.et_r_root_distance);
        R_keymoney_fee=(EditText)findViewById(R.id.et_r_Keymoneyfee);
        R_mounthly_fee=(EditText)findViewById(R.id.et_r_Mounthfee);
        R_rentperiod=(EditText)findViewById(R.id.et_r_rentperiod);
        R_description=(EditText)findViewById(R.id.et_r_discription);

        Radio_Roomtype=(RadioGroup)findViewById(R.id.radio_r_room_type);
        Radio_Room_Condition=(RadioGroup)findViewById(R.id.radio_r_room_condition);
        Radio_Member=(RadioGroup)findViewById(R.id.radio_r_room_member);
        Radio_Bathroom=(RadioGroup)findViewById(R.id.radio_r_room_bathroom_type);
        Radio_Ferniture=(RadioGroup)findViewById(R.id.radio_r_Farnicharitem);
        Radio_Parkin=(RadioGroup)findViewById(R.id.radio_r_parkin);
        Radio_Keymoney=(RadioGroup)findViewById(R.id.radio_r_keymoney);
    }


    public void Read_Roomtype() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Roomtype.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_roomtype = (RadioButton) findViewById(selectedId);

        Rm_roomtype = String.valueOf(R_roomtype.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_Roomcondition() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Room_Condition.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_roomcondition = (RadioButton) findViewById(selectedId);

        Rm_roomcondition = String.valueOf(R_roomcondition.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    public void Read_RoomMember() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Member.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_roommember = (RadioButton) findViewById(selectedId);

        Rm_member = String.valueOf(R_roommember.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_RoomBathroom() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Bathroom.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_bathroom = (RadioButton) findViewById(selectedId);

        Rm_bathroom= String.valueOf(R_bathroom.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_RoomFerniture() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Ferniture.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_ferniture = (RadioButton) findViewById(selectedId);

        Rm_ferniture= String.valueOf(R_ferniture.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_RoomParking() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Parkin.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_parkig = (RadioButton) findViewById(selectedId);

        Rm_parking= String.valueOf(R_parkig.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }


    public void Read_RoomKeymoney() {

        // get selected radio button from radioGroup
        int selectedId = Radio_Keymoney.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        R_keymonry = (RadioButton) findViewById(selectedId);

        Rm_keymoney= String.valueOf(R_keymonry.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    private void Clear_all()
    {
        R_nomber.setText("");
        R_street.setText("");
        R_city.setText("");
        R_ferniture_diss.setText("");
        R_neatroot_distance.setText("");
        R_keymoney_fee.setText("");
        R_mounthly_fee.setText("");
        R_rentperiod.setText("");
        R_description.setText("");

    }

}
