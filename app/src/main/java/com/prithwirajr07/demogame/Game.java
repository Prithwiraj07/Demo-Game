package com.prithwirajr07.demogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

// Game class is responsible for all the objects in the game as well as rendering things and updating states
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;


    public Game(Context context) {
        super(context);

        //Get Surface Holder and Add Callback
        SurfaceHolder surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);

        gameLoop= new GameLoop(this, surfaceHolder);
        //Initialize player
        player=new Player(getContext(),500,500,25);
        setFocusable(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Handle Touch Event Actions

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                player.setPosition((double)event.getX(),(double)event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                player.setPosition((double)event.getX(),(double)event.getY());
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        player.draw(canvas);
    }
    public void drawUPS(Canvas canvas){
        String averageUPS=Double.toString(gameLoop.getAverageUPS());
        Paint paint=new Paint();
        int color= ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);

        canvas.drawText("UPS: " + averageUPS,50,70,paint);

    }
    public void drawFPS(Canvas canvas){
        String averageFPS=Double.toString(gameLoop.getAverageFPS());
        Paint paint=new Paint();
        int color= ContextCompat.getColor(getContext(), R.color.magenta);

        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS,50,130,paint);

    }
    public void update(){
    //Update game state
        player.update();
    }
}
