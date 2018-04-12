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
import android.media.ToneGenerator;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

//    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = "MainActivity1";
    public String databaseChangeValue = "";
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference Beta = FirebaseDatabase.getInstance().getReference("Beta");
        DatabaseReference Theta = FirebaseDatabase.getInstance().getReference("Theta");
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        Beta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                databaseChangeValue = value;
                double threshold = Double.parseDouble(value);
                //change to threshold 0.5 for beta
                if(threshold < 0.5)
                    testFunction();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Theta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                databaseChangeValue = value;
                double threshold = Double.parseDouble(value);
                //change to threshold
                if(value.equals("testValue"))
                    testFunction();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

   public void testFunction() {
       //tone generator
       //audio generator

       Context context = getApplicationContext();
       int duration = Toast.LENGTH_SHORT;

       Toast toast = Toast.makeText(context, databaseChangeValue, duration);
       toast.show();
       ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 300);
       toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

       /*
       String toSpeak = "What is four plus two";
       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
       */
   }

}


// existing stimulus in java script