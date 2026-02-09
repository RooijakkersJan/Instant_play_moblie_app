package com.Adv.developer.instantmobile.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Adv.developer.instantmobile.Adapter.AlaramAdapter;
import com.Adv.developer.instantmobile.Adapter.AlarmScreenPop;
import com.Adv.developer.instantmobile.Adapter.AllPlaylistAdapter;
import com.Adv.developer.instantmobile.Adapter.MyAdapter;
import com.Adv.developer.instantmobile.Adapter.PlaylistAdapter;
import com.Adv.developer.instantmobile.Adapter.SongAdapter;
import com.Adv.developer.instantmobile.Adapter.VolumeAdapterPop;
import com.Adv.developer.instantmobile.AdvikonPreference;
import com.Adv.developer.instantmobile.Constants;
import com.Adv.developer.instantmobile.CustomSpinner;
import com.Adv.developer.instantmobile.Model.AlarmScreenSelect;
import com.Adv.developer.instantmobile.Model.AlarmSpecifications;
import com.Adv.developer.instantmobile.Model.AllPlaylist;
import com.Adv.developer.instantmobile.Model.KbdPlaylist;
import com.Adv.developer.instantmobile.Model.SchdPlaylist;
import com.Adv.developer.instantmobile.Model.Songs;
import com.Adv.developer.instantmobile.Model.Tokeninfo;
import com.Adv.developer.instantmobile.Model.Volume;
import com.Adv.developer.instantmobile.MySQLiteHelper;
import com.Adv.developer.instantmobile.Apihandle.OkHttpUtil;
import com.Adv.developer.instantmobile.R;
import com.Adv.developer.instantmobile.SharedPreferenceUtil;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class HomeActivity extends AppCompatActivity implements OkHttpUtil.OkHttpResponse, View.OnClickListener, CustomSpinner.OnSpinnerEventsListener,CompoundButton.OnCheckedChangeListener {
    public CustomSpinner spinnerfrPlayertoken,spinnerforvol,spinnerfrAlarmtxt;
    public Spinner spinnerfrPlaylist,spinnerfrkbdToken;
    public int currenttxtposition=0;
    String radioselect="";
    public EditText txtevacsend;
    public RadioGroup radioGroup;
    public TextView txtdur;
    public Dialog pickerDialogpopup,pickerVol,pickerAlarmScreen;
    public String strtime="",endtime="",user_name="";

    public CheckBox ch;
    public EditText dursec;
    public ImageView schdplaylist,sendinstantalarmsg,stopalarmdisp;
    public MyAdapter myAdapter;
    public VolumeAdapterPop voladapt;
    public AlarmScreenPop screenAdapt;
    public Dialog pickerDialog;
    public ArrayList<SchdPlaylist> schplay=new ArrayList<SchdPlaylist>();
    public RadioButton radioButtonInstant,radioButtonKbd,radioButtonAds;
    public ArrayList<AllPlaylist> allplay=new ArrayList<AllPlaylist>();

    RelativeLayout spinlayout;
    public TextView tv, tv1,tv3,addevac;

    public ImageView tv2,volset;
    private String selplaylistid="";
    private String selcat="";

    private String selkbtoken="";
    public SongAdapter songAdapter;
    public AlaramAdapter alarmAdapter;
    public PlaylistAdapter playlistAdapter;

    public AllPlaylistAdapter allplaylistAdapter;


    public ListView lvSongs;
    Typeface fontBold;
    CircularProgressView pv;

    public ArrayList<String> arrspin = new ArrayList<String>();
    public List<KbdPlaylist>  listmap = new ArrayList<KbdPlaylist>();
    public List<Volume> arrvolspin = new ArrayList<Volume>();
    public List<AlarmScreenSelect> arralrmgrpscreen = new ArrayList<AlarmScreenSelect>();

    public List<String> arralrmgrpscreenaftersel = new ArrayList<String>();

    public ArrayList<String>  arrtokenkbd= new ArrayList<String>();

    public HashMap<String, List<KbdPlaylist>> arrkeybdplaylist = new HashMap<>();

    public ArrayList<Tokeninfo> arrtoken = new ArrayList<>();
    public ArrayList<Songs> arrsongs = new ArrayList<Songs>();
    public ArrayList<Songs> arrcomb = new ArrayList<Songs>();

    public ArrayList<String> arrtokentosend = new ArrayList<String>();
    public ArrayList<Tokeninfo> arrtokentodisp = new ArrayList<Tokeninfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spinlayout = (RelativeLayout) findViewById(R.id.spinnerlay);
        pv = (CircularProgressView) findViewById(R.id.progress_view);
        spinnerfrPlayertoken = (CustomSpinner) findViewById(R.id.dropdowntokenlist);
        spinnerfrPlaylist= (Spinner) findViewById(R.id.dropdownplaylist);
        stopalarmdisp=(ImageView)findViewById(R.id.stop);
        spinnerfrkbdToken=(Spinner) findViewById(R.id.dropdownkbdtoken);
        fontBold = Typeface.createFromAsset(HomeActivity.this.getAssets(), this.getString(R.string.century_font_bold));
        tv = (TextView) findViewById(R.id.txtplayer);
        tv3 = (TextView) findViewById(R.id.txtClientname);
        tv1 = (TextView) findViewById(R.id.txtplaylist);
        tv2 = (ImageView) findViewById(R.id.txtlogout);
        addevac= (TextView) findViewById(R.id.txtadd);
        volset=(ImageView) findViewById(R.id.mastervol);

        schdplaylist=(ImageView) findViewById(R.id.calenschd);
        radioButtonInstant =(RadioButton) findViewById(R.id.radioInstant);
        radioButtonKbd =(RadioButton) findViewById(R.id.radioKbd);
        radioButtonAds =(RadioButton) findViewById(R.id.radioAdPlaylist);
        tv.setTypeface(fontBold);
        tv1.setTypeface(fontBold);
        lvSongs = (ListView) findViewById(R.id.lvSongs);
        String clientname= SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.Clientname);
        tv3.setText(clientname);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tv2.setOnClickListener(this);
        volset.setOnClickListener(this);
        addevac.setOnClickListener(this);
        stopalarmdisp.setOnClickListener(this);
        schdplaylist.setOnClickListener(this);
        radioButtonKbd.setOnCheckedChangeListener(this);
        radioButtonAds.setOnCheckedChangeListener(this);
        radioButtonInstant.setOnCheckedChangeListener(this);
        getTokenInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();
       // getGroupsForAlarmText();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtlogout: {
                popuplogout();
                break;

            }

            case R.id.mastervol: {
                popupVol();
                break;
            }

            case R.id.txtadd: {
                popupAlarmtextGroups(90);
                break;
            }

            case R.id.stop: {
                instantstopAlarm("");
                break;
            }

            case R.id.calenschd:
            {
                radioButtonInstant.setChecked(false);
                radioButtonKbd.setChecked(false);
                radioButtonAds.setChecked(false);
                spinnerfrPlaylist.setVisibility(View.GONE);
                lvSongs.setVisibility(View.INVISIBLE);
                getTokenInfo();
                break;

            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radioInstant) {
                arrtokentosend.clear();
                arrtokentodisp.clear();
                radioButtonKbd.setChecked(false);
                radioButtonAds.setChecked(false);
                lvSongs.setVisibility(View.INVISIBLE);
                //spinnerfrPlaylist.setVisibility(View.GONE);
                spinnerfrkbdToken.setVisibility(View.GONE);
                spinnerfrPlaylist.setVisibility(View.VISIBLE);
                addevac.setVisibility(View.INVISIBLE);
                stopalarmdisp.setVisibility(View.INVISIBLE);
                spinnerfrPlayertoken.setVisibility(View.VISIBLE);
                spinnerfrPlaylist.setSelection(0);
                getTokenInfo();


            }

            if (buttonView.getId() == R.id.radioAdPlaylist) {
                radioButtonKbd.setChecked(false);
                radioButtonInstant.setChecked(false);
                if(radioButtonAds.isChecked())
                {

                    getEmergencyAlarmText();
                    spinnerfrPlaylist.setVisibility(View.GONE);
                    lvSongs.setVisibility(View.INVISIBLE);
                    addevac.setVisibility(View.VISIBLE);
                    stopalarmdisp.setVisibility(View.VISIBLE);

                }

            }

           if (buttonView.getId() == R.id.radioKbd) {
                radioButtonInstant.setChecked(false);
                radioButtonAds.setChecked(false);
               addevac.setVisibility(View.INVISIBLE);
               stopalarmdisp.setVisibility(View.INVISIBLE);
               if(radioButtonKbd.isChecked())
                {
                    getTokenInfo();
                    spinnerfrPlaylist.setVisibility(View.GONE);
                    lvSongs.setVisibility(View.INVISIBLE);

                }
            }
        }
    }

    public void getAdvertisements(){

        try{
            JSONObject json = new JSONObject();

            json.put("Cityid", "0");
            json.put("CountryId", "0");
            json.put("CurrentDate", currentFormattedDate());
            json.put("DfClientId",SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            json.put("StateId", "0");
            json.put("TokenId", String.valueOf(arrtokentosend.get(0)));
            json.put("WeekNo", getDayNumberForAdv());

            new OkHttpUtil(HomeActivity.this, Constants.AdvTitles,json.toString(),
                    HomeActivity.this,false,
                    Constants.Adv_Titles_TAG).
                    callRequest();


        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public String currentFormattedDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.US);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }


    public int getDayNumberForAdv(){

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.US);
        Date d = new Date();
        String day = sdf.format(d);

        int dayNumber = 0;
        if (day.equals("Sunday")){
            dayNumber = 1;
        }else if (day.equals("Monday")){
            dayNumber = 2;
        }else if (day.equals("Tuesday")){
            dayNumber = 3;
        }else if (day.equals("Wednesday")){
            dayNumber = 4;
        }else if (day.equals("Thursday")){
            dayNumber = 5;
        }else if (day.equals("Friday")){
            dayNumber = 6;
        }else if (day.equals("Saturday")){
            dayNumber = 7;
        }

        return dayNumber;
    }


    public void getkbdTokeninfo()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clientId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            jsonObject.put("DBType", "Advikon");
            jsonObject.put("IsActiveTokens", "1");
            jsonObject.put("UserId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.UserId));
            new OkHttpUtil(HomeActivity.this, Constants.GetkbdPlayerToken, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.GetkbdPlayerToken_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }


    public void getplaylistfrKeybrd()
    {
        try {
            JSONObject jsonObject = new JSONObject();

            JSONArray array = new JSONArray();

            jsonObject.put("Tokenid",Integer.parseInt(arrtokentosend.get(0)));

            new OkHttpUtil(HomeActivity.this, Constants.GetkbdPlaylist, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.CHECK_KBdPlaylist_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public int getCurrentDayNumber(){

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return getDayNumber(dayOfTheWeek);
    }

    public String getCurrentDayName(){

        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.US);
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }


    public static int getDayNumber(String day){
        int dayNumber = 0;
        if (day.equals("Monday")){
            dayNumber = 1;
        }else if (day.equals("Tuesday")){
            dayNumber = 2;
        }else if (day.equals("Wednesday")){
            dayNumber = 3;
        }else if (day.equals("Thursday")){
            dayNumber = 4;
        }else if (day.equals("Friday")){
            dayNumber = 5;
        }else if (day.equals("Saturday")){
            dayNumber = 6;
        }else if (day.equals("Sunday")){
            dayNumber = 7;
        }

        return dayNumber;
    }
    public void logout() {
        MySQLiteHelper sb = new MySQLiteHelper(HomeActivity.this);
        sb.deletecredential();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void popuplogout() {
        pickerDialogpopup = new Dialog(HomeActivity.this);
        pickerDialogpopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerDialogpopup.setContentView(R.layout.logoutpopup);
        pickerDialogpopup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView txtcancel = (TextView) pickerDialogpopup.findViewById(R.id.btnNo);
        TextView txtcontinue = (TextView) pickerDialogpopup.findViewById(R.id.btnYes);
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialogpopup.dismiss();
            }
        });
        txtcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        pickerDialogpopup.show();


    }

    public void popupVol() {
        arrvolspin.clear();
        int p=100;
        for(int i=0;i<10;i++)
        {
            if(p!=100) {
                Volume model = new Volume();
                model.setVol(String.valueOf(p));
                arrvolspin.add(model);
            }
            p=p-10;
        }
        pickerVol = new Dialog(HomeActivity.this);
        pickerVol.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerVol.setContentView(R.layout.volumepopup);
        pickerVol.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        String volshow=SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.VolPosition);
        spinnerforvol = (CustomSpinner)pickerVol.findViewById(R.id.dropdownvol);
        voladapt = new VolumeAdapterPop(HomeActivity.this, 0, arrvolspin);
        spinnerforvol.setAdapter(voladapt);
        spinnerforvol.setSpinnerEventsListener(this);
        if(!volshow.equals("")) {
            arrvolspin.get(Integer.parseInt(volshow)).setSelected(true);
            spinnerforvol.setSelection(voladapt.getPosition(arrvolspin.get(Integer.parseInt(volshow))));
            voladapt.notifyDataSetChanged();
        }
        pickerVol.show();

       /* Spinner vol = (Spinner) pickerVol.findViewById(R.id.dropdownvol);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item,arrvolspin);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vol.setAdapter(arrayAdapter);
        vol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if(position==0)
                {

                }
                else {
                    sendVol(arrvolspin.get(position));
                   // pickerVol.dismiss();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });*/
    }

    public void popupAlarmtextGroups(int pos) {
        currenttxtposition=pos;
        pickerAlarmScreen = new Dialog(HomeActivity.this);
        pickerAlarmScreen.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerAlarmScreen.setContentView(R.layout.alarmtxtpopup);
        pickerAlarmScreen.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        spinnerfrAlarmtxt = (CustomSpinner)pickerAlarmScreen.findViewById(R.id.dropdownscreens);
        txtevacsend=(EditText) pickerAlarmScreen.findViewById(R.id.edttxtsend);
        dursec=(EditText) pickerAlarmScreen.findViewById(R.id.edttxtsec);
        radioGroup = (RadioGroup)pickerAlarmScreen.findViewById(R.id.groupScreencasttype);
        txtdur = (TextView) pickerAlarmScreen.findViewById(R.id.txtdur);
        ch=(CheckBox) pickerAlarmScreen.findViewById(R.id.checkAlways);
        radioGroup.clearCheck();
        ch.setChecked(false);
        if(pos!=90)
        {
            txtevacsend.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);

        }
        else {
            txtevacsend.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
        }
        sendinstantalarmsg=(ImageView) pickerAlarmScreen.findViewById(R.id.sendinstant);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the ID of the selected RadioButton
                radioselect="";
                RadioButton selectedRadioButton = (RadioButton)pickerAlarmScreen.findViewById(checkedId);
                String text = selectedRadioButton.getText().toString();
                radioselect=text;
            }
        });
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    dursec.setVisibility(View.GONE);
                    txtdur.setVisibility(View.GONE);
                }
                else{
                    dursec.setVisibility(View.VISIBLE);
                    txtdur.setVisibility(View.VISIBLE);

                }
            }
        });
      /*  for(int i=0;i<arralrmgrpscreen.size();i++)
        {
            arralrmgrpscreen.get(i).setSelected(true);

        }*/
        screenAdapt = new AlarmScreenPop(HomeActivity.this, 0, arralrmgrpscreen);
        spinnerfrAlarmtxt.setAdapter(screenAdapt);
        spinnerfrAlarmtxt.setSpinnerEventsListener(this);
        sendinstantalarmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*arralrmgrpscreenaftersel.clear();
                for(int i=0;i<arralrmgrpscreen.size();i++)
                {
                    if(arralrmgrpscreen.get(i).isSelected())
                    {
                        String p=arralrmgrpscreen.get(i).player;
                        if(!p.equalsIgnoreCase("")) {
                            String[] values = p.split(",");

                            // Print the individual values
                            for (String value : values) {
                                if(value!="null") {
                                    arralrmgrpscreenaftersel.add(value);
                                }
                            }
                        }
                    }
                }*/
                if(arrtokentosend.size()>0) {
                    String duration="";
                       String edttext= txtevacsend.getText().toString();
                       if(ch.isChecked())
                       {
                           duration="28800";
                       }
                       else {
                           duration=dursec.getText().toString();
                           if(duration.equals(""))
                           {
                               duration="15";
                           }
                       }
                  //  Toast.makeText(HomeActivity.this, duration, Toast.LENGTH_LONG).show();

                    instanttxtadd(edttext,radioselect,duration);
                }

            }
        });

        pickerAlarmScreen.show();
      /*  Spinner vol = (Spinner) pickerAlarmScreen.findViewById(R.id.dropdownscreens);
        ArrayAdapter<AlarmScreenSelect> arrayAdapter = new ArrayAdapter<AlarmScreenSelect>(HomeActivity.this, android.R.layout.simple_spinner_item,arralrmgrpscreen);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vol.setAdapter(arrayAdapter);
        vol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if(position==0)
                {

                }
                else {
                  //  sendVol(arrvolspin.get(position));
                   // pickerVol.dismiss();
                }


            }
            @Override
            public vo
            id onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });*/
    }

    public void sendVol(String data)
    {
        try {
            JSONObject jsonObject = new JSONObject();

            JSONArray array = new JSONArray();
            for (int i = 0; i < arrtokentosend.size(); i++) {
                array.put(Integer.parseInt(arrtokentosend.get(i)));
            }
            jsonObject.put("tokenIds",array);
            jsonObject.put("volume",data);


            new OkHttpUtil(HomeActivity.this, Constants.AllPlaylistVol, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.AllPlaylistVol_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void sendForceUpdate()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < arrtokentosend.size(); i++) {
                array.put(Integer.parseInt(arrtokentosend.get(i)));
            }
            jsonObject.put("tokenid",array);


            new OkHttpUtil(HomeActivity.this, Constants.PlayerUpdate, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.PlayerUpdate_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }


    @Override
    public void onResponse(String response, int tag) {
        if (response == null || response.equals("") || response.length() < 1) {
            // Utilities.showToast(HomeActivity.this, "Empty response for player statuses");
            return;
        } else {
            switch (tag) {

                case Constants.GetPlayerToken_TAG: {
                    TokenForPlayer(response);
                }
                break;

                case Constants.PlayerUpdate_TAG: {
                    fetchUpdate(response);
                }
                break;
                case Constants.Playlist_Titles_TAG: {
                    PlaylistSong(response);
                }
                break;
                case Constants.Alarm_Titles_TAG: {
                   AlarmText(response); ;
                }
                break;
                case Constants.Alarm_Send_TAG: {
                    GroupAlarm(response); ;
                }
                break;
                case Constants.AllPlaylistVol_TAG: {
                    fetchvol(response);
                }
                break;
                case Constants.AllPlaylistSchd_TAG: {
                    fetchfutureschd(response);
                }
                break;
               /* case Constants.Playlist_Cat_TAG: {
                    PlaylistCatSong(response);
                }
                break;*/


                case Constants.CHECK_KBdPlaylist_TAG: {
                    //spinnerfrPlaylist.setVisibility(View.GONE);
                    kbdplaylistresponse(response);
                }
                break;

                case Constants.GetkbdContents_TAG: {
                    PlaylistSong(response);

                }
                break;

                case Constants.Playlist_Schd_TAG: {
                    fillschdPlaylist(response);
                }
                break;

                case Constants.CHECK_InstantPlay_TAG: {
                    Instantsong(response);
                }
                break;
                case Constants.AllPlaylist_TAG:
                {
                fillAllPlaylist(response);
                }
                break;
            }
        }
    }

    @Override
    public void onError(Exception e, int tag) {

        Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_LONG).show();

    }

    private void GroupAlarm(String response)
    {
        try {
            JSONObject jsonObjectRes = new JSONObject(response);
            String Response = jsonObjectRes.getString("response");
            if(Response.equals("1")) {
                arralrmgrpscreen.clear();
                String jsonArray = jsonObjectRes.getString("data");
                JSONArray arr = new JSONArray(jsonArray);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    AlarmScreenSelect schd = new AlarmScreenSelect();
                    schd.setGrp(jsonObject.getString("GroupName"));
                    schd.setGrpId(jsonObject.getString("groupId"));
                    schd.setPlayers(jsonObject.getString("players"));
                    arralrmgrpscreen.add(schd);
                }
            }

        }
        catch (Exception e)
        {
            e.getCause();
        }
    }

    private void getSchdPlaylist()
    {
        try {
            JSONObject json = new JSONObject();

            json.put("DfClientId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            json.put("TokenId",Integer.parseInt(arrtokentosend.get(0)));
            json.put("WeekNo", getCurrentDayNumber());
            new OkHttpUtil(HomeActivity.this, Constants.SchdPlaylist, json.toString(),
                    HomeActivity.this, false,
                    Constants.Playlist_Schd_TAG).
                    callRequest();
        }
        catch(Exception e)
        {
            e.getCause();
        }
    }

    private void fillschdPlaylist(String response)
    {
        try {

            JSONObject jsonObjectRes = new JSONObject(response);
            String Response = jsonObjectRes.getString("response");
            if(Response.equals("1")) {
                schplay.clear();
                String jsonArray = jsonObjectRes.getString("data");
                JSONArray arr=new JSONArray(jsonArray);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    SchdPlaylist schd=new SchdPlaylist();
                    schd.setplaylistid(jsonObject.getString("splPlaylistId"));
                    schd.setPlaylistname(jsonObject.getString("splPlaylistName"));
                    String stdate=jsonObject.getString("StartTime");
                    String formattedstr=changeDateFormat(stdate);
                    String eddate=jsonObject.getString("EndTime");
                    String formattedend=changeDateFormat(eddate);
                    schd.setStdate(formattedstr);
                    schd.setEddate(formattedend);
                    schplay.add(schd);
                }
                playlistAdapter = new PlaylistAdapter(HomeActivity.this, schplay);
                lvSongs.setAdapter(playlistAdapter);
                lvSongs.setVisibility(View.VISIBLE);            }


        }catch (Exception e)
        {
            e.getCause();
        }

    }

    public static String changeDateFormat(String startTime) {

        String formattedDate = null;

        DateFormat readFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa",Locale.US);
        DateFormat writeFormat = new SimpleDateFormat("HH:mm",Locale.US);
        Date date = null;
        try {
            date = readFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }
        return formattedDate;
    }



    private void fillAllPlaylist(String response)
    {
        try {

                allplay.clear();
                JSONArray arr=new JSONArray(response);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    AllPlaylist ply=new AllPlaylist();
                    ply.setplaylistid(jsonObject.getString("Id"));
                    ply.setPlaylistname(jsonObject.getString("DisplayName"));
                    allplay.add(ply);
                }
                AllPlaylistAdapter plyadapt = new AllPlaylistAdapter(HomeActivity.this, allplay);
                lvSongs.setAdapter(plyadapt);
                lvSongs.setVisibility(View.VISIBLE);


        }catch (Exception e)
        {
            e.getCause();
        }

    }




    private void kbdplaylistresponse(String response)
    {
        try {
            arrkeybdplaylist.clear();
            arrspin.clear();
            selplaylistid="";
            selcat="";
            if (response!=null) {
                arrspin.add("");
                KbdPlaylist modal1=new KbdPlaylist();
                modal1.setplaylistid("");
                modal1.setcat("");
                listmap.add(modal1);
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String playlistname = jsonObj.getString("pName");
                    String playlistid = jsonObj.getString("splid");
                    String cat = jsonObj.getString("category");
                    arrspin.add(playlistname);
                    KbdPlaylist modal=new KbdPlaylist();
                    modal.setplaylistid(playlistid);
                    modal.setcat(cat);
                    listmap.add(modal);
                    arrkeybdplaylist.put(playlistname,listmap);
                }

                int p=arrspin.size();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item,arrspin);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerfrPlaylist.setAdapter(arrayAdapter);
                       // selplaylistid=arrspin.get(0);
                       // getTitlesKbdPlaylist();


                       spinnerfrPlaylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                Object item = adapterView.getItemAtPosition(position);
                                if(!item.equals("")) {
                                   selplaylistid=listmap.get(position).getplaylistid();
                                   selcat=listmap.get(position).getCat();
                                  // getTitle();
                                   getTitlesKbdPlaylist(selplaylistid,selcat);

                                }
                                else
                                {
                                    lvSongs.setVisibility(View.INVISIBLE);

                                    if(arrtokentodisp.size()>0)
                                    {

                                    }
                                    else
                                    {
                                       // arrspin.clear();
                                      //  arrkeybdplaylist.clear();
                                     //arrayAdapter.notifyDataSetChanged();
                                    }
                                }

                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                // TODO Auto-generated method stub
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            e.getCause();
        }



    }


   /* private void getPlaylistcat()
    {
        try {
            spinlayout.setVisibility(View.VISIBLE);
            pv.setVisibility(View.VISIBLE);
            pv.startAnimation();
            lvSongs.setVisibility(View.INVISIBLE);
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < arrtokentosend.size(); i++) {
                array.put(Integer.parseInt(arrtokentosend.get(i)));
            }
            jsonObject.put("Tokenid",array);


            new OkHttpUtil(HomeActivity.this, Constants.PlaylistCat, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.Playlist_Cat_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }*/


    private void getPlaylistTitles() {
        try {
            spinlayout.setVisibility(View.VISIBLE);
            pv.setVisibility(View.VISIBLE);
            pv.startAnimation();
            lvSongs.setVisibility(View.INVISIBLE);
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < arrtokentosend.size(); i++) {
                array.put(Integer.parseInt(arrtokentosend.get(i)));
            }
            jsonObject.put("Tokenid",array);


            new OkHttpUtil(HomeActivity.this, Constants.PlaylistTitles, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.Playlist_Titles_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void Instantsong(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String res = jsonObject.getString("Response");
            if (res.equals("1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(pickerAlarmScreen!=null)
                        {
                            pickerAlarmScreen.dismiss();
                        }
                    }

                });
                Toast.makeText(HomeActivity.this, "Request Send", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(HomeActivity.this, "Request Failed ", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.getCause().toString();
        }
    }





    private void getTokenInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clientId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            jsonObject.put("DBType", "Advikon");
            jsonObject.put("IsActiveTokens", "1");
            jsonObject.put("UserId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.UserId));
            new OkHttpUtil(HomeActivity.this, Constants.GetPlayerToken, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.GetPlayerToken_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }


    private void getEmergencyAlarmText()
    {
        try {
            spinlayout.setVisibility(View.VISIBLE);
            pv.setVisibility(View.VISIBLE);
            pv.startAnimation();
            lvSongs.setVisibility(View.INVISIBLE);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id",SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));

            new OkHttpUtil(HomeActivity.this, Constants.AlarmTitles, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.Alarm_Titles_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    private void getGroupsForAlarmText()
    {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id",SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));

            new OkHttpUtil(HomeActivity.this, Constants.GroupAlarmSend, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.Alarm_Send_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    private void getAllTokenPlaylist() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            jsonObject.put("mediaType", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.mType));
            new OkHttpUtil(HomeActivity.this, Constants.AllPlaylist, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.AllPlaylist_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }



    public void previewsong(String playlistid,String name) {
        pickerDialog = new Dialog(HomeActivity.this);
        pickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerDialog.setContentView(R.layout.custom_pop_preview_song);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pickerDialog.show();
        ImageButton save = (ImageButton) pickerDialog.findViewById(R.id.btn_Save);
        EditText vol = (EditText) pickerDialog.findViewById(R.id.edit_vol);
        TimePicker timePickerstr = (TimePicker) pickerDialog.findViewById(R.id.timePickerfrschd);
        timePickerstr.setIs24HourView(true);
        TimePicker timePickerend = (TimePicker) pickerDialog.findViewById(R.id.timePickerfrschd2);
        timePickerend.setIs24HourView(true);
        strtime="";
        int hourOfDay=timePickerstr.getHour();
        int minute=timePickerstr.getMinute();
        String formattedHour = (hourOfDay < 10) ? "0" + hourOfDay: String.valueOf(hourOfDay);
        String formattedMinute = (minute < 10) ? "0" + minute : String.valueOf(minute);
        strtime=formattedHour+":"+formattedMinute;
        timePickerstr.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                strtime="";
                String formattedHour = (hourOfDay < 10) ? "0" + hourOfDay: String.valueOf(hourOfDay);
                String formattedMinute = (minute < 10) ? "0" + minute : String.valueOf(minute);
                strtime=formattedHour+":"+formattedMinute;

            }
        });
        endtime="";
        int hourOfDay1=timePickerend.getHour();
        int minute1=timePickerend.getMinute();
        String formattedHour1 = (hourOfDay1 < 10) ? "0" + hourOfDay: String.valueOf(hourOfDay);
        String formattedMinute1 = (minute1 < 10) ? "0" + minute : String.valueOf(minute);
        endtime=formattedHour1+":"+formattedMinute1;
        timePickerend.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                endtime="";
                String formattedHour = (hourOfDay < 10) ? "0" + hourOfDay: String.valueOf(hourOfDay);
                String formattedMinute = (minute < 10) ? "0" + minute : String.valueOf(minute);
                endtime=formattedHour+":"+formattedMinute;

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name="70";
                savetempSchedule(playlistid,strtime,endtime,user_name,name);

            }
        });

    }


    public void savetempSchedule(String id,String strtime,String endtime,String vol,String pname)
    {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CustomerId", SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.dfClientId));
            jsonObject.put("FormatId", "0");
            jsonObject.put("PercentageValue", "0");
            jsonObject.put("ScheduleType", "Normal");
            jsonObject.put("volume", vol);
            jsonObject.put("PlaylistId",0);
            jsonObject.put("startDate",currentDate());
            jsonObject.put("EndDate",currentDate());
            jsonObject.put("startTime",strtime);
            jsonObject.put("EndTime",endtime);
            JSONArray jsonweek = new JSONArray();

            jsonObject.put("wList",jsonweek);
            JSONArray jsonarray = new JSONArray();
            JSONObject jsonObjecttoken = new JSONObject();
            for (int i = 0; i < arrtokentosend.size(); i++) {
                jsonObjecttoken.put("tokenId",arrtokentosend.get(i));
                jsonObjecttoken.put("schType","Normal");
                jsonarray.put(jsonObjecttoken);
            }
            jsonObject.put("TokenList",jsonarray);
            JSONArray jsonplay = new JSONArray();
            JSONObject jsonObjectplay = new JSONObject();
             jsonObjectplay.put("wId",String.valueOf(getCurrentDayNumber()));
             jsonObjectplay.put("wName",getCurrentDayName());
            jsonObjectplay.put("sTime",strtime);
            jsonObjectplay.put("eTime",endtime);
            jsonObjectplay.put("splId",id);
            jsonObjectplay.put("Id","0"+id);
            jsonObjectplay.put("FormatId", "0");
            jsonObjectplay.put("PercentageValue", "0");
            jsonObjectplay.put("volume", vol);
            jsonObjectplay.put("pName", pname);
            jsonplay.put(jsonObjectplay);
            jsonObject.put("lstPlaylist",jsonplay);
            new OkHttpUtil(HomeActivity.this, Constants.AllPlaylistSchd, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.AllPlaylistSchd_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }


    }

    public String currentDate(){
        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
        String played_date = simpleDateFormat1.format(calendar.getTime());
        return played_date;

    }


    public void instantplaysong(int position,int checked,String type) {
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("albumid", "0");
            if(type.equals("Stop")) {
                jsonObject.put("PlayType", "Stop");
            }
            jsonObject.put("artistid", arrsongs.get(position).getArtist_ID());
            jsonObject.put("artistname", arrsongs.get(position).getAr_Name());
            jsonObject.put("mediatype", arrsongs.get(position).getMediatype());
            jsonObject.put("id",arrsongs.get(position).getTitle_Id());
            jsonObject.put("title", arrsongs.get(position).getTitle());
            jsonObject.put("srNo", arrsongs.get(position).getSerialNo());
            jsonObject.put("category", arrsongs.get(position).getPlaylistCat());
            jsonObject.put("Repeat", checked);

            if(radioButtonKbd.isChecked()) {
                jsonObject.put("type", "instant");
            }
            else
            {
                jsonObject.put("type", "instant");

            }

            JSONArray jsonarray = new JSONArray();
            if(radioButtonKbd.isChecked())
            {
                jsonarray.put(Integer.parseInt(selkbtoken));
            }

            else
            {
                for (int i = 0; i < arrtokentosend.size(); i++) {
                    jsonarray.put(Integer.parseInt(arrtokentosend.get(i)));
                }
            }

            jsonObject.put("tid", jsonarray);

            new OkHttpUtil(HomeActivity.this, Constants.CHECKInstantPlay, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.CHECK_InstantPlay_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void instantstopAlarm(String type) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("albumid", "0");
            jsonObject.put("PlayType", "Stop");
            jsonObject.put("artistid","" );
            jsonObject.put("artistname", "");
            jsonObject.put("id","0");
            jsonObject.put("mediatype","");
            jsonObject.put("title","");
            jsonObject.put("srNo",0 );
            jsonObject.put("category","Normal" );
            jsonObject.put("type","alarm");
            jsonObject.put("Repeat", 0);
            jsonObject.put("screencasttype", "Event");
            jsonObject.put("audioPromoFileSize", "");
            jsonObject.put("audioPromoUrl", "");
            jsonObject.put("logoPromoUrl", "");
            jsonObject.put("logoPromoFileSize", "");
            jsonObject.put("alarmText", "");
            jsonObject.put("alarmTextDuration", "0");

            JSONArray jsonarray = new JSONArray();
            if(radioButtonKbd.isChecked())
            {
                jsonarray.put(Integer.parseInt(selkbtoken));
            }

            else
            {
                for (int i = 0; i < arrtokentosend.size(); i++) {
                    jsonarray.put(Integer.parseInt(arrtokentosend.get(i)));
                }
            }

            jsonObject.put("tid", jsonarray);

            new OkHttpUtil(HomeActivity.this, Constants.CHECKInstantPlay, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.CHECK_InstantPlay_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }
    public void instanttxt(int pos) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("albumid", "0");
            jsonObject.put("PlayType", "Now");
            jsonObject.put("artistid","" );
            jsonObject.put("artistname", "");
            jsonObject.put("mediatype","");
            jsonObject.put("id",arrsongs.get(pos).getTitle_Id());
            jsonObject.put("title","");
            jsonObject.put("srNo",0 );
            jsonObject.put("category","Normal" );
            jsonObject.put("type","alarm");
            jsonObject.put("Repeat", 0);
            jsonObject.put("audioPromoFileSize", arrsongs.get(pos).getAudioPromosize());
            jsonObject.put("audioPromoUrl", arrsongs.get(pos).getAudiourl());
            jsonObject.put("logoPromoUrl", arrsongs.get(pos).getLogourl());
            jsonObject.put("audioPromoId",arrsongs.get(pos).getAudioPromoId());
            jsonObject.put("logoPromoFileSize", "30");
            jsonObject.put("alarmText", arrsongs.get(pos).gettext());
            jsonObject.put("screencasttype", arrsongs.get(pos).getcastType());
            jsonObject.put("alarmTextDuration", arrsongs.get(pos).gettextduration());

            JSONArray jsonarray = new JSONArray();

            for (int i = 0; i < arrtokentosend.size(); i++) {
                jsonarray.put(Integer.parseInt(arrtokentosend.get(i)));
            }

            jsonObject.put("tid", jsonarray);

            new OkHttpUtil(HomeActivity.this, Constants.CHECKInstantPlay, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.CHECK_InstantPlay_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void instanttxtadd(String txt,String screencasttype,String dur) {
        try {
            if(screencasttype.equals(""))
            {
                screencasttype="Event";
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("albumid", "0");
            jsonObject.put("PlayType", "Now");
            jsonObject.put("artistid","" );
            jsonObject.put("artistname", "");
            jsonObject.put("id","000");
            jsonObject.put("mediatype","");
            jsonObject.put("title","");
            jsonObject.put("srNo",0 );
            jsonObject.put("category","Normal" );
            jsonObject.put("type","alarm");
            jsonObject.put("Repeat", 0);
            jsonObject.put("screencasttype", screencasttype);
            jsonObject.put("audioPromoFileSize", "142942");
            jsonObject.put("audioPromoUrl", "");
            jsonObject.put("logoPromoUrl", "");
            jsonObject.put("logoPromoFileSize", "");
            jsonObject.put("alarmText", txt);
            jsonObject.put("alarmTextDuration", dur);

            JSONArray jsonarray = new JSONArray();

            for (int i = 0; i < arrtokentosend.size(); i++) {
                jsonarray.put(Integer.parseInt(arrtokentosend.get(i)));
            }

            jsonObject.put("tid", jsonarray);

            new OkHttpUtil(HomeActivity.this, Constants.CHECKInstantPlay, jsonObject.toString(),
                    HomeActivity.this, false,
                    Constants.CHECK_InstantPlay_TAG).
                    callRequest();
        } catch (Exception e) {
            e.getCause();
        }
    }



    private void getTitlesKbdPlaylist(String playlistid,String cat)
   {
       try {
           spinlayout.setVisibility(View.VISIBLE);
           pv.setVisibility(View.VISIBLE);
           pv.startAnimation();
           lvSongs.setVisibility(View.INVISIBLE);
           JSONObject jsonObject = new JSONObject();

           jsonObject.put("splPlaylistId",playlistid);

           new OkHttpUtil(HomeActivity.this, Constants.GetkbdContents, jsonObject.toString(),
                   HomeActivity.this, false,
                   Constants.GetkbdContents_TAG).
                   callRequest();
       } catch (Exception e) {
           e.getCause();
       }
   }




    private void PlaylistSong(String response) {

        try {
            if(response.equals("[]"))
            {
                Toast.makeText(HomeActivity.this,"No content",Toast.LENGTH_LONG).show();
                spinlayout.setVisibility(View.GONE);
                pv.setVisibility(View.INVISIBLE);
                pv.stopAnimation();
                return;
            }
            arrcomb.clear();
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String albumid = jsonObject.getString("AlbumID");
                String artistid = jsonObject.getString("ArtistID");
                String artistname = jsonObject.getString("arName");
                String album = jsonObject.getString("alName");
                String titleid = jsonObject.getString("titleId");
                if(selcat.equals(""))
                {
                    selcat="Normal";
                }
                String cat = selcat;
                Long srno=jsonObject.getLong("srno");
                String mtype= jsonObject.getString("mediatype");
                String length = jsonObject.getString("tTime");
                String splid = jsonObject.getString("splPlaylistId");
                String titlename = jsonObject.getString("Title");
                String titleurl = jsonObject.getString("TitleUrl");
                Songs model = new Songs();
                model.setAl_Name(album);
                model.setgMediatype(mtype);
                model.setAlbum_ID(albumid);
                model.setSpl_PlaylistId(splid);
                model.setArtist_ID(artistid);
                model.setAr_Name(artistname);
                model.setTitle(titlename);
                model.setSerialNo(srno);
                model.setTitle_Id(titleid);
                model.setT_Time(length);
                model.setSongPath(titleurl);
                model.setPlaylistCat(cat);
                arrcomb.add(model);
            }
            arrsongs.clear();
           /* if(radioButtonInstant.isChecked())
            {
                List<Songs> songsList= arrcomb.stream()
                        .filter(abc -> abc.getPlaylistCat().equals("Normal"))
                        .collect(Collectors.toList());
                arrsongs.addAll(songsList);

            }
            else
            {
                List<Songs> songsList= arrcomb.stream()
                        .filter(abc -> abc.getPlaylistCat().equals("Ads"))
                        .collect(Collectors.toList());
                arrsongs.addAll(songsList);
            }*/
            arrsongs.addAll(arrcomb);
            arrsongs.size();
            spinlayout.setVisibility(View.GONE);
            pv.setVisibility(View.INVISIBLE);
            pv.stopAnimation();
            songAdapter = new SongAdapter(HomeActivity.this, arrsongs);
            lvSongs.setAdapter(songAdapter);
            lvSongs.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.getCause();
        }

    }


    private void AlarmText(String response) {

        try {
            JSONObject jsonObjectRes = new JSONObject(response);
            String Response = jsonObjectRes.getString("response");
            if(Response.equals("1")) {
                arrsongs.clear();
                String jsonArray = jsonObjectRes.getString("data");
                JSONArray arr = new JSONArray(jsonArray);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    Songs schd = new Songs();
                    schd.setAudiourl(jsonObject.getString("audioPromoUrl"));
                    schd.setTitle_Id(jsonObject.getString("id"));
                    schd.setLogourl(jsonObject.getString("logoPromoUrl"));
                    schd.setAudioPromoId(jsonObject.getString("audioPromoId"));
                    schd.setText(jsonObject.getString("alarmName"));
                    schd.setTextduration(jsonObject.getString("duration"));
                    schd.setAudioPromosize(jsonObject.getString("audioPromoFileSize"));
                    schd.setcastType(jsonObject.getString("screencasttype"));
                    arrsongs.add(schd);
                }

            }
            spinlayout.setVisibility(View.GONE);
            pv.setVisibility(View.INVISIBLE);
            pv.stopAnimation();
            alarmAdapter = new AlaramAdapter(HomeActivity.this, arrsongs);
            lvSongs.setAdapter(alarmAdapter);
            lvSongs.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.getCause();
        }

    }




    public void fetchvol(String response)
    {
        try{
                JSONObject jsonObject =new JSONObject(response);
                if (jsonObject.getString("Responce").equals("1")) {
                    pickerVol.dismiss();
                    sendForceUpdate();
                }

        }catch (Exception e)
        {
            e.getCause();
        }
    }

    public void fetchUpdate (String response)
    {
        try{
                JSONObject jsonObject =new JSONObject(response);
                if (jsonObject.getString("Responce").equals("1")) {

                }

        }catch (Exception e)
        {
            e.getCause();
        }
    }

    public void fetchfutureschd (String response)
    {
        try{

                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("Responce").equals("1")) {
                    pickerDialog.dismiss();
                    sendForceUpdate();
                }


        }catch (Exception e)
        {
            e.getCause();
        }
    }




    private void TokenForPlayer(String response) {
        try {
            arrtoken.clear();
            JSONArray jsonArray = new JSONArray(response);
            String chkadmin = SharedPreferenceUtil.getStringPreference(HomeActivity.this, AdvikonPreference.chkAdmin);
            Tokeninfo model1 = new Tokeninfo();
            Tokeninfo model2 = new Tokeninfo();
            model1.setToken(" ");
            model1.setTokeninfo(" ");
           // arrtoken.add(model1);
            if (chkadmin.equals("true")) {
                model2.setToken("00000");
                model2.setTokeninfo("All Locations");
                arrtoken.add(model2);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                Tokeninfo model = new Tokeninfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String tokenid = jsonObject.getString("tokenid");
                String tokeninfo = jsonObject.getString("city");
                String loc = jsonObject.getString("location");
                String mediatype = jsonObject.getString("MediaType");
                SharedPreferenceUtil.setStringPreference(HomeActivity.this, AdvikonPreference.mType,mediatype);
                model.setToken(tokenid);
                model.setTokeninfo(tokeninfo);
                model.setloc(loc);
                arrtoken.add(model);
            }
            arrtokentosend.clear();
            arrtokentodisp.clear();
            arrtoken.get(0).setSelected(true);
            arrtokentosend.add(arrtoken.get(0).tokenid);
            arrtokentodisp.add(arrtoken.get(0));
            myAdapter = new MyAdapter(HomeActivity.this, 0,
                    arrtoken);
            spinnerfrPlayertoken.setAdapter(myAdapter);
            spinnerfrPlayertoken.setSpinnerEventsListener(this);
            if(radioButtonInstant.isChecked())
            {
                getplaylistfrKeybrd();
            }
            else if(radioButtonKbd.isChecked())
            {
                getSchdPlaylist();
            }
            else {
                if(radioButtonAds.isChecked()) {
                   // getEmergencyAlarmText();
                }
                else {
                    getAllTokenPlaylist();

                }
            }



        } catch (Exception e) {
            e.getCause();
        }
    }


    @Override
    public void onSpinnerOpened(Spinner spin) {
      //  Toast.makeText(HomeActivity.this, "hghgh", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onSpinnerClosed(Spinner spin) {
       String h=spin.getAdapter().getClass().getSimpleName();
        int p=spin.getAdapter().getCount();
        if(p>=5)
        {
            for(int i=0;i<arrvolspin.size();i++) {
                if(arrvolspin.get(i).isSelected())
                {
                    SharedPreferenceUtil.setStringPreference(HomeActivity.this, AdvikonPreference.VolPosition,String.valueOf(i));
                    spinnerforvol.setSelection(voladapt.getPosition(arrvolspin.get(i)));
                    voladapt.notifyDataSetChanged();
                    sendVol(arrvolspin.get(i).getVol());
                }
            }
            return;
        }
        arrtokentosend.clear();
        arrtokentodisp.clear();
        for (int i = 0; i < arrtoken.size(); i++) {
            if (arrtoken.get(i).isSelected()) {
                if (arrtoken.get(i).getTokeninfo().equals(" ") || arrtoken.get(i).getTokeninfo().equals("All Locations")) {
                    // arrtoken.remove(i);
                } else {
                    arrtokentosend.add(arrtoken.get(i).tokenid);
                    arrtokentodisp.add(arrtoken.get(i));
                }
            }

        }
        if (arrtokentodisp.size() > 0) {
            myAdapter.getPosition(arrtokentodisp.get(0));
            spinnerfrPlayertoken.setSelection(myAdapter.getPosition(arrtokentodisp.get(0)));
            lvSongs.setVisibility(View.INVISIBLE);
        } else {
            spinnerfrPlayertoken.setSelection(0);
            spinnerfrPlaylist.setSelection(0);
            lvSongs.setVisibility(View.INVISIBLE);
        }



        if (arrtokentosend.size() > 0 && radioButtonInstant.isChecked() )
        {
        //   getPlaylistTitles();
            spinnerfrPlaylist.setVisibility(View.VISIBLE);
            getplaylistfrKeybrd();

        }
        else {
        //    getPlaylistTitles();

            if (arrtokentosend.size() > 0 && radioButtonKbd.isChecked()) {
                spinnerfrPlaylist.setVisibility(View.GONE);
                getSchdPlaylist();

            }
            if (radioButtonAds.isChecked()) {
                spinnerfrPlaylist.setVisibility(View.GONE);
                getEmergencyAlarmText();

            }
        }


    }


}
