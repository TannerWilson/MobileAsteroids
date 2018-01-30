package edu.u0851647utah.asteroids;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Tanner on 11/29/2016.
 */
public class MenuFragment extends Fragment implements Button.OnClickListener {

    // Get buttons from the layout
    Button newGame;
    Button highScore;
    ButtonClickedListener buttonListener = null;

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    public MenuFragment()
    {

    }

    interface ButtonClickedListener
    {
        void buttonClicked(boolean isNewGame);
    }

    public void setButtonListener(ButtonClickedListener listener) {
        this.buttonListener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        setButtonListeners();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_menu, container, false);
    }

    @Override
    public void onClick(View v) {
        if(v == newGame)
            buttonListener.buttonClicked(true);
        else if(v == highScore)
            buttonListener.buttonClicked(false);
    }

    public void setButtonListeners()
    {
        newGame = (Button) getActivity().findViewById(R.id.newGameButton);
        highScore = (Button) getActivity().findViewById(R.id.highScoreButton);
        newGame.setOnClickListener(this);
        highScore.setOnClickListener(this);
    }
}
