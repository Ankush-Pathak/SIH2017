package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

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
    Bitmap bitmap, bitmap1;
    boolean compareImg;
    Mat inpMat,outMat, desc1, desc2;
    ArrayList<Double> min;
    double contour;
    ArrayList<Double> contours;
    boolean sort = false;
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
        min = new ArrayList<>();
        contours = new ArrayList<>();
        for(int i = 0;i < arrayListPlant.size();i++){
            min.add(0.0);
            contours.add(0.0);
        }
        Log.d("SearchAdapter","Adapter constructed");
    }
    @Override
    public Plant getItem(int position) {
        return arrayListPlant.get(position);
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent)
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
                    if(min.get(position) < 1)
                    {
                        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        int ht = bitmap.getHeight();
                        int wd = bitmap.getWidth();
                        inpMat = new Mat();
                        outMat = new Mat();
                        Utils.bitmapToMat(bitmap, inpMat);
                        // = bitmap.copy(Bitmap.Config.ARGB_8888,true);

//                    inpMat = new Mat(bitmap.getHeight(),bitmap.getWidth(), CvType.CV_32F);
//                   outMat = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_32F);
                        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.DYNAMIC_ORB);
                        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();


                        featureDetector.detect(inpMat, matOfKeyPoint);
                        desc1 = new Mat();
                        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
                        descriptorExtractor.compute(inpMat, matOfKeyPoint, desc1);

                        bitmap1 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        bitmap1 = bitmap1.createScaledBitmap(bitmap1, wd, ht, false);
                        bitmap1 = bitmap1.copy(Bitmap.Config.ARGB_8888, true);
                        Utils.bitmapToMat(bitmap1, outMat);
//                    inpMat = new Mat(bitmap.getHeight(),bitmap.getWidth(), CvType.CV_32F);
//                    outMat = new Mat(bitmap.getHeight(),bitmap.getWidth(),CvType.CV_32F);
                        //Utils.bitmapToMat(bitmap, inpMat);
                       // Utils.bitmapToMat(bitmap, outMat);
                        featureDetector = FeatureDetector.create(FeatureDetector.DYNAMIC_ORB);
                        matOfKeyPoint = new MatOfKeyPoint();
                        featureDetector.detect(outMat, matOfKeyPoint);
                        desc2 = new Mat();
                        descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
                        descriptorExtractor.compute(outMat, matOfKeyPoint, desc2);
                        MatOfDMatch matOfDMatch = new MatOfDMatch();
                        DescriptorMatcher matcher;
                        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
                        matcher.match(desc1, desc2, matOfDMatch);
                        double lmin = 9999;
                        List<DMatch> matchesList = matOfDMatch.toList();
                        for (int i = 0; i < desc1.rows(); i++) {
                            if (matchesList.get(i).distance < lmin)
                                lmin = matchesList.get(i).distance;
                            Log.d("Plant5","Distance : " + matchesList.get(i).distance);
                        }
                        min.set(position,lmin);
                        Log.d("Plant5","Name : " + plant.getName() + " min : " + lmin);

                    }
                    /*else if(sort == false)
                    {
                        double temp;
                        Plant plant;
                        for(int i = 0;i < min.size();i++)
                        {
                            for(int j = 0;j < min.size() - 1;j++)
                            {
                                if(min.get(j) > min.get(j + 1))
                                {
                                    temp = min.get(j);
                                    plant = arrayListPlant.get(j);
                                    min.set(j,min.get(j + 1));
                                    arrayListPlant.set(j,arrayListPlant.get(j+1));
                                    min.set(j + 1, temp);
                                    arrayListPlant.set(j + 1,plant);
                                }
                            }
                        }
                        sort = true;
                    }*/
                    if(contours.get(position) < 1)
                    {
                        //contour = computeContour(bitmap,bitmap1);
                        //contours.set(position,contour);
                    }
                    textViewDiff.append(" " + min.get(position) / 3 + "% contour : "); //+ contours.get(position));

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
    private double computeContour(Bitmap bm,Bitmap bm1) {

        int iCannyLowerThreshold = 60, iCannyUpperThreshold = 80;
        Mat m = new Mat(bm.getWidth(), bm.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bm, m);
        Mat thr = new Mat(m.rows(), m.cols(), CvType.CV_8UC1);
        Mat dst = new Mat(m.rows(), m.cols(), CvType.CV_8UC1, Scalar.all(0));
        Imgproc.cvtColor(m, thr, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(thr, thr, 100, 255, Imgproc.THRESH_BINARY);
        Imgproc.Canny(thr, thr, iCannyLowerThreshold, iCannyUpperThreshold);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        //Imgproc.findContours(m, contours, new Mat(), 0, 1);

        Imgproc.findContours(thr, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        Scalar color = new Scalar( 255,255,255);
        Imgproc.drawContours(dst, contours, -1, color, 3);
        //Core.rectangle(m, bounding_rect.br(), bounding_rect.tl(), new Scalar(0,255,0));


       // Utils.matToBitmap(dst,bm);


        //Second image here
        //Bitmap bm1 = BitmapFactory.decodeResource(getResources(),R.drawable.papaya);
        //Utils.matToBitmap(m, bm);
        //convert to mat:
        // int iCannyLowerThreshold = 60, iCannyUpperThreshold = 80;
        Mat m1 = new Mat(bm1.getWidth(), bm1.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bm1, m1);
        Mat thr1 = new Mat(m1.rows(), m1.cols(), CvType.CV_8UC1);
        Mat dst1 = new Mat(m1.rows(), m1.cols(), CvType.CV_8UC1, Scalar.all(0));
        Imgproc.cvtColor(m1, thr1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(thr1, thr1, 100, 255, Imgproc.THRESH_BINARY);
        Imgproc.Canny(thr1, thr1, iCannyLowerThreshold, iCannyUpperThreshold);
        List<MatOfPoint> contours1 = new ArrayList<MatOfPoint>();
        //Imgproc.findContours(m1, contours1, new Mat(), 0, 1);

        Imgproc.findContours(thr1, contours1, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        Scalar color1 = new Scalar( 255,255,255);
        Imgproc.drawContours(dst1, contours1, -1, color1, 3);
        //Core.rectangle(m, bounding_rect.br(), bounding_rect.tl(), new Scalar(0,255,0));


        //Utils.matToBitmap(dst1, bm1);
        //Imgproc.calcHist(,dst1,thr1,m1,bm1,iCannyLowerThreshold,iCannyUpperThreshold);
        Double a = Imgproc.matchShapes(dst, dst1, 1, 0.0);
        Log.d("Contour", a.toString());
        return a;
    }


}
