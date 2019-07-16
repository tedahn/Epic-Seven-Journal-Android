package com.kumaduma.epicseveninfo.Model.Hero;

import com.kumaduma.epicseveninfo.R;

public class Rarity {
    public Rarity(){ }

    public int getStarId(int rarity){
        if (rarity == 1){
            return R.drawable.star_one;
        } else if (rarity == 2){
            return R.drawable.star_two;
        } else if (rarity == 3){
            return R.drawable.star_three;
        } else if (rarity == 4){
            return R.drawable.star_four;
        } else if (rarity == 5){
            return R.drawable.star_five;
        } else {
            return -1;
        }
    }
}
