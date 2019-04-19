package com.example.jtodolister;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String yearGivenKey = "Year_Key";
    private String monthGivenKey = "Month_Key";
    private String dayGivenKey = "Day_Key";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //set current date as default pick date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //TODO: actually transfer date picked to screen
//        Bundle b = new Bundle();
//        b.putInt(yearGivenKey,year);
//        b.putInt(monthGivenKey,month);
//        b.putInt(dayGivenKey,dayOfMonth);
//        DatePickerFragment.this.setArguments(b);

        String textToSet = dayOfMonth + "." + month;
        TextView tv = (TextView) getActivity().findViewById(R.id.datePickedTextView);
        tv.setText(textToSet);
    }
}

