package eti.kuchta.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    boolean isBound = false;
    Messenger mMessenger = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            // Utworzenie obiektu Messengera umożliwiającego nadawanie wiadomości.
            mMessenger = new Messenger(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            isBound = false;
        }
    };

    private final Messenger replyMessage = new Messenger(new ResponseHandler());

    private class ResponseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 54321){
                Log.e("CLI", "replyMessage:Dostalem msg:" + msg.getData().getString("AAA"));
            }
        }
    }

    boolean mBound = false;
    mojInterface mService = null;
    private ServiceConnection mConnection123 = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBound = true;
            mService = mojInterface.Stub.asInterface(service);
            Log.e("CLI", "CONNECT BY AIDL");

        }

        public void onServiceDisconnected (ComponentName className){
            mBound = false;
            mService = null;
            Log.e("CLI", "UNCONNECT BY AIDL");
        }
    };


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindService( new Intent( this, ServiceA.class ), serviceConnection, Context.BIND_AUTO_CREATE );



        final Button button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if (isBound) {
                    Message msg = Message.obtain(null, 12345, 0, 0);
                    Bundle data = new Bundle();
                    data.putString("FAJNY_STRING", "ALE FAJNY TEKST W FAJNYM STRINGU");
                    msg.setData(data);
                    msg.replyTo = replyMessage;
                    try {
                        mMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Log.e("CLI", "Nie mozna sie polaczyc z serwerem");
                }
            }
        });

        final Button button2 = (Button) findViewById(R.id.button2);

        bindService( new Intent( this, ServiceB.class ), mConnection123, Context.BIND_AUTO_CREATE );

        button2.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if (mBound) {
                    try {
                        int pid = mService.getPid();
                        Log.e("CLI", ""+Integer.toString(pid));
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                else{
                    Log.e("CLI", "Nie mozna sie polaczyc z serwerem");
                }
            }
        });


    }

    public void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

}