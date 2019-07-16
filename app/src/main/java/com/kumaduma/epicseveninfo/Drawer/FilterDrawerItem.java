package com.kumaduma.epicseveninfo.Drawer;

public class FilterDrawerItem {
    private String text;
    private String tagText;
    private String iconUrl;
    private int icon;
    private boolean selected;
    private boolean hasUrl;

    public FilterDrawerItem(String text, String tagText, int icon, boolean selected){
        this.text = text;
        this.tagText = tagText;
        this.icon = icon;
        this.selected = selected;
        this.hasUrl = false;
    }

    public FilterDrawerItem(String text, String tagText, String iconUrl, boolean selected){
        this.text = text;
        this.tagText = tagText;
        this.setIconUrl(iconUrl);
        this.selected = selected;
        this.hasUrl = true;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean hasUrl(){
        return hasUrl;
    }
}
