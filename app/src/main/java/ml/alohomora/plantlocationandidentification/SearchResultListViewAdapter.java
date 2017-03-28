package ml.alohomora.plantlocationandidentification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public SearchResultListViewAdapter(String searchString, Context context, ArrayList<Plant> arrayListPlant,ArrayList<String> matchingSections)
    {
        super(context,0,arrayListPlant);
        this.context = context;
        this.arrayListPlant = arrayListPlant;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.matchingSections = new ArrayList<>();
        this.searchString = searchString;
        Log.d("SearchAdapter","Adapter constructed");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.d("SearchAdapter","Trying to get view");
        Plant plant = getItem(position);
        View listItem = inflater.inflate(R.layout.list_item_search_search_results,null,true);
        TextView textViewSrchResName, textViewSrchResMatchingSection;
        textViewSrchResName = (TextView)listItem.findViewById(R.id.textViewSrchResListItmName);
        textViewSrchResMatchingSection = (TextView)listItem.findViewById(R.id.textViewSrchResListItmMatchSec);
        textViewSrchResName.append(" " + plant.getName());
        //If some attribute value matches the search string display it else, remove it from the list
        constructMatchingSection(plant);
        textViewSrchResMatchingSection.append(" " + convertListToString(matchingSections));
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
        if(plant.getName().contains(searchString))
        {
            matchingSections.add("Name");
            flag = true;
        }


        if (plant.getFruitColor().contains(searchString))
        {
            matchingSections.add("Fruit color");
            flag = true;
        }
        for(String s : plant.getCommonNames())
        {
            if(s.contains(searchString))
                flag = true;
        }
        if(flag == true)
        {
            matchingSections.add("Common name");
        }

        if (plant.getFruitShape().contains(searchString))
        {
            matchingSections.add("Fruit shape");
            flag = true;
        }

        if (plant.getLeafColor().contains(searchString))
        {
            matchingSections.add("Leaf color");
            flag = true;
        }

        if (plant.getLeafMargins().contains(searchString))
        {
            matchingSections.add("Leaf margin type");
            flag = true;
        }

        if (plant.getLeafSize().contains(searchString))
        {
            matchingSections.add("Leaf size");
            flag = true;
        }

        if (plant.getLeafShape().contains(searchString))
        {
            matchingSections.add("Leaf shape");
            flag = true;
        }

        Log.d("SearchAdapter","flag : " + flag);
        Log.d("SearchAdapter","MatchingSections : " + matchingSections.toString());
        return flag;
    }

}
