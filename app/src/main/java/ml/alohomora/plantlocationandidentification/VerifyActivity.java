package ml.alohomora.plantlocationandidentification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class VerifyActivity extends AppCompatActivity {

    EditText editTextVerifyName = (EditText) findViewById(R.id.editTextVerifyName);
    EditText editTextVerifyCommonName = (EditText) findViewById(R.id.editTextVerifyCommonName);
    EditText editTextVerifyLeafColour = (EditText) findViewById(R.id.editTextVerifyLeafColour);
    EditText editTextVerifyLeafSize   = (EditText) findViewById(R.id.editTextVerifyLeafSize);
    EditText editTextVerifyFruitBearing = (EditText) findViewById(R.id.editTextVerifyFruitBearing);

    CheckBox checkBoxVerifyName = (CheckBox) findViewById(R.id.checkBoxVerifyName);
    CheckBox checkBoxVerifyCommonName = (CheckBox) findViewById(R.id.checkBoxVerifyCommonName);
    CheckBox checkBoxVerifyLeafColour = (CheckBox) findViewById(R.id.checkBoxVerifyLeafColour);
    CheckBox checkBoxVerifyLeafSize = (CheckBox) findViewById(R.id.checkBoxVerifyLeafSize);
    CheckBox checkBoxVerifyFruitBearing = (CheckBox) findViewById(R.id.checkBoxVerifyFruitBearing);
    CheckBox checkBoxVerifyLeafTip = (CheckBox) findViewById(R.id.checkBoxVerifyLeafTip);
    CheckBox checkBoxVerifyFruitColour = (CheckBox) findViewById(R.id.checkBoxVerifyFruititColour);
    CheckBox checkBoxVerifyLeafBase = (CheckBox) findViewById(R.id.checkBoxVerifyLeafBase);
    CheckBox checkBoxVerifyLeafShape = (CheckBox) findViewById(R.id.checkBoxVerifyLeafShape);
    CheckBox checkBoxVerifyLeafMargins = (CheckBox) findViewById(R.id.checkBoxVerifyLeafMargins);


    Spinner spinnerVerifyLeafTip = (Spinner) findViewById(R.id.spinnerVerifyLeafTip);
    Spinner spinnerVerifyLeafShape = (Spinner) findViewById(R.id.spinnerVerifyLeafShape);
    Spinner spinnerVerifyLeafMargins = (Spinner) findViewById(R.id.spinnerVerifyLeafMargins);
    Spinner spinnerVerifyLeafBase = (Spinner) findViewById(R.id.spinnerVerifyLeafBase);
    Spinner spinnerVerifyFruitColour = (Spinner) findViewById(R.id.spinnerVerifyFruitColour);

    Plant plantObj;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Button buttonVerifyReject = (Button) findViewById(R.id.buttonVerifyReject);
        buttonVerifyReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectEntireSubmission();
            }
        });
        checkBoxVerifyName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }



        public void onCheckedChanged(View v)
    {
        switch (v.getId())
        {
            case R.id.checkBoxVerifyName :
                int nameVerificationCount = plantObj.getNameVerificationCount();
                if(nameVerificationCount < 3) {
                    nameVerificationCount = nameVerificationCount + 1;
                    plantObj.setNameVerificationCount(nameVerificationCount);
                }
                if(nameVerificationCount >= 3) {
                    editTextVerifyName.setEnabled(false);
                    editTextVerifyName.setCursorVisible(false);
                    checkBoxVerifyName.setChecked(true);
                    checkBoxVerifyName.setEnabled(false);
                }
        }
    }



    void rejectEntireSubmission()
    {
        int rejectionCount = plantObj.getRejectionCount();
        rejectionCount = rejectionCount + 1;
        plantObj.setRejectionCount(rejectionCount);
    }
}
