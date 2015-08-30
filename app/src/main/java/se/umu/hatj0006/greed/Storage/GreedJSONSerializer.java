package se.umu.hatj0006.greed.Storage;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import se.umu.hatj0006.greed.Objects.Die;
import se.umu.hatj0006.greed.Objects.Round;


/*
    This class handles the write/read of JsonObjects to/from the disc.
 */
public class GreedJSONSerializer {

    private Context mContext;
    private String mFilename;
    //Constructor
    public GreedJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }
    /*
        Save rounds on discs (write all rounds to disc)
     */
    public void saveRounds(ArrayList<Round> rounds)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Round r : rounds)
            array.put(r.toJSON());

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /*
        Load rounds using bufferedreader and read from file.
     */
    public ArrayList<Round> loadRounds() throws IOException, JSONException {
        ArrayList<Round> rounds = new ArrayList<Round>();
        BufferedReader reader = null;

        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            // Build the array of rounds from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                rounds.add(new Round(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return rounds;
    }



    /*
       Save dice on discs (write all dice to disc)
    */
    public void saveDice(ArrayList<Die> dice)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Die d : dice)
            array.put(d.toJSON());

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /*
        Load Dice (read from file with bufferedreader)
     */
    public ArrayList<Die> loadDice() throws IOException, JSONException {
        ArrayList<Die> dice = new ArrayList<Die>();
        BufferedReader reader = null;

        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            // Build the array of dice from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                dice.add(new Die(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return dice;
    }
}
