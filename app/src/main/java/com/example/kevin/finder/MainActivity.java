package com.example.kevin.finder;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.content.Intent;
import android.widget.*;

import com.facebook.FacebookSdk;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Button goToLoginButton = (Button)findViewById(R.id.goToButtonSignIN);
        Button goToSignupButton = (Button)findViewById(R.id.goToButtonSignUP);
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
