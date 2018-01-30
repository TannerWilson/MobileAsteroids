package edu.u0851647utah.asteroids;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tanner on 11/30/2016.
 */


public class Game {
    private static Game ourInstance = new Game();
    public Context context = null;
    public boolean thrust;

    public boolean gamePlayed = false;

    public boolean shields;
    public int lives;
    public int difficulty;
    public int asteroidNum;
    public int bigAsteroids;
    public double score;
    public mGLRenderer renderer;

    ArrayList<Sprite> sprites;
    ShipSprite ship;


    public void setListener(EndGameListener listener) {
        this.listener = listener;
    }

    public EndGameListener listener  = null;

    public interface EndGameListener
    {
        public void gameEnded(String target);
    }

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
        shields = false;
        lives = 3;
        difficulty = 1;
        asteroidNum = 5;
        bigAsteroids = 5;

        sprites = new ArrayList<>();
//        sprites.add(new ShipSprite());
//        sprites.add(new AsteroidSprite(true));
//        sprites.add(new RightArrowSprite());
    }

    public void toggleShields()
    {
        if(shields)
            shields = false;
        else
            shields = true;
    }

    public void explodeBigAsteroid()
    {
        bigAsteroids--;
        asteroidNum++;
        score += 50;

    }

    public void explodeSmallAsteroid()
    {
        asteroidNum--;
        score += 75;
    }

    public void collision(Sprite sprite1, Sprite sprite2)
    {
        if(sprite1.getType() == "asteroid" && sprite2.getType() == "ship")
        {

//            if(sprite1.getScaleX() == 0.15f)
//            {
//                explodeSmallAsteroid();
//            }
//            else
//                explodeBigAsteroid();



            // ship died
            ship.resetShip();
            lives--;
            renderer.asteroidShipCollide = sprite1;

            if(asteroidNum == 0)
            {
//                bigAsteroids++;
//                renderer.addAsteroid("big");
//                return;
            }


            if(lives == 0)
            {
                // End game
                gamePlayed = true;
                listener.gameEnded("SCORES");
            }

        }

        if(sprite1.getType() == "asteroid" && sprite2.getType() == "bullet")
        {
            if(sprite1.getScaleX() == 0.15f)
            {
                score += 75;

                renderer.bulletThatJustCollided = sprite2;
                renderer.asteroidShipCollide = sprite1;
                return;
            }
            else
            {
                score += 50;
            }

            if(asteroidNum == 0)
            {
//                bigAsteroids++;
//                renderer.addAsteroid("big");
//                return;
            }


            renderer.bulletThatJustCollided = sprite2;
            renderer.lastDestroyedAsteroid = sprite1;
            renderer.addAsteroid("small");
        }
    }
}
