package filmoteka.norles.github.com.filmoteka.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.activities.MovieDetailActivity;
import filmoteka.norles.github.com.filmoteka.models.SearchItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context mContext;
    private List<SearchItem> items;

    public SearchAdapter(Context mContext, List<SearchItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchItem item = items.get(position);

        holder.id = item.getId();

        holder.title.setText(item.getTitle());

        Glide.with(mContext)
                .load(item.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if (items==null)
            return 0;
        return items.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        public Integer id;
        public TextView title;
        public ImageView thumbnail;

        public SearchViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra("id",id);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
