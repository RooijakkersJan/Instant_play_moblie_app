package com.Adv.developer.instantmobile.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Adv.developer.instantmobile.AdvikonPreference;
import com.Adv.developer.instantmobile.Constants;
import com.Adv.developer.instantmobile.MySQLiteHelper;
import com.Adv.developer.instantmobile.Apihandle.OkHttpUtil;
import com.Adv.developer.instantmobile.R;
import com.Adv.developer.instantmobile.SharedPreferenceUtil;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements OkHttpUtil.OkHttpResponse, View.OnClickListener {
    private EditText edit_username, edit_token;
    ImageButton btn_Submit, btn_Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_token = (EditText) findViewById(R.id.edit_tokenNo);
        btn_Submit = (ImageButton) findViewById(R.id.btn_Submit);
        btn_Cancel = (ImageButton) findViewById(R.id.btn_Cancel);
        btn_Submit.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_Submit:{

                submitButtonClickHandler();
            }break;

            case R.id.btn_Cancel:{

                LoginActivity.this.finish();
            }break;
        }


    }


    private void submitButtonClickHandler(){
        String user_Name = edit_username.getText().toString();
        String token_ID = edit_token.getText().toString();

        if (user_Name.equals("")) {

            edit_username.setError("Please Enter Email");

        }
        else if (token_ID.equals("")) {

            edit_token.setError("Please Enter Password");
        }
        else
        {
            loginUser();
        }

    }
    private void loginUser(){

        String user_Name = edit_username.getText().toString();
        String token_ID = edit_token.getText().toString();

        try {
            JSONObject json = new JSONObject();

            json.put("email",user_Name );
            json.put("password",token_ID);
            json.put("DBType","Nusi");


            new OkHttpUtil(LoginActivity.this, Constants.CHECKCustomerLogin,json.toString(),
                    LoginActivity.this,false,
                    Constants.
                            CHECK_CustomerLogin_TAG).
                    callRequest();



        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onResponse(String response, int tag) {
        if (response == null || response.equals("") || response.length() < 1) {
            // Utilities.showToast(HomeActivity.this, "Empty response for player statuses");
            return;
        } else {
            switch (tag) {

                case Constants.CHECK_CustomerLogin_TAG: {
                    checklogindetails(response);

                }
                break;


            }
        }
    }
    @Override
    public void onError(Exception e, int tag) {

        Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show();

    }

    public void checklogindetails(String response)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String res = jsonObject.getString("Responce");
            if (res.equals("0"))
            {
                Toast.makeText(LoginActivity.this,"Please enter Correct Username and Password",Toast.LENGTH_LONG).show();

                return;
            }
            else {

                MySQLiteHelper sb=new MySQLiteHelper(LoginActivity.this);
                sb.insertcredential(edit_username.getText().toString(),edit_token.getText().toString());
                String userid = jsonObject.getString("UserId");
                String dfClientId = jsonObject.getString("dfClientId");
                Boolean chkuserid=jsonObject.getBoolean("chkUserAdmin");
                String Clientname=jsonObject.getString("ClientName");
                SharedPreferenceUtil.setStringPreference(LoginActivity.this, AdvikonPreference.UserId, userid);
                SharedPreferenceUtil.setStringPreference(LoginActivity.this, AdvikonPreference.Clientname,Clientname);
                SharedPreferenceUtil.setStringPreference(LoginActivity.this, AdvikonPreference.dfClientId, dfClientId);
                SharedPreferenceUtil.setStringPreference(LoginActivity.this, AdvikonPreference.chkAdmin,String.valueOf(chkuserid));
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        }catch (Exception e)
        {
            e.getCause().toString();
        }



    }

}
