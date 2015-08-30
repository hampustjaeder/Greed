package se.umu.hatj0006.greed.Storage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

import se.umu.hatj0006.greed.Objects.DieState;
import se.umu.hatj0006.greed.Objects.Die;
import se.umu.hatj0006.greed.Objects.Round;

/*
    Game Class, handles the game and seperates the modul from the view. More as a correct MVC architecture.
 */
public class Game implements Parcelable {

    private ArrayList<Die> mDice;
    private ArrayList<Round> mRounds;

    private int mTotalScore;
    private int mRoundScore;
    private int mThrowScore;
    private int mMaxScore = 10000;
    private int mReqFirstScore = 250;

    public Game(ArrayList<Die> Dice, ArrayList<Round> Rounds){
        mDice = Dice;
        mRounds = Rounds;
    }

    /* Return RoundArray */
    public ArrayList<Round> getRounds() {
        return mRounds;
    }
    /* Return DiceArray */
    public ArrayList<Die> getDice() {
        return mDice;
    }
    /* Return TotalScore */
    public int getTotalScore() {
        return mTotalScore;
    }
    /* Return RoundScore */
    public int getRoundScore() {
        return mRoundScore;
    }
    /* Return ThrowScore */
    public int getThrowScore() {
        return mThrowScore;
    }
    /* Sets Throwscore from a variable*/
    public void setThrowScore(int score) {
        mThrowScore = score;
    }
    /* Return MaxScore */
    public int getMaxScore() {
        return mMaxScore;
    }
    /* Return Req first score */
    public int getReqFirstScore() {
        return mReqFirstScore;
    }
    /*  Clear ThrowScore and RoundScore */
    public void clearScore() {
        mThrowScore = 0;
        mRoundScore = 0;
    }
    /* Update TotalScore by addition of round+throwscore. */
    public void updateTotalScore(){
        mTotalScore += mRoundScore + mThrowScore;
    }

    /*
        GAME FUNCTIONS!
     */

    /* Throw dices */
    public void throwDices() {
        boolean mThrowable = false;
        ArrayList<Integer> mDiceValues = new ArrayList<>();

        //only throw dice if throwable!
        for(int i=0; i<6; i++) {
            if(mDice.get(i).getState().equals(DieState.Throwable)) {
                mThrowable = true;
                mDice.get(i).throwDice();
                mDiceValues.add(mDice.get(i).getValue());
            } else if(mDice.get(i).getState().equals(DieState.NotThrowable))
                //if dice is notThrowable, it was locked previous round -> update State!
                mDice.get(i).setState(DieState.NotPlayable);
        }

        //All dices notThrowable but round not over! (We need to rethrow all dices but same round)
        if(!mThrowable) {
            for(int i = 0; i<6; i++) {
                mDice.get(i).setState(DieState.Throwable);
                mDice.get(i).throwDice();
                mDiceValues.add(mDice.get(i).getValue());
            }
        }

        mRoundScore += mThrowScore;

        //Ends the round if the maximum score for a round is under 300 for the first and under 1 for the rest.
        calculateScore(mDiceValues);
    }
    /* Function that check for a ladder.  */
    public boolean checkForLadder(ArrayList<Integer> mDiceValues) {
        //Sort the dice for easier check.
        Collections.sort(mDiceValues);

        //Check if all dice are used, and if they after sort have 1 unique value.
        return (mDiceValues.size() == 6 &&
                (mDiceValues.get(0) == 1 && mDiceValues.get(1) == 2 && mDiceValues.get(2) == 3 &&
                        mDiceValues.get(3) == 4 && mDiceValues.get(4) == 5 && mDiceValues.get(5) == 6));
    }

    /* Calculate the score after each throw */
    public void calculateScore(ArrayList<Integer> mDiceValues) {
        mThrowScore = 0;

        //Check for ladder
        if(checkForLadder(mDiceValues)) {
            mThrowScore = 1000;
        }
        else {
            //Check array for elements that gives points.
            int count;
            for (int k = 1; k < 7; k++) {
                count = countElements(k, mDiceValues);
                if (count > 2) {
                    if (k != 1 && k != 5) {
                        if(count == 6)
                            mThrowScore += k * 200;
                        else
                            mThrowScore += k * 100;
                    }

                    else if (k == 1) {
                        if (count == 6) //Check for exact 6 ones.
                            mThrowScore += 2000;
                        else if(count == 3) //Check for exact 3 ones.
                            mThrowScore += 1000;
                        else //If more than 3 ones.
                            mThrowScore += (count - 3) * 100 + 1000;

                    } else {
                        if (count == 6) //Check for exact 6 fives.
                            mThrowScore += 1000;
                        else if (count == 3) //Check for exact 3 fives.
                            mThrowScore += 500;
                        else //If more than 3 fives.
                            mThrowScore += (count - 3) * 50 + 500;
                    }

                } else if (k == 1) { //Check if any less than 3 ones.
                    mThrowScore += count * 100;
                } else if (k == 5) {  //Check if any less than 3 fives.
                    mThrowScore += count * 50;
                }
            }
        }
    }

    private void checkFor(ArrayList<Integer> mDiceValues) {

    }

    /* After a dice i clicked, recalculate score. */
    public void recalculate(){
        ArrayList<Integer> mDiceValues = new ArrayList<>();

        //We only want to calculate the dices who is "NotThrowable"!
        for(int i=0; i<6; i++) {
            if(mDice.get(i).getState().equals(DieState.NotThrowable))
                mDiceValues.add(mDice.get(i).getValue());
        }

        calculateScore(mDiceValues);
    }

    /* Counts the number of a selected element in a arraylist */
    public int countElements(int element, ArrayList<Integer> mDicesValues) {
        int count = 0;
        for(int i=0; i<mDicesValues.size(); i++) {
            if(mDicesValues.get(i)==element) {
                count += 1;
            }
        }
        return count;
    }

    /*
            PARCELABLE
     */

    /* Parcelable init */
    private Game(Parcel in) {
        //Read in Arrays of Dice and Rounds.
        mDice = in.readArrayList(ClassLoader.getSystemClassLoader());
        mRounds = in.readArrayList(ClassLoader.getSystemClassLoader());

        //Read in Ints that needs to be saved.
        mThrowScore = in.readInt();
        mRoundScore = in.readInt();
        mTotalScore = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /* Write to Parcelable by override this function.*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Write over Score integers.
        dest.writeInt(mThrowScore);
        dest.writeInt(mRoundScore);
        dest.writeInt(mTotalScore);

        //Write of list with Dice and Rounds
        dest.writeList(mDice);
        dest.writeList(mRounds);
    }

    /* Creates the Parcelable */
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
