package com.sh.ori.feedmeplease;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sh.ori.feedmeplease.Recipes.Ingredient;
import com.sh.ori.feedmeplease.Recipes.Recipe;

public class MatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matcher);
    }

    private void findRecipeMatches(final Ingredient[] ingArr){ //todo: add user object as paramter

        String urlGETrecipeJsonString = "https://edamam-recipe-search-and-diet-v1.p.mashape.com/search?q=\"+strIngr";
//        Log.d("notes","channel in getArrivalTime: "+CHANNEL);
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequestGET = new StringRequest(Request.Method.GET, urlGETrecipeJsonString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonStr = response.substring(0);
                        Recipe.getRecipes(ingArr,jsonStr);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequestGET);


    }
}
