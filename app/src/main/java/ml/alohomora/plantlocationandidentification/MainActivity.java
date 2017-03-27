package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button buttonMenuSearchPlnt,buttonAddToDatabase;
    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferenceSync;
    @Override
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
    }
}
