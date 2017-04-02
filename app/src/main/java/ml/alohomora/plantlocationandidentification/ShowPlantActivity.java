package ml.alohomora.plantlocationandidentification;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowPlantActivity extends FragmentActivity implements OnMapReadyCallback {
    Plant plantSelectedToView;
    String iD;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override

    //Calligraphy additions
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Intent intent = getIntent();
        if(intent != null)
        {
            plantSelectedToView = (Plant) intent.getSerializableExtra("plant");
            iD = intent.getStringExtra("iD");
            Log.d("ShowPlant","Intent got, value : " + plantSelectedToView.toString());
        }
        else
            finish();
        TextView textViewDialogBioName, textViewDialogCommonName,textViewDialogLeafSize,textViewDialogLeafShape,textViewDialogLeafMargin, textViewDialogLeafTip,textViewDialogLeafBase,textViewDialogLeafColor,textViewDialogFruitColor,textViewDialogFruitBearing,textViewDialogComment;
        Button buttonDialogOk,buttonAddLocation;
        SupportMapFragment mapView;
        ImageView imageView;
        imageView = (ImageView)findViewById(R.id.imageViewDialogShwPlantImage);
        Glide.with(ShowPlantActivity.this).load(plantSelectedToView.getImageLeafRef().get(0)).into(imageView);
        textViewDialogBioName = (TextView) findViewById(R.id.textViewDialogShwPlantBioName);
        textViewDialogLeafSize = (TextView) findViewById(R.id.textViewDialogShwLeafSize);
        textViewDialogCommonName = (TextView) findViewById(R.id.textViewDialogShwPlantCommonName);
        textViewDialogFruitBearing = (TextView) findViewById(R.id.textViewDialogShwPlantFruitBear);
        textViewDialogFruitColor = (TextView) findViewById(R.id.textViewShwPlantFruitColor);
        textViewDialogLeafBase = (TextView) findViewById(R.id.textViewDialogShwPlantLeafBase);
        textViewDialogLeafColor = (TextView) findViewById(R.id.textViewDialogShwPlantLeafColor);
        textViewDialogLeafMargin = (TextView) findViewById(R.id.textViewDialogShwPlantLeafMargin);
        textViewDialogLeafShape = (TextView) findViewById(R.id.textViewDialogShwLeafShape);
        textViewDialogLeafTip = (TextView) findViewById(R.id.textViewDialogShwPlantLeafTip);
        textViewDialogComment = (TextView) findViewById(R.id.textViewDialogShwComments);
        buttonDialogOk = (Button) findViewById(R.id.buttonDialogShwPlantOk);
        buttonAddLocation = (Button) findViewById(R.id.buttonDialogShwAddCurrentLocation);
        mapView = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapViewDialogShwPlant);
        textViewDialogBioName.append(" " + plantSelectedToView.getName());
        textViewDialogComment.append(" " + plantSelectedToView.getComments());
        textViewDialogCommonName.append(" " + convertListToString(plantSelectedToView.getCommonNames()));
        String s;
        if(plantSelectedToView.isFruitBearing())
            s = " Yes";
        else
            s = " No";
        textViewDialogFruitBearing.append(" " + s);
        textViewDialogFruitColor.append(" " + plantSelectedToView.getFruitColor());
        textViewDialogLeafBase.append(" " + plantSelectedToView.getLeafBase());
        textViewDialogLeafColor.append(" " + plantSelectedToView.getLeafColor());
        textViewDialogLeafMargin.append(" " + plantSelectedToView.getLeafMargins());
        textViewDialogLeafShape.append(" " + plantSelectedToView.getLeafShape());
        textViewDialogLeafSize.append(" " + plantSelectedToView.getLeafSize());
        textViewDialogLeafTip.append(" " + plantSelectedToView.getLeafTip());
        buttonDialogOk.setText("Done");
        buttonDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView.getView().setMinimumHeight(mapView.getView().getWidth());
        ViewGroup vg = (ViewGroup) findViewById(R.id.linearLayoutAcitivityShowPlant);
        vg.invalidate();
        mapView.getMapAsync(ShowPlantActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("plant");

        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationProvider locationProvider = new LocationProvider(ShowPlantActivity.this);
                if(locationProvider.canGetLocation())
                {
                    double lat = locationProvider.getLatitude();
                    double lon = locationProvider.getLongitude();
                    List<Double> latL = plantSelectedToView.getLocationLat();
                    latL.add(lat);
                    List<Double> lonL = plantSelectedToView.getLocationLon();
                    lonL.add(lon);

                    plantSelectedToView.setLocationLat(latL);
                    plantSelectedToView.setLocationLon(lonL);
                    databaseReference.child(iD).setValue(plantSelectedToView);
                    AlertDialog alertDialog;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShowPlantActivity.this);
                    alertDialogBuilder.setTitle("Info");
                    alertDialogBuilder.setMessage("Your current location has been marked in the database for this plant.");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<Double> lat =  (ArrayList<Double>) plantSelectedToView.getLocationLat();
        ArrayList<Double> lon = (ArrayList<Double>) plantSelectedToView.getLocationLon();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.plant_map_marker);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap,60,60,false);

        float zoomLevel = 16.0f;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        for(Double d : lat)
        {
            LatLng latLng = new LatLng(d,lon.get(lat.indexOf(d)));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(plantSelectedToView.getName()).title(plantSelectedToView.getName()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
        }

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ShowPlantActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

}
