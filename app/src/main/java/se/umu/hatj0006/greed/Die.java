package se.umu.hatj0006.greed;

import java.util.Random;


/* The Die class */
public class Die {
    private int mValue;
    private DieState mState;

    public Die() {
        mValue = 1;
        mState = DieState.Throwable;
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
