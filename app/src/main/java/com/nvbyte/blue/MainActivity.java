package com.nvbyte.blue;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText textBox = (EditText) findViewById(R.id.textBox);
        textBox.addTextChangedListener(new TextWatcher() {
            long lastWordBegin = -1;
            long lastKeyStroke = -1;
            long wordTime = -1;
            long lastWordEnd = -1;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public synchronized void onTextChanged(CharSequence s, int start, int before, int count) {
                if(lastKeyStroke == -1) {
                    lastKeyStroke = System.currentTimeMillis();
                    lastWordBegin = lastKeyStroke;
                    if(lastWordEnd != -1) {
                        Log.d(TAG,"Inter word speed: " + (lastWordBegin - lastWordEnd));
                    }
                } else {


                    long oldStroke = lastKeyStroke;
                    lastKeyStroke = System.currentTimeMillis();
                    Log.d(TAG,"Inter Letter Time: " + (lastKeyStroke - oldStroke));

                    if(count < before) {
                        Log.d(TAG,"Backspace");
                        return;
                    }
                    char newLetter = s.charAt(start);
                    if (newLetter == ' ') {
                        wordTime = lastKeyStroke - lastWordBegin;
                        Log.d(TAG,"Word Time: " + wordTime);
                        lastWordBegin = -1;
                        lastKeyStroke = -1;
                        lastWordEnd = System.currentTimeMillis();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
