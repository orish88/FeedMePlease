package com.sh.ori.feedmeplease;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sh.ori.feedmeplease.Recipes.Ingredient;
import com.sh.ori.feedmeplease.Recipes.JSONS;
import com.sh.ori.feedmeplease.Recipes.Recipe;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matcher);



    }


    private void findRecipeMatches(final Ingredient[] ingArr){ //todo: add user object as paramter

        String strIngr = "";
        for(int i=0; i<ingArr.length;i++){
            if(i>0){
                strIngr +="+";
            }
            strIngr += ingArr[i].name;
        }


        String urlGETrecipeJsonString = "https://edamam-recipe-search-and-diet-v1.p.mashape.com/search?q="+strIngr;
//        Log.d("notes","channel in getArrivalTime: "+CHANNEL);
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequestGET = new StringRequest(Request.Method.GET, urlGETrecipeJsonString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Recipe[] recipes;
                        String jsonStr = response.substring(0);
                        recipes = Recipe.getRecipes(ingArr,jsonStr);
                        Log.println(Log.INFO,"Omer","imageURL:");
                        Log.println(Log.INFO,"Omer",recipes[0].getImageURL());
                        Log.println(Log.INFO,"Omer","end");
                    }

                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        //headers.put("Content-Type", "application/json");
                        String key1 = "X-Mashape-Key";
                        String value1 = "k0od1PUczFmshycscTJ1SsAYQUKwp18ABlsjsnMnnKXqYYCkBb";
                        String key2 = "Accept";
                        String value2 = "application/json";
                        headers.put(key1, value1);
                        headers.put(key2, value2);
                        return headers;
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
