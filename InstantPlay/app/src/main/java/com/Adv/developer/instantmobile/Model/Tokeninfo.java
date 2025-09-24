package com.Adv.developer.instantmobile.Model;

public class Tokeninfo {
    public boolean selected;
    public String tokenid="";
    public  String location="";

   public String tokeninfo="";

    public String getToken()
    {
        return tokenid;
    }

    public void setToken(String id) {
        tokenid=id;
    }

    public String getTokeninfo()
    {
        return tokeninfo;
    }

    public void setTokeninfo(String id) {
        tokeninfo=id;
    }

    public void setloc(String id) {
        location=id;
    }

    public String getlocinfo()
    {
        return location;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
