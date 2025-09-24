package com.Adv.developer.instantmobile.Model;

public class KbdPlaylist {
    private boolean selected;
    String playlistid="";
    String playlistname="";
    String cat="";

    public String getplaylistid()
    {
        return playlistid;
    }

    public void setplaylistid(String id) {
        playlistid=id;
    }

    public String getPlaylistname()
    {
        return playlistname;
    }

    public void setPlaylistname(String name) {
        playlistname=name;
    }

    public String getCat()
    {
        return cat;
    }

    public void setcat(String id) {
        cat=id;
    }
}
