package com.Adv.developer.instantmobile;

public class Constants {

    public static final String SERVER = "https://applicationaddons.com/api/";
  // public static final String SERVER = "https://api.htvled.com/api/";

    // public static final String SERVER = "https://api.lcdmedia-audio.com/api/";

    public static final String GetPlayerToken = SERVER + "FillTokenInfo";//DeviceId
    public static final int GetPlayerToken_TAG = 1;

    public static final String SchdPlaylist= SERVER + "GetPlaylistsSchedule";//DeviceId,TokenNo,UserName
    public static final int Playlist_Schd_TAG= 2;

    public static final String PlaylistTitles= SERVER + "GetInstantPlaySpecialPlaylistsTitles";//DeviceId,TokenNo,UserName
    public static final int Playlist_Titles_TAG= 3;


    public static final String CHECKCustomerLogin = SERVER + "CustomerLogin";//DeviceId,TokenNo,UserName
    public static final int CHECK_CustomerLogin_TAG= 5;

    public static String lcd="InstantPlaylcdmedia";
    public static String smc="InstantPlay";
    public static final String CHECKInstantPlay = SERVER + smc;//DeviceId,TokenNo,UserName
    public static final int CHECK_InstantPlay_TAG= 6;

    public static final String GetkbdPlaylist = SERVER + "GetInstantMobileAnnouncement";//DeviceId,TokenNo,UserName
    public static final int CHECK_KBdPlaylist_TAG= 7;

    public static final String GetkbdContents = SERVER + "GetSplPlaylistTitlesLive";//DeviceId,TokenNo,UserName
    public static final int GetkbdContents_TAG= 8;

    public static final String GetkbdPlayerToken = SERVER + "FillTokenInfo";//DeviceId
    public static final int GetkbdPlayerToken_TAG = 9;

    public static final String AdvTitles= SERVER + "AdvtSchedule";//DeviceId,TokenNo,UserName
    public static final int Adv_Titles_TAG= 10;


 public static final String AllPlaylist= SERVER + "GetClientPlaylists";//DeviceId,TokenNo,UserName
 public static final int AllPlaylist_TAG= 11;


 public static final String AllPlaylistSchd= SERVER + "SaveFutureSchedule";//DeviceId,TokenNo,UserName
 public static final int AllPlaylistSchd_TAG= 12;

 public static final String AllPlaylistVol= SERVER + "SavePlayerVolume";//DeviceId,TokenNo,UserName
 public static final int AllPlaylistVol_TAG= 13;

 public static String lcdupd="ForceUpdatelcdMedia";
 public static String smcupd="ForceUpdate";
 public static final String PlayerUpdate= SERVER + smcupd;//DeviceId,TokenNo,UserName
 public static final int PlayerUpdate_TAG= 14;

 public static final String AlarmTitles= SERVER + "GetClientAlarm";//DeviceId,TokenNo,UserName
 public static final int Alarm_Titles_TAG= 15;
 public static final String GroupAlarmSend= SERVER + "GetClientPlayerGroups";//DeviceId,TokenNo,UserName
 public static final int Alarm_Send_TAG= 16;
}
