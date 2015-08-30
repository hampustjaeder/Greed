package se.umu.hatj0006.greed;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/* ScoreLab handles the RoundArray and is bind to the ScoreList etc. */
public class ScoreLab {
        private ArrayList<Round> mRounds;

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
}
