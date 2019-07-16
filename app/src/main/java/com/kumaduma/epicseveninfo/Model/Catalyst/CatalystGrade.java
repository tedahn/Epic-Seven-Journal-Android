package com.kumaduma.epicseveninfo.Model.Catalyst;

public class CatalystGrade {
    private int gradeValue;

    public CatalystGrade(int grade){
        gradeValue = grade;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeByString(){
        if (gradeValue == 1){
            return "";
        } else if (gradeValue == 2){
            return "";
        } else if (gradeValue == 3){
            return "";
        } else if (gradeValue == 4){
            return "";
        } else if (gradeValue == 5){
            return "";
        } else {
            return "";
        }
    }
}
