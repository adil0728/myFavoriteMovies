package com.example.arcarchitecturalomponentsmyfavoritemovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.arcarchitecturalomponentsmyfavoritemovies.databinding.ActivityAddEditBinding;
import com.example.arcarchitecturalomponentsmyfavoritemovies.model.Movie;

public class AddEditActivity extends AppCompatActivity {

    private Movie mMovie;

    public static final String MOVIE_ID = "movieId";
    public static final String MOVIE_NAME = "movieName";
    public static final String MOVIE_DESCRIPTION = "movieDescription";

    private ActivityAddEditBinding mActivityAddEditBinding;
    private AddEditActivityClickHandlers mAddEditActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        mActivityAddEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);

        mMovie = new Movie();

        mActivityAddEditBinding.setMovie(mMovie);
        mAddEditActivityClickHandlers = new AddEditActivityClickHandlers(this);
        mActivityAddEditBinding.setClickHandlers(mAddEditActivityClickHandlers);

        Intent intent = getIntent();

        if (intent.hasExtra(MOVIE_ID)){
            setTitle("Edit movie");

            mMovie.setMovieName(intent.getStringExtra(MOVIE_NAME));
            mMovie.setMovieDescription(intent.getStringExtra(MOVIE_DESCRIPTION));
        } else {
            setTitle("Add movie");

        }
    }

    public class AddEditActivityClickHandlers{

        Context context;

        public AddEditActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onOkButtonClicked(View view){

            if (mMovie.getMovieName() == null){
                Toast.makeText(context, "Please input the name", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent1 = new Intent();
                intent1.putExtra(MOVIE_NAME, mMovie.getMovieName());
                intent1.putExtra(MOVIE_DESCRIPTION, mMovie.getMovieDescription());
                setResult(RESULT_OK, intent1);
                finish();
            }
        }
    }
}