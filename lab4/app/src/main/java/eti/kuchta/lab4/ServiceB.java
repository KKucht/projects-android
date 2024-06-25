package eti.kuchta.lab4;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


public class ServiceB extends Service {
    // Klasa obsługująca wiadomości otrzymane od klienta.
    // Obiekt Messenger służący do odbierania wiadomości od klienta.


    private final mojInterface.Stub mBinder = new mojInterface.Stub() {
        public int getPid() {
            return android.os.Process.myPid();
        }
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            // Does nothing.
        }
    };


    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

}
