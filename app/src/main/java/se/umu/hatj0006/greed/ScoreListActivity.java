package se.umu.hatj0006.greed;

import android.support.v4.app.Fragment;

/* This class extends the SingleFragmentActivity. */
public class ScoreListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        //Creates the ScoreListFragment!
        return new ScoreListFragment();
    }
}
