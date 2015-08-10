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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 6/13/2015.
 */
public class DisplayProfilesActivity extends ActionBarActivity{

    MobileServiceClient mClient;
    MobileServiceTable<Person> mPersonTable;

    ScrollView scrollView;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profiles);

        final Person p = getIntent().getExtras().getParcelable("myProfile");

        try {
            mClient = new MobileServiceClient("https://findr.azure-mobile.net/",getString(R.string.azure_code), DisplayProfilesActivity.this);
            mPersonTable = mClient.getTable(Person.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        getDataFromTable();
    }

    public void getDataFromTable(){
        scrollView = (ScrollView)findViewById(R.id.scroll_view);
        tableLayout = new TableLayout(this);

        try {
            final MobileServiceList<Person> result = mPersonTable.execute().get();
            int textViewId = 0;
            for(Person person : result){
                TableRow tableRow = new TableRow(this);
                TextView textView = new TextView(this);
                textView.setId(textViewId);
                textView.setText("Username: " + person.getUsername() + " | Id: " + textViewId);
                textViewId++;
                tableRow.addView(textView);
                tableLayout.addView(tableRow);
            }
        } catch (Exception exception){
            Log.d("Exception: ", exception.toString());
        }

        scrollView.addView(tableLayout);
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
