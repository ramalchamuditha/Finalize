package com.example.introscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.introscreen.Model.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText Username,PWord,ConPWord;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        Username =findViewById(R.id.txtUserName);
        PWord = findViewById(R.id.txtPassword);
        ConPWord = findViewById(R.id.txtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_login = database.getReference("UserLogin");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Waiting ...!");
                mDialog.show();

                if((PWord.getText().toString()).isEmpty())
                {
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this,"Please enter UserName",Toast.LENGTH_SHORT).show();
                }
                else if((ConPWord.getText().toString()).isEmpty())
                {
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this,"Please enter Password",Toast.LENGTH_SHORT).show();
                }
                else if (!((PWord.getText().toString()).isEmpty() && (ConPWord.getText().toString()).isEmpty()))
                {
                    table_login.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(Username.getText().toString()).exists())
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "User already registered !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                mDialog.dismiss();
                                Login login = new Login(Username.getText().toString(),ConPWord.getText().toString(),"Student","U003");
                                table_login.child(Username.getText().toString()).setValue(login);
                                Toast.makeText(SignUp.this, "Sign up Successfully!! !", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }





            }
        });
    }
}
