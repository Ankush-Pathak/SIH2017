package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class IdentifyPlantActivity extends AppCompatActivity {
    RadioGroup leafShapeGroup,leafColorGroup,leafMarginGroup,leafTipGroup,leafSizeGroup,leafBaseGroup;
    RadioButton leafShapeButton,leafColorButton,leafMarginButton,leafTipButton,leafSizeButton,leafBaseButton;
    LinearLayout linearLayoutLeafShape,linearLayoutLeafMargin,linearLayoutLeafColor,linearLayoutLeafTip,linearLayoutLeafBase,linearLayoutLeafSize;

    LinkedList<String> attribute;
    ArrayList<Plant> plant;
    ArrayList<Plant> unmatchedPlant;

    int counterMax = 6;

    FirebaseDatabase firebaseDatabaseSync;
    DatabaseReference databaseReferencePlant,databaseReferenceId;

    void setup(){
        firebaseDatabaseSync = FirebaseDatabase.getInstance();
        databaseReferencePlant = firebaseDatabaseSync.getReference().child("plant");
        databaseReferenceId = firebaseDatabaseSync.getReference();

        plant = new ArrayList();
        attribute = new LinkedList();

        databaseReferencePlant.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    plant.add(ds.getValue(Plant.class));
                }
                    Log.d("debug","Plant size : " + plant.size());
                leafShapeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("shape"))
                            attribute.add("shape");
                        int radioId = leafShapeGroup.getCheckedRadioButtonId();
                        leafShapeButton = (RadioButton) findViewById(radioId);
                        String userShape = leafShapeButton.getText().toString().toLowerCase();
                        Log.d("debug",userShape);
                        linearLayoutLeafShape.setVisibility(View.GONE);
                        linearLayoutLeafMargin.setVisibility(View.VISIBLE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userShape, plant,unmatchedPlant,attribute));

                        check(plant,attribute);

                    }
                });

                leafMarginGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("margin"))
                            attribute.add("margin");
                        int radioIdMargin = leafMarginGroup.getCheckedRadioButtonId();
                        leafMarginButton = (RadioButton) findViewById(radioIdMargin);
                        String userMargin = leafMarginButton.getText().toString().toLowerCase();
                        Log.d("debug","Usermargin : " + userMargin);
                        linearLayoutLeafMargin.setVisibility(View.GONE);
                        linearLayoutLeafTip.setVisibility(View.VISIBLE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userMargin, plant,unmatchedPlant,attribute));

                        check(plant,attribute);
                    }
                });
                leafTipGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("tip"))
                            attribute.add("tip");

                        int radioId = leafTipGroup.getCheckedRadioButtonId();
                        leafTipButton = (RadioButton) findViewById(radioId);
                        String userTip = leafTipButton.getText().toString().toLowerCase();

                        linearLayoutLeafTip.setVisibility(View.GONE);
                        linearLayoutLeafBase.setVisibility(View.VISIBLE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userTip, plant,unmatchedPlant,attribute));

                        check(plant,attribute);
                    }
                });

                leafBaseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("base"))
                            attribute.add("base");

                        int radioId = leafBaseGroup.getCheckedRadioButtonId();
                        leafBaseButton = (RadioButton) findViewById(radioId);
                        String userBase = leafBaseButton.getText().toString().toLowerCase();

                        linearLayoutLeafBase.setVisibility(View.GONE);
                        linearLayoutLeafSize.setVisibility(View.VISIBLE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userBase, plant,unmatchedPlant,attribute));

                        check(plant,attribute);
                    }
                });

                leafSizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("size"))
                            attribute.add("size");

                        int radioId = leafSizeGroup.getCheckedRadioButtonId();
                        leafSizeButton = (RadioButton) findViewById(radioId);
                        String userSize = leafSizeButton.getText().toString().toLowerCase();

                        linearLayoutLeafSize.setVisibility(View.GONE);
                        linearLayoutLeafColor.setVisibility(View.VISIBLE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userSize, plant,unmatchedPlant,attribute));

                        check(plant,attribute);
                    }
                });
                leafColorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(!attribute.contains("color"))
                            attribute.add("color");

                        int radioId = leafColorGroup.getCheckedRadioButtonId();
                        leafColorButton = (RadioButton) findViewById(radioId);
                        String userColor = leafColorButton.getText().toString().toLowerCase();

                        linearLayoutLeafColor.setVisibility(View.GONE);
                        unmatchedPlant = new ArrayList();
                        plant.removeAll(search(userColor, plant,unmatchedPlant,attribute));
                        check(plant,attribute);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        leafShapeGroup = (RadioGroup)findViewById(R.id.leafShapeGroup);
        leafColorGroup = (RadioGroup)findViewById(R.id.leafColorGroup);
        leafMarginGroup = (RadioGroup)findViewById(R.id.leafMarginGroup);
        leafTipGroup = (RadioGroup)findViewById(R.id.leafTipGroup);
        leafSizeGroup = (RadioGroup)findViewById(R.id.leafSizeGroup);
        leafBaseGroup = (RadioGroup)findViewById(R.id.leafBaseGroup);

        linearLayoutLeafShape =(LinearLayout)findViewById(R.id.linearLayoutLeafShape);
        linearLayoutLeafColor =(LinearLayout)findViewById(R.id.linearLayoutLeafColor);
        linearLayoutLeafMargin =(LinearLayout)findViewById(R.id.linearLayoutLeafMargin);
        linearLayoutLeafTip =(LinearLayout)findViewById(R.id.linearLayoutLeafTip);
        linearLayoutLeafBase =(LinearLayout)findViewById(R.id.linearLayoutLeafBase);
        linearLayoutLeafSize =(LinearLayout)findViewById(R.id.linearLayoutLeafSize);

        linearLayoutLeafShape.setVisibility(View.VISIBLE);
        linearLayoutLeafColor.setVisibility(View.INVISIBLE);
        linearLayoutLeafMargin.setVisibility(View.INVISIBLE);
        linearLayoutLeafSize.setVisibility(View.INVISIBLE);
        linearLayoutLeafBase.setVisibility(View.INVISIBLE);
        linearLayoutLeafTip.setVisibility(View.INVISIBLE);
    }
    void check(ArrayList<Plant> p,LinkedList<String> attr){
        if(p.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT);
            toast.show();
            finish();
            //TODO call to last activity
        }
        if(p.size()==1) {
            //TODO display the dialog box
            Intent intent = new Intent(this, ShowPlantActivity.class);
            intent.putExtra("plant",p.get(0));
            startActivity(intent);
            finish();
        }
        if(attr.size() == counterMax) {
            Intent intent;
            if(p.size()==1) {
                //TODO display the dialog box
                intent = new Intent(this, ShowPlantActivity.class);
                intent.putExtra("plant",p.get(0));
                startActivity(intent);
                finish();
            }
            else{
                //TODO print the list of matched plants
                intent = new Intent(this,PlantIdentifyResultListActivity.class);
                intent.putExtra("plant",p);
                startActivity(intent);
                finish();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_plant);

        setup();
        /*
        * Order in which search is carried out is
        *   1.Shape
        *   2.Margin
        *   3.Tip
        *   4.Base
        *   5.Size
        *   6.Color
        * */
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(IdentifyPlantActivity.this , MainActivity.class);
        startActivity(intent);
    }


    ArrayList<Plant> search(String userSearch,ArrayList<Plant> dataPlant,ArrayList<Plant> unmatchedPlant  ,LinkedList<String> attribute) {
        if(attribute.getLast().equals("shape")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafShape().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                }
                Log.d("debug",p.getLeafShape() + " and " + userSearch);
            }
            Log.d("debug","Unmatched size : "+unmatchedPlant.size());
        }
        else if(attribute.getLast().equals("margin")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafMargins().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                    Log.d("debug","Adding this to unmatched : " + p.getLeafShape());
                }
            }
            Log.d("debug","Unmatched size : "+unmatchedPlant.size());
        }
        else if(attribute.getLast().equals("color")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafColor().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                }
            }
            Log.d("debug","Unmatched size : "+unmatchedPlant.size());
        }

        else if(attribute.getLast().equals("tip")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafTip().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                }
            }
        }
        else if(attribute.getLast().equals("base")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafBase().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                }
            }
        }
        else if(attribute.getLast().equals("size")) {
            for (Plant p : dataPlant) {
                if (!p.getLeafSize().toLowerCase().equals(userSearch)) {
                    unmatchedPlant.add(p);
                }
            }
        }
        Log.d("debug","Unmatched size : "+unmatchedPlant.size());
        return unmatchedPlant;
    }
}
