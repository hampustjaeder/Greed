package se.umu.hatj0006.greed;

import java.util.UUID;

//Round Class
public class Round {

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
