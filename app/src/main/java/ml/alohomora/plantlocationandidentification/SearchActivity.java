package ml.alohomora.plantlocationandidentification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText editTextSrchTxtSrch;
    FirebaseDatabase firebaseDatabaseRetrieveAllData;
    DatabaseReference databaseReferenceRetrieveAllData;
    ArrayList<Plant> arrayListPlantFromDb;
    ArrayList<String> matchingSectionsWithSearch;
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
        matchingSectionsWithSearch = new ArrayList<>();
        editTextSrchTxtSrch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(SearchActivity.this,"Text before changed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    searchString = s.toString();
                    arrayListPlantFromDb.clear();
                    retrieveFbData(searchString);
                    //Toast.makeText(SearchActivity.this,"Text changed",Toast.LENGTH_SHORT).show();
                    Log.d("Search","Txt Changed");
                }
                if (count == 0)
                {
                    arrayListPlantFromDb.clear();
                    searchResultListViewAdapter = new SearchResultListViewAdapter(searchString,SearchActivity.this,arrayListPlantFromDb,matchingSectionsWithSearch);
                    listViewSrchRes.setAdapter(searchResultListViewAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(SearchActivity.this,"After Text changed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void retrieveFbData(String st)
    {
        arrayListPlantFromDb.clear();
        final String str = st;
        databaseReferenceRetrieveAllData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Plant plant;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    plant = ds.getValue(Plant.class);
                    if(checkIfShouldBeAdded(plant))
                    {
                        arrayListPlantFromDb.add(plant);
                        Log.d("Search","Arraylist size : " + arrayListPlantFromDb.size() + " content " + arrayListPlantFromDb.toString());

                    }
                }
                updateArrayAdapter(searchString);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void updateArrayAdapter(String s)
    {
        Log.d("Search","Now setting adapeter, matchingsection size : " + matchingSectionsWithSearch.size()  + " with values " + matchingSectionsWithSearch.toString());
        searchResultListViewAdapter = new SearchResultListViewAdapter(s,SearchActivity.this,arrayListPlantFromDb,matchingSectionsWithSearch);
        listViewSrchRes.setAdapter(searchResultListViewAdapter);
        Log.d("Search","Array Adapter set");
    }

    boolean checkIfShouldBeAdded(Plant plant)
    {
        boolean flag = false;
        matchingSectionsWithSearch.clear();
        Log.d("Search","Search String : " + searchString);
        Log.d("Search", plant.getName() + " contains : " + searchString + " : " + plant.getName().contains(searchString));
        if(plant.getName().contains(searchString))
        {
            matchingSectionsWithSearch.add("Name");
            flag = true;
        }
        for(String s : plant.getCommonNames())
        {
            if(s.contains(searchString))
                flag = true;
        }
        if(flag == true)
        {
            matchingSectionsWithSearch.add("Common name");
        }

        if (plant.getFruitColor().contains(searchString))
        {
            matchingSectionsWithSearch.add("Fruit color");
            flag = true;
        }

        if (plant.getFruitShape().contains(searchString))
        {
            matchingSectionsWithSearch.add("Fruit shape");
            flag = true;
        }

        if (plant.getLeafColor().contains(searchString))
        {
            matchingSectionsWithSearch.add("Leaf color");
            flag = true;
        }

        if (plant.getLeafMargins().contains(searchString))
        {
            matchingSectionsWithSearch.add("Leaf margin type");
            flag = true;
        }

        if (plant.getLeafSize().contains(searchString))
        {
            matchingSectionsWithSearch.add("Leaf size");
            flag = true;
        }

        if (plant.getLeafShape().contains(searchString))
        {
            matchingSectionsWithSearch.add("Leaf shape");
            flag = true;
        }
        for (String s : plant.getCommonNames())
        {
            if(s.contains(searchString)) {
                flag = true;
                matchingSectionsWithSearch.add("Common name");
            }
        }
        Log.d("Search","flag : " + flag);
        Log.d("Search","MatchingSections : " + matchingSectionsWithSearch.toString());
        return flag;
    }
}
