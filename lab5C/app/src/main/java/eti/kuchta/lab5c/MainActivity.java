package eti.kuchta.lab5c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import eti.kuchta.lab5c.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'lab5c' library on application startup.
    static {
        System.loadLibrary("lab5c");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] x = {1 , 5, 2, 6, 0, 23, 75, 4, 0};

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        String list = "";
        for (int i = 0; i < x.length; i++){
            list = list + Integer.toString(x[i]) + " ";
        }
        TextView text =  (TextView) findViewById(R.id.text_id);
        text.setText(list);
        tv.setText(stringMyList(x));
    }

    /**
     * A native method that is implemented by the 'lab5c' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String stringMyList(int[] x);
}