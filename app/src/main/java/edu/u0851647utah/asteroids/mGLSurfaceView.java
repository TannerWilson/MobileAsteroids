package edu.u0851647utah.asteroids;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tanner on 11/30/2016.
 */
public class mGLSurfaceView extends GLSurfaceView  {

    private final mGLRenderer mRenderer;

    public mGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new mGLRenderer();

        // Set the Sprite for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // save in game singleton
        Game.getInstance().renderer = mRenderer;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX =  event.getX();
        float touchY = event.getY();

        if(touchX >= getWidth()/2)
        {
            touchX -= getWidth()/2;
            touchX = touchX/(getWidth()/2);
        }
        else if(touchX < getWidth()/2)
        {
            touchX = touchX/(getWidth()/2);
            touchX = 1 - touchX;
            touchX = -touchX;
        }

        if(touchY < getHeight()/2)
        {
            touchY = touchY/(getHeight()/2);
            touchY = 1 - touchY;
        }
        else if(touchY >= getHeight()/2)
        {
            touchY -= getHeight()/2;
            touchY = touchY/(getHeight()/2);
            touchY = -touchY;
        }



//        Log.i("Got Touch: ", "(" + touchX + " , " + touchY + ")");

        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            //Left button
            if(     touchX >= -.99f && touchX <= -.99f + 0.18f &&
                    touchY >= -1 && touchY <= -1 + 0.18f)
            {
                mRenderer.rotateShip(false, "LEFT");
            }

            // right button
            if(touchX >= -.72f && touchX <= -.72f + 0.18 &&
                    touchY >= -1 && touchY <= -1 + 0.18f)
            {
                mRenderer.rotateShip(false, "RIGHT");
            }

        }

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            // do action

            //Left button
            if(     touchX >= -.99f && touchX <= -.99f + 0.18f &&
                    touchY >= -1 && touchY <= -1 + 0.18f)
            {
                mRenderer.rotateShip(true, "LEFT");
            }

            // right button
            if(touchX >= -.72f && touchX <= -.72f + 0.18 &&
                    touchY >= -1 && touchY <= -1 + 0.18f)
            {
                mRenderer.rotateShip(true, "RIGHT");
            }

            // Thrust button
            if(touchX >= -.87f && touchX <= -.87f + 0.18 &&
                    touchY >= -.786f && touchY <= -.786f + 0.18f)
            {
                mRenderer.accelerateShip();
            }

            // Target button
            if(touchX >= 0.7f && touchX <= 0.7f + 0.28 &&
                    touchY >= -1 && touchY <= -1 + 0.28f)
            {
                mRenderer.shootBullet();
            }
        }

        return true;
    }
}
