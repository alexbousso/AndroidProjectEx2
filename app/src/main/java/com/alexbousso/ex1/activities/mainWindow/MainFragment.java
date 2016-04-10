package com.alexbousso.ex1.activities.mainWindow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alexbousso.ex1.FoodItemContent;
import com.alexbousso.ex1.R;

public class MainFragment extends Fragment {
    private static final int seekBarMaxValue = 100;

    private View view;
    private EditText editText;
    private Button makeOrderButton;
    private Button selectFoodButton;
    private SeekBar seekBar;
    private CheckBox checkBox;
    private MenuItem sendOrderItem;

    private static FoodItemContent currentItem = null;
    private boolean isSendOrderEnabled = false;
    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    public void updateFoodSelection(FoodItemContent item) {
        currentItem = item;
        selectFoodButton.setText(item.getFoodName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents();
        setHasOptionsMenu(true);

        if (currentItem != null) {
            updateFoodSelection(currentItem);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu, menu);
        sendOrderItem = menu.findItem(R.id.action_sendOrder);
        sendOrderItem.setEnabled(isSendOrderEnabled);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        sendOrderItem.setEnabled(isSendOrderEnabled);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sendOrder:
                mListener.onMakeOrderButtonClicked();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initializeComponents() {
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        editText = (EditText) view.findViewById(R.id.editText);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        makeOrderButton = (Button) view.findViewById(R.id.makeOrderButton);
        selectFoodButton = (Button) view.findViewById(R.id.selectFoodButton);

        initializeEditText();
        initializeSeekBar();
        initializeCheckBox();
        initializeMakeOrderButton();
        initializeSelectFoodButton();
    }

    private void initializeSelectFoodButton() {
        selectFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFoodSelectButtonClicked();
            }
        });
    }

    private void initializeMakeOrderButton() {
        makeOrderButton.setEnabled(false);

        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMakeOrderButtonClicked();
            }
        });
    }

    private void initializeEditText() {
        editText.addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ignoreChange) {
                    String str = s.toString();
                    str = str.replace(".", "");

                    Integer i = 0;
                    try {
                        if (str.length() > 0) {
                            i = Integer.parseInt(str);
                        }
                    } catch (NumberFormatException ignored) {
                    }

                    ignoreChange = true;
                    editText.setText(str);
                    editText.setSelection(editText.getText().length());
                    setSeekBarProgress(i);
                    ignoreChange = false;

                    setMakeOrderButtonEnabledStatus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializeSeekBar() {
        seekBar.setMax(seekBarMaxValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    editText.setText(String.format("%d", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initializeCheckBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMakeOrderButtonEnabledStatus();
            }
        });
    }

    private void setSeekBarProgress(Integer progress) {
        if (progress >= 0 && progress <= seekBarMaxValue) {
            seekBar.setProgress(progress);
        } else if (progress > seekBarMaxValue) {
            seekBar.setProgress(seekBarMaxValue);
        } else if (progress < 0) {
            seekBar.setProgress(0);
        }
    }

    private void setMakeOrderButtonEnabledStatus() {
        String str = editText.getText().toString();
        try {
            if (str.length() <= 0 || Integer.parseInt(str) <= 0) {
                setSendOrderEnabled(false);
                return;
            }
        } catch (NumberFormatException e) {
            setSendOrderEnabled(false);
            return;
        }

        if (!checkBox.isChecked()) {
            setSendOrderEnabled(false);
            return;
        }

        setSendOrderEnabled(true);
    }

    private void setSendOrderEnabled(boolean isEnabled) {
        isSendOrderEnabled = isEnabled;
        makeOrderButton.setEnabled(isEnabled);
    }

    public interface OnFragmentInteractionListener {
        void onFoodSelectButtonClicked();
        void onMakeOrderButtonClicked();
    }
}
