package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
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
    String imageName,imagePath;
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
                try {
                    imageName = String.valueOf(System.currentTimeMillis()) + ".jpeg";
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ,"Plant_Id");
                    file.mkdirs();
                    //file.mkdirs();
                    File file2 = new File(file.getPath() + File.separator + imageName);
                    Log.d("File",file2.getName() + " " + file2.getAbsolutePath());
                    if(file2.exists() || file2.isDirectory())
                        Log.d("File", "File exists");
                    Uri uri = Uri.fromFile(file2);
                    if(uri == null)
                        Log.d("File","Uri null");
                    else
                        Log.d("File","Uri : " + uri.toString());
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 100);
                        imagePath = file2.getPath();
                        Log.d("Leaving control","Leaving control");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
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
                databaseReferenceUploadData.push().setValue(plant);
                finish();
                Log.d("AddtoDatabase","Ref : " + ref + " List : " + list.toString());
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Result", "ReqCode : " + requestCode + " Res Code : " + resultCode);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Uri uri = data.getData();
            try {
                File file = new File(imagePath);
                Uri uri = Uri.fromFile(file);
                imageBitmap = BitmapFactory.decodeStream(AddToDatabaseActivity.this.getContentResolver().openInputStream(uri),null,null);
                Log.d("Image",imageBitmap.getHeight() + " " + imageBitmap.getWidth());
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap,1600,1200,false);
                Log.d("Image",imageBitmap.getHeight() + " " + imageBitmap.getWidth());
                imageViewAddImage.setImageBitmap(imageBitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
