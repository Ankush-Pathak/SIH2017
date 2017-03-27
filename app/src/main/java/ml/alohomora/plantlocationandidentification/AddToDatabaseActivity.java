package ml.alohomora.plantlocationandidentification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddToDatabaseActivity extends AppCompatActivity {
    ImageView imageViewAddImage;
    Button buttonAddLeafImage,buttonAddSubmit;
    Spinner spinnerAddLeafSize, spinnerAddLeafShape, spinnerAddLeafMargins, spinnerAddLeafTip, spinnerAddLeafBase, spinnerAddLeafColor;
    EditText editTextAddBioName, editTextAddFruitColor, editTextAddCommonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);
        setUpObjects();
    }
    void setUpObjects()
    {
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

        buttonAddLeafImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewAddImage.setImageBitmap(imageBitmap);
        }
    }
}
