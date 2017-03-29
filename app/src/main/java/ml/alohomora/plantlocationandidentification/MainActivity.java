package ml.alohomora.plantlocationandidentification;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button buttonMenuSearchPlnt,buttonAddToDatabase, buttonMenuPlotPlantsSpottedNearby;
    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferenceSync;
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
    }

    void setUpObjects()
    {
        buttonMenuSearchPlnt = (Button)findViewById(R.id.buttonMenuSearchPlnt);
        buttonAddToDatabase = (Button) findViewById(R.id.buttonMenuAddNewPlntTDb);
        buttonMenuPlotPlantsSpottedNearby = (Button)findViewById(R.id.buttonMenuShowNearbyPlants);
        firebaseDatabaseSync = FirebaseDatabase.getInstance();
        databaseReferenceSync = firebaseDatabaseSync.getReference();
        databaseReferenceSync.keepSynced(true);
    }

    void setupListenersAndIntents()
    {
        buttonMenuSearchPlnt.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(MainActivity.this,AddToDatabaseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonMenuPlotPlantsSpottedNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , PlotPlantsSpottedNearby.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



