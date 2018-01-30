package edu.u0851647utah.asteroids;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.FrameLayout;



public class AsteroidsActivity extends AppCompatActivity implements MenuFragment.ButtonClickedListener, ScoresFragment.ButtonListener, Game.EndGameListener {

    FrameLayout main = null;
    MenuFragment menuFrag;
    GameFragment gameFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Save away context
        Game.getInstance().context = this.getApplicationContext();
        Game.getInstance().setListener(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        setContentView(new mGLSurfaceView(this));
//
        setContentView(R.layout.activity_asteroids);
        main = (FrameLayout) findViewById(R.id.mainFrame);

        /* Set up fragment transactions */

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentByTag("MenuFragment");

        // Load menu fragment on start up
        if(menuFrag == null)
        {
            menuFrag =  MenuFragment.newInstance();
            menuFrag.setButtonListener(this);
            transaction.add(main.getId(), menuFrag, "MenuFragment");
        }
        else
        {
            transaction.replace(main.getId(), menuFrag);
        }
        transaction.commit();
    }

    @Override
    public void buttonClicked(boolean isNewGame)
    {
        if(isNewGame) //Game button pressed
        {
            // Check status here if we need to load a game or not

            // Show message and start game on button press
            showNewGameMessage("The game is about to start.\n\n" +
                                "Good luck pilot");

            // Start a new game here
            //switchFragments("NEWGAME");
        }
        else // Scores button pressed
        {
            // Go to high scores here
            switchFragments("SCORES");
        }

    }

    public void switchFragments(String type)
    {
        // Open a game fragment
        if(type == "NEWGAME")
        {
            gameFrag = GameFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(main.getId(), gameFrag);

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else if(type == "RESUME")
        {

        }
        else if(type == "SCORES") // Open HighScores fragment
        {
            ScoresFragment scoreFrag = ScoresFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(main.getId(), scoreFrag);

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            scoreFrag.setButtonListener(this);
        }
        else if(type == "MENU")
        {
//            MenuFragment menuFrag = MenuFragment.newInstance();
//            menuFrag.setButtonListener(this);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(main.getId(), menuFrag);

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
//            menuFrag.setButtonListeners();
        }

    }

    @Override
    public void buttonClick() {
        switchFragments("MENU");
    }

    public void showNewGameMessage(String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Game Time");
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Lets Go!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switchFragments("NEWGAME");
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void gameEnded(String target) {
        switchFragments("SCORES");


    }


}
