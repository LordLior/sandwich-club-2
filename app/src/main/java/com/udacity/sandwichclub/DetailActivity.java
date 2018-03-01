package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Adding up button
        // https://developer.android.com/training/implementing-navigation/ancestral.html#up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int imagePlaceHolder = R.drawable.loading_sandwich;

        ImageView sandwichIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(imagePlaceHolder)
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        TextView originLabelTv = findViewById(R.id.origin_label_tv);
        TextView descriptionLabelTv = findViewById(R.id.description_label_tv);
        TextView ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        TextView alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);

        String description = sandwich.getDescription();

        if (description.isEmpty()) {
            descriptionLabelTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionLabelTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(description);
            descriptionTv.setVisibility(View.VISIBLE);
        }

        List<String> ingredients = sandwich.getIngredients();

        if (ingredients.size() == 0) {
            ingredientsLabelTv.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        } else {
            ingredientsLabelTv.setVisibility(View.VISIBLE);
            ingredientsTv.setText(listToDisplayText(ingredients));
            ingredientsTv.setVisibility(View.VISIBLE);
        }

        String origin = sandwich.getPlaceOfOrigin();

        if (origin.isEmpty()) {
            originLabelTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            originLabelTv.setVisibility(View.VISIBLE);
            originTv.setText(origin);
            originTv.setVisibility(View.VISIBLE);
        }


        List<String> alsoKnown = sandwich.getAlsoKnownAs();

        if (alsoKnown.size() == 0) {
            alsoKnownLabelTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        } else {
            alsoKnownLabelTv.setVisibility(View.VISIBLE);
            alsoKnownTv.setText(listToDisplayText(alsoKnown));
            alsoKnownTv.setVisibility(View.VISIBLE);
        }


    }

    private String listToDisplayText(List<String> list) {

        String text = "";
        String listIndicator = "- ";

        if (list.size() == 1) {
            listIndicator = "";
        }

        for (int i = 0; i < list.size(); i++) {

            text += listIndicator + list.get(i);
            if (i < list.size() - 1) {
                text += "\n";
            }

        }

        return text;
    }

}
