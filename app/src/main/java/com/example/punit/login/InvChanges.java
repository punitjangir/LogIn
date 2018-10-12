package com.example.punit.login;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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


public class InvChanges extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ProductRef = database.getReference();
    DatabaseReference Prodidd;
    Spinner spinnername,Selectv;
    Button Addv, Removev, Signt, NewProduct1;
    String v1, v2, v3;
    TextView Pvalue;
    EditText getnum;
    final Handler handler = new Handler();



    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //FirebaseAuth.getInstance().signOut();
        //onStop();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_changes);

        Selectv = findViewById(R.id.gtvalue);
        Addv = findViewById(R.id.Addproduct);
        Addv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getnum = findViewById(R.id.getnum);
                String r1 = getnum.getText().toString();
                if (TextUtils.isEmpty(r1)) {
                    Toast.makeText(getApplicationContext(), "Number Field can't be Empty", Toast.LENGTH_SHORT).show();
                } else {

                    Adddata();
                    Addv.setClickable(false);
                    final Handler myHandler = new Handler();
                    myHandler.postDelayed(new Runnable() {
                        public void run() {
                            Addv.setClickable(true);
                        }
                    }, 2000);
                }
            }
        });

        NewProduct1 = findViewById(R.id.NewProduct1);
        NewProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvChanges.this, Allog.class));
            }
        });


        Signt = findViewById(R.id.Signt);
        Signt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        Removev = findViewById(R.id.Removeproduct);
        Removev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getnum = findViewById(R.id.getnum);
                String r1 = getnum.getText().toString();
                if (TextUtils.isEmpty(r1)) {
                    Toast.makeText(getApplicationContext(), "Number Field can't be Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Removev.setClickable(false);

                    final Handler myHandler = new Handler();
                    myHandler.postDelayed(new Runnable() {
                        public void run() {
                            Removev.setClickable(true);
                        }
                    }, 2000);
                    RemoveData();
                }
            }
        });





    }


    @Override
    public void onStart() {
        super.onStart();
        ProductRef.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ArrayList<String> Product1 = new ArrayList<>();


                for (DataSnapshot ProductSnapshot : dataSnapshot.getChildren()) {
                    String Product = ProductSnapshot.child("ProductDetail").getValue(String.class);
                    Product1.add(Product);
                }
                Spinner spinnername = findViewById(R.id.spinnername);
                ArrayAdapter<String> ProductAdapter = new ArrayAdapter<>(InvChanges.this, android.R.layout.simple_selectable_list_item, Product1);
                ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnername.setAdapter(ProductAdapter);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }

        });
        Spinner spinnername = findViewById(R.id.spinnername);
        spinnername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Sltdata();
                DataHistory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
        public void DataHistory()
        {
            spinnername = findViewById(R.id.spinnername);
            Prodidd = FirebaseDatabase.getInstance().getReference();
            Selectv = findViewById(R.id.gtvalue);
            Prodidd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String v1 = spinnername.getSelectedItem().toString().trim();
                    final ArrayList<String> Product2 = new ArrayList<>();
                    for (DataSnapshot ProductSnapshot : dataSnapshot.child("Product").child(v1).child("ProductHistory").getChildren()) {
                        String Product = ProductSnapshot.getKey();
                        Product2.add(Product);
                    }
                    final ArrayList<String> prod3 = new ArrayList<>();
                    for(String rev : Product2)
                    {
                        String rev2 = dataSnapshot.child("Product").child(v1).child("ProductHistory").child(rev).getValue(String.class);
                        prod3.add(rev2);
                    }

                    Spinner Selectv = findViewById(R.id.gtvalue);
                    ArrayAdapter<String> ProductAdapter = new ArrayAdapter<>(InvChanges.this, android.R.layout.simple_list_item_1, prod3);
                    ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Selectv.setAdapter(ProductAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {



                }
            });
        }

        public void Sltdata ()
        {

            Pvalue = findViewById(R.id.Pvalue);
            spinnername = findViewById(R.id.spinnername);
            Prodidd = FirebaseDatabase.getInstance().getReference();

            Prodidd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String v1 = spinnername.getSelectedItem().toString().trim();

                    String v2 = dataSnapshot.child("Product").child(v1).child("valueOfProduct").getValue(String.class);
                    String v3 = "Availabe : "  +v2;

                    Pvalue.setText(v3);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("loadPost:onCancelled", databaseError.toException());


                }
            });

        }
    public void Adddata ()
    {
        Pvalue = findViewById(R.id.Pvalue);
        getnum = findViewById(R.id.getnum);
        spinnername = findViewById(R.id.spinnername);
        Prodidd = FirebaseDatabase.getInstance().getReference();

        Prodidd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String c1 = spinnername.getSelectedItem().toString().trim();
                String c2 = dataSnapshot.child("Product").child(c1).child("valueOfProduct").getValue(String.class);
                int yy = Integer.parseInt(c2);
                String y1 = getnum.getText().toString();
                int z = Integer.parseInt(y1);
                int z1 = yy+z;
                String Addfinal = Integer.toString(z1);

                Prodidd.child("Product").child(c1).child("valueOfProduct").setValue(Addfinal);
                String date = getCurrentTime();
                String datef = y1 +" Item Added On :" +date;
                Prodidd.child("Product").child(c1).child("ProductHistory").push().setValue(datef);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());


            }
        });

        String vr = getnum.getText().toString();

        Toast.makeText(this, "Added No. of Product :  " + vr, Toast.LENGTH_SHORT).show();

        DataHistory();

    }




    public void RemoveData ()
    {
        Pvalue = findViewById(R.id.Pvalue);
        getnum = findViewById(R.id.getnum);
        spinnername = findViewById(R.id.spinnername);
        Prodidd = FirebaseDatabase.getInstance().getReference();

        Prodidd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String c1 = spinnername.getSelectedItem().toString().trim();

                String c2 = dataSnapshot.child("Product").child(c1).child("valueOfProduct").getValue(String.class);
                int yy = Integer.parseInt(c2);
                String y1 = getnum.getText().toString();
                int z = Integer.parseInt(y1);
                int z1 = yy-z;
                String Addfinal = Integer.toString(z1);
                String date = getCurrentTime();
                Prodidd.child("Product").child(c1).child("valueOfProduct").setValue(Addfinal);
                String datef = y1 +" Item Removed On :" +date;
                Prodidd.child("Product").child(c1).child("ProductHistory").push().setValue(datef);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());


            }
        });

        String vr = getnum.getText().toString();

        Toast.makeText(this, "Added No. of Removed :  " + vr, Toast.LENGTH_SHORT).show();

        DataHistory();

    }






}












