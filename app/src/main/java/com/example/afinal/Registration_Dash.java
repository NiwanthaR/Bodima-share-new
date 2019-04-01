package com.example.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_Dash extends AppCompatActivity {

    //variable declare
    private EditText Fname;
    private EditText Email;
    private EditText NIC;
    private EditText Password;
    private EditText Repassword;
    private Button Submit;

    private TextView displayfname;
    private TextView displayemail;
    private TextView displaynic;
    private TextView displaypassword;
    private TextView displayrepassword;
    private TextView gobacklogin;

    //validate
    String pass , repass , nic, email, fname;

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
                            }

                        }
                    });
                }


            }
        });

    }

    private void uiload()  {
        Fname=(EditText)findViewById(R.id.etfname);
        Email=(EditText)findViewById(R.id.etemail);
        NIC=(EditText)findViewById(R.id.etnic);
        Password=(EditText)findViewById(R.id.etpassword);
        Repassword=(EditText)findViewById(R.id.etrepassword);
        Submit=(Button)findViewById(R.id.btnsubmit);

        displayfname=(TextView)findViewById(R.id.tvdissfname);
        displayemail=(TextView)findViewById(R.id.tvdisemail);
        displaynic=(TextView)findViewById(R.id.tvdissnic);
        displaypassword=(TextView)findViewById(R.id.tvdisspassword);
        displayrepassword=(TextView)findViewById(R.id.tvdissrepassword);
        gobacklogin=(TextView)findViewById(R.id.tvgobackloginreg);
    }

    public boolean validatedetails(){

        final Validetion validate = new Validetion();
        boolean result = false;

        pass = Password.getText().toString();
        repass = Repassword.getText().toString();
        nic = NIC.getText().toString();
        fname = Fname.getText().toString();
        email = Email.getText().toString();

        displayfname.setText(null);
        displayemail.setText(null);
        displaynic.setText(null);
        displaypassword.setText(null);
        displayrepassword.setText(null);

        if(fname.isEmpty() || email.isEmpty() || nic.isEmpty() || pass.isEmpty() || repass.isEmpty())
        {
            displayrepassword.setText("Fill All The Details....!!");
        }
        else
        {
            if(validate.isValidPassword(pass) && validate.isValidNic(nic) && validate.issame(pass,repass) && validate.isValidmail(email))
            {
                //Toast.makeText(Registration_Dash.this,"Your Details Submitted..!!",Toast.LENGTH_SHORT).show();
                result = true;
                //firebase part
            }
            else  if (validate.isValidNic(nic)==false && validate.isValidPassword(pass)==false)
            {
                displaynic.setText("You Want to Enter Real NIC number.");
                displaypassword.setText("One capital letter , one Special Symbol & Numbers");
                //displayemail.setText("Enter Correct Email");
                displayrepassword.setText("NIC and Password & Email Wrong Check Again..!!");

            }
            else if(validate.isValidPassword(pass)==false)
            {
                displayrepassword.setText("Request Failed Password is Wrong..!");
                displaypassword.setText("One capital letter,one Special Symbol & Number");
            }
            else if (validate.isValidNic(nic)==false)
            {
                displaynic.setText("You Want to Enter Real NIC number.");
                displayrepassword.setText("Request Failed NIC is Wrong..!");
            }
            else if (validate.issame(pass,repass)==false)
            {
                displayrepassword.setText("Request Failed Password isn't Match..!");
                displaypassword.setText("You want to Enter Same Password..!");
            }
            else if (validate.isValidmail(email)==false)
            {
                displayemail.setText("Enter Correct Email..!");
                displayrepassword.setText("Request Failed Email is Incorrect");
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
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(fname,nic,email);
        myref.setValue(userProfile);

    }

}
