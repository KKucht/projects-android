package eti.kuchta.lab1v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public SeekBar seekBar;
    public TextView textEdit;


    private class SuperTask extends AsyncTask {
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            while(seekBar.getProgress() < 100){
                if(isCancelled()){
                    return null;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                seekBar.setProgress(seekBar.getProgress()+1);
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.my_text);

        final Button leftButton = (Button) findViewById(R.id.left_button);


        final SuperTask[] runner = {null};

        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                textView.setText("Dziala!!!");
                if (runner[0] == null){
                    runner[0] = new SuperTask();
                    runner[0].execute();
                }
            }
        });

        final Button rightButton = (Button) findViewById(R.id.right_button);

        rightButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                textView.setText("Przerwano!!!");
                if (runner[0] != null){
                    runner[0].cancel(true);
                    runner[0] = null;
                }
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        textEdit =  findViewById(R.id.text1);

        textEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int change = Integer.parseInt(textEdit.getText().toString());
                if (change > 100){
                    seekBar.setProgress(100);
                }
                else{
                    seekBar.setProgress(Integer.parseInt(textEdit.getText().toString()));
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                textEdit.setText(Integer.toString(seekBar.getProgress()));
            }
        });



    }






}