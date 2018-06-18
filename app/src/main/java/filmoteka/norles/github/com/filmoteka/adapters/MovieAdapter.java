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
import filmoteka.norles.github.com.filmoteka.models.MovieItem;

import java.util.List;

// Adapter obsługujacy elementy dodawane do RecycleView
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<MovieItem> results;

    public MovieAdapter(Context mContext, List<MovieItem> results) {
        this.mContext = mContext;
        this.results = results;
    }

    // Tworzenie Holdera - obiektu ktory bedzie reprezentowal jeden element recycleView
    // przypisany do niego zostaje rownież widok dla pojedynczego elementu
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    // Metoda wykonywana po utworzeniu holdera. W mozemy zdefiniowac
    // jakie wartosci maja byc przypisane do widoku pojedynczego elementu
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieItem result = results.get(position);

        holder.id = result.getId();

        holder.title.setText(result.getOriginalTitle());

        Glide.with(mContext)
                .load(result.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
    }

    // Zwraca liczbe elementow
    @Override
    public int getItemCount() {
        if (results==null)
            return 0;
        return results.size();
    }

    // Klasa reprezentujaca jeden element
    class MovieViewHolder extends RecyclerView.ViewHolder {

        public Integer id;
        public TextView title;
        public ImageView thumbnail;

        // Jako parametr przyjmuje view - jest to widok dla poszczegolnego elementu
        public MovieViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            // ClickListener - obsluga klikniecia w element,
            // w tym przypadku otwarcie nowej aktywnosci
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
