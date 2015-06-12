package com.example.kevin.finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;


/**
 * Created by Tommy on 6/11/2015.
 */
public class CreateProfileActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public static int day;
    public static int year;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        TextView textView = (TextView) findViewById(R.id.birthday);
        textView.setTextSize(20);

        final EditText nameEnter = (EditText) findViewById(R.id.nameEnter);

        Button confirmProfile = (Button) findViewById(R.id.confirmProfile);
        confirmProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View onClickView){
                String name = nameEnter.getText().toString();
                if(name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    final Calendar c = Calendar.getInstance();
                    int currYear = c.get(Calendar.YEAR);
                    int currDay = c.get(Calendar.DAY_OF_YEAR);
                    Integer age = currYear - year;
                    if(day > currDay){
                        age--;
                    }
                    Person p = new Person(name, age);
                    //store person in database based on username
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
