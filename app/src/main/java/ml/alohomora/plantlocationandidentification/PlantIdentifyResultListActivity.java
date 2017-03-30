package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PlantIdentifyResultListActivity extends AppCompatActivity {

    ArrayList<Plant> plant;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewSearchSrchResults);
        if(intent!=null){
            plant = (ArrayList<Plant>)intent.getSerializableExtra("plant");
        }

        PlantIdentifyResultViewAdapter mAdapter = new PlantIdentifyResultViewAdapter("",this,plant,new ArrayList<String>());
        listView.setAdapter(mAdapter);
    }
}
