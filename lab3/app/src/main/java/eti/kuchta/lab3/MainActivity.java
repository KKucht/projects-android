package eti.kuchta.lab3;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private String fileName = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                ParcelFileDescriptor pfd = null;
                try {
                    pfd = getContentResolver().openFileDescriptor(uri, "w");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
                TextView textInput =  findViewById(R.id.TextInput);
                try {
                    fileOutputStream.write(textInput.getText().toString().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    pfd.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        else if (requestCode == 112 && resultCode == Activity.RESULT_OK) {
        Uri uri = null;
        if (resultData != null) {
            uri = resultData.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                TextView textInput =  findViewById(R.id.TextInput);
                byte[] buffer = new byte[2000];
                inputStream.read(buffer);
                textInput.setText(
                        new String(buffer, StandardCharsets.UTF_8));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textName =  findViewById(R.id.TextName);

        final Button saveButton = (Button) findViewById(R.id.SaveButton);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/txt");
                fileName = textName.getText().toString();
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI,
                        Environment.DIRECTORY_DOCUMENTS);
                startActivityForResult(intent, 111);
            }
        });

        final Button loadButton = (Button) findViewById(R.id.LoadButton);

        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                fileName = "";
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI,
                        Environment.DIRECTORY_DOCUMENTS);
                startActivityForResult(intent, 112);

            }
        });

        


    }
}