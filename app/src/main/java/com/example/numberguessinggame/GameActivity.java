package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import kotlin.BuilderInference;

public class GameActivity extends AppCompatActivity {
    private TextView textViewLast, textViewChance, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;
    boolean twoDigits, threeDigits, fourDigits;
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempt;
    Random r = new Random();
    int random;
    int remainingChance = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewHint = findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewChance = findViewById(R.id.textViewChance);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        editTextGuess = findViewById(R.id.editTextGuess);
        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        if(twoDigits){
            random = r.nextInt(90)+10;
        }
        if (threeDigits){
            random = r.nextInt(900)+100;
        }
        if (fourDigits){
            random = r.nextInt(9000)+1000;
        }
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals("")){
                    Toast.makeText(GameActivity.this, "Please enter a guess", Toast.LENGTH_LONG).show();
                } else {
                    textViewHint.setVisibility(View.VISIBLE);
                    textViewChance.setVisibility(View.VISIBLE);
                    textViewLast.setVisibility(View.VISIBLE);
                    userAttempt++;
                    remainingChance--;
                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);
                    textViewLast.setText("Your last guess was: "+guess);
                    textViewChance.setText("Your remaining chances: "+remainingChance);
                    if (userGuess == random){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulations. My guess was :"+random+"\n\n You knew my number in "+userAttempt+" attempts."
                        +"\n\nYour guesses: "+guessesList+"\n\n Would you like to play again?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    if(userGuess > random){
                        textViewHint.setText("Decrease your guess");
                    }
                    if (userGuess< random){
                        textViewHint.setText("Increase your guess");
                    }
                    if(remainingChance==0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, your chance for guessing is over. duh😣\n\nYour guesses: "+guessesList+"\n\n Would you like to play again?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    editTextGuess.setText("");
                }
            }
        });
    }
}