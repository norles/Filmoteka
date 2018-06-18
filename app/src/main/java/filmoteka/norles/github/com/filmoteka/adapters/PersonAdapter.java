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
import filmoteka.norles.github.com.filmoteka.activities.PersonDetailActivity;
import filmoteka.norles.github.com.filmoteka.models.PersonItem;

import java.util.List;

// Adapter obsługujacy elementy dodawane do RecycleView
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context mContext;
    private List<PersonItem> people;

    public PersonAdapter(Context mContext, List<PersonItem> people) {
        this.mContext = mContext;
        this.people = people;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_card, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        PersonItem person = people.get(position);

        holder.id = person.getId();

        holder.name.setText(person.getName());

        Glide.with(mContext)
                .load(person.getProfilePath())
                .placeholder(R.drawable.person_placeholder)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (people==null)
            return 0;
        return people.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        public Integer id;

        public TextView name;
        public ImageView img;

        public PersonViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.person_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PersonDetailActivity.class);
                    intent.putExtra("id",id);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
