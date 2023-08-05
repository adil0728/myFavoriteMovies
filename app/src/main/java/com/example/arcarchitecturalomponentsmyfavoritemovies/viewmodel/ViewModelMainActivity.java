package com.example.arcarchitecturalomponentsmyfavoritemovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arcarchitecturalomponentsmyfavoritemovies.model.AppRepo;
import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Genre;
import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Movie;

import java.util.List;

public class ViewModelMainActivity extends AndroidViewModel {

    AppRepo mAppRepo;
    private LiveData<List<Genre>> genres;
    private LiveData<List<Movie>> genreMovies;



    public ViewModelMainActivity(@NonNull Application application) {
        super(application);
        mAppRepo = new AppRepo(application);
    }

    public LiveData<List<Genre>> getGenres() {
        genres = mAppRepo.getGenres();
        return genres;
    }

    public LiveData<List<Movie>> getGenreMovies(int genreId) {
        genreMovies = mAppRepo.getMovies(genreId);
        return genreMovies;
    }

    public void addNewMovie(Movie movie){
        mAppRepo.insertMovie(movie);
    }

    public void updateMovie(Movie movie){
        mAppRepo.updateMovie(movie);
    }

    public void deleteMovie(Movie movie){
        mAppRepo.deleteMovie(movie);
    }
}
