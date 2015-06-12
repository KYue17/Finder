package com.example.kevin.finder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

/**
 * Created by Kevin on 6/10/2015.
 */
public class ProfileActivity extends ActionBarActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //retrieve person's profile from database using username
        Person p = new Person(1, "name", 17);
        p.addInterest("interest1");
        p.addInterest("interest2");
        p.addInterest("interest3");
        TextView textView1 = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView1.setText("Name : " + p.getName());
        textView2.setText("Age : " + Integer.toString(p.getAge()));
        textView3.setText("Interests\n" + p.getInterests());

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}