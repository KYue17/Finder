package com.example.kevin.finder;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin on 6/10/2015.
 */
public class SignupActivity extends ActionBarActivity{

    private MobileServiceClient mClient;
    private MobileServiceTable mPersonTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText usernameSignup = (EditText)findViewById(R.id.usernameSignup);
        final EditText passwordSignup = (EditText)findViewById(R.id.passwordSignup);
        final EditText confirmPasswordSignup = (EditText)findViewById(R.id.confirmPasswordSignup);

        Button createAccount = (Button)findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View onClickView){
                String username = usernameSignup.getText().toString();
                String password = passwordSignup.getText().toString();
                String confirmPassword = confirmPasswordSignup.getText().toString();

                if(username.equals("") || password.equals("") || confirmPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!confirmPassword.equals(password)){
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                    return;
                }
                //check if username
                else{
                    /*try {
                        final Person check = (Person)mPersonTable.lookUp(username).get(); //Checks if username is already in database
                        if(check != null){
                            Toast.makeText(getApplicationContext(), "Username already in use", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }*/

                    /*try {
                        final Person result = (Person)mPersonTable.lookUp(username).get();

                    } catch (Exception exception) {
                        Log.d("Exception:", exception.toString());
                    }*/

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    final Person p = new Person();
                    p.setUsername(username);
                    p.setPassword(password);
                    p.setId(username);

                    //store person in database based on username

                    try {
                        mClient = new MobileServiceClient("https://findr.azure-mobile.net/", getString(R.string.azure_code), SignupActivity.this);
                        mPersonTable = mClient.getTable(Person.class);

                        mPersonTable.insert(p, new TableOperationCallback<Person>() {
                            @Override
                            public void onCompleted(Person entity, Exception exception, ServiceFilterResponse response) {
                                if(exception == null){

                                }else{

                                }
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    Intent createProfileIntent = new Intent(SignupActivity.this,CreateProfileActivity.class);
                    createProfileIntent.putExtra("myPerson", p);
                    startActivity(createProfileIntent);
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
