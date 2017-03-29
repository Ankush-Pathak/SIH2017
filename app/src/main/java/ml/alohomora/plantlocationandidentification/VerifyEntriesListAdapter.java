package ml.alohomora.plantlocationandidentification;

/**
 * Created by Chandan Singh on 28-Mar-17.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VerifyEntriesListAdapter extends RecyclerView.Adapter<VerifyEntriesListAdapter.NumberViewHolder> {

    private final ListItemClickListner mOnClickListner;
    private static int viewHolderCount;
    private int mNumberItems;
    ArrayList<Plant> plant;
    Context context;
    //@param numberOfItems Number of items to display in list
    public VerifyEntriesListAdapter(Context c,int numberOfItems, ListItemClickListner onListItemClickId, ArrayList<Plant> p) {
        mNumberItems = numberOfItems;
        mOnClickListner = onListItemClickId;
        viewHolderCount = 0;
        plant = new ArrayList<>();
        plant.addAll(p);
        context=c;
    }

    public interface ListItemClickListner{
        void onListItemClick(int onListItemClickId);
    }
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.verify_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.textViewVerifyListName.setText("Plant Name :" + plant.get(viewHolderCount).getName());
        Glide.with(context).load(plant.get(viewHolderCount).getImageLeafRef()).into(viewHolder.imageViewVerifyListImage);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewVerifyListImage;
        TextView textViewVerifyListName,textViewVerifyListCount;
        public NumberViewHolder(View itemView) {
            super(itemView);

            imageViewVerifyListImage =(ImageView)itemView.findViewById(R.id.imageViewVerifyListImage);
            textViewVerifyListName =(TextView)itemView.findViewById(R.id.textViewVerifyListName);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            Glide.with(context).load(plant.get(listIndex).getImageLeafRef()).into(imageViewVerifyListImage);
            textViewVerifyListName.setText("Plant Name " + plant.get(listIndex).getLeafMargins());
        }

        @Override
        public void onClick(View v) {
            mOnClickListner.onListItemClick(getAdapterPosition());
        }
    }
}
