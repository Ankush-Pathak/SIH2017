package ml.alohomora.plantlocationandidentification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;


public class VerifyActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    EditText editTextVerifyName = (EditText) findViewById(R.id.editTextVerifyName);
    EditText editTextVerifyCommonName = (EditText) findViewById(R.id.editTextVerifyCommonName);
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
    Spinner spinnerVerifyLeafColour = (Spinner) findViewById(R.id.spinnerVerifyLeafColour);
    Spinner spinnerVerifyLeafSize = (Spinner) findViewById(R.id.spinnerVerifyLeafSize);


    Plant plantObj;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        ArrayAdapter<CharSequence> adapterLeafShape = ArrayAdapter.createFromResource(this, R.array.leaf_shape, android.R.layout.simple_spinner_dropdown_item);
        adapterLeafShape.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVerifyLeafShape.setAdapter(adapterLeafShape);


        editTextVerifyFruitBearing.addTextChangedListener(VerifyActivity.this);
        editTextVerifyName.addTextChangedListener(VerifyActivity.this);
        editTextVerifyCommonName.addTextChangedListener(VerifyActivity.this);




        checkBoxVerifyLeafTip.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyLeafBase.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyName.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyCommonName.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyLeafColour.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyFruitBearing.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyLeafMargins.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyLeafShape.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyLeafSize.setOnClickListener(VerifyActivity.this);
        checkBoxVerifyFruitColour.setOnClickListener(VerifyActivity.this);



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

        public void afterChanged(View vEditText)
        {
            switch(vEditText.getId())
            {
                case R.id.editTextVerifyName :
                    String editTextVerifyNameText = editTextVerifyName.getText().toString();
                    String plantObjNameText = plantObj.getName();
                    if(editTextVerifyNameText.equalsIgnoreCase(plantObjNameText))
                    {
                        int nameVerificationCount = plantObj.getNameVerificationCount();
                        nameVerificationCount++;
                        plantObj.setNameVerificationCount(nameVerificationCount);
                    }
                    else
                    {
                        plantObj.setNameVerificationCount(0);
                        plantObj.setName(editTextVerifyNameText);
                    }

                    break;

                case R.id.editTextVerifyCommonName :
                    String editTextVerifyCommonNameText = editTextVerifyCommonName.getText().toString();
                    String plantObjCommonNameText[] = plantObj.getCommonNames();
            }
        }

        public void onClick(View v)
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
                    checkBoxVerifyName.setClickable(false);
                }
                break;

            case R.id.checkBoxVerifyCommonName :
                int commonNameVerificationCount = plantObj.getCommonNameVerificationCount();
                if (commonNameVerificationCount < 3) {
                    commonNameVerificationCount = commonNameVerificationCount + 1;
                    plantObj.setCommonNameVerificationCount(commonNameVerificationCount);
                }

                if(commonNameVerificationCount => 3 )
            {
                editTextVerifyCommonName.setEnabled(false);
                editTextVerifyCommonName.setCursorVisible(false);
                checkBoxVerifyCommonName.setChecked(true);
                checkBoxVerifyCommonName.setEnabled(false);
                checkBoxVerifyCommonName.setClickable(false);
            }

            break;

            case R.id.checkBoxVerifyFruitBearing :
                int fruitBearingVerificationCount = plantObj.getIsFruitBearingVerificationCount();
                if(fruitBearingVerificationCount < 3) {
                fruitBearingVerificationCount++;
                    plantObj.setIsFruitBearingVerificationCount(fruitBearingVerificationCount);
                }

                if(fruitBearingVerificationCount => 3)
            {
                editTextVerifyFruitBearing.setEnabled(false);
                editTextVerifyFruitBearing.setCursorVisible(false);
                checkBoxVerifyFruitBearing.setChecked(true);
                checkBoxVerifyFruitBearing.setEnabled(false);
                checkBoxVerifyFruitBearing.setClickable(false);
            }

            break;


            case R.id.checkBoxVerifyLeafColour :
                int leafColourVerificationCount = plantObj.getLeafColorVerificationCount();

                if(leafColourVerificationCount < 3) {

                    leafColourVerificationCount++;
                    plantObj.setLeafColorVerificationCount(leafColourVerificationCount);

                }

                if(leafColourVerificationCount >= 3)
                {
                    spinnerVerifyLeafColour.setEnabled(false);
                    spinnerVerifyLeafColour.setClickable(false);
                    checkBoxVerifyLeafColour.setChecked(true);
                    checkBoxVerifyLeafColour.setEnabled(false);
                    checkBoxVerifyLeafColour.setClickable(false);
                }

                break;

            case R.id.checkBoxVerifyLeafShape :
                int leafShapeVerificationCount = plantObj.getLeafShapeVerificationCount();
                if(leafShapeVerificationCount < 3)
                {
                    leafShapeVerificationCount++;
                    plantObj.setLeafShapeVerificationCount(leafShapeVerificationCount);
                }

                if(leafShapeVerificationCount >= 3)
                {
                    spinnerVerifyLeafShape.setEnabled(false);
                    spinnerVerifyLeafShape.setClickable(false);
                    checkBoxVerifyLeafShape.setChecked(true);
                    checkBoxVerifyLeafShape.setEnabled(false);
                    checkBoxVerifyLeafShape.setClickable(false);

                }

                break;

            case R.id.checkBoxVerifyLeafSize :
                int leafSizeVerificationCount = plantObj.getLeafSizeVerificationCount();

                if(leafSizeVerificationCount < 3)
                {
                    leafSizeVerificationCount++;
                    plantObj.setLeafSizeVerificationCount(leafSizeVerificationCount);
                }

                if(leafSizeVerificationCount >= 3)
                {
                    spinnerVerifyLeafSize.setEnabled(false);
                    spinnerVerifyLeafSize.setClickable(false);
                    checkBoxVerifyLeafSize.setChecked(true);
                    checkBoxVerifyLeafSize.setEnabled(false);
                    checkBoxVerifyLeafSize.setClickable(false);
                }

                break;;

            case R.id.checkBoxVerifyLeafBase :
                int leafBaseVerificationCount = plantObj.getLeafBaseVerificationCount();

                if(leafBaseVerificationCount < 3)
                {
                    leafBaseVerificationCount++;
                    plantObj.setLeafBaseVerificationCount(leafBaseVerificationCount);
                }

                if(leafBaseVerificationCount >= 3)
                {
                    spinnerVerifyLeafBase.setEnabled(false);
                    spinnerVerifyLeafBase.setClickable(false);
                    checkBoxVerifyLeafBase.setChecked(true);
                    checkBoxVerifyLeafBase.setEnabled(false);
                    checkBoxVerifyLeafBase.setClickable(false);
                }

                break;

            case R.id.checkBoxVerifyLeafTip :
                int leafTipVerificationCount = plantObj.getLeafTipVerificationCount();

                if(leafTipVerificationCount < 3)
                {
                    leafTipVerificationCount++;
                    plantObj.setLeafTipVerificationCount(leafTipVerificationCount);
                }

                if(leafTipVerificationCount >= 3)
                {
                    spinnerVerifyLeafTip.setEnabled(false);
                    spinnerVerifyLeafTip.setClickable(false);
                    checkBoxVerifyLeafTip.setChecked(true);
                    checkBoxVerifyLeafTip.setEnabled(false);
                    checkBoxVerifyLeafTip.setClickable(false);
                }

                break;



            case R.id.checkBoxVerifyLeafMargins :
                int leafMarginsVerificationCount = plantObj.getLeafMarginsVerificationCount();

                if(leafMarginsVerificationCount < 3)
                {
                    leafMarginsVerificationCount++;
                    plantObj.setLeafMarginsVerificationCount(leafMarginsVerificationCount);
                }

                if(leafMarginsVerificationCount >= 3)
                {
                    spinnerVerifyLeafBase.setEnabled(false);
                    spinnerVerifyLeafBase.setClickable(false);
                    checkBoxVerifyLeafBase.setChecked(true);
                    checkBoxVerifyLeafBase.setEnabled(false);
                    checkBoxVerifyLeafBase.setClickable(false);
                }

                break;




        }
    }



    void checkIfAllVerified()
    {
        if(plantObj.getNameVerificationCount() >=3 &&
           plantObj.getCommonNameVerificationCount() >= 3 &&
           plantObj.getLeafColorVerificationCount() >= 3 &&
           plantObj.getLeafShapeVerificationCount() >= 3 &&
           plantObj.getLeafTipVerificationCount() >= 3 &&
           plantObj.getLeafBaseVerificationCount() >=3 &&
           plantObj.getLeafMarginsVerificationCount() >= 3 &&
           plantObj.getFruitColorVerificationCouunt() >= 3 &&
           plantObj.getIsFruitBearingVerificationCount() >= 3 &&
                )
        {
            plantObj.setFullyVerfied(true);
        }
    }

    void rejectEntireSubmission()
    {
        int rejectionCount = plantObj.getRejectionCount();
        rejectionCount = rejectionCount + 1;
        plantObj.setRejectionCount(rejectionCount);
    }
}
