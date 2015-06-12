package com.example.kevin.finder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Tommy on 6/11/2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int newYear, int month, int newDay) {
        // Do something with the date chosen by the user
        CreateProfileActivity.year = newYear;
        CreateProfileActivity.day = newDay;
        if(month > 1){
            CreateProfileActivity.day+=31;
        }
        if(month > 2){
            CreateProfileActivity.day+=28;
        }
        if(month > 3){
            CreateProfileActivity.day+=31;
        }
        if(month > 4){
            CreateProfileActivity.day+=30;
        }
        if(month > 5){
            CreateProfileActivity.day+=31;
        }
        if(month > 6){
            CreateProfileActivity.day+=30;
        }
        if(month > 7){
            CreateProfileActivity.day+=31;
        }
        if(month > 8){
            CreateProfileActivity.day+=31;
        }
        if(month > 9){
            CreateProfileActivity.day+=30;
        }
        if(month > 10){
            CreateProfileActivity.day+=31;
        }
        if(month > 11){
            CreateProfileActivity.day+=30;
        }
        if(month > 2 && ((newYear%100!=0 && newYear%4==0)||newYear%400==0)){
            CreateProfileActivity.day++;
        }
    }
}
