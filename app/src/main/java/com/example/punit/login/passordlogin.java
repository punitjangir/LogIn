package com.example.punit.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class passordlogin extends AppCompatActivity{

    Button Login3, cancel_action;
    EditText passtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_login);

        Login3 = findViewById(R.id.Login3);
        Login3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            LogCall();
            }
        });


        cancel_action = findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelclass();
            }
        });

    }













    public void LogCall ()
    {
        passtext = findViewById(R.id.passtext);
        String passget = passtext.getText().toString();
        if (TextUtils.isEmpty(passget))
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else if (passget.equals("passadmin"))
        {
            Toast.makeText(this, "Password is Correct", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, act1.class));
        }
        else
        {
            Toast.makeText(this,"Password is Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelclass()
    {
        startActivity(new Intent(this, MainActivity.class));
    }



}
