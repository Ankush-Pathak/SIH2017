package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VerifyActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher,AdapterView.OnItemSelectedListener{


    private View view;
    public VerifyActivity()
    {

    }
    public VerifyActivity(View view)
    {
        this.view = view;
    }

    EditText editTextVerifyName;
    EditText editTextVerifyCommonName;
    EditText editTextVerifyFruitBearing;
    EditText editTextVerifyComments;
    EditText editTextVerifyVerified;
    EditText editTextVerifyNotVerified;

    CheckBox checkBoxVerifyName;
    CheckBox checkBoxVerifyCommonName;
    CheckBox checkBoxVerifyLeafColour;
    CheckBox checkBoxVerifyLeafSize;
    CheckBox checkBoxVerifyFruitBearing;
    CheckBox checkBoxVerifyLeafTip;
    CheckBox checkBoxVerifyFruitColour;
    CheckBox checkBoxVerifyLeafBase;
    CheckBox checkBoxVerifyLeafShape;
    CheckBox checkBoxVerifyLeafMargins;
    CheckBox checkBoxVerifyComments;

    ScrollView scrollView1;

    Spinner spinnerVerifyLeafTip;
    Spinner spinnerVerifyLeafShape;
    Spinner spinnerVerifyLeafMargins;
    Spinner spinnerVerifyLeafBase;
    Spinner spinnerVerifyFruitColour;
    Spinner spinnerVerifyLeafColour;
    Spinner spinnerVerifyLeafSize;

    ImageView imageViewDisplayImage;

    String iD;
    Plant plantObj;
    Plant plantOriginalObj;

    User user;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
        Intent intent = getIntent();
        if(intent != null)
        {
            plantObj = (Plant)intent.getSerializableExtra("plant");
            iD = intent.getStringExtra("iD");

        }
        plantOriginalObj = plantObj;

        editTextVerifyName = (EditText) findViewById(R.id.editTextVerifyName);
        editTextVerifyCommonName = (EditText) findViewById(R.id.editTextVerifyCommonName);
        editTextVerifyFruitBearing = (EditText) findViewById(R.id.editTextVerifyFruitBearing);
        editTextVerifyComments = (EditText) findViewById(R.id.editTextVerifyComments);
        editTextVerifyVerified = (EditText) findViewById(R.id.editTextVerifyVerified);
        editTextVerifyNotVerified = (EditText) findViewById(R.id.editTextVerifyNotVerified);



        editTextVerifyName.setText(plantObj.getName());
        editTextVerifyCommonName.setText(convertListToString(plantObj.getCommonNames()));
        editTextVerifyFruitBearing.setText(converBoolToString(plantObj.isFruitBearing()));
        editTextVerifyComments.setText(plantObj.getComments());


        editTextVerifyNotVerified.setClickable(false);
        editTextVerifyNotVerified.setEnabled(false);
        editTextVerifyNotVerified.setCursorVisible(false);

        editTextVerifyVerified.setClickable(false);
        editTextVerifyVerified.setEnabled(false);
        editTextVerifyVerified.setCursorVisible(false);



        scrollView1 = (ScrollView) findViewById(R.id.scrollView);

        checkBoxVerifyName = (CheckBox) findViewById(R.id.checkBoxVerifyName);
        checkBoxVerifyCommonName = (CheckBox) findViewById(R.id.checkBoxVerifyCommonName);
        checkBoxVerifyLeafColour = (CheckBox) findViewById(R.id.checkBoxVerifyLeafColour);
        checkBoxVerifyLeafSize = (CheckBox) findViewById(R.id.checkBoxVerifyLeafSize);
        checkBoxVerifyFruitBearing = (CheckBox) findViewById(R.id.checkBoxVerifyFruitBearing);
        checkBoxVerifyLeafTip = (CheckBox) findViewById(R.id.checkBoxVerifyLeafTip);
        checkBoxVerifyFruitColour = (CheckBox) findViewById(R.id.checkBoxVerifyFruititColour);
        checkBoxVerifyLeafBase = (CheckBox) findViewById(R.id.checkBoxVerifyLeafBase);
        checkBoxVerifyLeafShape = (CheckBox) findViewById(R.id.checkBoxVerifyLeafShape);
        checkBoxVerifyLeafMargins = (CheckBox) findViewById(R.id.checkBoxVerifyLeafMargins);
        checkBoxVerifyComments = (CheckBox) findViewById(R.id.checkBoxVerifyComments);

        spinnerVerifyLeafTip = (Spinner) findViewById(R.id.spinnerVerifyLeafTip);
        spinnerVerifyLeafShape = (Spinner) findViewById(R.id.spinnerVerifyLeafShape);
        spinnerVerifyLeafMargins = (Spinner) findViewById(R.id.spinnerVerifyLeafMargins);
        spinnerVerifyLeafBase = (Spinner) findViewById(R.id.spinnerVerifyLeafBase);
        spinnerVerifyFruitColour = (Spinner) findViewById(R.id.spinnerVerifyFruitColour);
        spinnerVerifyLeafColour = (Spinner) findViewById(R.id.spinnerVerifyLeafColour);
        spinnerVerifyLeafSize = (Spinner) findViewById(R.id.spinnerVerifyLeafSize);


        spinnerVerifyLeafColour.setSelection(returnSpinnerPosition(spinnerVerifyLeafColour,plantObj.getLeafColor()));
        spinnerVerifyLeafBase.setSelection(returnSpinnerPosition(spinnerVerifyLeafBase,plantObj.getLeafBase()));
        spinnerVerifyLeafShape.setSelection(returnSpinnerPosition(spinnerVerifyLeafShape,plantObj.getFruitShape()));
        spinnerVerifyLeafTip.setSelection(returnSpinnerPosition(spinnerVerifyLeafTip,plantObj.getLeafTip()));
        spinnerVerifyLeafMargins.setSelection(returnSpinnerPosition(spinnerVerifyLeafMargins,plantObj.getLeafMargins()));
        spinnerVerifyLeafSize.setSelection(returnSpinnerPosition(spinnerVerifyLeafSize,plantObj.getLeafSize()));
       // spinnerVerifyFruitColour.setSelection(returnSpinnerPosition(spinnerVerifyFruitColour,plantObj.getFruitColor()));



        imageViewDisplayImage = (ImageView) findViewById(R.id.imageViewDisplayImage);
        Glide.with(VerifyActivity.this).load(plantObj.getImageLeafRef().get(0)).into(imageViewDisplayImage);


       /* ArrayAdapter<CharSequence> adapterLeafShape = ArrayAdapter.createFromResource(this, R.array.leaf_shape, android.R.layout.simple_spinner_dropdown_item);
        adapterLeafShape.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVerifyLeafShape.setAdapter(adapterLeafShape);*/



        checkVerificationCount(plantObj.getNameVerificationCount(),checkBoxVerifyName);
        checkVerificationCount(plantObj.getCommonNameVerificationCount(),checkBoxVerifyCommonName);
        checkVerificationCount(plantObj.getLeafBaseVerificationCount(),checkBoxVerifyLeafBase);
        checkVerificationCount(plantObj.getFruitColorVerificationCount(),checkBoxVerifyFruitColour);
        checkVerificationCount(plantObj.getLeafColorVerificationCount(),checkBoxVerifyLeafColour);
        checkVerificationCount(plantObj.getLeafMarginsVerificationCount(),checkBoxVerifyLeafMargins);
        checkVerificationCount(plantObj.getLeafShapeVerificationCount(),checkBoxVerifyLeafShape);
        checkVerificationCount(plantObj.getLeafTipVerificationCount(),checkBoxVerifyLeafTip);
        checkVerificationCount(plantObj.getLeafSizeVerificationCount(),checkBoxVerifyLeafSize);
        checkVerificationCount(plantObj.getIsFruitBearingVerificationCount(),checkBoxVerifyFruitBearing);
        checkVerificationCount(plantObj.getCommentsVerficationCount(), checkBoxVerifyComments);



        checkVerificationCountEditText(plantObj.getNameVerificationCount(), editTextVerifyName);
        checkVerificationCountEditText(plantObj.getCommonNameVerificationCount(), editTextVerifyCommonName);
        checkVerificationCountEditText(plantObj.getIsFruitBearingVerificationCount(), editTextVerifyFruitBearing);
        checkVerificationCountEditText(plantObj.getCommentsVerficationCount(), editTextVerifyComments);



        checkVerificationCountSpinner(plantObj.getLeafMarginsVerificationCount(), spinnerVerifyLeafMargins);
        checkVerificationCountSpinner(plantObj.getLeafSizeVerificationCount(), spinnerVerifyLeafSize);
        //checkVerificationCountSpinner(plantObj.getFruitColorVerificationCount(), spinnerVerifyFruitColour);
        checkVerificationCountSpinner(plantObj.getLeafShapeVerificationCount(),spinnerVerifyLeafShape);
        checkVerificationCountSpinner(plantObj.getLeafBaseVerificationCount(), spinnerVerifyLeafBase);
        checkVerificationCountSpinner(plantObj.getLeafTipVerificationCount(), spinnerVerifyLeafTip);
        checkVerificationCountSpinner(plantObj.getLeafColorVerificationCount(), spinnerVerifyLeafColour);



        Resources.Theme theme = getTheme();
        //spinnerVerifyLeafColour.getBackground().setColorFilter(getResources().getColor(Color.BLACK, getTheme()), PorterDuff.Mode.SRC_ATOP);
        spinnerVerifyLeafColour.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark, theme), PorterDuff.Mode.SRC_ATOP);


        editTextVerifyFruitBearing.addTextChangedListener(VerifyActivity.this);
        editTextVerifyName.addTextChangedListener(VerifyActivity.this);
        editTextVerifyCommonName.addTextChangedListener(VerifyActivity.this);
        editTextVerifyComments.addTextChangedListener(VerifyActivity.this);


        /*spinnerVerifyLeafShape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerVerifyLeafTip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/



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
        checkBoxVerifyComments.setOnClickListener(VerifyActivity.this);

        /*SharedPreferences settings = getSharedPreferences("logedinUser",0);
        String mailID = settings.getString("email",null);

        ArrayList<String> dummyList = user.getContributTo();
        dummyList.add(mailID);

        user.setContributTo(dummyList);*/


        Button buttonVerifySubmitChanges = (Button) findViewById(R.id.buttonVerifySubmitChanges);
        buttonVerifySubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptChanges();
            }
        });


        Button buttonCancelChanges = (Button) findViewById(R.id.buttonVerifyCancelChanges) ;
        buttonCancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelChanges();
            }
        });


        Button buttonVerifyReject = (Button) findViewById(R.id.buttonVerifyReject);
        buttonVerifyReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectEntireSubmission();
            }
        });

        checkIfAllVerified();


    }
        @Override
        public void afterTextChanged(Editable editable)
        {
            switch(view.getId())
            {
                case R.id.editTextVerifyName :
                    String editTextVerifyNameText = editTextVerifyName.getText().toString();
                    String plantOriginalObjNameText = plantOriginalObj.getName();


                    if(editTextVerifyNameText.equalsIgnoreCase(plantOriginalObjNameText))
                    {
                        plantObj.setNameVerificationCount(plantOriginalObj.getNameVerificationCount());

                        if(plantObj.getNameVerificationCount() >= 3)
                        {
                            editTextVerifyName.setEnabled(false);
                        }

                        break;
                    }


                    else
                    {
                        plantObj.setNameVerificationCount(0);
                        plantObj.setName(editTextVerifyNameText);
                    }

                    break;

                case R.id.editTextVerifyCommonName :
                    String editTextVerifyCommonNameText = editTextVerifyCommonName.getText().toString();
                    String delimiter = "\n";
                    String ediTextCommonName[] = editTextVerifyCommonNameText.split(delimiter);

                    List<String> editTextItemsList = Arrays.asList(ediTextCommonName);


                    List<String> dummyCommonNameString = plantOriginalObj.getCommonNames();


                        if(editTextItemsList == dummyCommonNameString)
                        {
                            plantObj.setCommonNameVerificationCount(plantOriginalObj.getCommonNameVerificationCount());

                            if(plantObj.getCommonNameVerificationCount() >= 3)
                            {
                                editTextVerifyCommonName.setEnabled(false);
                            }
                           break;
                        }

                        else
                        {
                            plantObj.setCommonNames(editTextItemsList);
                        }


                    break;


                case R.id.editTextVerifyFruitBearing :
                    String editTextVerifyFruitBearingText = editTextVerifyFruitBearing.getText().toString();
                    boolean isFruitBearing= plantOriginalObj.isFruitBearing();

                    if(((isFruitBearing == true && editTextVerifyFruitBearingText.equalsIgnoreCase("Yes")) || (isFruitBearing==false && editTextVerifyFruitBearingText.equalsIgnoreCase("No") )))
                    {
                      plantObj.setIsFruitBearingVerificationCount(plantOriginalObj.getIsFruitBearingVerificationCount());

                        if(plantObj.getIsFruitBearingVerificationCount() >= 3)
                        {
                            editTextVerifyFruitBearing.setEnabled(false);
                        }
                        break;

                    }

                    else
                {
                    plantObj.setIsFruitBearingVerificationCount(0);

                    plantObj.setFruitBearing(!isFruitBearing);
                }

                break;


                case R.id.editTextVerifyComments :

                    String editTextVerifyCommentsText = editTextVerifyComments.getText().toString();
                    String plantObjCommentsText = plantObj.getComments();

                    if(editTextVerifyCommentsText == plantObjCommentsText)
                    {

                        plantObj.setCommentsVerficationCount(plantOriginalObj.getCommentsVerficationCount());

                        if(plantObj.getCommentsVerficationCount() >= 3)
                        {
                            editTextVerifyComments.setEnabled(false);

                        }
                        break;
                    }

                    else
                    {
                        plantObj.setCommentsVerficationCount(0);
                        plantObj.setComments(editTextVerifyCommentsText);
                    }

            }
        }




        public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.checkBoxVerifyName :

                int nameVerificationCount = plantObj.getNameVerificationCount();


                if (nameVerificationCount < 3 && checkBoxVerifyName.isChecked()) {
                    nameVerificationCount = nameVerificationCount + 1;
                    plantObj.setNameVerificationCount(nameVerificationCount);
                }

                else if(!checkBoxVerifyName.isChecked())
                {
                    nameVerificationCount--;
                    plantObj.setNameVerificationCount(nameVerificationCount);

                }


                if (nameVerificationCount >= 3)
                {
                    editTextVerifyName.setEnabled(false);
                    editTextVerifyName.setCursorVisible(false);
                    checkBoxVerifyName.setChecked(true);
                    checkBoxVerifyName.setEnabled(false);
                    checkBoxVerifyName.setClickable(false);
                }


                break;

            case R.id.checkBoxVerifyCommonName :
                int commonNameVerificationCount = plantObj.getCommonNameVerificationCount();

                if (commonNameVerificationCount < 3 && checkBoxVerifyCommonName.isChecked())
                {
                    commonNameVerificationCount = commonNameVerificationCount + 1;
                    plantObj.setCommonNameVerificationCount(commonNameVerificationCount);
                }


                else if(!checkBoxVerifyCommonName.isChecked())
                {
                    commonNameVerificationCount--;
                    plantObj.setNameVerificationCount(commonNameVerificationCount);
                }



                if(commonNameVerificationCount >= 3 )
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

                if(fruitBearingVerificationCount < 3 && checkBoxVerifyFruitBearing.isChecked())
                {
                    fruitBearingVerificationCount++;
                    plantObj.setIsFruitBearingVerificationCount(fruitBearingVerificationCount);
                }

                else if(!checkBoxVerifyFruitBearing.isChecked())
                {
                    fruitBearingVerificationCount--;
                    plantObj.setIsFruitBearingVerificationCount(fruitBearingVerificationCount);
                }


                if(fruitBearingVerificationCount >= 3)
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

                if(leafColourVerificationCount < 3 && checkBoxVerifyLeafColour.isChecked())
                {

                    leafColourVerificationCount++;
                    plantObj.setLeafColorVerificationCount(leafColourVerificationCount);

                }

                else if(!checkBoxVerifyLeafColour.isChecked())
                {
                    leafColourVerificationCount--;
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

                if(leafShapeVerificationCount < 3 && checkBoxVerifyLeafShape.isChecked())
                {
                    leafShapeVerificationCount++;
                    plantObj.setLeafShapeVerificationCount(leafShapeVerificationCount);
                }


                else if (!checkBoxVerifyLeafShape.isChecked())
                {
                    leafShapeVerificationCount--;
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

                if(leafSizeVerificationCount < 3 && checkBoxVerifyLeafSize.isChecked())
                {
                    leafSizeVerificationCount++;
                    plantObj.setLeafSizeVerificationCount(leafSizeVerificationCount);
                }

                else if (!checkBoxVerifyLeafSize.isChecked())
                {
                    leafSizeVerificationCount--;
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

                break;

            case R.id.checkBoxVerifyLeafBase :

                int leafBaseVerificationCount = plantObj.getLeafBaseVerificationCount();

                if(leafBaseVerificationCount < 3 && checkBoxVerifyLeafBase.isChecked())
                {
                    leafBaseVerificationCount++;
                    plantObj.setLeafBaseVerificationCount(leafBaseVerificationCount);
                }

                else if(!checkBoxVerifyLeafBase.isChecked())
                {
                    leafBaseVerificationCount--;
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

                if(leafTipVerificationCount < 3 && checkBoxVerifyLeafTip.isChecked())
                {
                    leafTipVerificationCount++;
                    plantObj.setLeafTipVerificationCount(leafTipVerificationCount);
                }


                else if(!checkBoxVerifyLeafTip.isChecked())
                {
                    leafTipVerificationCount--;
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

                if(leafMarginsVerificationCount < 3 && checkBoxVerifyLeafMargins.isChecked())
                {
                    leafMarginsVerificationCount++;
                    plantObj.setLeafMarginsVerificationCount(leafMarginsVerificationCount);
                }


                else if(!checkBoxVerifyLeafMargins.isChecked())
                {
                    leafMarginsVerificationCount--;
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


                case R.id.checkBoxVerifyComments :

                    int commentsVerificationCount = plantObj.getCommentsVerficationCount();

                    if(commentsVerificationCount < 3 && checkBoxVerifyComments.isChecked())
                    {
                        commentsVerificationCount++;
                        plantObj.setCommentsVerficationCount(commentsVerificationCount);
                    }

                    else if(!checkBoxVerifyComments.isChecked())
                {
                    commentsVerificationCount--;
                    plantObj.setCommentsVerficationCount(commentsVerificationCount);
                }

                if(commentsVerificationCount >=3)
                {
                    editTextVerifyComments.setEnabled(false);
                    editTextVerifyComments.setClickable(false);
                    checkBoxVerifyComments.setEnabled(false);
                    checkBoxVerifyComments.setChecked(true);
                    checkBoxVerifyComments.setClickable(false);

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
           plantObj.getFruitColorVerificationCount() >= 3 &&
           plantObj.getIsFruitBearingVerificationCount() >= 3
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
        databaseReference.child("plant").child(iD).setValue(plantObj);


        databaseReference.child("plant").child(iD).setValue(plantObj);
        Intent intent = new Intent(this, VerifyEntriesActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch(view.getId())
        {
            case R.id.spinnerVerifyLeafShape :
                String spinnerVerifyLeafShapeItem = spinnerVerifyLeafShape.getSelectedItem().toString();
                String plantOriginalObjleafShapeText = plantOriginalObj.getLeafShape();

                if(spinnerVerifyLeafShapeItem == plantOriginalObjleafShapeText)
                {
                    plantObj.setLeafShapeVerificationCount(plantOriginalObj.getLeafShapeVerificationCount());

                    if(plantObj.getLeafShapeVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafShape.setEnabled(false);
                    }
                    break;

                }

                else
                {
                    plantObj.setLeafShapeVerificationCount(0);
                    plantObj.setLeafShape(spinnerVerifyLeafShapeItem);
                }


                break;




            case R.id.spinnerVerifyLeafSize :
                String spinnerVerifyLeafSizeItem = spinnerVerifyLeafSize.getSelectedItem().toString();
                String plantOriginalObjleafSizeText = plantOriginalObj.getLeafSize();

                if(spinnerVerifyLeafSizeItem == plantOriginalObjleafSizeText)
                {
                    plantObj.setLeafSizeVerificationCount(plantOriginalObj.getLeafSizeVerificationCount());

                    if(plantObj.getLeafSizeVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafSize.setEnabled(false);
                    }
                    break;
                }

                else
                {
                    plantObj.setLeafSizeVerificationCount(0);
                    plantObj.setLeafSize(spinnerVerifyLeafSizeItem);
                }

                break;


            case R.id.spinnerVerifyLeafColour :


                String spinnerVerifyLeafColourItem = spinnerVerifyLeafColour.getSelectedItem().toString();
                String plantObjLeafColourText = plantObj.getLeafColor();

                if(spinnerVerifyLeafColourItem == plantObjLeafColourText)
                {

                    plantObj.setLeafColorVerificationCount(plantOriginalObj.getLeafColorVerificationCount());


                    if(plantObj.getLeafColorVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafColour.setEnabled(false);
                    }

                    break;
                }

                else
                {
                    plantObj.setLeafColorVerificationCount(0);
                    plantObj.setLeafColor(spinnerVerifyLeafColourItem);
                }


                break;


            case R.id.spinnerVerifyLeafMargins :

                String spinnerVerifyLeafMarginsItem = spinnerVerifyLeafMargins.getSelectedItem().toString();
                String plantObjLeafMarginsText = plantObj.getLeafMargins();


                if(spinnerVerifyLeafMarginsItem == plantObjLeafMarginsText)
                {
                   plantObj.setLeafMarginsVerificationCount(plantOriginalObj.getLeafMarginsVerificationCount());

                    if(plantObj.getLeafMarginsVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafMargins.setEnabled(false);
                    }


                   break;
                }

                else
                {
                    plantObj.setLeafMarginsVerificationCount(0);
                    plantObj.setLeafMargins(spinnerVerifyLeafMarginsItem);
                }

                break;


            case R.id.spinnerVerifyLeafTip :

                String spinnerVerifyLeafTipItem = spinnerVerifyLeafTip.getSelectedItem().toString();
                String plantObjLeafTipText = plantObj.getLeafTip();


                if(spinnerVerifyLeafTipItem == plantObjLeafTipText)
                {

                    plantObj.setLeafTipVerificationCount(plantOriginalObj.getLeafTipVerificationCount());

                    if(plantObj.getLeafTipVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafTip.setEnabled(false);
                    }

                    break;
                }


                else
                {
                    plantObj.setLeafTipVerificationCount(0);
                    plantObj.setLeafTip(spinnerVerifyLeafTipItem);
                }


                break;

            case R.id.spinnerVerifyLeafBase :
                String spinnerVerifyLeafBaseItem = spinnerVerifyLeafBase.getSelectedItem().toString();
                String plantObjLeafBaseText = plantObj.getLeafBase();




                if(spinnerVerifyLeafBaseItem == plantObjLeafBaseText)
                {

                    plantObj.setLeafBaseVerificationCount(plantOriginalObj.getLeafBaseVerificationCount());

                    if(plantObj.getLeafBaseVerificationCount() >= 3)
                    {
                        spinnerVerifyLeafBase.setEnabled(false);
                    }

                    break;
                }


                else
                {
                    plantObj.setLeafBaseVerificationCount(0);
                    plantObj.setLeafBase(spinnerVerifyLeafBaseItem);
                }

                break;


            case R.id.spinnerVerifyFruitColour :

                String spinnerVerifyFruitColourItem = spinnerVerifyFruitColour.getSelectedItem().toString();
                String plantObjFruitColourText = plantObj.getFruitColor();


                if(spinnerVerifyFruitColourItem == plantObjFruitColourText)
                {

                    plantObj.setFruitColorVerificationCount(plantOriginalObj.getFruitColorVerificationCount());

                    if(plantObj.getFruitColorVerificationCount() >= 3)
                    {
                        spinnerVerifyFruitColour.setEnabled(false);
                    }


                    break;
                }


                else
                {
                    plantObj.setFruitColorVerificationCount(0);
                    plantObj.setFruitColor(spinnerVerifyFruitColourItem);
                }


                break;
        }


    }

    String convertListToString(List<String> list)
    {
        String s = "";
        if (list.size() > 0) {
            s = list.get(0);

            for(int i = 1;i < list.size();i++)
            {
                s = s + ", " + list.get(i);
            }
        }
        return s;
    }


    void checkVerificationCount(int verificationCount, CheckBox checkBox)
    {
        if (verificationCount >= 3) {
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
            checkBox.setClickable(false);
            checkBox.setBackgroundColor(Color.GREEN);

        }
    }

    void checkVerificationCountEditText(int verificationCount, EditText editText)
    {
        if(verificationCount >= 3)
        {
            editText.setEnabled(false);
            editText.setCursorVisible(false);
            editText.setTextColor(Color.GREEN);
        }
        else
        {
            editText.setTextColor(Color.RED);
        }
    }


    void checkVerificationCountSpinner(int verificationCount, Spinner spinner)
    {
        if(verificationCount >= 3)
        {


            scrollView1.post(new Runnable() {
                public void run() {
                    scrollView1.scrollTo(0, scrollView1.getBottom());
                }
            });

            TextView selectedText = (TextView) spinner.getChildAt(0);


            if (selectedText!= null) {
                selectedText.setTextColor(Color.GREEN);
            }
            //spinner.setBackgroundColor(Color.GREEN);
            spinner.setEnabled(false);

            Log.d(" Position" , String.valueOf(spinner.getFirstVisiblePosition()));

            //Log.d("Item " , spinner.getChildAt(spinner.getSelectedItemPosition()).toString());
            //((TextView) spinner.getChildAt(spinner.getSelectedItemPosition())).setTextColor(Color.GREEN);
            //((TextView) spinner.getChildAt(spinner.getFirstVisiblePosition())).setTextColor(Color.GREEN);



        }

        else
        {
            TextView selectedText = (TextView) spinner.getChildAt(spinner.getFirstVisiblePosition());
            if (selectedText != null) {
                selectedText.setTextColor(Color.RED);

            }

            //spinner.setBackgroundColor(Color.RED);


        }
    }




    String converBoolToString(Boolean bool)
    {
        String s = "";
        if(bool)
        {
            s="Yes";
        }
        else
        {
            s="No";
        }

        return s;
    }



    int returnSpinnerPosition(Spinner spinner, String str)
    {

        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();



        int position = myAdap.getPosition(str);

        return position;
    }






    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    public void acceptChanges()
    {

        databaseReference.child("plant").child(iD).setValue(plantObj);
        Intent intent = new Intent(this, VerifyEntriesActivity.class);
        startActivity(intent);
        finish();

    }


    public void cancelChanges()
    {
        databaseReference.child("plant").child(iD).setValue(plantOriginalObj);
        Intent intent = new Intent(this, VerifyEntriesActivity.class);
        startActivity(intent);
        finish();
    }


    @Override

    public void onBackPressed()
    {
        databaseReference.child("plant").child(iD).setValue(plantOriginalObj);
        Intent intent = new Intent(this, VerifyEntriesActivity.class);
        startActivity(intent);
        finish();
    }
}
