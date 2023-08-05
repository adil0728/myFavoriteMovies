package com.example.arcarchitecturalomponentsmyfavoritemovies;

import android.content.Intent;
import android.os.Bundle;

import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Genre;
import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Movie;
import com.example.arcarchitecturalomponentsmyfavoritemovies.viewmodel.ViewModelMainActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arcarchitecturalomponentsmyfavoritemovies.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModelMainActivity mViewModelMainActivity;
    private ActivityMainBinding binding;
    private MainActivityClickHandlers mClickHandlers;
    private Genre selectedGenre;
    private ArrayList<Genre> mGenreArrayList;
    private ArrayList<Movie> mMovieArrayList;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private int selectedMovieId;

    public static final int ADD_MOVIE_REQUEST_CODE = 111;
    public static final int EDIT_MOVIE_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModelMainActivity = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ViewModelMainActivity.class);

        mViewModelMainActivity.getGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {

                mGenreArrayList = (ArrayList<Genre>) genres;

                for (Genre genre : genres) {
                    Log.d("MyTAG", genre.getGenreName());
                }
                showInSpinner();
            }
        });


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mClickHandlers = new MainActivityClickHandlers();
        binding.setClickHandlers(mClickHandlers);

        setSupportActionBar(binding.toolbar);


    }

    private void showInSpinner() {
        ArrayAdapter<Genre> genreArrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, mGenreArrayList);
        genreArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.setSpinnerAdapter(genreArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MainActivityClickHandlers {

        public void onFabClicked(View view) {

            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_MOVIE_REQUEST_CODE);
        }

        public void onSelectedItem(AdapterView<?> parent, View view, int position, long id) {
            selectedGenre = (Genre) parent.getItemAtPosition(position);
//            String message = "id is " + selectedGenre.getId() + "\n name is " + selectedGenre.getGenreName();
//            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            loadGenreMoviesInArrayList(selectedGenre.getId());
        }
    }

    private void loadGenreMoviesInArrayList(int genreId) {
        mViewModelMainActivity.getGenreMovies(genreId).observe(this, movies -> {
            mMovieArrayList = (ArrayList<Movie>) movies;

            loadRecyclerView();
        });
    }

    private void loadRecyclerView() {
        mRecyclerView = binding.secondaryLayout.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter();
        mMovieAdapter.setMovieArrayList(mMovieArrayList);
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieAdapter.setOnItemClickListener(movie -> {

            selectedMovieId = movie.getMovieId();
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.MOVIE_ID, selectedMovieId);
            intent.putExtra(AddEditActivity.MOVIE_NAME, movie.getMovieName());
            intent.putExtra(AddEditActivity.MOVIE_DESCRIPTION, movie.getMovieDescription());
            startActivityForResult(intent ,EDIT_MOVIE_REQUEST_CODE);

        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Movie movieToDelete =  mMovieArrayList.get(viewHolder.getAdapterPosition());
               mViewModelMainActivity.deleteMovie(movieToDelete);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedGenreId = selectedGenre.getId();

        if (requestCode == ADD_MOVIE_REQUEST_CODE && resultCode == RESULT_OK){
            Movie movie = new Movie();
            movie.setGenreId(selectedGenreId);
            movie.setMovieName(data.getStringExtra(AddEditActivity.MOVIE_NAME));
            movie.setMovieDescription(data.getStringExtra(AddEditActivity.MOVIE_DESCRIPTION));

            mViewModelMainActivity.addNewMovie(movie);

        } else if (requestCode == EDIT_MOVIE_REQUEST_CODE && resultCode == RESULT_OK) {

            Movie movie = new Movie();
            movie.setMovieId(selectedMovieId);
            movie.setGenreId(selectedGenreId);
            movie.setMovieName(data.getStringExtra(AddEditActivity.MOVIE_NAME));
            movie.setMovieDescription(data.getStringExtra(AddEditActivity.MOVIE_DESCRIPTION));

            mViewModelMainActivity.updateMovie(movie);
        }
    }
}