package com.sh.ori.feedmeplease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FeedMeActivity extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<String> foodList1 = new ArrayList<>(Arrays.asList("eggs_basic","bread_basic","fish_basic","milk_basic","pasta_basic","rice_basic","tomato_basic"));
    static ArrayList<String> foodList2 = new ArrayList<>(Arrays.asList("cheese","carrot","eggs"));
    static ArrayList<String> foodList3 = new ArrayList<> ( Arrays.asList("bread","butter","pasta","rice"));
    static int listCounter = 1;
    ImageButton imBt1,imBt2,imBt3,imBt4,imBt5,imBt6,imBt7;
    Button btAddPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me);

        btAddPic = (Button) findViewById(R.id.bt_feed_me_submit);
        btAddPic.setOnClickListener(this);
        imBt1 = (ImageButton) findViewById(R.id.im_bt_food1);
        imBt2 = (ImageButton) findViewById(R.id.im_bt_food2);
        imBt3 = (ImageButton) findViewById(R.id.im_bt_food3);
        imBt4 = (ImageButton) findViewById(R.id.im_bt_food4);
        imBt5 = (ImageButton) findViewById(R.id.im_bt_food5);
        imBt6 = (ImageButton) findViewById(R.id.im_bt_food6);
        imBt7 = (ImageButton) findViewById(R.id.im_bt_food7);
        imBt1.setOnClickListener(this);
        imBt2.setOnClickListener(this);
        imBt3.setOnClickListener(this);
        imBt4.setOnClickListener(this);
        imBt5.setOnClickListener(this);
        imBt6.setOnClickListener(this);
        imBt7.setOnClickListener(this);
        addPics(foodList1);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.bt_feed_me_submit):
                listCounter ++;
                switch (listCounter){
                    case(2):
                        addPics(foodList2);
                        break;
                    case(3):
                        addPics(foodList3);
                        break;
                    default:
                        Intent matcherAct = new Intent(getApplicationContext(), MatcherActivity.class);
                        startActivity(matcherAct);
//                         GO to new activity
                }
        }
    }

    private void addPics(ArrayList<String> productList){
        Log.d("notes   " ,"****************************************************");
        for(String productName: productList){

            int counter = 1;
            if(counter <= 7) {
                ImageButton curImBt = getImageButton(counter++);
                if(curImBt != null){
                    Context context = curImBt.getContext();
                    int id = context.getResources().getIdentifier(productName, "drawable", context.getPackageName());
                    Log.d("notes   " ,""+id);
                    curImBt.setImageResource(id);
//ded

                }
            }
        }
    }


    private ImageButton getImageButton(int i) {
        switch (i){
            case(1):
                return imBt1;
            case(2):
                return imBt2;
            case(3):
                return imBt3;
            case(4):
                return imBt4;
            case(5):
                return imBt5;
            case(6):
                return imBt6;
            case(7):
                return imBt7;
        }
        return null;

    }





}
