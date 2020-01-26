package com.example.testingarchitecturecomponent.ui.list_movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testingarchitecturecomponent.data.entity.Movie;
import com.example.testingarchitecturecomponent.R;
import com.example.testingarchitecturecomponent.data.api.Retrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void openMovieActivity(int position);
    }

    public Adapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Movie currentMovie = movies.get(position);

        holder.title.setText(currentMovie.getTitle());

        Picasso.with(context)
                .load(Retrofit.BASE_URL_FOR_IMAGE + currentMovie.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> nextMovies) {
        movies.addAll(nextMovies);
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movieTitleTextView);
            imageView = itemView.findViewById(R.id.movieImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int movieId = movies.get(getAdapterPosition()).getMovieId();
                    if (listener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            listener.openMovieActivity(movieId);
                        }
                    }
                }
            });
        }
    }
}
