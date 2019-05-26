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

import com.google.android.gms.maps.GoogleMap;
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

public class Post_Add_Home_Dash extends AppCompatActivity {

    private ImageView advertice_image;
    private Button get_location , go_back , submit_data;
    private EditText U_address ,U_street , U_city ,  U_stair , U_rooms , U_bathroom , U_parking , U_keymoneyfee , U_mounthfee , U_renttime , U_discription ;

    private RadioGroup radioSexGroup , radiogarageGroup , radiowaterGroup , radiogarbageGroup , radiogateandwallGroup , radiokeymoneyGroup ;
    private RadioButton r_SexButton , r_garageButton , r_waterButton , r_garbageButton , r_gateandwallButton , r_keymoneyButton;

    private FirebaseAuth firebaseAuth;

    private String h_address , h_street , h_city , h_stair , h_rooms , h_bathroom , h_kitchen , h_garage , h_parking , h_water , h_garbage , h_gateandwall , h_keymoney , h_keymoneyfee , h_mounthlyfee , h_renttime , h_discription , h_owner_nic , image_url ;
    public static  String Home_latitude , Home_longitude;

    private FirebaseStorage firebaseStorage;
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
            final StorageReference imageRefarance = storageReference.child("Room advertise").child(h_owner_nic);

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
                advertice_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__add__home__dash);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //----------------------------------------------------part of load image---------------------------------------------------------

        storageReference = firebaseStorage.getReference();
        //StorageReference myref = storageReference.child("Home advertisement").child(h_ownername);


        //-----------------------------------------part of load owner-------------------------------------------------------------------
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                h_owner_nic =(userProfile.getUpload_Nic());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Post_Add_Home_Dash.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


        //---------------------------------------------------load component----------------------------------------------------
        uiload();

        //---------------------------------------------------------------------------------------------------------------------


        //--------------------------------------------goback button-----------------------------------------
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Post_Add_Home_Dash.this,SelectPostType.class));
            }
        });

        //--------------------------------------------location button-----------------------------------------
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
                    Clear_all();
                    startActivity(new Intent(Post_Add_Home_Dash.this,LoginUserDashboard.class));
                }

            }
        });


        advertice_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });


    }


    private void uiload()
    {
        advertice_image = (ImageView)findViewById(R.id.myadd_img);

        get_location =(Button)findViewById(R.id.btn_locations);
        go_back =(Button)findViewById(R.id.btn_backtomenu);
        submit_data =(Button)findViewById(R.id.btn_submitshome);

        U_address =(EditText) findViewById(R.id.et_h_address);
        U_city =(EditText) findViewById(R.id.et_h_city);
        U_street =(EditText) findViewById(R.id.et_h_province);
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
        h_street = U_street.getText().toString();
        h_stair = U_stair.getText().toString();
        h_rooms =U_rooms.getText().toString();
        h_bathroom = U_bathroom.getText().toString();
        h_parking = U_parking.getText().toString();
        h_keymoneyfee = U_keymoneyfee.getText().toString();
        h_mounthlyfee = U_mounthfee.getText().toString();
        h_renttime = U_renttime.getText().toString();
        h_discription = U_discription.getText().toString();


        if (h_address.isEmpty() || h_city.isEmpty() || h_street.isEmpty() || h_stair.isEmpty() || h_rooms.isEmpty() || h_bathroom.isEmpty() || h_parking.isEmpty() || h_keymoneyfee.isEmpty() || h_mounthlyfee.isEmpty())
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
        String child = h_owner_nic;
        DatabaseReference homepost = firebaseDatabase.getReference().child("User_Home_Post").child(child);

        User_add_Homepost user_add_homepost = new User_add_Homepost(h_address,h_street,h_city,h_stair,h_rooms,h_bathroom,h_kitchen,h_garage,h_parking,h_water,h_garbage,h_gateandwall,h_keymoney,h_keymoneyfee,h_mounthlyfee,h_renttime,h_discription,Home_latitude,Home_longitude,h_owner_nic,image_url);
        homepost.setValue(user_add_homepost);

        //----------------------------------------------------image part---------------------------------------------------
        StorageReference imageRefarance = storageReference.child("Home advertise").child(h_owner_nic);
        UploadTask uploadTask = imageRefarance.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Post_Add_Home_Dash.this,"Image upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Post_Add_Home_Dash.this,"Image upload successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Clear_all()
    {
        U_address.setText("");
        U_city.setText("");
        U_street.setText("");
        U_stair.setText("");
        U_rooms.setText("");
        U_bathroom.setText("");
        U_parking.setText("");
        U_keymoneyfee.setText("");
        U_mounthfee.setText("");
        U_renttime.setText("");
        U_discription.setText("");

    }

}
