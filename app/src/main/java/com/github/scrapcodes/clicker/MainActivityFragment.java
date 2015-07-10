package com.github.scrapcodes.clicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String PREFS_NAME = "CountAppFile";
    public Integer count = 0;
    public View rootView;
    public Integer simple = 0;
    public Integer multiple = 1;

    public MainActivityFragment() {

    }

    private void updateTextViews() {
        Integer mCount = count / multiple; // multiple count
        Integer rCount = count % multiple; // remainder count
        if (simple == 1){
            final View textViewCount = rootView.findViewById(R.id.textView_count);
            ((TextView) textViewCount).setText(count.toString());
        } else {
            final View textViewCount = rootView.findViewById(R.id.textView_count);
            ((TextView) textViewCount).setText(rCount.toString());
            final View textViewMCount = rootView.findViewById(R.id.textView_mcount);
            ((TextView) textViewMCount).setText(mCount.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreSavedState();
        updateTextViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        restoreSavedState();
        updateTextViews();
    }

    private void restoreSavedState() {
        SharedPreferences savedCount = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.count = savedCount.getInt("count", 0);
        this.simple = Integer.parseInt(sharedPref.getString("mode_list", "1").trim());
        this.multiple = Integer.parseInt(sharedPref.getString("multiple_number", "1").trim());
        if (simple == 1) {
            final View textViewMCount = rootView.findViewById(R.id.textView_mcount);
            textViewMCount.setVisibility(View.INVISIBLE);
        } else {
            final View textViewMCount = rootView.findViewById(R.id.textView_mcount);
            textViewMCount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            count = 0;
            updateTextViews();
            return true;
        } else if (id == R.id.action_decrement) {
            if (count > 0) count--;
            updateTextViews();
            return true;
        } else if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final View buttonI = rootView.findViewById(R.id.button_i);
        buttonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                updateTextViews();
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("count", count);
        // Commit the edits!
        editor.commit();
    }
}
