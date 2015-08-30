package se.umu.hatj0006.greed.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


/* The Die class */
public class Die {
    //JSON variables
    private static final String JSON_VALUE = "value";
    private static final String JSON_STATE = "state";


    private int mValue;
    private DieState mState;

    public Die() {
        mValue = 1;
        mState = DieState.Throwable;
    }

    /*
       Recreate from JSONObject
    */
    public Die(JSONObject json) throws JSONException {
        mValue = json.getInt(JSON_VALUE);
        mState = DieState.valueOf(json.getString(JSON_STATE));
    }

    /*
       Put Event to JSONobject
    */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(JSON_VALUE, mValue);
        json.put(JSON_STATE, mState);

        return json;
    }

    /* Get current die value. */
    public int getValue() {
        return mValue;
    }

    /* For hardsetting a value to the die. */
    public void setValue(int value) {
        mValue = value;
    }

    /* Throws the die and update the value. */
    public void throwDice() {
        Random r = new Random();
        mValue = r.nextInt(6)+1;
    }

    /* Return current state */
    public DieState getState() {
        return mState;
    }

    /* Update the current state */
    public void setState(DieState state) {
        mState = state;
    }

    /* From the current state return correct Color of the dice.
       String is return instead of Enum color because we want to
       build up a pointer to an object with a string!
    */
    public String getColor() {
        if(mState.equals(DieState.Throwable))
            return "white";
        else if (mState.equals(DieState.NotThrowable))
            return "grey";
        else
            return "red";
    }
}
