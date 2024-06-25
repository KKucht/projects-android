package eti.kuchta.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onBackPressed(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("wyjsc?");
        adb.setPositiveButton("Jo!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        adb.setNegativeButton("Zostan!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        adb.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                Log.d("ZX", "Result is OK: ");
                Bitmap bitmap = (Bitmap) data.getParcelableExtra("BitmapImage");
                LinearLayout layout = (LinearLayout) findViewById(R.id.okok);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                layout.removeAllViews();
                layout.addView(imageView);
            }
            else{
                Log.d("ZX", "Result is NOT OK");
            }
        }
        else{
            Log.d("ZX", "None 999");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button xdButton = (Button) findViewById(R.id.xd_button);

        Intent secondActivityIntent = new Intent(this, NewActivity.class);

        xdButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                startActivityForResult(secondActivityIntent, 999);
            }
        });
    }
}