package se.umu.hatj0006.greed.Storage;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import se.umu.hatj0006.greed.Objects.Round;

/* ScoreLab handles the RoundArray and is bind to the ScoreList etc. */
public class ScoreLab {
    private static final String TAG = "ScoreLab";
    private ArrayList<Round> mRounds;

    private GreedJSONSerializer mSerializer;

    private static ScoreLab sScoreLab;
    private Context mAppContext;

    private ScoreLab(Context appContext) {
        mAppContext = appContext;
        //Create the arraylist
        mRounds = new ArrayList<Round>();
    }

    /* Return static DiceLab using context */
    public static ScoreLab get(Context c) {
        if (sScoreLab == null) {
            sScoreLab = new ScoreLab(c.getApplicationContext());
        }
        return sScoreLab;
    }

    /* Return the Round array  */
    public ArrayList<Round> getRounds() {
            return mRounds;
        }

    /* Overwrite the round array to a new round array.*/
    public void setRounds(ArrayList<Round> rounds) {
        mRounds = rounds;
    }

    /*
        Save rounds
     */
    public boolean saveRounds() {
        try {
            mSerializer.saveRounds(mRounds);
            Log.d(TAG, "Rounds saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving Rounds: ", e);
            return false;
        }
    }
}
