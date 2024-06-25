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

import androidx.annotation.Nullable;

public class ServiceA extends Service {
    // Klasa obsługująca wiadomości otrzymane od klienta.
    class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 12345){
                Log.e("SRV", "replyMessage:Dostalem msg:" + msg.getData().getString("FAJNY_STRING"));
                Message message = Message.obtain(null, 54321, 0, 0);
                Bundle data = new Bundle();
                data.putString("AAA", "AAA odpowiedz");
                message.setData(data);
                Messenger replyTo = msg.replyTo;
                try {
                    replyTo.send(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            // Tu należy obsłużyć wiadomość msg w zależności od jej parametru „what”.
        }
    }
    // Obiekt Messenger służący do odbierania wiadomości od klienta.
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    // Zwrócenie interfejsu obiektu Messengera podczas zestawienia połączenia
    // pomiędzy klientem a serwisem.
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("SRV", "IBinder");
        return mMessenger.getBinder();
    }


}
