package com.example.kevin.finder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Kevin on 6/10/2015.
 */
public class ProfileActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_profile);
        //retrieve person's profile from database using username
        ArrayAdapter<String> adapter;
        final Person p = getIntent().getExtras().getParcelable("myProfile");
        final ArrayList<Person> personArrayList = getIntent().getParcelableArrayListExtra("personArrayList");

        TextView textView1 = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);

        textView1.setText("Name: " + p.getName());
        textView2.setText("Age: " + Integer.toString(p.getAge()));
        textView3.setText("Phone: " + p.getPhoneNumber());
        textView4.setText("Email: " + p.getEmailAddress());

        String[] separated = p.getInterests().split("\n");
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.product_name, separated);
        listView.setAdapter(adapter);
        Arrays.sort(separated);

        Button viewProfiles = (Button)findViewById(R.id.viewProfiles);
        Button interestSearch = (Button)findViewById(R.id.interestSearch);

        viewProfiles.setOnClickListener(new View.OnClickListener(){
            public void onClick(View onClickView){
                Intent viewProfilesIntent = new Intent(ProfileActivity.this, DisplayProfilesActivity.class);
                viewProfilesIntent.putExtra("myProfile", p);
                viewProfilesIntent.putParcelableArrayListExtra("personArrayList", personArrayList);
                startActivity(viewProfilesIntent);
            }
        });

        interestSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View onClickView) {
                Intent interestIntent = new Intent(ProfileActivity.this, InterestActivity.class);
                interestIntent.putExtra("MyProfile", p);
                startActivity(interestIntent);
            }
        });
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