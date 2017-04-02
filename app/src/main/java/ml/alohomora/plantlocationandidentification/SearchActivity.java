package ml.alohomora.plantlocationandidentification;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{
    EditText editTextSrchTxtSrch;
    FirebaseDatabase firebaseDatabaseRetrieveAllData;
    DatabaseReference databaseReferenceRetrieveAllData;
    ArrayList<Plant> arrayListPlantFromDb;
    ArrayList<String> matchingSectionsWithSearch;
    String searchString = null,imagePath;
    SearchResultListViewAdapter searchResultListViewAdapter;
    ListView listViewSrchRes;
    Plant plantSelectedToView;
    Bitmap imageBitmap = null;
    boolean imageSearch = false;
    Button buttonSrchImageSrch,buttonImageView;

    ArrayList<String> iD;


    static {
        OpenCVLoader.initDebug();
    }


    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("TAG", "OpenCV loaded successfully");
                    // Create and set View
                    setContentView(R.layout.activity_search);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpObjects();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(SearchActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    void setUpObjects()
    {
        editTextSrchTxtSrch = (EditText)findViewById(R.id.editTextSearchTextSrch);
        buttonImageView = (Button) findViewById(R.id.buttonSrchDispImg);
        buttonSrchImageSrch = (Button) findViewById(R.id.buttonSearchImageSrch);
        firebaseDatabaseRetrieveAllData = FirebaseDatabase.getInstance();
        databaseReferenceRetrieveAllData = firebaseDatabaseRetrieveAllData.getReference().child("plant");
        listViewSrchRes = (ListView)findViewById(R.id.listViewSearchSrchResults);
        arrayListPlantFromDb = new ArrayList<>();
        matchingSectionsWithSearch = new ArrayList<>();
        iD = new ArrayList<>();
        editTextSrchTxtSrch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(SearchActivity.this,"Text before changed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    searchString = s.toString();
                    arrayListPlantFromDb.clear();
                    retrieveFbData(searchString);
                    //Toast.makeText(SearchActivity.this,"Text changed",Toast.LENGTH_SHORT).show();
                    Log.d("Search","Txt Changed");
                }
                if (count == 0)
                {
                    arrayListPlantFromDb.clear();
                    iD.clear();
                    searchResultListViewAdapter = new SearchResultListViewAdapter(searchString,SearchActivity.this,arrayListPlantFromDb,matchingSectionsWithSearch,iD,null,false);
                    listViewSrchRes.setAdapter(searchResultListViewAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(SearchActivity.this,"After Text changed",Toast.LENGTH_SHORT).show();
            }
        });
        buttonSrchImageSrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBasedSearch();
            }
        });
        buttonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SearchActivity.this,android.R.style.Theme_Holo_Light_Dialog);
                dialog.setTitle("Captured image");
                dialog.setContentView(R.layout.dialog_image_view);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageViewDialogDispImg);
                Button button = (Button) dialog.findViewById(R.id.buttonDialogDispImgOk);
                imageView.setImageBitmap(imageBitmap);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    void retrieveFbData(String st)
    {
        arrayListPlantFromDb.clear();
        iD.clear();
        final String str = st;
        databaseReferenceRetrieveAllData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Plant plant;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    plant = ds.getValue(Plant.class);
                    if(checkIfShouldBeAdded(plant))
                    {
                        arrayListPlantFromDb.add(plant);
                        Log.d("Search","Arraylist size : " + arrayListPlantFromDb.size() + " content " + arrayListPlantFromDb.toString());
                        iD.add(ds.getKey());
                    }
                }
                updateArrayAdapter(searchString);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void updateArrayAdapter(String s)
    {
        Log.d("Search","Now setting adapeter, matchingsection size : " + matchingSectionsWithSearch.size()  + " with values " + matchingSectionsWithSearch.toString());
        searchResultListViewAdapter = new SearchResultListViewAdapter(s,SearchActivity.this,arrayListPlantFromDb,matchingSectionsWithSearch,iD,imageBitmap,imageSearch);
        listViewSrchRes.setAdapter(searchResultListViewAdapter);
        Log.d("Search","Array Adapter set");

    }

    boolean checkIfShouldBeAdded(Plant plant)
    {
        boolean flag = false;
        matchingSectionsWithSearch.clear();
        Log.d("Search","Search String : " + searchString);
        Log.d("Search", plant.getName() + " contains : " + searchString + " : " + plant.getName().contains(searchString));
        if(plant.getName().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Name");
            flag = true;
        }
        for(String s : plant.getCommonNames())
        {
            if(s.toLowerCase().contains(searchString.toLowerCase()))
                flag = true;
        }
        if(flag == true)
        {
            matchingSectionsWithSearch.add("Common name");
        }

        if (plant.getFruitColor().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Fruit color");
            flag = true;
        }

        if (plant.getFruitShape().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Fruit shape");
            flag = true;
        }

        if (plant.getLeafColor().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Leaf color");
            flag = true;
        }

        if (plant.getLeafMargins().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Leaf margin type");
            flag = true;
        }

        if (plant.getLeafSize().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Leaf size");
            flag = true;
        }

        if (plant.getLeafShape().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSectionsWithSearch.add("Leaf shape");
            flag = true;
        }

        Log.d("Search","flag : " + flag);
        Log.d("Search","MatchingSections : " + matchingSectionsWithSearch.toString());
        return flag;
    }
    void imageBasedSearch()
    {
        String imageName = "temp.jpeg";
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Result", "ReqCode : " + requestCode + " Res Code : " + resultCode);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Uri uri = data.getData();
            try {
                File file = new File(imagePath);
                Uri uri = Uri.fromFile(file);
                imageBitmap = BitmapFactory.decodeStream(SearchActivity.this.getContentResolver().openInputStream(uri),null,null);
                Log.d("Image",imageBitmap.getHeight() + " " + imageBitmap.getWidth());
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap,1600,1200,false);
                Log.d("Image",imageBitmap.getHeight() + " " + imageBitmap.getWidth());
                imageSearch = true;
                searchString = "";
                retrieveFbData("");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




}

