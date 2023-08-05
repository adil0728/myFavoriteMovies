package com.example.arcarchitecturalomponentsmyfavoritemovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arcarchitecturalomponentsmyfavoritemovies.databinding.MovieListItemBinding;
import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private ArrayList<Movie> mMovieArrayList = new ArrayList<>();

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_list_item,
                parent,
                false
        );
        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieArrayList.get(position);
        holder.mMovieListItemBinding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        MovieListItemBinding mMovieListItemBinding;

        public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            this.mMovieListItemBinding = movieListItemBinding;
            mMovieListItemBinding.getRoot().setOnClickListener(v -> {

                int position = getAdapterPosition();
                if (mOnItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(mMovieArrayList.get(position));
                }

            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        mMovieArrayList = movieArrayList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
