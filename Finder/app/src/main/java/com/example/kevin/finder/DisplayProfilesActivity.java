package com.example.kevin.finder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kevin on 6/13/2015.
 */
public class DisplayProfilesActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        sv.addView(ll);

        TextView tv = new TextView(this);
        tv.setText("Profiles");
        ll.addView(tv);

        for(int i = 0; i < 20; i++){
            Button b = new Button(this);
            b.setId(i);
            ll.addView(b);
        }

        setContentView(sv);
    }

}
