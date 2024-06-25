package eti.kuchta.lab2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }


    public Canvas canvas;
    private ArrayList<Float> X = new ArrayList();
    private ArrayList<Float> Y = new ArrayList();
    @Override
    public boolean onTouchEvent(MotionEvent event){
        X.add(event.getX());
        Y.add(event.getY());
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        this.canvas = canvas;
        Paint p = new Paint();
        p.setARGB(255,255,0,0);
        p.setStrokeWidth(25);
        for(int i = 0 ; i < Y.size() ; i++){
            canvas.drawCircle(X.get(i),Y.get(i),25,p);
        }
    }

    public Bitmap getBitmap()
    {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

}