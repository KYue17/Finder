package com.example.kevin.finder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
/**
 * Created by Kevin on 8/27/2015.
 */
public class OtherProfileActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        //retrieve person's profile from database using username
        ArrayAdapter<String> adapter;

        final Person p = getIntent().getExtras().getParcelable("otherProfile");

        TextView opTextView1 = (TextView)findViewById(R.id.opTextView);
        TextView opTextView2 = (TextView)findViewById(R.id.opTextView2);
        TextView opTextView3 = (TextView)findViewById(R.id.opTextView3);
        TextView opTextView4 = (TextView)findViewById(R.id.opTextView4);
        TextView opTextView5 = (TextView)findViewById(R.id.opTextView5);

        opTextView1.setText("Name : " + p.getName());
        opTextView2.setText("Age : " + Integer.toString(p.getAge()));
        opTextView3.setText("Phone: " + p.getPhoneNumber());
        opTextView4.setText("Email: " + p.getEmailAddress());
        opTextView5.setText("Zipcode: " + p.getZipcode());

        String[] separated = p.getInterests().split("\n");
        ListView listView = (ListView) findViewById(R.id.opListView);
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.product_name, separated);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
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
