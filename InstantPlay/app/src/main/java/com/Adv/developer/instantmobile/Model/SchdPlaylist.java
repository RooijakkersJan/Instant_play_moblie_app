package com.Adv.developer.instantmobile.Model;

public class SchdPlaylist {
    String playlistid="";
    String playlistname="";
    String stdate="";
    String eddate="";


    public String getplaylistid()
    {
        return playlistid;
    }

    public void setplaylistid(String id) {
        playlistid=id;
    }

    public String getStartdate()
    {
        return stdate;
    }

    public void setStdate(String id) {
        stdate=id;
    }

    public String getEnddate()
    {
        return eddate;
    }

    public void setEddate(String id) {
        eddate=id;
    }

    public String getPlaylistname()
    {
        return playlistname;
    }

    public void setPlaylistname(String name) {
        playlistname=name;
    }
}
