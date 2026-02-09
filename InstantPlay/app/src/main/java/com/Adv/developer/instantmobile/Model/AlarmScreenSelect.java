package com.Adv.developer.instantmobile.Model;

public class AlarmScreenSelect {
    public boolean selected;
    public String grpname="";
    public String grpid="";
    public String player="";



    public boolean isSelected() {
        return selected;
    }

    public String getGrpId()
    {
        return grpid;
    }

    public void setPlayers(String id) {
        player=id;
    }
    public String getPlayers()
    {
        return player;
    }

    public void setGrpId(String id) {
        grpid=id;
    }

    public String getGrp()
    {
        return grpname;
    }

    public void setGrp(String id) {
        grpname=id;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
