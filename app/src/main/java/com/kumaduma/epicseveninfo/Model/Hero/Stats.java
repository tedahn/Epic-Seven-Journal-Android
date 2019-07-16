package com.kumaduma.epicseveninfo.Model.Hero;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {
    private int cp;
    private int atk;
    private int hp;
    private int spd;
    private int def;
    private float chc;
    private float chd;
    private float eff;
    private float efr;
    private float dac;

    private Map<String,String> statsNameMap;

    public Stats(){
        statsNameMap = new HashMap<>();
        statsNameMap.put("cp","CP");
        statsNameMap.put("atk","Attack");
        statsNameMap.put("hp","Health");
        statsNameMap.put("spd","Speed");
        statsNameMap.put("def","Defense");
        statsNameMap.put("chc","Critical Hit Chance");
        statsNameMap.put("chd","Critical Hit Damage");
        statsNameMap.put("eff","Effectiveness");
        statsNameMap.put("efr","Effect Resistance");
        statsNameMap.put("dac","Dual Attack Chance");
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public float getChc() {
        return chc;
    }

    public void setChc(float chc) {
        this.chc = chc;
    }

    public float getChd() {
        return chd;
    }

    public void setChd(float chd) {
        this.chd = chd;
    }

    public float getEff() {
        return eff;
    }

    public void setEff(float eff) {
        this.eff = eff;
    }

    public float getEfr() {
        return efr;
    }

    public void setEfr(float efr) {
        this.efr = efr;
    }

    public float getDac() {
        return dac;
    }

    public void setDac(float dac) {
        this.dac = dac;
    }

    public List<String> getStatAsList(){
        List<String> list = new ArrayList<>();
        list.add(""+cp);
        list.add(""+atk);
        list.add(""+hp);
        list.add(""+spd);
        list.add(""+def);
        DecimalFormat df = new DecimalFormat("0.0");
        list.add(df.format(chc*100)+"%");
        list.add(df.format(chd*100)+"%");
        list.add(df.format(eff*100)+"%");
        list.add(df.format(efr*100)+"%");
        list.add(df.format(dac*100)+"%");
        return list;
    }

    public List<String> getStatsNameAsList(){
        List<String> list = new ArrayList<>();
        list.add(("cp"));
        list.add(("atk"));
        list.add(("hp"));
        list.add(("spd"));
        list.add(("def"));
        list.add(("chc"));
        list.add(("chd"));
        list.add(("eff"));
        list.add(("efr"));
        list.add(("dac"));
        return list;
    }

    public String getStatName(String stat){
        if (statsNameMap.containsKey(stat)){
            return statsNameMap.get(stat);
        } else {
            return stat;
        }
    }

    public String convertStatDouble(double stat){
        String str;
        if (stat <= 1){
            DecimalFormat df = new DecimalFormat("0.0");
            str = df.format(stat * 100) + "%";
        } else {
            str = Integer.toString((int) stat);
        }
        return str;
    }

}
