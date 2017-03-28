package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class AddToDatabaseActivity extends AppCompatActivity {
    ImageView imageViewAddImage;
    Button buttonAddLeafImage,buttonAddSubmit;
    Spinner spinnerAddLeafSize, spinnerAddLeafShape, spinnerAddLeafMargins, spinnerAddLeafTip, spinnerAddLeafBase, spinnerAddLeafColor;
    EditText editTextAddBioName, editTextAddFruitColor, editTextAddCommonName, editTextAddComments;
    CheckBox checkBoxIsFruitBearing;
    FirebaseDatabase firebaseDatabaseUploadData;
    DatabaseReference databaseReferenceUploadData;
    StorageReference storageReferenceUploadImage;
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);
        setUpObjects();
    }
    void setUpObjects()
    {
        firebaseDatabaseUploadData = FirebaseDatabase.getInstance();
        databaseReferenceUploadData = firebaseDatabaseUploadData.getReference().child("plant");
        storageReferenceUploadImage = FirebaseStorage.getInstance().getReference();
        storageReferenceUploadImage = storageReferenceUploadImage.child("leaf_images");
        imageViewAddImage = (ImageView) findViewById(R.id.imageViewAddAddedImage);
        buttonAddLeafImage = (Button) findViewById(R.id.buttonAddLeafImage);
        buttonAddSubmit = (Button) findViewById(R.id.buttonAddSubmit);
        spinnerAddLeafSize = (Spinner) findViewById(R.id.spinnerAddLeafSize);
        spinnerAddLeafShape = (Spinner) findViewById(R.id.spinnerAddLeafShape);
        spinnerAddLeafMargins = (Spinner)findViewById(R.id.spinnerAddLeafMargins);
        spinnerAddLeafTip = (Spinner) findViewById(R.id.spinnerAddLeafTip);
        spinnerAddLeafBase = (Spinner) findViewById(R.id.spinnerAddLeafBase);
        spinnerAddLeafColor = (Spinner) findViewById(R.id.spinnerAddLeafColor);
        editTextAddBioName = (EditText) findViewById(R.id.editTextAddBioName);
        editTextAddCommonName = (EditText) findViewById(R.id.editTextAddCommonName);
        editTextAddFruitColor = (EditText) findViewById(R.id.editTextAddFruitColor);
        editTextAddCommonName = (EditText) findViewById(R.id.editTextAddCommonName);
        editTextAddComments = (EditText) findViewById(R.id.editTextAddComments);
        checkBoxIsFruitBearing = (CheckBox) findViewById(R.id.checkBoxAddIsFruitBearing);
        buttonAddLeafImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }

            }
        });

        buttonAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractDataAndAddToDatabase();
            }
        });
    }

    void extractDataAndAddToDatabase()
    {
        final String imageName;
        final Plant plant = new Plant();
        plant.setComments(editTextAddComments.getText().toString());
        plant.setCommentsVerficationCount(0);

        List<String> list = plant.getCommonNames();
        list.add(editTextAddCommonName.getText().toString());
        plant.setCommonNames(list);
        plant.setCommentsVerficationCount(0);

        plant.setFruitBearing(checkBoxIsFruitBearing.isChecked());
        plant.setIsFruitBearingVerificationCount(0);

        plant.setFruitColor(editTextAddFruitColor.getText().toString());
        plant.setFruitColorVerificationCount(0);

        plant.setFruitShape("NA");
        plant.setFruitShapeVerificationCount(0);

        plant.setFullyVerfied(false);

        plant.setLeafBase(spinnerAddLeafBase.getSelectedItem().toString());
        plant.setLeafBaseVerificationCount(0);

        plant.setLeafColor(spinnerAddLeafColor.getSelectedItem().toString());
        plant.setLeafColorVerificationCount(0);

        plant.setLeafMargins(spinnerAddLeafMargins.getSelectedItem().toString());
        plant.setLeafMarginsVerificationCount(0);

        plant.setLeafShape(spinnerAddLeafShape.getSelectedItem().toString());
        plant.setLeafShapeVerificationCount(0);

        plant.setLeafSize(spinnerAddLeafSize.getSelectedItem().toString());
        plant.setLeafSizeVerificationCount(0);

        plant.setLeafTip(spinnerAddLeafTip.getSelectedItem().toString());
        plant.setLeafTipVerificationCount(0);

        plant.setName(editTextAddBioName.getText().toString());
        plant.setNameVerificationCount(0);

        plant.setRejectionCount(0);
        plant.setUploaderId("ANONYMOUS");
        imageName = String.valueOf(System.currentTimeMillis()) + ".png";
        storageReferenceUploadImage = storageReferenceUploadImage.child(imageName);
        Log.d("Upload path : ",storageReferenceUploadImage.getPath());
        LocationProvider locationProvider = new LocationProvider(AddToDatabaseActivity.this);
        if(locationProvider.canGetLocation())
        {
            double lat = locationProvider.getLatitude();
            double lon = locationProvider.getLongitude();
            List<Double> latL = plant.getLocationLat();
            latL.add(lat);
            List<Double> lonL = plant.getLocationLon();
            lonL.add(lon);

            plant.setLocationLat(latL);
            plant.setLocationLon(lonL);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReferenceUploadImage.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(AddToDatabaseActivity.this,"Image upload failed try again!",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") String ref = taskSnapshot.getDownloadUrl().toString();
                List<String> list = plant.getImageLeafRef();
                list.add(ref);
                plant.setImageLeafRef(list);
                Toast.makeText(AddToDatabaseActivity.this,"Success!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        databaseReferenceUploadData.push().setValue(plant);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageViewAddImage.setDrawingCacheEnabled(true);
            imageViewAddImage.setImageBitmap(imageBitmap);
            Log.d("Bitmap", imageBitmap.getHeight() + " " + imageBitmap.getWidth());
        }
    }
}
