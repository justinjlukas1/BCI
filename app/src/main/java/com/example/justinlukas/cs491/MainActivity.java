package com.example.justinlukas.cs491;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = "MainActivity1";
    public String databaseChangeValue = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                databaseChangeValue = value;
                if(value.equals("testValue"))
                    testFunction();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        /*
        while(true) {
            testFunction("test");
        }

        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                testFunction("hello, World!");
            }
        });
        */
    }

   public void testFunction() {
       Context context = getApplicationContext();
       int duration = Toast.LENGTH_SHORT;

       Toast toast = Toast.makeText(context, databaseChangeValue, duration);
       toast.show();

    }

}


// existing stimulus in java script