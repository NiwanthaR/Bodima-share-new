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

public class Login_Dash extends AppCompatActivity {

    private EditText Useremail;
    private EditText Userpassword;
    private Button Login;
    private TextView goforget;
    private TextView goregistor;
    private TextView display;
    //firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login__dash);
        getSupportActionBar().hide();
        //load
        uiview();
        //firebase
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //click forget
        goforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Dash.this,Forget_Dash.class));
            }
        });

        //click sign
        goregistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Dash.this,Registration_Dash.class));
            }
        });

        if(user != null)
        {
            finish();
            startActivity(new Intent(Login_Dash.this,MainActivity.class));
        }

        //Click login button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginvalidate(Useremail.getText().toString(),Userpassword.getText().toString());
            }
        });
    }

    private void uiview()
    {
        Useremail=(EditText)findViewById(R.id.etuseremail);
        Userpassword=(EditText)findViewById(R.id.etuserpassword);
        goforget=(TextView)findViewById(R.id.tvforget);
        goregistor=(TextView)findViewById(R.id.tvregistor);
        Login=(Button)findViewById(R.id.btnlogin);
        display=(TextView)findViewById(R.id.tvlogdisplay);
    }

    private void loginvalidate(String umail,String upassword)
    {
        String usermail = umail;
        String userpassword = upassword;
        display.setText(null);

        if (usermail.isEmpty() || userpassword.isEmpty()) {
            if (usermail.isEmpty() && userpassword.isEmpty()){
                display.setText("Fill Your Email & Password");
            } else if (userpassword.isEmpty()) {
                display.setText("Fill Your Password It's important");
            }
            else
                display.setText("Fill Your Email It's important");

        }
        else {

            firebaseAuth.signInWithEmailAndPassword(usermail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        //Toast.makeText(Login_Dash.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(Login_Dash.this,MainActivity.class));
                        checkEmailVerification();
                    }
                    else{
                        Toast.makeText(Login_Dash.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        display.setText("Your Details incorrect..!");
                    }
                }
            });
        }

    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag){
            finish();
            Toast.makeText(Login_Dash.this,"Login Successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_Dash.this,MainActivity.class));
        }
        else{
            Toast.makeText(Login_Dash.this,"Login Failed",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }
}
