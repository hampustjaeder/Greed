package se.umu.hatj0006.greed.Activity;

import android.support.v4.app.Fragment;

import se.umu.hatj0006.greed.Fragment.ScoreListFragment;
import se.umu.hatj0006.greed.SingleFragmentActivity;

/* This class extends the SingleFragmentActivity. */
public class ScoreListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        //Creates the ScoreListFragment!
        return new ScoreListFragment();
    }
}
