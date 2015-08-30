package se.umu.hatj0006.greed;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/* Class that handles a singlefragment. In other words a activity that binds a fragment to the fragment cointainer
 * so a specific fragment will be viewed. */
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_score);

        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        //Bind fragmet to container!
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        //If fragment is NULL, we need to create a new fragment.
        if (fragment == null) {
            fragment = createFragment();    //See ScoreListActivity.
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();      //Add a single fragment into the container view (list)
        }
    }
}
