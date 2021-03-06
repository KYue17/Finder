package com.example.kevin.finder;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Tommy on 6/11/2015.
 */
public class CreateProfileActivity extends ActionBarActivity {

    MobileServiceClient mClient;
    MobileServiceTable mPersonTable;

    String name;
    Integer age;
    Integer zipcode;
    String phoneNumber;
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        final Calendar c = Calendar.getInstance();
        TextView textView = (TextView) findViewById(R.id.birthday);
        textView.setTextSize(20);
        final EditText nameEnter = (EditText) findViewById(R.id.nameEnter);
        final EditText zipcodeEnter = (EditText) findViewById(R.id.zipcodeEnter);
        final EditText phoneEnter = (EditText) findViewById(R.id.phoneEnter);
        final EditText emailEnter = (EditText) findViewById(R.id.emailEnter);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMaxDate(c.getTimeInMillis());
        Button confirmProfile = (Button) findViewById(R.id.confirmProfile);

        final Person p = getIntent().getExtras().getParcelable("myPerson");

        confirmProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View onClickView) {
                name = nameEnter.getText().toString();
                zipcode = Integer.parseInt(zipcodeEnter.getText().toString());
                phoneNumber = phoneEnter.getText().toString();
                emailAddress = emailEnter.getText().toString();
                if (name.equals("") || phoneNumber.equals("") || emailAddress.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field(s) Empty", Toast.LENGTH_LONG).show();
                    return;
                }else if(String.valueOf(zipcode).length() != 5 || String.valueOf(phoneNumber).length() != 10){
                    Toast.makeText(getApplicationContext(), "Invalid Field(s)", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    int year = datePicker.getYear();
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    if (month > 1) {
                        day += 31;
                    }
                    if (month > 2) {
                        day += 28;
                    }
                    if (month > 3) {
                        day += 31;
                    }
                    if (month > 4) {
                        day += 30;
                    }
                    if (month > 5) {
                        day += 31;
                    }
                    if (month > 6) {
                        day += 30;
                    }
                    if (month > 7) {
                        day += 31;
                    }
                    if (month > 8) {
                        day += 31;
                    }
                    if (month > 9) {
                        day += 30;
                    }
                    if (month > 10) {
                        day += 31;
                    }
                    if (month > 11) {
                        day += 30;
                    }
                    if (month > 2 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
                        day += 1;
                    }
                    int currYear = c.get(Calendar.YEAR);
                    int currDay = c.get(Calendar.DAY_OF_YEAR);
                    age = currYear - year;
                    if (day > currDay) {
                        age--;
                    }

                    Toast.makeText(getApplicationContext(), p.getId(), Toast.LENGTH_LONG).show();

                    try {
                        mClient = new MobileServiceClient("https://findr.azure-mobile.net/",getString(R.string.azure_code), CreateProfileActivity.this);
                        mPersonTable = mClient.getTable(Person.class);

                        updatePerson(p);

                        Intent profileIntent = new Intent(CreateProfileActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("myProfile", p);
                        startActivity(profileIntent);
                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }


                }
            }
        });
    }

    public void updatePerson(final Person person) {
        person.setAge(age);
        person.setName(name);
        person.setZipcode(zipcode);
        person.setPhoneNumber(phoneNumber);
        person.setEmailAddress(emailAddress);

        if (mClient == null) {
            return;
        }

        mPersonTable.update(person, new TableOperationCallback() {
            @Override
            public void onCompleted(Object entity, Exception exception, ServiceFilterResponse response) {

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