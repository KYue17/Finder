package com.example.kevin.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Tommy on 6/11/2015.
 */
public class CreateProfileActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        final EditText nameEnter = (EditText) findViewById(R.id.nameEnter);

        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        monthSpinner.setAdapter(mAdapter);

        final int currYear = 2015;

        Integer[] years = new Integer[125];
        for(int i = 0; i < 125; i++)
        {
            years[i] = currYear - i;
        }

        final Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        ArrayAdapter<Integer> yAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, years);
       yearSpinner.setAdapter(yAdapter);

        Integer[] days28 = new Integer[28];
        for(int i = 0; i < 28; i++){
            days28[i] = i+1;
        }
        Integer[] days29 = new Integer[29];
        for(int i = 0; i < 29; i++){
            days29[i] = i+1;
        }
        Integer[] days30 = new Integer[30];
        for(int i = 0; i < 30; i++){
            days30[i] = i+1;
        }
        Integer[] days31 = new Integer[31];
        for(int i = 0; i < 31; i++){
            days31[i] = i+1;
        }

        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        //if february and not leap year, use days28
        //if february and leap year, use days29
        //if april, june, september, november use days30
        //if january, march, may, july, august, october, december use days31
        ArrayAdapter<Integer> dAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, days31);
        daySpinner.setAdapter(dAdapter);

        Button confirmProfile = (Button) findViewById(R.id.confirmProfile);
        confirmProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View onClickView){
                String name = nameEnter.getText().toString();
                Integer age = currYear - (Integer)yearSpinner.getItemAtPosition(0); // subtract one if birthday not yet passed
                Person p = new Person(name, age);
                //store person in database based on username
                if(name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(), age.toString(), Toast.LENGTH_LONG).show();
                    Intent profileIntent = new Intent(CreateProfileActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return;
                }
            }
        });



    }

    protected void onDestroy(){
        super.onDestroy();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
