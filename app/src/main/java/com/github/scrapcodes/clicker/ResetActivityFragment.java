package com.github.scrapcodes.clicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A placeholder fragment containing a simple view.
 */
public class ResetActivityFragment extends Fragment {
    private View rootView;

    public ResetActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_reset, container, false);
        final EditText editText = (EditText) rootView.findViewById(R.id.editText_reset_count);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    updateCount(editText);
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    private void updateCount(EditText editText) {
        final String string = editText.getText().toString().trim();
        if (!string.isEmpty() && string.length() < 9) {
            Integer count =
                    Integer.parseInt(string);
            if (count < 0) {
                MainActivityFragment.getInstance().count = 0;
            } else {
                MainActivityFragment.getInstance().count = count;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final EditText editText = (EditText) rootView.findViewById(R.id.editText_reset_count);
        editText.setText(MainActivityFragment.getInstance().count.toString()); // To string is important here.
    }

    @Override
    public void onResume() {
        super.onResume();
        final EditText editText = (EditText) rootView.findViewById(R.id.editText_reset_count);
        editText.setText(MainActivityFragment.getInstance().count.toString()); // To string is important here.
    }

    @Override
    public void onStop() {
        super.onStop();
        final EditText editText = (EditText) rootView.findViewById(R.id.editText_reset_count);
        updateCount(editText);
        MainActivityFragment.getInstance().updateTextViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        final EditText editText = (EditText) rootView.findViewById(R.id.editText_reset_count);
        updateCount(editText);
        MainActivityFragment.getInstance().updateTextViews();
    }
}
