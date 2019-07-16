package com.kumaduma.epicseveninfo.Model.Hero.Skills;

import java.util.List;

public class Resources {
    private String item;
    private int qty;

    public Resources(){

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String toString(List<Resources> list){
        StringBuilder str = new StringBuilder();
        int c = 0;
        for(Resources res : list){
            if (c>0) str.append(", ");
            String tempRes = res.getItem();
            if (tempRes.length() > 1) tempRes = tempRes.substring(0,1).toUpperCase() + tempRes.substring(1);
            while(tempRes.contains("-") && tempRes.indexOf("-")+2 != tempRes.length()){
                int p = tempRes.indexOf("-");
                tempRes = tempRes.substring(0,p) + " " + tempRes.substring(p+1,p+2).toUpperCase() + tempRes.substring(p+2);
            }
            str.append(res.getQty() + "x "+tempRes);
            c++;
        }
        return str.toString();
    }
}
