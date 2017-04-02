package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush on 3/26/2017.
 */

public class SearchResultListViewAdapter extends ArrayAdapter<Plant>{
    Context context;
    ArrayList<Plant> arrayListPlant;
    LayoutInflater inflater;
    ArrayList<String> matchingSections;
    String searchString;
    ArrayList<String> iD;
    Bitmap bitmap;
    boolean compareImg;
    Mat inpMat,outMat, desc1, desc2;
    static {
        OpenCVLoader.initDebug();}
    public SearchResultListViewAdapter(String searchString, Context context, ArrayList<Plant> arrayListPlant,ArrayList<String> matchingSections,ArrayList<String> iD,Bitmap bitmap, boolean compareImg)
    {
        super(context,0,arrayListPlant);
        this.context = context;
        this.arrayListPlant = arrayListPlant;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.matchingSections = new ArrayList<>();
        this.searchString = searchString;
        this.iD = iD;
        this.bitmap = bitmap;
        this.compareImg = compareImg;
        Log.d("SearchAdapter","Adapter constructed");
    }
    @Override
    public Plant getItem(int position) {
        return arrayListPlant.get(position);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Log.d("SearchAdapter","Trying to get view");
        final Plant plant = getItem(position);
        View listItem = inflater.inflate(R.layout.list_item_search_search_results,null,true);
        final TextView textViewSrchResName, textViewSrchResMatchingSection,textViewDiff;
        Button buttonView;
        final ImageView imageView;
        textViewSrchResName = (TextView)listItem.findViewById(R.id.textViewSrchResListItmName);
        textViewSrchResMatchingSection = (TextView)listItem.findViewById(R.id.textViewSrchResListItmMatchSec);
        buttonView = (Button)listItem.findViewById(R.id.buttonSrchResListView);
        imageView = (ImageView)listItem.findViewById(R.id.imageViewSrchResListImg);
        textViewDiff = (TextView)listItem.findViewById(R.id.textViewSrchResListItmDiff);
        textViewSrchResName.append(" " + plant.getName());
        //If some attribute value matches the search string display it else, remove it from the list
        constructMatchingSection(plant);
        textViewSrchResMatchingSection.append(" " + convertListToString(matchingSections));
        if(compareImg)
        {
            Picasso.with(context).load(plant.getImageLeafRef().get(0)).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                    int ht = bitmap.getHeight();
                    int wd = bitmap.getWidth();
                    inpMat = new Mat();
                    outMat = new Mat();
                    Utils.bitmapToMat(bitmap,inpMat);
                    Utils.bitmapToMat(bitmap,outMat);
//                    inpMat = new Mat(bitmap.getHeight(),bitmap.getWidth(), CvType.CV_32F);
//                   outMat = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_32F);
                    FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.DYNAMIC_ORB);
                    MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();


                    featureDetector.detect(inpMat,matOfKeyPoint);
                    desc1 = new Mat();
                    DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
                    descriptorExtractor.compute(inpMat,matOfKeyPoint,desc1);

                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    bitmap = bitmap.createScaledBitmap(bitmap,wd,ht,false);
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
//                    inpMat = new Mat(bitmap.getHeight(),bitmap.getWidth(), CvType.CV_32F);
//                    outMat = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_32F);
                    Utils.bitmapToMat(bitmap,inpMat);
                    Utils.bitmapToMat(bitmap,outMat);
                    featureDetector = FeatureDetector.create(FeatureDetector.DYNAMIC_ORB);
                    matOfKeyPoint = new MatOfKeyPoint();
                    featureDetector.detect(inpMat,matOfKeyPoint);
                    desc2 = new Mat();
                    descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
                    descriptorExtractor.compute(inpMat,matOfKeyPoint,desc2);
                    MatOfDMatch matOfDMatch = new MatOfDMatch();
                    DescriptorMatcher matcher;
                    matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
                    matcher.match(desc1,desc2,matOfDMatch);
                    double min = 9999;
                    List<DMatch> matchesList = matOfDMatch.toList();
                    for(int i = 0;i < desc1.rows();i++) {
                        if (matchesList.get(i).distance < min)
                            min = matchesList.get(i).distance;
                    }
                    textViewDiff.append(" " + min);

                }

                @Override
                public void onError() {

                }
            });




        }
        else
            Glide.with(context).load(plant.getImageLeafRef().get(0)).into(imageView);



        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowPlantActivity.class);
                intent.putExtra("plant",plant);
                intent.putExtra("iD",iD.get(position));
                context.startActivity(intent);
            }
        });

        return listItem;
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
    boolean constructMatchingSection(Plant plant)
    {
        boolean flag = false;
        matchingSections.clear();
        Log.d("SearchAdapter","Search String : " + searchString);
        Log.d("SearchAdapter", plant.getName() + " contains : " + searchString + " : " + plant.getName().contains(searchString));
        if(plant.getName().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Name");
            flag = true;
        }


        if (plant.getFruitColor().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Fruit color");
            flag = true;
        }
        for(String s : plant.getCommonNames())
        {
            if(s.toLowerCase().contains(searchString.toLowerCase()))
                flag = true;
        }
        if(flag == true)
        {
            matchingSections.add("Common name");
        }

        if (plant.getFruitShape().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Fruit shape");
            flag = true;
        }

        if (plant.getLeafColor().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Leaf color");
            flag = true;
        }

        if (plant.getLeafMargins().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Leaf margin type");
            flag = true;
        }

        if (plant.getLeafSize().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Leaf size");
            flag = true;
        }

        if (plant.getLeafShape().toLowerCase().contains(searchString.toLowerCase()))
        {
            matchingSections.add("Leaf shape");
            flag = true;
        }

        Log.d("SearchAdapter","flag : " + flag);
        Log.d("SearchAdapter","MatchingSections : " + matchingSections.toString());
        return flag;
    }


}
