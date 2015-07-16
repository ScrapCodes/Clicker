package com.github.scrapcodes.clicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static MainActivityFragment INSTANCE;
    public static final String PREFS_NAME = "CountAppFile";
    public Integer count = 0;
    public View rootView;
    public Integer simple = 0;
    public Integer multiple = 1;
    private boolean fsButton;
    private boolean vibrate;

    public MainActivityFragment() {

    }

    public static MainActivityFragment getInstance() {
        return INSTANCE;
    }

    void updateTextViews() {
        Integer mCount = count / multiple; // multiple count
        Integer rCount = count % multiple; // remainder count
        if (rCount == 0 && mCount > 0 && vibrate) {
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(300); // 300 milli seconds
        }
        if (simple == 1) {
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
        INSTANCE = this;
        super.onCreate(savedInstanceState);
        SharedPreferences savedCount = getActivity().getSharedPreferences(PREFS_NAME, 0);
        count = savedCount.getInt("count", 0);
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        try {
            this.simple = Integer.parseInt(sharedPref.getString("mode_list", "1").trim());
            this.multiple = Integer.parseInt(sharedPref.getString("multiple_number", "1").trim());
            this.fsButton = sharedPref.getBoolean("fs_button_checkbox", false);
            this.vibrate = sharedPref.getBoolean("vibrate_mode", false);
        } catch (Throwable t) {
            this.simple = 1;
            this.multiple = 1;
            this.fsButton = false;
        }
        if (simple == 1) {
            final View textViewMCount = rootView.findViewById(R.id.textView_mcount);
            textViewMCount.setVisibility(View.INVISIBLE);
        } else {
            final View textViewMCount = rootView.findViewById(R.id.textView_mcount);
            textViewMCount.setVisibility(View.VISIBLE);
        }
        if (fsButton) {
            final RelativeLayout parentView =
                    (RelativeLayout) rootView.findViewById(R.id.parent_layout_panel);
            parentView.setClickable(true);
            final View buttonI = rootView.findViewById(R.id.button_i);
            buttonI.setClickable(false);
            buttonI.setVisibility(View.GONE);
        } else {
            final RelativeLayout parentView =
                    (RelativeLayout) rootView.findViewById(R.id.parent_layout_panel);
            parentView.setClickable(false);
            final View buttonI = rootView.findViewById(R.id.button_i);
            buttonI.setClickable(true);
            buttonI.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            final Intent resetIntent = new Intent(getActivity(), ResetActivity.class);
            startActivity(resetIntent);
            return true;
        } else if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
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
        final View parentView = rootView.findViewById(R.id.parent_layout_panel);
        parentView.setOnClickListener(new View.OnClickListener() {
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
