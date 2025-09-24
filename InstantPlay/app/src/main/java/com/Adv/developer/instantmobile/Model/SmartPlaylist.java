package com.Adv.developer.instantmobile.Model;

import java.io.Serializable;

public class SmartPlaylist implements Serializable {


    private String splPlaylist_Id ="";
    private String splPlaylist_Name = "";

    public String getsplPlaylist_Id() {
        return splPlaylist_Id;
    }
    public void setsplPlaylist_Id(String splPlaylist_Id1) {
        splPlaylist_Id= splPlaylist_Id1;
    }


    public String getsplPlaylist_Name() {

        return splPlaylist_Name;
    }
    public void setsplPlaylist_Name(String splPlaylist_Name1) {
        splPlaylist_Name= splPlaylist_Name1;
    }


}
