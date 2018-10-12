package com.example.punit.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Allog extends AppCompatActivity {

    EditText ProdName;
    Button Addinv;
    String Value;
    Spinner siz, spin1;
    List<String> ProductList = new ArrayList<>();

    DatabaseReference databaseProduct;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allog);


        databaseProduct = FirebaseDatabase.getInstance().getReference("Product");

        ProdName = findViewById(R.id.ProdName);
        Addinv = findViewById(R.id.Addinv);
        siz = findViewById(R.id.siz);
        spin1 = findViewById(R.id.spin1);




        Addinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductAdd();

            }
        });
    }




        public void ProductAdd()
        {

            databaseProduct.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ProductSnapshot : dataSnapshot.getChildren()) {
                        String Product = ProductSnapshot.child("ProductDetail").getValue(String.class);
                        ProductList.add(Product);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            databaseProduct = FirebaseDatabase.getInstance().getReference("Product");

            ProdName = findViewById(R.id.ProdName);
            Addinv = findViewById(R.id.Addinv);
            siz = findViewById(R.id.siz);
            spin1 = findViewById(R.id.spin1);


            String id =  databaseProduct.push().getKey();
            String name  = ProdName.getText().toString().trim();
            String size = siz.getSelectedItem().toString();
            String color1 = spin1.getSelectedItem().toString();
            String productdetail = name + " - " + size + " - " + color1.trim();
            String Value = "0";
            String date = getCurrentTime();
            if (ProductList.contains(productdetail)) {
                Toast.makeText(this, "Product Already Exists", Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(name))
            {
                Toast.makeText(this, "You should enter the name", Toast.LENGTH_LONG).show();
            }





           else if ((!TextUtils.isEmpty(name))& (!ProductList.contains(productdetail)))
            {
                    prdadd Product1 = new prdadd(id, name, size, color1, productdetail, Value, date);
                    databaseProduct.child(productdetail).setValue(Product1);
                    Toast.makeText(this, "Product Added", Toast.LENGTH_LONG).show();
            }

            else
            {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }



        }

    }