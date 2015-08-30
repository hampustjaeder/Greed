package se.umu.hatj0006.greed;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ScoreListFragment extends ListFragment {
    private ArrayList<Round> mRounds;

    private static final String TAG = "ScoreListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRounds = ScoreLab.get(getActivity()).getRounds();  //Get round array

        ScoreAdapter adapter = new ScoreAdapter(mRounds);
        setListAdapter(adapter);    //Bind Adapter(rounds) to list
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Round r = ((ScoreAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, r.getTitle() + " was clicked");
    }

    private class ScoreAdapter extends ArrayAdapter<Round> {
        public ScoreAdapter(ArrayList<Round> rounds) {
            super(getActivity(), 0, rounds);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_score, null);
            }
            // Configure the view for this round
            Round r = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.round_list_item_titleTextView);
            titleTextView.setText(r.getTitle());

            TextView scoreTextView = (TextView)convertView.findViewById(R.id.round_list_item_scoreTextView);
            scoreTextView.setText("Round Score: " + r.getScore());

            return convertView;

        }
    }
}
