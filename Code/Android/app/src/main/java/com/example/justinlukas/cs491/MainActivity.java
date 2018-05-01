package com.example.justinlukas.cs491;

import android.content.Context;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextToSpeech t1;
    DatabaseReference Beta;
    DatabaseReference Theta;
    DatabaseReference TriviaQuestion;
    DatabaseReference TriviaAnswer;

    private static class Trivia {
        public String question;
        public String answer;

        public Trivia(String question1, String answer1){
            question = question1;
            answer = answer1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActivity();
    }

    private void loadActivity() {
        Beta = FirebaseDatabase.getInstance().getReference().child("Beta").child("BandPower");
        Theta = FirebaseDatabase.getInstance().getReference().child("Theta").child("BandPower");
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        Beta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "beta is: " + value);

                double threshold = Double.parseDouble(value);
                //change to threshold 0.5 for beta
                if(threshold < 1) {
                    testFunction();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void testFunction() {
        //tone generator
        //audio generator

        //Random r = new Random();
        //final int[] size = new int[1];

        //TriviaQuestion = FirebaseDatabase.getInstance().getReference().child("masterSheet");

        //set query to get count of questions
        //int question = r.nextInt(size[0]);
        //TriviaQuestion = FirebaseDatabase.getInstance().getReference().child("masterSheet").child(Integer.toString(question)).child("0");
        //TriviaAnswer = FirebaseDatabase.getInstance().getReference().child("masterSheet").child(Integer.toString(question)).child("1");

        //ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 300);
        //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

        String toSpeak = "What is four, plus two, times nine, plus fourteen";
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        //Sleep to prevent too many reads
        try {
            Thread.sleep(10000);

        }
        catch (InterruptedException e) {

        }
        //Restart read activity
        loadActivity();
    }
}
