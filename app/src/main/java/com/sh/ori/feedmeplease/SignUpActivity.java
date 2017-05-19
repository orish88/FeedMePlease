package com.sh.ori.feedmeplease;

import android.content.Intent;
import android.net.MailTo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {






    private static String TAG = "notes";
    EditText etMail,etUsername, etpassword;
    Button btSignup, btCancel;
    //aqua: 31.776640, 35.197886
    //dorms: 31.768740, 35.198462
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etMail = (EditText) findViewById(R.id.et_sign_up_email);
        etUsername = (EditText) findViewById(R.id.et_sign_up_username);
        etpassword = (EditText) findViewById(R.id.et_sign_up_password);
        btSignup = (Button)  findViewById(R.id.bt_sign_up_button);
        btCancel = (Button)  findViewById(R.id.bt_sign_up_cancel);
        btSignup.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.bt_sign_up_button):
                String email = etMail.getText().toString();
                String password = etpassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case(R.id.bt_sign_up_cancel):

                break;
        }
    }
    public void updateUI(FirebaseUser user){

        //todo: add something to usersInfo in firebase?


    }
}
