package com.Adv.developer.instantmobile.Activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.Adv.developer.instantmobile.MySQLiteHelper;
import com.Adv.developer.instantmobile.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;


public class Splash_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        progressView = (CircularProgressView) findViewById(R.id.progress_view);

        checkUserCred();

    }

    private void checkUserCred()
    {
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
       MySQLiteHelper sb=new MySQLiteHelper(Splash_Activity.this);


       if((sb.getCredential()!=null) && (sb.getCredential().size() > 0) && (!sb.getCredential().isEmpty()))
       {
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {

                   Intent intent = new Intent(Splash_Activity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
                   progressView.setVisibility(View.VISIBLE);
                   progressView.stopAnimation();

               }

           }, SPLASH_DISPLAY_LENGTH);

       }
       else
       {
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {

                   Intent intent = new Intent(Splash_Activity.this, LoginActivity.class);
                   startActivity(intent);
                   finish();
                   progressView.setVisibility(View.INVISIBLE);
                   progressView.stopAnimation();

               }

           }, SPLASH_DISPLAY_LENGTH);

       }

    }


}
