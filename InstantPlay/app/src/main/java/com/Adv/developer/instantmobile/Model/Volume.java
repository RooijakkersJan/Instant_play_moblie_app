package com.Adv.developer.instantmobile.Model;

public class Volume {
    public boolean selected;
    public String vol="";

    public boolean isSelected() {
        return selected;
    }

    public String getVol()
    {
        return vol;
    }

    public void setVol(String id) {
        vol=id;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
