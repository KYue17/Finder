package com.example.kevin.finder;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 6/13/2015.
 */
public class DisplayProfilesActivity extends ActionBarActivity{

    MobileServiceClient mClient;
    MobileServiceTable<Person> mPersonTable;

    ScrollView scrollView;
    TableLayout tableLayout;
    ArrayList<String> temp = new ArrayList<String>();

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profiles);

        final Person p = getIntent().getExtras().getParcelable("myProfile");
        ArrayList<Person> personArrayList = getIntent().getParcelableArrayListExtra("personArrayList");

        try {
            mClient = new MobileServiceClient("https://findr.azure-mobile.net/",getString(R.string.azure_code), DisplayProfilesActivity.this);
            mPersonTable = mClient.getTable(Person.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final ArrayList<Person> personsWithCommonInterests = getDataFromTable(personArrayList, p);

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.product_name, temp);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                final int arrayPosition = position;
                Person otherPerson = personsWithCommonInterests.get(arrayPosition);
                Intent otherProfileIntent = new Intent(DisplayProfilesActivity.this, OtherProfileActivity.class);
                otherProfileIntent.putExtra("otherProfile", otherPerson);
                startActivity(otherProfileIntent);

            }
        });
    }

    public ArrayList<Person> getDataFromTable(ArrayList<Person> personArrayList, Person user){
        scrollView = (ScrollView)findViewById(R.id.scroll_view);
        tableLayout = new TableLayout(this);

        ArrayList<Person> tempPersonArrayList = new ArrayList<Person>();

        try {
//          int textViewId = 0;
            for(Person person : personArrayList){
                if(!person.getUsername().equals(user.getUsername())){
                    if((String.valueOf(person.getZipcode()).charAt(0) == String.valueOf(user.getZipcode()).charAt(0)) &&
                            (String.valueOf(person.getZipcode()).charAt(1) == String.valueOf(user.getZipcode()).charAt(1))) {
                        String[] tempInterestArray = person.getInterests().split("\n");
                        ArrayList<String> commonInterests = new ArrayList<String>();
                        for (String interest : tempInterestArray) {
                            if (user.getInterests().contains(interest) && !interest.equals("")) {
                                commonInterests.add(interest);
                            }
                        }

                        if (commonInterests.size() != 0) {
                            Collections.sort(commonInterests);
                            String displayInterests = "";
                            if (commonInterests.size() > 2) {
                                displayInterests += commonInterests.get(0) + ", " + commonInterests.get(1) + "," + commonInterests.get(2) + "...";
                            } else if (commonInterests.size() == 2) {
                                displayInterests += commonInterests.get(0) + ", " + commonInterests.get(1);
                            } else {
                                displayInterests += commonInterests.get(0);
                            }
                            //                        TableRow tableRow = new TableRow(this);
                            //                        TextView textView = new TextView(this);
                            //                        textView.setId(textViewId);

                            temp.add("Username: " + person.getUsername() + " | " + commonInterests.size() + "interests in common" + " | " +
                                    displayInterests);
                            //                        textViewId++;
                            //                        tableRow.addView(textView);
                            //                        tableLayout.addView(tableRow);

                            tempPersonArrayList.add(person);
                        }
                    }
                }
            }

        } catch (Exception exception) {
            Log.d("Exception: ", exception.toString());
        }

        return tempPersonArrayList;
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
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                }else{
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
