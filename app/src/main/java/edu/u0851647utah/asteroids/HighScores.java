package edu.u0851647utah.asteroids;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Tanner on 11/29/2016.
 */
public class HighScores {
    static HighScores instance = null;
    double[] scores = new double[10];
    String[] names = new String[10];


    public static HighScores getInstance()
    {
        if(instance == null)
            instance = new HighScores();
        return instance;
    }

    private HighScores()
    {
        // Load on creation
        loadNames();
        loadScores();


        if(names == null || scores == null) {

            // Initialize array if no names loaded
            for (int i = 0; i < names.length; i++) {
                if (names[i] == null)
                    names[i] = "No Score";

            }
        }
    }

    public void addHighScore(String name, Double score)
    {
       int position = -1;

        // New Top Score
        if(score >= scores[0])
        {
            Double temp = scores[0];
            scores[0] = score;
            position = 0;

            // Shift all scores down one
            for(int i = 1; i < scores.length; i++)
            {
                Double temp2 = scores[i];
                scores[i] = temp;
                temp = temp2;
            }
        }

        // Loop through all scores to find position
        for(int i = 0; i < scores.length-1; i++)
        {
            if(score < scores[i] && score >= scores[i+1])
            {
                Double temp = scores[i];
                scores[i] = score;
                position = i+1;

                // Shift remaining scores down one
                for(int j = i; j < scores.length; j++)
                {
                    Double temp2 = scores[j];
                    scores[j] = temp;
                    temp = temp2;
                }
            }
        }

        if(position == -1)
            return;

        // Place name and resort list
        String tempString = names[position];
        names[position] = name;
        for(int i = position+1; i < names.length; i++)
        {
            String tempString2 = names[i];
            names[i] = tempString;
            tempString = tempString2;
        }

        // Save lists
        saveNames();
        saveScores();
    }

    public int getSize()
    {
        return scores.length;
    }

    public boolean isEmpty()
    {
        return scores.length == 0;
    }

    public  Double getScore(int index)
    {
        return scores[index];
    }

    public String getName(int index)
    {
        return names[index];
    }

    public void saveScores()
    {
        Gson gson = new Gson();

        String toSave = gson.toJson(scores);
        String filename = "scores.txt";
        FileOutputStream outputStream;

        try {
            outputStream =  Game.getInstance().context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(toSave.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadScores()
    {
        // Open and load drawings file
        try {
            FileInputStream fis = Game.getInstance().context.openFileInput("scores.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                System.out.println("Could not read file");
            }

            String json = sb.toString();
            System.out.println(json);
            Gson gson = new Gson();
            double[] loadedScores = gson.fromJson(json, new TypeToken<double[]>(){}.getType());
            scores = loadedScores;

        }catch (FileNotFoundException e)
        {
            System.out.println("File doesn't exist");
        }
    }

    public void saveNames()
    {
        Gson gson = new Gson();

        String toSave = gson.toJson(names);
        String filename = "names.txt";
        FileOutputStream outputStream;

        try {
            outputStream =  Game.getInstance().context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(toSave.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadNames()
    {
        // Open and load drawings file
        try {
            FileInputStream fis = Game.getInstance().context.openFileInput("names.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                System.out.println("Could not read file");
            }

            String json = sb.toString();
            System.out.println(json);
            Gson gson = new Gson();
            String[] loadedNames = gson.fromJson(json, new TypeToken<String[]>(){}.getType());
            names = loadedNames;


        }catch (FileNotFoundException e)
        {
            System.out.println("File doesn't exist");
        }
    }
}
