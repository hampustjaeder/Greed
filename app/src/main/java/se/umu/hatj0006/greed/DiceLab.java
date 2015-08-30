package se.umu.hatj0006.greed;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/* DiceLab is used for handling the DiceArray. */
public class DiceLab {

    private ArrayList<Die> mDice;

    private static DiceLab sDiceLab;
    private Context mAppContext;

    private DiceLab(Context appContext) {
        mAppContext = appContext;
        //Create the arraylist
        mDice = new ArrayList<Die>();

        //Creates 6 dice and add them to array
        for(int i=0; i<6; i++) {
            Die newDie = new Die();
            mDice.add(i,newDie);
        }
    }

    /* Return static DiceLab using context */
    public static DiceLab get(Context c) {
        if (sDiceLab == null) {
            sDiceLab = new DiceLab(c.getApplicationContext());
        }
        return sDiceLab;
    }

    /* Call this function for accessing the Dice. */
    public ArrayList<Die> getDices() {
        return mDice;
    }
}
