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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    private static final String TAG = MainActivity.class.getSimpleName();

    public static class Trivia {
        public String question;
        public String answer;

        public Trivia(String question1, String answer1){
            question = question1;
            answer = answer1;
        }
    }
    private static final String TAG = "MainActivity1";
    public String databaseChangeValue = "";
    TextToSpeech t1;
    DatabaseReference Beta;
    DatabaseReference Theta;
    DatabaseReference TriviaQuestion;
    DatabaseReference TriviaAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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

        Beta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "beta is: " + value);
                //databaseChangeValue = value;

                double threshold = Double.parseDouble(value);
                //change to threshold 0.5 for beta
                if(threshold < 25)
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
                Log.d(TAG, "Theta is: " + value);
                //databaseChangeValue = value;

                double threshold = Double.parseDouble(value);
                //change to threshold 0.5 for beta
                if(threshold < 25)
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

       Random r = new Random();
       final int[] size = new int[1];










       /*TriviaQuestion = FirebaseDatabase.getInstance().getReference().child("masterSheet");
       // Attach a listener to read the data at our posts reference
       TriviaQuestion.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Trivia trivia = dataSnapshot.getValue(Trivia.class);
               System.out.println(trivia);
               Context context = getApplicationContext();
               int duration = Toast.LENGTH_SHORT;

               Toast toast = Toast.makeText(context, trivia.question, duration);
               toast.show();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               System.out.println("The read failed: " + databaseError.getCode());
           }
       });
*/
    /*
       TriviaQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // get total available quest
               size[0] = (int) dataSnapshot.getChildrenCount();
           }
           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       //set query to get count of questions
       int question = r.nextInt(size[0]);
       TriviaQuestion = FirebaseDatabase.getInstance().getReference().child("masterSheet").child(Integer.toString(question)).child("0");
       TriviaAnswer = FirebaseDatabase.getInstance().getReference().child("masterSheet").child(Integer.toString(question)).child("1");
       */
       //ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 300);
       //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);


       String toSpeak = "What is four plus two times nine and add fourteen";
       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

   }

}


// existing stimulus in java script