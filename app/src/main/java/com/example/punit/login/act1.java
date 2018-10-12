package com.example.punit.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class act1 extends AppCompatActivity {

private EditText mEmail1, mPass1;
 Button mLog1;

private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);



        mAuth = FirebaseAuth.getInstance();
        mEmail1 = findViewById(R.id.mEmail1);
        mPass1 = findViewById(R.id.mPass1);
        mLog1 = findViewById(R.id.mLog1);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(act1.this, InvChanges.class));
                }
            }
        };
        mLog1.setOnClickListener(new View.OnClickListener()


        {

            @Override
                    public void onClick(View view)
            {

                startSignIn();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn ()
    {
        String email = mEmail1.getText().toString();
        String password = mPass1.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(act1.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if (email.equals("seller@gmail.com") || email.equalsIgnoreCase("seller@gmail.com"))
        {
            Toast.makeText(this, "Use Right Credentials", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(act1.this, "Signed In", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}
