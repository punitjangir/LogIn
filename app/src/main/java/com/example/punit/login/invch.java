package com.example.punit.login;


import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class invch extends AppCompatActivity {

    FirebaseDatabase Proddata = FirebaseDatabase.getInstance();
    DatabaseReference ProductRef = Proddata.getReference();
    FirebaseAuth sgn;
    Spinner spinner1;
    Button Addpr, Removepr, SignOut, NewProduct;
    EditText getnumber;


    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

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
        setContentView(R.layout.activity_invch);


        SignOut = findViewById(R.id.Signout);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        NewProduct = findViewById(R.id.NewProduct);
        NewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(invch.this, Allog.class));
            }
        });


        Addpr = findViewById(R.id.Addpr);

        Addpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getnumber = findViewById(R.id.getnumber);
                String s2 = getnumber.getText().toString();
                if (TextUtils.isEmpty(s2)) {
                    Toast.makeText(getApplicationContext(), "Number field can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Prodadding();

                    Addpr.setClickable(false);

                    final Handler myHandler = new Handler();
                    myHandler.postDelayed(new Runnable() {
                        public void run() {
                            Addpr.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });


        Removepr = findViewById(R.id.Removepr);
        Removepr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getnumber = findViewById(R.id.getnumber);
                String s2 = getnumber.getText().toString();
                if (TextUtils.isEmpty(s2)) {
                    Toast.makeText(getApplicationContext(), "Number field can't be empty", Toast.LENGTH_SHORT).show();
                } else {


                    Prodremove();
                    Removepr.setClickable(false);

                    final Handler myHandler = new Handler();
                    myHandler.postDelayed(new Runnable() {
                        public void run() {
                            Removepr.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });
    }

//Getting Value to Spinner
    @Override
    protected void onStart() {
        super.onStart();
        ProductRef.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> ProdVal = new ArrayList<>();
                for (DataSnapshot ProductSnapshot : dataSnapshot.getChildren()) {
                    String Product = ProductSnapshot.child("ProductDetail").getValue(String.class);
                    ProdVal.add(Product);
                }
                spinner1 = findViewById(R.id.spinner1);
                ArrayAdapter<String> ProductAdapter = new ArrayAdapter<>(invch.this, android.R.layout.simple_selectable_list_item, ProdVal);
                ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(ProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Product Value Add
    public void Prodadding()
    {
        getnumber = findViewById(R.id.getnumber);
        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String d1 = spinner1.getSelectedItem().toString();
                String d2  = dataSnapshot.child("Product").child(d1).child("valueOfProduct").getValue(String.class);
                int e1 = Integer.parseInt(d2);
                String d3 = getnumber.getText().toString();
                int e2 = Integer.parseInt(d3);
                int e3 = e1+e2;
                String Addfinal = Integer.toString(e3);
                ProductRef.child("Product").child(d1).child("valueOfProduct").setValue(Addfinal);

                String date = getCurrentTime();
                String datef = d3 +" Item Added On :" +date;
                ProductRef.child("Product").child(d1).child("ProductHistory").push().setValue(datef);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String h2 = getnumber.getText().toString();
        Toast.makeText(this, "No. of Product Added : "+h2, Toast.LENGTH_SHORT).show();
    }

        // Value remove
    public void Prodremove()
    {
        getnumber = findViewById(R.id.getnumber);
        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String f1 = spinner1.getSelectedItem().toString();
                String f2 = dataSnapshot.child("Product").child(f1).child("valueOfProduct").getValue(String.class);
                int g1 = Integer.parseInt(f2);
                String f3 = getnumber.getText().toString();
                int g2 = Integer.parseInt(f3);
                int g3 = g1-g2;
                String Removefinal = Integer.toString(g3);
                ProductRef.child("Product").child(f1).child("valueOfProduct").setValue(Removefinal);

                String date = getCurrentTime();
                String datef = f3 +"Item Removed On :" +date;
                ProductRef.child("Product").child(f1).child("ProductHistory").push().setValue(datef);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String h1 = getnumber.getText().toString();
        Toast.makeText(this, "No. of Product Removed : "+h1, Toast.LENGTH_SHORT).show();
    }



}
