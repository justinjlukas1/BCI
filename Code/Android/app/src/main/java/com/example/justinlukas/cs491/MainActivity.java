package com.example.justinlukas.cs491;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextToSpeech t1;
    DatabaseReference Beta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        loadActivity();
    }

    private void loadActivity() {
        Beta = FirebaseDatabase.getInstance().getReference().child("Beta").child("BandPower");

        Beta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "beta is: " + value);

                double threshold = Double.parseDouble(value);
                launchTrivia(threshold);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void launchTrivia(double threshold) {

        if(threshold < 25) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("masterSheet");

            Random rn = new Random();
            int question = rn.nextInt(14) + 1;
            Log.w(TAG, "Question Number: " + Integer.toString(question));

            //Get question and answer from database
            ref.child(Integer.toString(question)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String q = dataSnapshot.child("0").getValue(String.class);
                    String a = dataSnapshot.child("1").getValue(String.class);

                    Log.w(TAG, "Question: " + q);
                    Log.w(TAG, "Answer: " + a);

                    //Ask question
                    t1.speak(q, TextToSpeech.QUEUE_FLUSH, null);

                    //Wait 8 seconds, then give the answer
                    hold(8000);
                    t1.speak("The answer is, " + a, TextToSpeech.QUEUE_FLUSH, null);

                    //Sleep to prevent excessive reads after an event
                    hold(6000);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });
        }

        //Restart read activity
        loadActivity();
    }

    //Sleep function
    private void hold(int mils) {
        try {
            Thread.sleep(mils);

        }
        catch (InterruptedException e) {

        }
    }
}
