package se.umu.hatj0006.greed.Activity;

import android.support.v4.app.Fragment;

import se.umu.hatj0006.greed.Fragment.GreedFragment;
import se.umu.hatj0006.greed.SingleFragmentActivity;

/**
 * Created by mtr on 2015-08-30.
 */
public class GreedActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new GreedFragment();
    }
}
