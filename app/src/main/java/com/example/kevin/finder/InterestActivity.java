package com.example.kevin.finder;
import com.example.kevin.finder.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InterestActivity extends ActionBarActivity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    //EditText inputSearch;

    // ArrayList for Listview
    //ArrayList<HashMap<String, String>> productList;
    private Button add;
    ArrayList<String> listItems;
    ListView listView;
    EditText editText;

    private MobileServiceClient mClient;
    private MobileServiceTable<Person> mPersonTable;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        final Person p = getIntent().getExtras().getParcelable("MyProfile");

//        ArrayList<String> lines = new ArrayList<String>();
//        try {
//            InputStream instream = openFileInput("activities.txt");
//
//            InputStreamReader inputreader = new InputStreamReader(instream);
//            BufferedReader buffreader = new BufferedReader(inputreader);
//            boolean hasNextLine = true;
//            while (hasNextLine == true){
//                String line =  buffreader.readLine();
//                lines.add(line);
//                hasNextLine = line != null;
//            }
//
//            instream.close();
//
//        } catch (Exception ex) {
//
//        }

        try {
            mClient = new MobileServiceClient("https://findr.azure-mobile.net/",getString(R.string.azure_code), InterestActivity.this);
            mPersonTable = mClient.getTable(Person.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//        listItems = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list_view);
        editText = (EditText) findViewById(R.id.inputSearch);
        add = (Button) findViewById(R.id.addItem);
        listItems = new ArrayList<String>();
        listItems.add("Test Item");
        listItems.add("2nd Item");
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.product_name, listItems);
        listView.setAdapter(adapter);
//        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                final int arrayPosition = position;
                PopupMenu popupMenu = new PopupMenu(InterestActivity.this, view);
                popupMenu.inflate(R.menu.add_or_delete_interest_popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        boolean success = false;
                        if(item.getTitle().equals("Add")){
                            if(p.getInterests().contains(listItems.get(arrayPosition))){
                                Toast.makeText(InterestActivity.this, "Interest Already Present", Toast.LENGTH_LONG).show();
                            }else {
                                p.addInterest(listItems.get(arrayPosition));
                                updatePerson(p);
                                success = true;
                            }
                        }else{
                            if(!p.getInterests().contains(listItems.get(arrayPosition))){
                                Toast.makeText(InterestActivity.this, "Interest Not Present", Toast.LENGTH_LONG).show();
                            }else {
                                p.deleteInterest(listItems.get(arrayPosition));
                                updatePerson(p);
                                success = true;
                            }
                        }
                        if(success == true) {
                            Toast.makeText(InterestActivity.this, item.getTitle() + "Success", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
//
            public void onClick(View v) {
                listItems.add(editText.getText().toString());
                adapter.clear();
                adapter.addAll(listItems);
                adapter.notifyDataSetChanged();
//            }
//        });
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position,
                                            long id) {
                        Toast.makeText(InterestActivity.this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG)
                                .show();
                        //p.addInterest(listView.getItemAtPosition(position).toString());
                    }
                });
            }
        });

        editText.addTextChangedListener(new

                                                TextWatcher() {

                                                    @Override
                                                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                                        // When user changed the Text
                                                        InterestActivity.this.adapter.getFilter().filter(cs);
                                                    }

                                                    @Override
                                                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                                                  int arg3) {
                                                        // TODO Auto-generated method stub

                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable arg0) {
                                                        // TODO Auto-generated method stub
                                                    }
                                                }

        );
    }

    public void updatePerson(final Person person) {
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
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



