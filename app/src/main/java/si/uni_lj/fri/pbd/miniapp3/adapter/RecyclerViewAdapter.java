package si.uni_lj.fri.pbd.miniapp3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;
import si.uni_lj.fri.pbd.miniapp3.ui.DetailsActivity;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {

    private int beforelayout;
    private List<RecipeSummaryIM> recipelist;

    public RecyclerViewAdapter(int layoutID)
    {
        beforelayout = layoutID;
    }

    public void setRecipelist(List<RecipeSummaryIM> recipelist)
    {
        this.recipelist = recipelist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_grid_item,
                viewGroup, false);
        RecyclerViewAdapter.CardViewHolder viewHolder = new RecyclerViewAdapter.CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CardViewHolder holder, int position) {
        TextView itemDetail = holder.itemDetail;
        ImageView itemImage = holder.itemImage;
        itemDetail.setText(recipelist.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(recipelist.get(position).getStrMealThumb()).into(itemImage);

        if(beforelayout == 1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("id",recipelist.get(holder.getAdapterPosition()).getIdMeal());
                    intent.putExtra("nowActivity",beforelayout);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else if(beforelayout == 2)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("id",recipelist.get(holder.getAdapterPosition()).getIdMeal());
                    intent.putExtra("nowActivity",beforelayout);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(recipelist!=null)
            return recipelist.size();
        else return 0;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemDetail;
        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.image_view);
            itemDetail = (TextView)itemView.findViewById(R.id.text_view_content);
        }
    }
}
