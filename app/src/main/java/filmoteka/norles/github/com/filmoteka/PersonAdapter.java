package filmoteka.norles.github.com.filmoteka;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.models.PersonItem;
import filmoteka.norles.github.com.filmoteka.models.Result;

import java.util.List;

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

        holder.name.setText(person.getName());

        Glide.with(mContext)
                .load(person.getProfilePath())
                .placeholder(R.drawable.placeholder)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (people==null)
            return 0;
        return people.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView img;

        public PersonViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.person_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("ITEM", name.getText().toString());
                }
            });
        }
    }
}
