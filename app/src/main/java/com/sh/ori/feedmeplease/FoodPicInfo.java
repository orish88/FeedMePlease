package com.sh.ori.feedmeplease;

/**
 * Created by Ori on 5/18/2017.
 */

public class FoodPicInfo {

    String foodName;
    private int uncheckedPic;
    private int checkedPic;
    public FoodPicInfo(String foodName, int uncheckedPic, int checkedPic) {
        this.foodName = foodName;
        this.uncheckedPic = uncheckedPic;
        this.checkedPic = checkedPic;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getUncheckedPic() {
        return uncheckedPic;
    }

    public void setUncheckedPic(int uncheckedPic) {
        this.uncheckedPic = uncheckedPic;
    }

    public int getCheckedPic() {
        return checkedPic;
    }

    public void setCheckedPic(int checkedPic) {
        this.checkedPic = checkedPic;
    }
}
