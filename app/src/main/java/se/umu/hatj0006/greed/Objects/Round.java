package se.umu.hatj0006.greed.Objects;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

//Round Class
public class Round {
    //JSON variables
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SCORE = "score";
    private static final String JSON_ROUND_ENDED = "roundEnded";
    private static final String JSON_ROUND_STARTED = "roundStarted";
    private static final String JSON_THROWS = "throws";

    private UUID mId;
    private String mTitle;
    private int mScore;
    private boolean mRoundEnded;
    private boolean mRoundStarted;
    private int mThrows;

    public Round() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        //Initialize values for a round.
        mScore = 0;
        mThrows = 1;
        mRoundEnded = false;
        mRoundStarted = false;
    }

    /*
       Recreate from JSONObject
    */
    public Round(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mScore = json.getInt(JSON_SCORE);
        mRoundStarted = json.getBoolean(JSON_ROUND_STARTED);
        mRoundEnded = json.getBoolean(JSON_ROUND_ENDED);
        mThrows = json.getInt(JSON_THROWS);
    }

    /*
       Put Event to JSONobject
    */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId);
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SCORE, mScore);
        json.put(JSON_ROUND_STARTED, mRoundStarted);
        json.put(JSON_ROUND_ENDED, mRoundEnded);
        json.put(JSON_THROWS, mThrows);

        return json;
    }


    /* Return boolean if round is over. */
    public boolean isRoundEnded() {
        return mRoundEnded;
    }
    /* Sets the boolean if the round is over. (ended) */
    public void setRoundEnded(boolean roundEnded) {
        mRoundEnded = roundEnded;
    }

    /* Return boolean that is true if the round i started. */
    public boolean isRoundStarted() {
        return mRoundStarted;
    }
    /* Sets the boolean if round is started */
    public void setRoundStarted(boolean roundStarted) {
        mRoundStarted = roundStarted;
    }
    /* Return mID */
    public UUID getId() {
        return mId;
    }
    /* Return Score for this round. */
    public int getScore() {
        return mScore;
    }
    /* Sets Score for this round object */
    public void setScore(int score) {
        mScore = score;
    }
    /* Return Round title */
    public String getTitle() {
        return mTitle;
    }
    /* Sets round title */
    public void setTitle(String title) {
        mTitle = title;
    }
    /* Return number of Throws for this round object */
    public int getThrows() {
        return mThrows;
    }
    /* Increment throws by 1 for a round. */
    public void incrementThrows() {
        mThrows += 1;
    }

}
