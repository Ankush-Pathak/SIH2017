package ml.alohomora.plantlocationandidentification;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ankush on 3/26/2017.
 */

public class PlantLocationAndIdentification extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
