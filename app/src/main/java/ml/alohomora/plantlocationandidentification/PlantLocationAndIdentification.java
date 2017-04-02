package ml.alohomora.plantlocationandidentification;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Ankush on 3/26/2017.
 */

public class PlantLocationAndIdentification extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        //for Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvantGardeBold.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }


}
