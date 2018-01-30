package edu.u0851647utah.asteroids;


/**
 * Created by Tanner on 12/16/2016.
 */
public interface SpriteInterface {


    void start();


    void update(double timeChange);


    void draw();

    void changeLocation(float xPos, float yPos, float rotation, float scaleX, float scaleY);


}
