package com.example.kevin.finder;
import com.example.kevin.finder.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    //EditText inputSearch;

    // ArrayList for Listview
    //ArrayList<HashMap<String, String>> productList;
    private Button add;
    ListView listView;
    EditText editText;
    private MobileServiceClient mClient;
    private MobileServiceTable<Person> mPersonTable;
    String masterList = "Amateur radio,Acting,Baton twirling,Board games,Book restoration,Cabaret,Calligraphy,Candle making,Computer programming,Cooking,Coloring,Cosplaying,Couponing,Creative writing,Crocheting,Cryptography,Dance,Digital arts,Drama,Drawing,Do it yourself,Electronics,Embroidery,Fashion,Flower arranging,Foreign language learning,Gaming (tabletop games and role-playing games),Gambling,Genealogy,Glassblowing,Homebrewing,Ice skating,Jewelry making,Jigsaw puzzles,Juggling,Knapping,Knitting,Kabaddi,Knife making,Lacemaking,Lapidary,Leather crafting,Lego building,LockpickingMachining,Macrame,Metalworking,Magic,Model building,Listening to music,Origami,Painting,Playing musical instruments,Pet,Pottery,Puzzles,Quilting,Reading,Scrapbooking,Sculpting,Sewing,Singing,Sketching,Soapmaking,Sports,Stand-up comedy,Sudoku,Table tennis,Taxidermy,Video gaming,Watching movies,Web surfing,Whittling,Wood carving,Woodworking,Worldbuilding,Writing,Yoga,Yo-yoing,Outdoors,Air sports,Archery,Astronomy,Backpacking,BASE jumping,Baseball,Basketball,Beekeeping,Bird watching,Blacksmithing,Board sports,Bodybuilding,Brazilian jiu-jitsu,Cycling,Dowsing,Driving,Fishing,Flag Football,Flying,Flying disc,Foraging,Gardening,Geocaching,Ghost hunting,Graffiti,Gunsmithing,Handball,Hiking,Hooping,Horseback riding,Hunting,Inline skating,Jogging,Kayaking,Kite flying,Kitesurfing,LARPing,Letterboxing,Metal detecting,Motor sports,Mountain biking,Mountaineering,Mushroom hunting/Mycology,Netball,Nordic skating,Orienteering,Paintball,Parkour,Photography,Polo,Rafting,Rappelling,Rock climbing,Roller skating,Rugby,Running,Sailing,Sand art,Scouting,Scuba diving,Sculling or Rowing,Shooting,Shopping,Skateboarding,Skiing,Skimboarding,Skydiving,Slacklining,Snowboarding,Stone skipping,Surfing,Swimming,Taekwondo,Tai chi,Urban exploration,Vacation,Vehicle restoration,Water sports,Collection hobbies,Indoors,Action figure,Antiquing,Art collecting,Book collecting,Card collecting,Coin collecting,Comic book collecting,Deltiology (postcard collecting),Die-cast toy,Element collecting,Movie and movie memorabilia collecting,Record collecting,Stamp collecting,Video game collecting,Vintage cars,Weapon collecting,Outdoors,Antiquities,Auto audiophilia,Flower collecting and pressing,Fossil hunting,Insect collecting,Metal detecting,Stone collecting,Mineral collecting,Rock balancing,Sea glass collecting,Seashell collecting,Competition hobbies,Indoors,Aqua-lung,Animal fancy,Badminton,Baton Twirling,Billiards,Bowling,Boxing,Bridge,Cheerleading,Chess,Color guard,Curling,Dancing,Darts,Debate,Fencing,Go,Gymnastics,Marbles,Martial arts,Mahjong,Poker,Slot car racing,Table football,Video Games,Volleyball,Weightlifting,Outdoors,Airsoft,American football,Archery,Association football,Australian rules football,Auto racing,Baseball,Beach Volleyball,Breakdancing,Climbing,Cricket,Cycling,Disc golf,Dog sport,Equestrianism,Exhibition drill,Field hockey,Figure skating,Fishing,Ultimate Frisbee,Footbag,Golfing,Handball,Ice hockey,Judo,Jukskei,Kart racing,Knife throwing,Lacrosse,Laser tag,Model aircraft,Pigeon racing,Racquetball,Radio-controlled car racing,Roller derby,Rugby league football,Shooting sport,Skateboarding,Speed skating,Squash,Surfing,Swimming,Table tennis,Tennis,Tour skating,Triathlon,Volleyball,Observation hobbies,Indoors,Fishkeeping,Microscopy,Reading,Shortwave listening,Videophilia,Outdoors,Aircraft spotting,Amateur astronomy,Amateur geology,Astrology,Birdwatching,Bus spotting,Geocaching,Gongoozling,Herping,Meteorology,People watching,Trainspotting,Traveling,Hiking/backpacking";
    ArrayList<String> listItems = new ArrayList<String>(Arrays.asList(masterList.split(",")));

    public void onCreate(Bundle savedInstanceState) {
        Collections.sort(listItems, String.CASE_INSENSITIVE_ORDER);
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
                        if(item.getTitle().equals("Add")) {
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



