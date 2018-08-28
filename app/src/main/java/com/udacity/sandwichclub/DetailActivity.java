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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EMPTY_STRING = "";
    public static final String COMMA = " , ";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final int ZERO = 0;

    @BindView(R.id.image_iv)
    ImageView sandwichPhotoIv;
    @BindView(R.id.main_name_tv)
    TextView mainNameTv;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownTv;
    @BindView(R.id.also_known_label_tv)
    TextView alsoKnownLabelTv;
    @BindView(R.id.origin_tv)
    TextView originTv;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }else {
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
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        Picasso.get()
                .load(sandwich.getImage())
                .error(android.R.drawable.stat_notify_error)
                .into(sandwichPhotoIv);

        mainNameTv.setText(sandwich.getMainName());

        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        if (!alsoKnown.isEmpty()) {
            alsoKnownTv.setText(alsoKnown.get(ZERO));
        } else {
            alsoKnownLabelTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        }

        originTv.setText(sandwich.getPlaceOfOrigin());

        List<String> ingredients = sandwich.getIngredients();
        ingredientsTv.setText(EMPTY_STRING);
        for (String ingredient : ingredients) ingredientsTv.append(ingredient + COMMA);

        descriptionTv.setText(sandwich.getDescription());
    }
}
