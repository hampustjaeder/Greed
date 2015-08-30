package se.umu.hatj0006.greed.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import se.umu.hatj0006.greed.Objects.Die;
import se.umu.hatj0006.greed.Objects.DieState;
import se.umu.hatj0006.greed.Storage.Game;
import se.umu.hatj0006.greed.R;
import se.umu.hatj0006.greed.Objects.Round;
import se.umu.hatj0006.greed.Storage.DiceLab;
import se.umu.hatj0006.greed.Storage.ScoreLab;

public class GameActivity extends ActionBarActivity {
    private Button mSaveButton;
    private Button mScoreButton;
    private Button mThrowButton;

    private Game mGame;

    private static final String TAG = "GameActivity";
    private static final String KEY_INDEX = "index";

    /*  instanceSave function saves the Game Object by using Parcelable   */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Sets mGame object to the savedInstanceState!
        savedInstanceState.putParcelable("GAME_obj", mGame);
    }

    /* Updates all the UI, this function is called after rotate etc. */
    private void updateGame() {
        updateDice();

        TextView currentRound = (TextView) findViewById(R.id.CurrentRoundTextView);
        TextView currentThrow = (TextView) findViewById(R.id.CurrentThrowTextView);
        TextView totalScore = (TextView) findViewById(R.id.TotalScoreTextView);
        TextView roundScore = (TextView) findViewById(R.id.RoundScoreTextView);
        TextView throwScore = (TextView) findViewById(R.id.ThrowScoreTextView);

        if(!mGame.getRounds().isEmpty()) {
            currentThrow.setText("Throw: " + mGame.getRounds().get(mGame.getRounds().size() - 1).getThrows());
            currentRound.setText("Round: " + mGame.getRounds().size());
        } else {
            currentThrow.setText("Throw: " + 1);
            currentRound.setText("Round: " + 1);
        }

        totalScore.setText("Total Score: " + mGame.getTotalScore());
        roundScore.setText("RoundScore: " + mGame.getRoundScore());
        throwScore.setText("ThrowScore: " + mGame.getThrowScore());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        if (savedInstanceState == null || !savedInstanceState.containsKey("GAME_obj")) {
            ArrayList<Die> mDice = DiceLab.get(getApplicationContext()).getDices();   //Gets the Die array from DiceLab
            ArrayList<Round> mRounds = ScoreLab.get(getApplicationContext()).getRounds(); //Get the Rounds array from ScoreLab

            //Create a new Game class, initialize with round and dice array.
            mGame = new Game(mDice, mRounds);
            initDice();

        } else {
            //Get the saved Game object.
            mGame = savedInstanceState.getParcelable("GAME_obj");
            //Update game
            updateGame();
        }

        initDiceImageListeners();


        //Save Button
        mSaveButton = (Button)findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mGame.getRounds().isEmpty()) { //As long as the game has started trigger roundOver();
                    roundOver();
                }
            }
        });

        //Score Button
        mScoreButton = (Button)findViewById(R.id.score_button);
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start ScoreListActivity for displaying every score for each played round in a list.
                // Go back by using the "back" on the phone.
                ScoreLab.get(getApplicationContext()).setRounds(mGame.getRounds());
                Intent i = new Intent(GameActivity.this, ScoreListActivity.class);
                startActivityForResult(i, 0);
            }
        });

        //Throw Button
        mThrowButton = (Button)findViewById(R.id.throw_button);
        mThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Round newRound;
                TextView currentRound = (TextView) findViewById(R.id.CurrentRoundTextView);
                TextView currentThrow = (TextView) findViewById(R.id.CurrentThrowTextView);

                if (mGame.getRounds().isEmpty()) { //Is it the first round?
                    newRound = new Round();
                    mGame.getRounds().add(newRound); //Add to array
                    newRound.setTitle("Round 1");
                    currentRound.setText("Round: 1");

                } else if (mGame.getRounds().get(mGame.getRounds().size() - 1).isRoundEnded()) {   //Is current round ended? -> Start new round
                    newRound = new Round();
                    newRound.setTitle("Round " + (mGame.getRounds().size() + 1));
                    mGame.getRounds().add(newRound);  //Add to array
                    currentThrow.setText("Throw: 1");
                    currentRound.setText("Round: " + (mGame.getRounds().size()));
                }
                //We need to check so we have selected dices given required points before we can throw again.
                // We have checks that will end round if we cant have the required points but not if we use the given dices.. Therefore this.
                else if (mGame.getRounds().get(mGame.getRounds().size() - 1).isRoundStarted()) {
                    if (mGame.getThrowScore() < 1) {
                        mGame.clearScore();
                        roundOver();
                        return; //No throw! We want the user to see that round is over and red.
                    }
                    mGame.getRounds().get(mGame.getRounds().size() - 1).incrementThrows();    //else.. Increment the number of Throws by 1.
                    currentThrow.setText("Throw: " + mGame.getRounds().get(mGame.getRounds().size() - 1).getThrows());
                } else {
                    mGame.getRounds().get(mGame.getRounds().size() - 1).incrementThrows();    //else.. Increment the number of Throws by 1.
                    currentThrow.setText("Throw: " + mGame.getRounds().get(mGame.getRounds().size() - 1).getThrows());
                }

                mGame.throwDices();
                newThrowDice();//Throw function trigger
                //Save rounds to disc
                ScoreLab.get(getApplicationContext()).saveRounds();
            }
        });
    }

    /* Initialize all the imageButtons */
    private void initDiceImageListeners() {

        for(int index=0; index<6; index++) {
            String res = "dieImage" + (index+1);

            ImageButton mImageButton = (ImageButton)findViewById(getResourceId(res, "id", getPackageName()));

            final int i = index;
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDiceClick(i);
                }
            });
        }
    }

     /* Function that runs when a Die is clicked */
    private void onDiceClick(int index) {
        Die currentDie = mGame.getDice().get(index);

        //We are only going to check playable Dices (if they give points).
        // Therefore we need to build an valid list with those dices
        // and check if the clicked dice can give points in any way.

        ArrayList<Integer> mDiceValues = new ArrayList<>();

        for(int i=0; i<6; i++) {
            if(!mGame.getDice().get(i).getState().equals(DieState.NotPlayable))
                mDiceValues.add(mGame.getDice().get(i).getValue());
        }

        //The Die is only clickable if it can give points!
        if(!currentDie.getState().equals(DieState.NotPlayable) && (currentDie.getValue() == 1 ||
                currentDie.getValue() == 5 || mGame.checkForLadder(mDiceValues)) ||
                mGame.countElements(currentDie.getValue(), mDiceValues) > 2 ) {

            if(currentDie.getState().equals(DieState.Throwable)) {
                currentDie.setState(DieState.NotThrowable);
            } else if(currentDie.getState().equals(DieState.NotThrowable)) {
                currentDie.setState(DieState.Throwable);
            }

            mGame.recalculate();  //Recalculate score after each click
            updateDice();
        }
    }

    /*  Initialize Dice so that we have some images up on startup/new game*/
    private void initDice() {
        Random r = new Random();
        ImageView imgView;

        for(int i=0; i<6; i++) {
            int mValue = r.nextInt(6)+1;
            String loc = "red" + mValue;

            String res = "dieImage" + (i+1);

            //Update mGame dice
            mGame.getDice().get(i).setValue(mValue);
            mGame.getDice().get(i).setState(DieState.NotPlayable);

            imgView = (ImageView)findViewById( getResourceId(res, "id", getPackageName()));
            imgView.setImageResource(getResourceId(loc, "drawable", getPackageName()));
        }

    }
    /* Update all dice Images after their specifications. */
    private void updateDice() {
        ImageView imgView;
        String res;
        String loc;

        for(int i=0; i<6; i++) {
            loc = mGame.getDice().get(i).getColor()+ mGame.getDice().get(i).getValue();

            Log.i(TAG, "onSaveInstanceState: LOC =" + loc);

            res = "dieImage" + (i+1);

            imgView = (ImageView)findViewById( getResourceId(res, "id", getPackageName()));
            imgView.setImageResource(getResourceId(loc, "drawable", getPackageName()));
        }

        //Update TextView for ThrowScore
        TextView throwScore = (TextView)findViewById(R.id.ThrowScoreTextView);
        throwScore.setText("Throwscore: " + mGame.getThrowScore());
        //Update TextView for RoundScore
        TextView roundScore = (TextView)findViewById(R.id.RoundScoreTextView);
        roundScore.setText("Roundscore: " + mGame.getRoundScore());

        //Save dice to disc
        DiceLab.get(this).saveDice();
    }

    /* Throw dices */
    private void newThrowDice() {
        if(!mGame.getRounds().get(mGame.getRounds().size() - 1).isRoundStarted()) {
            /* Need to have mReqFirstScore points first throw! */
            mGame.getRounds().get(mGame.getRounds().size() - 1).setRoundStarted(true);
            if(mGame.getThrowScore() < mGame.getReqFirstScore()) {
                mGame.clearScore();
                roundOver();
            }
        } else {
            //Need to have over 0 points
            if(mGame.getThrowScore() < 1) {
                mGame.clearScore();
                roundOver();
            }
        }

        mGame.setThrowScore(0);

        updateDice();
    }

    /* Ends the round and sets the roundScore to the Round object... (Updates textfields) */
    private void roundOver(){
        mGame.getRounds().get(mGame.getRounds().size()-1).setRoundEnded(true);

        for(int i=0; i<6; i++) {    //Make all dices notplayable because round is about to end.
            mGame.getDice().get(i).setState(DieState.NotPlayable);
        }
        updateDice();  //Update dices so they become red.

        //Set scores for the round.
        mGame.getRounds().get(mGame.getRounds().size()-1).setScore(mGame.getRoundScore() + mGame.getThrowScore());
        mGame.updateTotalScore();
        mGame.clearScore();

        if(mGame.getTotalScore() > mGame.getMaxScore()) {   //CHECK IF WE HAVE REACHED MAXSCORE!
            // START GAME OVER ACTIVITY
            Intent i = new Intent(GameActivity.this, GameOverActivity.class);
            i.putExtra(GameOverActivity.NUMBER_OF_ROUNDS, mGame.getRounds().size());
            i.putExtra(GameOverActivity.TOTAL_SCORE, mGame.getTotalScore());
            startActivityForResult(i, 0);;
            ScoreLab.get(getApplicationContext()).setRounds(new ArrayList<Round>());
        }

        //Update totalScore text.
        TextView totalScore = (TextView)findViewById(R.id.TotalScoreTextView);
        totalScore.setText("TotalScore: " + mGame.getTotalScore());
    }

    /* Get a resourceID from strings */
    private int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
