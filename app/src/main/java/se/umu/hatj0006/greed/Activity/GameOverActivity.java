package se.umu.hatj0006.greed.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.umu.hatj0006.greed.R;

/* Activity that starts when the game ends. */
public class GameOverActivity extends ActionBarActivity {
    private Button mTryAgainButton;
    //String with number of rounds that was req. for finishing the game.
    public static final String NUMBER_OF_ROUNDS =
            "se.umu.hatj0006.greed.number_of_rounds";
    //Total Score after finishing the game.
    public static final String TOTAL_SCORE =
            "se.umu.hatj0006.greed.total_score";

    private int mNumberOfRounds;
    private int mTotalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Sets the Score and NrRounds variabels from intent extras that was given.
        mTotalScore = getIntent().getIntExtra(TOTAL_SCORE, 0);
        mNumberOfRounds = getIntent().getIntExtra(NUMBER_OF_ROUNDS, 0);

        //Sets game over Text.
        TextView gameOverText = (TextView)findViewById(R.id.game_over);
        gameOverText.setText("Well played! \n" + "Score: " + mTotalScore + "\n" + "Rounds: " + mNumberOfRounds);

        //Try again Button starts a new Game. (GameActivity)
        mTryAgainButton = (Button)findViewById(R.id.tryAgainButton);
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this, GameActivity.class);
                startActivityForResult(i, 0);;
            }
        });
    }

}
