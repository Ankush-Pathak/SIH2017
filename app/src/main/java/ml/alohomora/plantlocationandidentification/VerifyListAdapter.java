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

import java.util.ArrayList;

public class VerifyListAdapter extends RecyclerView.Adapter<VerifyListAdapter.NumberViewHolder> {

    private final ListItemClickListner mOnClickListner;
    private static int viewHolderCount;
    private int mNumberItems;
    ArrayList<Plant> plant;
    //@param numberOfItems Number of items to display in list
    public VerifyListAdapter(int numberOfItems, ListItemClickListner onListItemClickId, ArrayList<Plant> p) {
        mNumberItems = numberOfItems;
        mOnClickListner = onListItemClickId;
        viewHolderCount = 0;
        plant = new ArrayList<>();
        plant.addAll(p);
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

        viewHolder.textViewVerifyListName.setText("Name " + plant.get(viewHolderCount).getLeafMargins());
        viewHolder.textViewVerifyListCount.setText("Count "+ plant.get(viewHolderCount).getLeafColor());

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
            textViewVerifyListCount =(TextView)itemView.findViewById(R.id.textViewVerifyListCount);
            textViewVerifyListName =(TextView)itemView.findViewById(R.id.textViewVerifyListName);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            textViewVerifyListName.setText("Name " + plant.get(listIndex).getLeafMargins());
            textViewVerifyListCount.setText("Count "+ plant.get(listIndex).getLeafColor());
        }

        @Override
        public void onClick(View v) {
            mOnClickListner.onListItemClick(getAdapterPosition());
        }
    }
}
