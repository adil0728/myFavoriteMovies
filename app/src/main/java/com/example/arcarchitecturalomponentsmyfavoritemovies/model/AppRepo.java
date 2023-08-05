package com.example.arcarchitecturalomponentsmyfavoritemovies.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepo {

    private GenreDao genreDao;
    private MovieDao movieDao;

    private LiveData<List<Genre>> genres;
    private LiveData<List<Movie>> movies;

    public AppRepo(Application application) {
        MoviesDatabase database = MoviesDatabase.getInstance(application);
        genreDao = database.getGenreDao();
        movieDao = database.getMovieDao();
    }

    public LiveData<List<Genre>> getGenres(){
        return genreDao.getAllGenre();
    }

    public LiveData<List<Movie>> getMovies(int genreId){
        return movieDao.getGenreMovies(genreId);
    }

    public void insertGenre(Genre genre){

        new InsertGenreAsyncTusk(genreDao).execute(genre);
    }


    public void insertMovie(Movie movie){

        new InsertMovieAsyncTusk(movieDao).execute(movie);
    }

    private static class InsertGenreAsyncTusk extends AsyncTask<Genre, Void, Void>{

        private GenreDao genreDao;

        public InsertGenreAsyncTusk(GenreDao genreDao) {
            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.insert(genres[0]);

            return null;
        }
    }

    private static class InsertMovieAsyncTusk extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public InsertMovieAsyncTusk(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.insert(movies[0]);

            return null;
        }
    }

    public void updateGenre(Genre genre){

        new UpdateGenreAsyncTusk(genreDao).execute(genre);
    }


    public void updateMovie(Movie movie){

        new UpdateMovieAsyncTusk(movieDao).execute(movie);
    }

    private static class UpdateGenreAsyncTusk extends AsyncTask<Genre, Void, Void>{

        private GenreDao genreDao;

        public UpdateGenreAsyncTusk(GenreDao genreDao) {
            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.update(genres[0]);

            return null;
        }
    }

    private static class UpdateMovieAsyncTusk extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public UpdateMovieAsyncTusk(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.update(movies[0]);

            return null;
        }
    }


    public void deleteGenre(Genre genre){

        new DeleteGenreAsyncTusk(genreDao).execute(genre);
    }


    public void deleteMovie(Movie movie){

        new DeleteMovieAsyncTusk(movieDao).execute(movie);
    }

    private static class DeleteGenreAsyncTusk extends AsyncTask<Genre, Void, Void>{

        private GenreDao genreDao;

        public DeleteGenreAsyncTusk(GenreDao genreDao) {
            this.genreDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {

            genreDao.delete(genres[0]);

            return null;
        }
    }

    private static class DeleteMovieAsyncTusk extends AsyncTask<Movie, Void, Void>{

        private MovieDao movieDao;

        public DeleteMovieAsyncTusk(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {

            movieDao.delete(movies[0]);

            return null;
        }
    }
}
