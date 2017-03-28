package ml.alohomora.plantlocationandidentification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText editTextSrchTxtSrch;
    FirebaseDatabase firebaseDatabaseRetrieveAllData;
    DatabaseReference databaseReferenceRetrieveAllData;
    ArrayList<Plant> arrayListPlantFromDb;
    String searchString = null;
    SearchResultListViewAdapter searchResultListViewAdapter;
    ListView listViewSrchRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpObjects();
    }

    void setUpObjects()
    {
        editTextSrchTxtSrch = (EditText)findViewById(R.id.editTextSearchTextSrch);
        firebaseDatabaseRetrieveAllData = FirebaseDatabase.getInstance();
        databaseReferenceRetrieveAllData = firebaseDatabaseRetrieveAllData.getReference().child("plant");
        listViewSrchRes = (ListView)findViewById(R.id.listViewSearchSrchResults);
        arrayListPlantFromDb = new ArrayList<>();
        editTextSrchTxtSrch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 1) {
                    searchString = s.toString();
                    retrieveFbData(searchString);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void retrieveFbData(String s)
    {
        databaseReferenceRetrieveAllData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    addToPlantArrayList(dataSnapshot,s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void addToPlantArrayList(DataSnapshot dataSnapshot,String s)
    {
        arrayListPlantFromDb.add(dataSnapshot.getValue(Plant.class));
        updateArrayAdapter(s);
    }
    void updateArrayAdapter(String s)
    {
        searchResultListViewAdapter = new SearchResultListViewAdapter(s,SearchActivity.this,arrayListPlantFromDb);
        listViewSrchRes.setAdapter(searchResultListViewAdapter);
    }
}
