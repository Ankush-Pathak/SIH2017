package ml.alohomora.plantlocationandidentification;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.List;

public class ShowPlantActivity extends FragmentActivity implements OnMapReadyCallback {
    Plant plantSelectedToView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Intent intent = getIntent();
        if(intent != null)
        {
            plantSelectedToView = (Plant) intent.getSerializableExtra("plant");
            Log.d("ShowPlant","Intent got, value : " + plantSelectedToView.toString());
        }
        else
            finish();
        TextView textViewDialogBioName, textViewDialogCommonName,textViewDialogLeafSize,textViewDialogLeafShape,textViewDialogLeafMargin, textViewDialogLeafTip,textViewDialogLeafBase,textViewDialogLeafColor,textViewDialogFruitColor,textViewDialogFruitBearing,textViewDialogComment;
        Button buttonDialogOk;
        SupportMapFragment mapView;
        ImageView imageView;
        imageView = (ImageView)findViewById(R.id.imageViewDialogShwPlantImage);
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

        Glide.with(ShowPlantActivity.this).load(plantSelectedToView.getImageLeafRef().get(0)).into(imageView);
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
        ArrayList<Double> lon = (ArrayList<Double>) plantSelectedToView.getLocationLat();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.plant_map_marker);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap,60,60,false);

        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        for(Double d : lat)
        {
            LatLng latLng = new LatLng(d,lon.get(lat.indexOf(d)));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(plantSelectedToView.getName()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
        }

    }

}
