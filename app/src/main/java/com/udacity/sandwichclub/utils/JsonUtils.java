package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // Check if json string is empty
        if (json.isEmpty()) {
            return null;
        }

        try {

            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject jsonNameObject = jsonSandwich.getJSONObject("name");

            String mainName = jsonNameObject.getString("mainName");

            // Return an empty object if we don't have a main name for it
            if (mainName == null) {
                return null;
            }

            // ArrayList<String> alsoKnownAs = new ArrayList<String>();

            List<String> alsoKnownAs = toList(jsonNameObject.getJSONArray("alsoKnownAs"));

            String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            String description = jsonSandwich.getString("description");
            String image = jsonSandwich.getString("image");

            List<String> ingredients = toList(jsonSandwich.getJSONArray("ingredients"));

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            return null;
        }


    }

    // Convert Android JSONObject/JSONArray to a standard Map/List.
    // Modified code from https://gist.github.com/codebutler/2339666
    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.get(i));
        }
        return list;
    }

}
