package se.umu.hatj0006.greed.Storage;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import se.umu.hatj0006.greed.Objects.Die;

/* DiceLab is used for handling the DiceArray. */
public class DiceLab {
    private static final String TAG = "DiceLab";
    private ArrayList<Die> mDice;

    private GreedJSONSerializer mSerializer;

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

    /*
        Save dice
     */
    public boolean saveDice() {
        try {
            mSerializer.saveDice(mDice);
            Log.d(TAG, "Dice saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving Dice: ", e);
            return false;
        }
    }
}
