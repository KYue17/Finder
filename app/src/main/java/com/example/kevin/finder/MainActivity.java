package com.example.kevin.finder;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import java.net.MalformedURLException;
import android.database.sqlite.*;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    CallbackManager callbackManager;

    private MobileServiceClient mClient;
    private MobileServiceTable mPersonTable;
    private EditText username;
    private EditText password;
    private Button login;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_main);
        setupVariables();

        callbackManager = CallbackManager.Factory.create();
       // Button signup = (Button)findViewById(R.id.signupBtn);
        LoginButton loginButton = (LoginButton)findViewById(R.id.fbLoginButton);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

        signup.setOnClickListener(new View.OnClickListener() {
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

        if (username.getText().toString().equals("a") &&
                password.getText().toString().equals("a")) {
            Person p = new Person();
            p.setUsername(username.getText().toString());
            p.setPassword(password.getText().toString());
            Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            profileIntent.putExtra("myProfile", p);
            startActivity(profileIntent);
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failure",
                    Toast.LENGTH_SHORT).show();



        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);
        login = (Button) findViewById(R.id.loginBtn);
        signup = (Button) findViewById(R.id.signupBtn);

    }
}
