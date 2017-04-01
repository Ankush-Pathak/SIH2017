package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PlotPlantsSpottedNearby extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    TrackGPS gps;
    ArrayList<Plant> arrayListPlant;
    private DatabaseReference firebaseDatabase;
    Marker plantMarker;
    boolean flag = false;


    EditText getSciName,getIsFruitBearing;
    Bundle savedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedBundle =savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_plants_spotted_nearby);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        arrayListPlant = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("plant");

        final Double locationLat , lcationLon;

        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    arrayListPlant.add(child.getValue(Plant.class));
                }
                mapFragment.getMapAsync(PlotPlantsSpottedNearby.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    @Override
        public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gps = new TrackGPS(PlotPlantsSpottedNearby.this);




        Double latitudes[];// = new double[arrayListPlant.size()];
        Double longitudes[];// = new double[arrayListPlant.size()];

        Iterator<Plant> iteratePlant =  arrayListPlant.iterator();
        int i=0;

        int height=60;
        int width=60;
        Intent intent1;

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.marker1);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap sM = Bitmap.createScaledBitmap(b,width,height,false);

        double myLatitude = gps.getLatitude();
        double myLongitude = gps.getLongitude();
        double resultLatLon;


        // Add a marker in myLatLan and move the camera



        if(gps.canGetLocation)
             {

                 mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                LatLng myLatLan = new LatLng(gps.getLatitude(), gps.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLatLan).icon(BitmapDescriptorFactory.fromBitmap(sM)).title("myLocation : " + myLatitude + ", " + myLongitude));
                mMap.addCircle(new CircleOptions().center(myLatLan).radius(500).strokeColor(Color.BLUE).strokeWidth(5).fillColor(0x5500ff00));
                float zoomLevel = 16.0f;

                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLan));


                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLan, zoomLevel));


            }



        //To display nearby plants
        Log.d("Size",""+arrayListPlant.size());
        Intent intent;
        for(Plant p : arrayListPlant)
        {
            for(Double d : p.getLocationLat())
            {
                resultLatLon= distance(myLatitude,myLongitude,(double) d, (double) p.getLocationLon().get(p.getLocationLat().indexOf(d)));
                Log.d("Result0",""+resultLatLon);
                if(resultLatLon<=500)
                {

                    Log.d("msg:","OK");

                    // Add a marker in Sydney and move the camera
                    if(gps.canGetLocation() ){

                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);



                        LatLng plantlocation = new LatLng(d,(double)p.getLocationLon().get(p.getLocationLat().indexOf(d)));
                        //mMap.addMarker(new MarkerOptions().position(plantlocation).icon(BitmapDescriptorFactory.fromBitmap(sM)).title(p.getName()));
                        plantMarker = mMap.addMarker(new MarkerOptions().position(plantlocation).icon(BitmapDescriptorFactory.fromBitmap(sM)).title(p.getName()));





                        float zoomLevel = 16.0f;


                        mMap.moveCamera(CameraUpdateFactory.newLatLng(plantlocation));

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(plantlocation, zoomLevel));
                        //Log.d("tag:", "Lat:" + latitudes[i] + ", lon:" + longitudes[i]);
                    }


                }

            }

        }



    }

    private double distance(double myLatitude, double myLongitude, double latitude, double longitude) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(latitude-myLatitude);
        double dLng = Math.toRadians(longitude-myLongitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(myLatitude)) * Math.cos(Math.toRadians(latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float distance = (float) (earthRadius * c);





        return (distance);
    }

    private double rad2deg(double rad) {
        return (rad*180.0/Math.PI);
    }

    private double deg2rad(double deg) {
        return (deg*Math.PI/180.0);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(flag) {
            Intent intent = new Intent(this, PlotPlantsSpottedNearby.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(plantMarker))
        {
            marker.getPosition();
            String bioName="",fruitColor;



            for(Plant p : arrayListPlant)
            {
                for(Double d : p.getLocationLat())
                {




                        // Add a marker in Sydney and move the camera
                      /*  if(gps.canGetLocation() ){



                            LatLng plantlocation = new LatLng(d,(double)p.getLocationLon().get(p.getLocationLat().indexOf(d)));
                            //mMap.addMarker(new MarkerOptions().position(plantlocation).icon(BitmapDescriptorFactory.fromBitmap(sM)).title(p.getName()));
                            plantMarker = mMap.addMarker(new MarkerOptions().position(plantlocation).icon(BitmapDescriptorFactory.fromBitmap(sM)).title(p.getName()));
                            */
                            bioName = p.getName();

                    p.getCommonNames();

                    fruitColor = p.fruitColor;







                          /*  float zoomLevel = 16.0f;


                            mMap.moveCamera(CameraUpdateFactory.newLatLng(plantlocation));

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(plantlocation, zoomLevel));
                            //Log.d("tag:", "Lat:" + latitudes[i] + ", lon:" + longitudes[i]);*/
                        }


                    }

                }







        return false;
    }
}

