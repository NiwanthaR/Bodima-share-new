package com.example.afinal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Registration_Dash extends AppCompatActivity {

    //variable declare
    private EditText Fname;
    private EditText Lname;
    private EditText City;
    private EditText NIC;
    private EditText DOB;
    private EditText Contact;
    private EditText Email;
    private EditText Password;
    private EditText Repassword;
    private Button Submit;



    EditText testview;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    private TextView txtErrorDisplay;
    private TextView gobacklogin;
    //validate
    String  fname,lname,city,nic,dob,contact,email,password,repasswod;

    //function
    private Validetion validetion;
    //firebase
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //hide top panel
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration__dash);
        getSupportActionBar().hide();
        //load ui
        uiload();
        //validetion
         final Validetion validate = new Validetion();
         //firebase
        firebaseAuth=FirebaseAuth.getInstance();


        try {
//
        }catch (Exception i){
            Toast.makeText(this, i.getMessage(), Toast.LENGTH_SHORT).show();
        }


        testview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                int date=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                datePickerDialog=new DatePickerDialog(Registration_Dash.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        testview.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                },year,month,date);
                datePickerDialog.show();
            }
        });





        gobacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration_Dash.this , Login_Dash.class));
            }
        });





        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validatedetails()){

                    String umail = Email.getText().toString();
                    String upassword = Password.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(umail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                //Toast.makeText(Registration_Dash.this,"Registration Successfully!",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Registration_Dash.this,Login_Dash.class));
                                sendEmailVerification();
                            }
                            else {
                                Toast.makeText(Registration_Dash.this,"Registration Failed!",Toast.LENGTH_SHORT).show();
                                System.out.println("Registration Failed!");
                            }

                        }
                    });
                }


            }
        });

    }

    private void uiload()  {
        testview=(EditText) findViewById(R.id.txtdob);
        Fname=(EditText)findViewById(R.id.etfname);
        Lname=(EditText)findViewById(R.id.etlname);
        City=(EditText)findViewById(R.id.etCity);
        NIC=(EditText)findViewById(R.id.etnic);
        DOB=(EditText)findViewById(R.id.txtdob);
        Contact=(EditText)findViewById(R.id.etContactNum);
        Email=(EditText)findViewById(R.id.etemail);
        Password=(EditText)findViewById(R.id.etpassword);
        Repassword=(EditText)findViewById(R.id.etrepassword);
        Submit=(Button)findViewById(R.id.btnsubmit);

        txtErrorDisplay=(TextView)findViewById(R.id.txtErrorDisplay);
        gobacklogin=(TextView)findViewById(R.id.tvgobackloginreg);
    }

    public boolean validatedetails(){

        final Validetion validate = new Validetion();
        boolean result = false;

        fname = Fname.getText().toString();
        lname = Lname.getText().toString();
        city = City.getText().toString();
        nic = NIC.getText().toString();
        dob = DOB.getText().toString();
        contact = Contact.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        repasswod = Repassword.getText().toString();



        txtErrorDisplay.setText(null);

        if(fname.isEmpty() ||lname.isEmpty()|| email.isEmpty() || nic.isEmpty() || password.isEmpty() || repasswod.isEmpty())
        {
            txtErrorDisplay.setText("Fill All The Details....!!");
        }
        else
        {
            if(validate.isValidPassword(password) && validate.isValidNic(nic) && validate.issame(password,repasswod) && validate.isValidmail(email))
            {
                //Toast.makeText(Registration_Dash.this,"Your Details Submitted..!!",Toast.LENGTH_SHORT).show();
                result = true;
                //firebase part
            }
            else  if (validate.isValidNic(nic)==false && validate.isValidPassword(password)==false)
            {
                Email.setText("You Want to Enter Real NIC number.");
                txtErrorDisplay.setText("One capital letter , one Special Symbol & Numbers");
                //displayemail.setText("Enter Correct Email");
                txtErrorDisplay.setText("NIC and Password & Email Wrong Check Again..!!");

            }
            else if(validate.isValidPassword(password)==false)
            {
                txtErrorDisplay.setText("Request Failed Password is Wrong..!");
                txtErrorDisplay.setText("One capital letter,one Special Symbol & Number");
            }
            else if (validate.isValidNic(nic)==false)
            {
                txtErrorDisplay.setText("You Want to Enter Real NIC number.");
                txtErrorDisplay.setText("Request Failed NIC is Wrong..!");
            }
            else if (validate.issame(password,password)==false)
            {
                txtErrorDisplay.setText("Request Failed Password isn't Match..!");
                txtErrorDisplay.setText("You want to Enter Same Password..!");
            }
            else if (validate.isValidmail(email)==false)
            {
                txtErrorDisplay.setText("Enter Correct Email..!");
                txtErrorDisplay.setText("Request Failed Email is Incorrect");
            }

        }

        return result;
    }

    public void sendEmailVerification(){

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(Registration_Dash.this,"Registration Successfully!",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Registration_Dash.this,"verify mail was send!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration_Dash.this,Login_Dash.class));
                    }else{
                        Toast.makeText(Registration_Dash.this,"Registration Failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //upload database

    public void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference().child("Profile").child(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(fname,lname,city,nic,dob,contact,email);
        System.out.println(fname);
        myref.setValue(userProfile);
        System.out.println(lname);

    }

}
