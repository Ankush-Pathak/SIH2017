package ml.alohomora.plantlocationandidentification;

import android.content.Context;
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
    public SearchResultListViewAdapter(String searchString, Context context, ArrayList<Plant> arrayListPlant)
    {
        super(context,0);
        this.context = context;
        this.arrayListPlant = arrayListPlant;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        matchingSections = new ArrayList<>();
        this.searchString = searchString;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Plant plant = getItem(position);
        View listItem = inflater.inflate(R.layout.list_item_search_search_results,null,true);
        TextView textViewSrchResName, textViewSrchResMatchingSection;
        textViewSrchResName = (TextView)listItem.findViewById(R.id.textViewSrchResListItmName);
        textViewSrchResMatchingSection = (TextView)listItem.findViewById(R.id.textViewSrchResListItmMatchSec);
        textViewSrchResName.append(" " + plant.getName());

        //If some attribute value matches the search string display it else, remove it from the list
        if(!searchAttrAndAddToMtchSection(plant))
        {
            listItem.setVisibility(View.GONE);
            listItem.getLayoutParams().height = 0;
        }
        textViewSrchResMatchingSection.append(" " + convertListToString(matchingSections));
        return listItem;
    }
    String convertListToString(List<String> list)
    {
        String s = null;
        s = list.get(0);

        for(int i = 1;i < list.size();i++)
        {
            s = s + ", " + list.get(i);
        }
        return s;
    }
    boolean searchAttrAndAddToMtchSection(Plant plant)
    {
        //This flag will be true if atleast one attribute value matches search string, else false
        boolean flag = false;

        if(plant.getName().contains(searchString))
        {
            matchingSections.add("Name");
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

        if (plant.getFruitColor().contains(searchString))
        {
            matchingSections.add("Fruit color");
            flag = true;
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
        for (String s : plant.getCommonNames())
        {
            if(s.contains(searchString)) {
                flag = true;
                matchingSections.add("Common name");
            }
        }
        return flag;
    }
}
