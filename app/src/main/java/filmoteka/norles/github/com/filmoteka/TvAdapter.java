package filmoteka.norles.github.com.filmoteka;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.models.MovieItem;
import filmoteka.norles.github.com.filmoteka.models.TvItem;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private Context mContext;
    private List<TvItem> results;

    public TvAdapter(Context mContext, List<TvItem> results) {
        this.mContext = mContext;
        this.results = results;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_card, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvViewHolder holder, int position) {
        TvItem result = results.get(position);

        holder.id = result.getId();

        holder.title.setText(result.getOriginalName());

        Glide.with(mContext)
                .load(result.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if (results==null)
            return 0;
        return results.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {

        public Integer id;
        public TextView title;
        public ImageView thumbnail;

        public TvViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            thumbnail = itemView.findViewById(R.id.tv_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TvDetailActivity.class);
                    intent.putExtra("id",id);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
