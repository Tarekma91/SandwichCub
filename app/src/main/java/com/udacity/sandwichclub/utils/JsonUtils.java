package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEYWORD = "name";
    private static final String MAIN_NAME_KEYWORD ="mainName";
    private static final String ALSO_KNOWN_AS_KEYWORD = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEYWORD = "placeOfOrigin" ;
    private static final String DESCRIPTION_KEYWORD  = "description";
    private static final String IMAGE_KEYWORD = "image";
    private static final String INGREDIENTS_KEYWORD   = "ingredients";
    private static final int ZERO = 0 ;


    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject sandwichName = jsonObject.getJSONObject(NAME_KEYWORD);
            String mainName = sandwichName.getString(MAIN_NAME_KEYWORD);
            JSONArray alsoKnownAsArray = sandwichName.getJSONArray(ALSO_KNOWN_AS_KEYWORD) ;
            List<String> alsoKnownAs = new ArrayList<>();
            if (alsoKnownAsArray.length() > ZERO)
                for (int i = ZERO ; i < alsoKnownAsArray.length() ; i++)
                    alsoKnownAs.add(alsoKnownAsArray.get(i).toString());

            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN_KEYWORD);
            String description = jsonObject.getString(DESCRIPTION_KEYWORD);
            String imageUrl = jsonObject.getString(IMAGE_KEYWORD);

            JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS_KEYWORD);
            List<String> ingredients = new ArrayList<>();
            if (ingredientsArray.length() > ZERO)
                for (int i = ZERO ; i < ingredientsArray.length() ; i++)
                    ingredients.add(ingredientsArray.get(i).toString());

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl , ingredients );

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
