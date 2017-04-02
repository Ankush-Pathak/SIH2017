package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifyEntriesActivity extends AppCompatActivity implements VerifyEntriesListAdapter.ListItemClickListner{

    VerifyEntriesListAdapter verifyListAdapter;
    RecyclerView mNumberList;
    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferencePlant,databaseReferenceId;
    ArrayList<Plant> plant;
    ArrayList<String> idList;
    SharedPreferences sharedPreferences;
    String userId;
    void setup() {
        plant = new ArrayList<>();
        idList = new ArrayList<>();
        sharedPreferences = this.getSharedPreferences("logedinUser", MODE_PRIVATE);
        userId = sharedPreferences.getString("email","");
        firebaseDatabaseSync = FirebaseDatabase.getInstance();
        databaseReferencePlant = firebaseDatabaseSync.getReference().child("plant");
        databaseReferenceId = firebaseDatabaseSync.getReference();
        databaseReferencePlant.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    plant.add(ds.getValue(Plant.class));
                    idList.add(ds.getKey());
                }
                for (int i = 0; i < plant.size(); i++) {
                    if (plant.get(i).isFullyVerfied()||plant.get(i).getUploaderId().equals(userId)||plant.get(i).getNameVerificationCount()>=5) {
                        plant.remove(i);
                        idList.remove(i);
                    }
                }
                mNumberList = (RecyclerView) findViewById(R.id.recyclerViewList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(VerifyEntriesActivity.this);
                mNumberList.setLayoutManager(layoutManager);
                mNumberList.setHasFixedSize(true);

                verifyListAdapter = new VerifyEntriesListAdapter(VerifyEntriesActivity.this,plant.size(),VerifyEntriesActivity.this, plant);
                mNumberList.setAdapter(verifyListAdapter);
                Log.d("debug","plant :"+plant.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("debug","plant to be verified: "+plant.size());
    }
            @Override
            protected void onCreate (Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                //Create object in setup functions
                setContentView(R.layout.activity_verify_entries);
                setup();

            }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(VerifyEntriesActivity.this , MainActivity.class);
        startActivity(intent);
    }

            @Override
            public void onListItemClick ( int onListItemClickId){
//        Toast toast = Toast.makeText(this,plant.get(onListItemClickId).getLeafColor(),Toast.LENGTH_SHORT);
//        toast.show();

                Intent intent = new Intent(this, VerifyActivity.class);
                Plant p = plant.get(onListItemClickId);
                String id = idList.get(onListItemClickId);
                intent.putExtra("plant", p);
                intent.putExtra("iD",id);
                startActivity(intent);
                finish();
            }
}