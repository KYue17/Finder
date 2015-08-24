package com.example.kevin.finder;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.content.Intent;
import android.widget.*;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.*;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    CallbackManager callbackManager;

    private MobileServiceClient mClient;
    private MobileServiceTable<Person> mPersonTable;
    private EditText usernameET;
    private EditText passwordET;
    private Button loginButton;
    private Button signupButton;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_main);
        setupVariables();

        callbackManager = CallbackManager.Factory.create();
        LoginButton fbLoginButton = (LoginButton)findViewById(R.id.fbLoginButton);
        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent fbIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(fbIntent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View onClickView){
                authenticateLogin(onClickView);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View onClickView) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void authenticateLogin(View view) {

        username = usernameET.getText().toString();
        password = passwordET.getText().toString();

        try {
            mClient = new MobileServiceClient("https://findr.azure-mobile.net/",getString(R.string.azure_code), MainActivity.this);
            mPersonTable = mClient.getTable(Person.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            mPersonTable.execute(new TableQueryCallback<Person>() {
                @Override
                public void onCompleted(List<Person> result, int count, Exception exception, ServiceFilterResponse response) {
                    ArrayList<Person> personArrayList = new ArrayList<Person>();
                    for(Person p : result){
                        personArrayList.add(p);
                    }
                    boolean userFound = false;
                    if(exception == null){
                        for(Person p : personArrayList){
                            if(p.getUsername().equals(username)){
                                userFound = true;
                                if(p.getPassword().equals(password)){
                                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                                    profileIntent.putExtra("myProfile", p);
                                    profileIntent.putParcelableArrayListExtra("personArrayList", personArrayList);
                                    startActivity(profileIntent);
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Invalid Username/Password Combination", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        }
                        if(userFound == false) {
                            Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception exception) {
            Log.d("Exception: ", exception.toString());
        }
    }

    private void setupVariables() {
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginButton = (Button) findViewById(R.id.loginBtn);
        signupButton = (Button) findViewById(R.id.signupBtn);

    }
}
