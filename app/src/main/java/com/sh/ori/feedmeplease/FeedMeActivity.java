package com.sh.ori.feedmeplease;

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

    public Map picMap;
    public void initializePicMap(){
//        picMap.put("almunds",new FoodPicInfo("almunds",R.drawable.almunds_expert,R.drawable.almunds_expert_v);
//        picMap.put("basil",new FoodPicInfo("basil", R.drawable.basil_expert,R.drawable.basil_expert_v_));
        picMap.put("bread",R.drawable.bread_basic);
        picMap.put("broccoli",R.drawable.broccoli_medium);
        picMap.put("butter",R.drawable.butter_medium);
        picMap.put("carrot",R.drawable.carrot_medium);
        picMap.put("chalapinio",R.drawable.chalapinio_expert);
        picMap.put("cheese",R.drawable.cheese_medium);
        picMap.put("cream",R.drawable.cream_expert);
//        picMap.put("eggplant",R.drawable.eggplant_medium);
//        picMap.put("eggs",R.drawable.eggs_basic);
//        picMap.put("fish",R.drawable.fish_basic);
//        picMap.put("garlic",R.drawable.garlic_expert);
//        picMap.put("lemon",R.drawable.lemon_medium);
//        picMap.put("milk",R.drawable.milk_basic);
//        picMap.put("mushroom",R.drawable.mushroom_medium);
//        picMap.put("pasta",R.drawable.pasta_basic);
//        picMap.put("rice",R.drawable.rice_basic);
//        picMap.put("thyme",R.drawable.thyme_expert);
//        picMap.put("tomato",R.drawable.tomato_basic);
//        picMap.put("wallnuts",R.drawable.wallnuts_expert);

    }
    static ArrayList<String> foodList1 = new ArrayList<>(Arrays.asList("bread","thyme","tomato","lemon"));
    static ArrayList<String> foodList2 = new ArrayList<>(Arrays.asList("cheese","carrot","eggs"));
    static ArrayList<String> foodList3 = new ArrayList<> ( Arrays.asList("bread","butter","pasta","rice"));
    static int listCounter = 1;
    ImageButton imBt1,imBt2,imBt3,imBt4,imBt5,imBt6,imBt7;
    Button btAddPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me);
        picMap = new HashMap();
//        initializePicMap();
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

        for(String productName: productList){
            int counter = 1;
            if(counter <= 7) {
                ImageButton curImBt = getImageButton(counter++);
                if(curImBt != null){
                    curImBt.setTag(productName);
                    Log.d("notes","prodName: "+productName+" val: "+picMap.get(productName));
                    curImBt.setImageResource((int)picMap.get(productName));
//                    curImBt.setImageResource(R.drawable.p1);

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
