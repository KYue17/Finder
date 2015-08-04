package com.example.kevin.finder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Kevin on 6/13/2015.
 */
public class DisplayProfilesActivity extends ActionBarActivity{

    ScrollView scrollView;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profiles);
        scrollView = (ScrollView)findViewById(R.id.scroll_view);
        tableLayout = new TableLayout(this);
        for(int i = 1; i <= 50; i++){
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText("Id: " + i);
            tableRow.addView(textView);
            tableLayout.addView(tableRow);
        }
        scrollView.addView(tableLayout);
    }
}
