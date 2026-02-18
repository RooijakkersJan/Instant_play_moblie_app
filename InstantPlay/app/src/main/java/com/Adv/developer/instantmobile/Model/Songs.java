package com.Adv.developer.instantmobile.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Songs implements Serializable, Comparator {
    private boolean isSelected;
    private int isChecked=0;
    private String Album_ID = "";
    private String Mediatype = "";
    private String Artist_ID ="";
    private String Title = "";
    private String Title_Url = "";
    private String al_Name = "";
    private String ar_Name = "";
    private String spl_PlaylistId = "";
    private String sch_id = "";
    private String t_Time = "";
    private String Cat = "";
    private String title_Id = "";
    private String audiopromoid = "";

    private int is_Downloaded = 0 ;
    private String SongPath = "";
    private long SerialNo = 0;

    public String logourl="";
    public String audiourl="";
    public String audiopromosize="";

    public String txt="";
    public String casttype="";
    public String promoname="";
    public String logopromoname="";
    public String logoPromoid="";

    public String txtdur="";


    public String getLogourl()
    {
        return logourl;
    }

    public void setText(String id) {
        txt=id;
    }
    public String gettext()
    {
        return txt;
    }
    public void setcastType(String id) {
        casttype=id;
    }
    public String getcastType()
    {
        return casttype;
    }

    public void setAudioPromoName(String id) {
        promoname=id;
    }
    public String getAudioPromoName()
    {
        return promoname;
    }

    public void setlogoPromoName(String id) {
        logopromoname=id;
    }
    public String getlogoPromoName()
    {
        return logopromoname;
    }


    public void setlogoPromoId(String id) {
        logoPromoid=id;
    }
    public String getlogoPromoId()
    {
        return logoPromoid;
    }


    public void setTextduration(String id) {
        txtdur=id;
    }
    public String gettextduration()
    {
        return txtdur;
    }

    public void setLogourl(String id) {
        logourl=id;
    }

    public String getAudiourl()
    {
        return audiourl;
    }

    public void setAudiourl(String id) {
        audiourl=id;
    }

    public String getAudioPromosize()
    {
        return audiopromosize;
    }

    public void setAudioPromosize(String id) {
        audiopromosize=id;
    }

    public String getSongPath() {
        return SongPath;
    }

    public void setSongPath(String songPath) {
        SongPath = songPath;
    }

    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String sch_id) {
        this.sch_id = sch_id;
    }

    public int getIs_Downloaded() {
        return is_Downloaded;
    }

    public void setIs_Downloaded(int is_Downloaded) {
        this.is_Downloaded = is_Downloaded;
    }

    private long _id = 0;
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }


    public String getMediatype() {
        return Mediatype;
    }

    public void setgMediatype(String mtype) {
        Mediatype = mtype;
    }

    public String getAlbum_ID() {
        return Album_ID;
    }

    public void setAlbum_ID(String album_ID) {
        Album_ID = album_ID;
    }

    public String getArtist_ID() {
        return Artist_ID;
    }

    public void setArtist_ID(String artist_ID) {
        Artist_ID = artist_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle_Url() {
        return Title_Url;
    }

    public void setTitle_Url(String title_Url) {
        Title_Url = title_Url;
    }

    public String getAl_Name() {
        return al_Name;
    }

    public void setAl_Name(String al_Name) {
        this.al_Name = al_Name;
    }

    public String getAr_Name() {
        return ar_Name;
    }

    public void setAr_Name(String ar_Name) {
        this.ar_Name = ar_Name;
    }

    public String getSpl_PlaylistId() {
        return spl_PlaylistId;
    }

    public void setSpl_PlaylistId(String spl_PlaylistId) {
        this.spl_PlaylistId = spl_PlaylistId;
    }

    public String getT_Time() {
        return t_Time;
    }

    public void setT_Time(String t_Time) {
        this.t_Time = t_Time;
    }

    public String getPlaylistCat() {
        return Cat;
    }

    public void setPlaylistCat(String t_Time) {
        Cat = t_Time;
    }


    public String getTitle_Id() {
        return title_Id;
    }

    public void setTitle_Id(String title_Id) {
        this.title_Id = title_Id;
    }


    public long getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(long serialNo) {
        SerialNo = serialNo;
    }

    public boolean getSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCheckvalue() {
        return isChecked;
    }
    public void setCheckValue(int selected) {
        isChecked = selected;
    }

    public String getAudioPromoId() {
        return audiopromoid;
    }

    public void setAudioPromoId(String title_Id) {
        this.audiopromoid = title_Id;
    }

    @Override
    public int compare(Object o, Object t1) {
        return 0;
    }
}
