package edu.u0851647utah.asteroids;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Tanner on 11/30/2016.
 */
public class mGLRenderer implements GLSurfaceView.Renderer{

    //ShipSprite shipSprite;

    double timeChange;
    double lastTime;
    boolean addBullet = false;
    boolean addAsteroid = false;
    boolean addSmallAsteroid = false;
    boolean addBigAsteroid = false;

    Sprite lastDestroyedAsteroid;
    Sprite asteroidShipCollide;
    Sprite bulletThatJustCollided;

    ArrayList<Sprite> toRemove = new ArrayList<>();

    @Override
    public void onSurfaceCreated(GL10 unused, javax.microedition.khronos.egl.EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


        // Initialize the game screen
        ShipSprite shipSprite = new ShipSprite();
        Game.getInstance().sprites.add(shipSprite);
        Game.getInstance().ship = shipSprite;

        for(int i = 0; i < Game.getInstance().bigAsteroids; i++)
        {
            AsteroidSprite asteroid = new AsteroidSprite(true);
            Game.getInstance().sprites.add(asteroid);
        }

        Game.getInstance().sprites.add(new RightArrowSprite());
        Game.getInstance().sprites.add(new LeftArrowSprite());
        Game.getInstance().sprites.add(new UpArrowSprite());
        Game.getInstance().sprites.add(new TargetSprite());
//        Game.getInstance().sprites.add(new BulletSprite());

        for(Sprite s: Game.getInstance().sprites)
            s.start();

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        double current = SystemClock.elapsedRealtime();

        timeChange = current - lastTime;
        timeChange = timeChange/1000.0;
        lastTime = current;

        if(asteroidShipCollide != null)
        {
            Game.getInstance().sprites.remove(asteroidShipCollide);
            asteroidShipCollide = null;
        }

        if(addBullet)
        {
            BulletSprite bullet = new BulletSprite();
            Game.getInstance().sprites.add(bullet);
            bullet.start();
            addBullet = false;
        }

        if(addAsteroid)
        {
            if(addBigAsteroid)
            {
                for(int i = 0; i < Game.getInstance().bigAsteroids; i++)
                {
                    AsteroidSprite asteroid = new AsteroidSprite(true);
                    Game.getInstance().sprites.add(asteroid);
                    asteroid.start();
                }
                Game.getInstance().asteroidNum = Game.getInstance().bigAsteroids;

                addAsteroid = false;
                addBigAsteroid = false;
            }
            if(addSmallAsteroid)
            {
                Game.getInstance().sprites.remove(bulletThatJustCollided);
                bulletThatJustCollided = null;

                // Add two small asteroids
                AsteroidSprite asteroid = new AsteroidSprite(lastDestroyedAsteroid);
                Game.getInstance().sprites.add(asteroid);
                asteroid.start();

                addAsteroid = false;
                addSmallAsteroid = false;
            }
        }

        // remove needed sprites
//        for(Sprite s: toRemove)
//                Game.getInstance().sprites.remove(s);
//        toRemove.clear();

        // Update all sprites before drawing
        for(Sprite s: Game.getInstance().sprites)
        {
            s.update(timeChange);

            if(s.getType() == "bullet" && s.getTime() > 3)
            {
                Game.getInstance().sprites.remove(s);
            }

        }

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        for(Sprite s: Game.getInstance().sprites)
            s.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void shootBullet()
    {
        addBullet = true;
//        BulletSprite bullet = new BulletSprite();
//        Game.getInstance().sprites.add(bullet);
//        bullet.start();
    }

    public void addAsteroid(String type)
    {
        if(type == "small")
        {
            addAsteroid = true;
            addSmallAsteroid = true;
        }
        if(type == "big")
        {
            addAsteroid = true;
            addBigAsteroid = true;
        }
    }

    public void accelerateShip()
    {
        Game.getInstance().ship.accelerate();
    }

    public void rotateShip(boolean rotate, String direction)
    {
       if(rotate)
       {
           if(direction == "RIGHT")
           {
               Game.getInstance().ship.rotateRight = true;
               Game.getInstance().ship.rotating = true;
           }
           if(direction == "LEFT")
           {
               Game.getInstance().ship.rotateRight = false;
               Game.getInstance().ship.rotating = true;
           }
       }
        else
           Game.getInstance().ship.rotating = false;
    }

}
