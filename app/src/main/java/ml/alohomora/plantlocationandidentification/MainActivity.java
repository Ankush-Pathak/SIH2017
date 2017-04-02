package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button buttonMenuSearchPlnt,buttonAddToDatabase, buttonMenuPlotPlantsSpottedNearby,buttonMenuVerifyEntries,buttonMenuIdentifyPlnt, buttonMenuViewUserProfile;
    TextView textviewMenuDisplayUsername, textviewMenuDisplayUserTagline, textviewMenuUserLevelNum, textviewMenuUserScore;
    ProgressBar progressBarMenuLevelNum;
    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferenceSync;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    FragmentManager fragmentManager;

    TrackGPS gps;
    @Override

    //Calligraphy additions
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpObjects();
        setupListenersAndIntents();
        extractScoreFromSP();
        //extractScoreFromSP(); //this method calls setupLevelsAndPointsForUser(String, int, int, int)
    }


    void setUpObjects()
    {

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
        //bottomNavigationView.inflateMenu(R.menu.bottomnavigation); this is already done in XML when menu.xml is called.
        fragmentManager = getSupportFragmentManager();



        buttonMenuVerifyEntries  = (Button) findViewById(R.id.buttonMenuVerifyEntries);
        //buttonMenuIdentifyPlnt = (Button)findViewById(R.id.buttonMenu);

       // BottomNavigationItemView buttonMenuSearchPlnt = (BottomNavigationItemView)findViewById(R.id.navigationbutton_searchforaplant);

        //buttonAddToDatabase = (Button) findViewById(R.id.navigationbutton_addaplant);
        //buttonMenuPlotPlantsSpottedNearby = (Button)findViewById(R.id.buttonMenuShowNearbyPlants);
        buttonMenuViewUserProfile=(Button)findViewById(R.id.buttonMenuViewProfile);

        textviewMenuDisplayUsername=(TextView)findViewById(R.id.textviewMenuUsername);
        textviewMenuDisplayUserTagline=(TextView)findViewById(R.id.textViewMenuUserTagline);
        textviewMenuUserLevelNum=(TextView)findViewById(R.id.textViewMenuLevelNum);
        textviewMenuUserScore=(TextView)findViewById(R.id.textViewMenuXP);

        progressBarMenuLevelNum=(ProgressBar)findViewById(R.id.progressBarMenuLevel);



        firebaseDatabaseSync = FirebaseDatabase.getInstance();
        databaseReferenceSync = firebaseDatabaseSync.getReference();
        databaseReferenceSync.keepSynced(true);
        sharedPreferences = getApplicationContext().getSharedPreferences("logedinUser", MODE_PRIVATE);
    }

    void setupListenersAndIntents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int item_id = item.getItemId();
                switch (item_id) {
                    case R.id.navigationbutton_addaplant:
                        SharedPreferences settings = getSharedPreferences("logedinUser",MODE_PRIVATE);
                        Boolean language = settings.getBoolean("privilege", false);
                        if (language) {
                            Intent intent = new Intent(MainActivity.this, AddToDatabaseActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "you are not a privileged user", Toast.LENGTH_SHORT).show();
                        }
                        break;


                    case R.id.navigationbutton_searchforaplant:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigationbutton_plantsnearby:
                        plants_nearby();
                        break;


                }
                return true;
            }
        });







        /*buttonMenuSearchPlnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonAddToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                Boolean language = settings.getBoolean("privilege",false);
                if(language)
                {
                Intent intent = new Intent(MainActivity.this,AddToDatabaseActivity.class);
                startActivity(intent);
                finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"you are not a privilege user",Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonMenuIdentifyPlnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IdentifyPlantActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        buttonMenuVerifyEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerifyEntriesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




       /* buttonMenuPlotPlantsSpottedNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gps = new TrackGPS(getApplicationContext());


                NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();





                //To display my location

                if(info == null)
                {
                    Toast.makeText(getApplicationContext(),"Internet Unavailable",Toast.LENGTH_LONG).show();

                }


                else {


                    if(gps.canGetLocation) {

                        Intent intent = new Intent(MainActivity.this, PlotPlantsSpottedNearby.class);
                        startActivity(intent);
                        finish();
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please enable location and restart app",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }*/

    private void plants_nearby() {
        gps = new TrackGPS(getApplicationContext());

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        //To display my location

        if(info == null)
        {
            Toast.makeText(getApplicationContext(),"Internet Unavailable",Toast.LENGTH_LONG).show();

        }

        else {
            if(gps.canGetLocation) {

                Intent intent = new Intent(MainActivity.this, PlotPlantsSpottedNearby.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please enable location and restart app",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void extractScoreFromSP() {

        String email=sharedPreferences.getString("email", "");
        int username_count=0;
        int email_len=email.length();
        for(int i = 0;i<email_len;i++)
        {
            char ch=email.charAt(i);
            if(ch=='@')
            {
                username_count=i;
                break;
            }
        }

        String username=email.substring(0,username_count);
        int level=Integer.parseInt(sharedPreferences.getString("level","0"));
        Log.d("Level from SP",sharedPreferences.getString("level",""));
        int score=Integer.parseInt(sharedPreferences.getString("score","0"));
        int maxscore;
        if(level==0){ maxscore=5;        }
        else        { maxscore=level*10; }
        setupLevelsAndPointsForUser(username,level,score,maxscore);


    }

    private void setupLevelsAndPointsForUser(String username, int level, int score, int maxscore){

       textviewMenuDisplayUsername.setText(username);

        progressBarMenuLevelNum.setMax(maxscore);
        progressBarMenuLevelNum.setProgress(score);
        textviewMenuUserScore.setText(score+"/"+" XP");
        textviewMenuUserLevelNum.setText("Level "+level);




    }



}





