package com.example.kevin.finder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

/**
 * Created by Kevin on 6/10/2015.
 */
public class SignupActivity extends ActionBarActivity{

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
                else{
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
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
}
