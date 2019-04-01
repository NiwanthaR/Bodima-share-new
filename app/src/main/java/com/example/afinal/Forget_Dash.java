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
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Dash extends AppCompatActivity {
    //varible declare
    private EditText forgetmail;
    private TextView gobacklogin;
    private Button sendverifymail;
    private TextView resetdisplay;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide top panel
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forget__dash);
        getSupportActionBar().hide();
        //load
        uiload();

        gobacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget_Dash.this,Login_Dash.class));
            }
        });

        sendverifymail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgetmail.getText().toString().trim();
                resetdisplay.setText(null);

                if(email.isEmpty()){
                    resetdisplay.setText("Enter your Account Email to send Verify Email");
                }else {

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Forget_Dash.this,"Verify Email send Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Forget_Dash.this,Login_Dash.class));
                            }else {
                                Toast.makeText(Forget_Dash.this,"Verify Email sending Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });



    }

    private void uiload(){
        forgetmail=(EditText)findViewById(R.id.etforgetmail);
        gobacklogin=(TextView)findViewById(R.id.tvgoLogin);
        sendverifymail=(Button)findViewById(R.id.btnsendveryfy);
        resetdisplay=(TextView)findViewById(R.id.tvresetdisplay);
        firebaseAuth =FirebaseAuth.getInstance();
    }

}
