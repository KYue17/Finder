package com.example.kevin.finder;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

        final Person p = getIntent().getExtras().getParcelable("myProfile");

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
