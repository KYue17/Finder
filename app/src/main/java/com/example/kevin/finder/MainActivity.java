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
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import android.database.sqlite.*;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    CallbackManager callbackManager;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        try {
            mClient = new MobileServiceClient("https://finderandroid.azure-mobile.net/", "tjziqMoVOuszxlpChPyGLVHsPexbFL10",this);
            Item item = new Item();
            item.Text = "Awesome item";
            mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
                public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded
                    } else {
                        // Insert failed
                    }
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        Button goToSignupButton = (Button)findViewById(R.id.goToButtonSignUP);

        callbackManager = CallbackManager.Factory.create();

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

        goToSignupButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View onClickView){
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

    public void goToLogin(View view){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");

        Button loginButton = (Button)dialog.findViewById(R.id.buttonSignIn);

        final EditText etUsername = (EditText)dialog.findViewById(R.id.usernameLogin);
        final EditText etPassword = (EditText)dialog.findViewById(R.id.passwordLogin);

        loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View onClickView){
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();


                JSONObject object = new JSONObject();

                if(username.equals("username") && password.equals("password")){
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }
}
