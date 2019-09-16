package com.example.introscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.introscreen.Common.Common;
import com.example.introscreen.Model.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Login extends AppCompatActivity {

    Button btnLogin,btnSignUP,btnforget;
    EditText UserName,Password;
    RelativeLayout rellay1,rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
            Animation an2;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        rellay1 =(RelativeLayout)findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout)findViewById(R.id.rellay2);
        btnLogin=findViewById(R.id.btn_login);
        btnSignUP=findViewById(R.id.btn_signUp);
        btnforget=findViewById(R.id.btn_forgotPW);
        UserName = findViewById(R.id.txtUserName);
        Password=findViewById(R.id.txtPassword);

        //Init FireBase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_login = database.getReference("UserLogin");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(Activity_Login.this);
                mDialog.setMessage("Please Waiting ...!");
                mDialog.show();

                if((UserName.getText().toString()).isEmpty())
                {
                    mDialog.dismiss();
                    Toast.makeText(Activity_Login.this,"Please enter UserName",Toast.LENGTH_SHORT).show();
                }

                else if ((Password.getText().toString()).isEmpty())
                {
                    mDialog.dismiss();
                    Toast.makeText(Activity_Login.this,"Please enter Password",Toast.LENGTH_SHORT).show();
                }

                else if (!((UserName.getText().toString()).isEmpty() && (Password.getText().toString()).isEmpty())) {

                    table_login.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //check if user not exist in Database
                            if (dataSnapshot.child(UserName.getText().toString()).exists()) {
                                //Get User Data
                                mDialog.dismiss();
                                Login login = dataSnapshot.child(UserName.getText().toString()).getValue(Login.class);
                                if (login.getU_password().equals(Password.getText().toString())) {
                                    Toast.makeText(Activity_Login.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                                    Intent main = new Intent(Activity_Login.this,Home.class);
                                    Common.currentUser = login;
                                    startActivity(main);
                                    finish();
                                } else {
                                    Toast.makeText(Activity_Login.this, "Sign in Fail !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Activity_Login.this, "User Not Found!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    mDialog.dismiss();
                    Toast.makeText(Activity_Login.this,"Error Occurred !!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(Activity_Login.this,SignUp.class);
                startActivity(signUp);

            }
        });
        handler.postDelayed(runnable,2000);
        getSupportActionBar().hide();
    }
}
