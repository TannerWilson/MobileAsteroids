package edu.u0851647utah.asteroids;

import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Tanner on 11/29/2016.
 */
public class ScoresFragment extends Fragment implements ListAdapter, Button.OnClickListener {


    Button backButton;
    ButtonListener buttonListener;

    interface ButtonListener
    {
        void buttonClick();
    }

    public static ScoresFragment newInstance() {
        ScoresFragment fragment = new ScoresFragment();
        return fragment;
    }

    public ScoresFragment()
    {
//        HighScores.getInstance().addHighScore("First",10.0);
//        HighScores.getInstance().addHighScore("zane1",5.0);
//        HighScores.getInstance().addHighScore("Zane5",3.0);
//        HighScores.getInstance().addHighScore("Blah",9.0);
//        HighScores.getInstance().addHighScore("Zane2",4.5);
//        HighScores.getInstance().addHighScore("Zane6",2.5);
//        HighScores.getInstance().addHighScore("Zane4",3.5);
//        HighScores.getInstance().addHighScore("Zane3",4.0);
//        HighScores.getInstance().addHighScore("Zane9",1.0);
//        HighScores.getInstance().addHighScore("Zane7",2.0);
//        HighScores.getInstance().addHighScore("Zane8",1.5);

    }

    public void setButtonListener(ButtonListener buttonClickedListener) {
        this.buttonListener = buttonClickedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getActivity());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundResource(R.drawable.stars2);

        TextView title = new TextView(getActivity());
        title.setText("     High Scores");
        title.setTextSize(50f);
        title.setTextColor(Color.WHITE);
        root.addView(title, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 8));

        ListView soresView = new ListView(getActivity());
        soresView.setAdapter(this);
        root.addView(soresView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 3));

        backButton = new Button(getActivity());
        backButton.setOnClickListener(this);
        backButton.setText("Return to Menu");
        backButton.setTextSize(15f);
        backButton.setTextColor(Color.BLACK);
        root.addView(backButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 9));

        if(Game.getInstance().gamePlayed)
            showNewGameMessage();


        return root;
    }

    @Override
    public boolean isEmpty() {
        return HighScores.getInstance().isEmpty();
    }

    @Override
    public int getCount() {
        return HighScores.getInstance().getSize();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView score = new TextView(getActivity());
        double num = HighScores.getInstance().getScore(position);
        Math.round(num);
        String name = HighScores.getInstance().getName(position);
        score.setText("        " + name + "........................" + num);
        score.setTextSize(25f);
        score.setTextColor(Color.WHITE);
        return score;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onClick(View v) {
        buttonListener.buttonClick();
    }

    public void showNewGameMessage()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final double score = Game.getInstance().score;
        builder.setTitle("Game Over!\nYou scored: " + score + "points.\nEnter Your Name");

        // Set up the input
        final EditText input2 = new EditText(getActivity());
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input2);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pName = input2.getText().toString();
                HighScores.getInstance().addHighScore(pName, score);
                Game.getInstance().gamePlayed = false;
                Game.getInstance().ship = null;
                Game.getInstance().sprites.clear();
                Game.getInstance().lives = 3;
            }
        });
        builder.show();
    }
}
