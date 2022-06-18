package com.prithwirajr07.demogame;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.Observer;

public class GameLoop extends Thread {
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning= false;
    private double averageUPS;
    private double averageFPS;


    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game=game;
        this.surfaceHolder=surfaceHolder;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public void startLoop() {
        isRunning= true;
        start();
    }

    @Override
    public void run() {
        super.run();

        //Variables for FPS and UPS Calculation
        int updateCount=0;
        int frameCount=0;

        double startTime;
        double elapsedTime;
        double sleepTime;


        Canvas canvas=null;
        //This is the actual game loop
        startTime=System.currentTimeMillis();
        while(isRunning){
            /* Things to be done here-
            1. Try to update and render Games
            2. Pause game loop to not exceed target UPS
            3. Skip frames(Lower down FPS) to keep up with target UPS
            4. Calculate average UPS and FPS
             */


            //Try to update and render Games
            try {
                canvas= surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }



            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            } finally {
                if(canvas!=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }


            elapsedTime=System.currentTimeMillis()-startTime;
            if (elapsedTime>=1000){
                averageUPS=updateCount/(1E-3 * elapsedTime);
                averageFPS=frameCount/(1E-3 * elapsedTime);
                updateCount=0;
                frameCount=0;
                startTime=System.currentTimeMillis();
            }


        }
    }


}
