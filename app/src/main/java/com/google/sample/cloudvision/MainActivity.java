package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.CookieBarDismissListener;
import org.aviran.cookiebar2.OnActionClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cookiemain);

        Button btnTop = findViewById(R.id.btn_top);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieBar.build(MainActivity.this)
                        .setTitle(R.string.top_cookie_title)
                        .setTitleColor(R.color.yellow)
                        .setMessage(R.string.top_cookie_message)
                        .setIcon(R.drawable.ic_android_white_48dp)
                        .setDuration(5000)
                        .setCookieListener(new CookieBarDismissListener() {

                            @Override
                            public void onDismiss(int dismissType) {
                                String desc = "";
                                switch (dismissType) {
                                    case DismissType.DURATION_COMPLETE:
                                        desc = "Cookie display duration completed";
                                        break;
                                    case DismissType.USER_DISMISS:
                                        desc = "Cookie dismissed by user";
                                        break;
                                    case DismissType.USER_ACTION_CLICK:
                                        desc = "Cookie dismissed by action click";
                                        break;
                                    case DismissType.PROGRAMMATIC_DISMISS:
                                        desc = "Cookie dismissed programmatically";
                                        break;
                                    case DismissType.REPLACE_DISMISS:
                                        desc = "Replaced by new cookie";
                                        break;

                                }

                                Toast.makeText(MainActivity.this, desc, Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });


        Button btnBottomAnimated = findViewById(R.id.btn_bottom_animated);
        btnBottomAnimated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieBar.build(MainActivity.this)
                        .setTitle(R.string.fancy_cookie_title)
                        .setMessage(R.string.fancy_cookie_message)
                        .setIcon(R.drawable.ic_settings_white_48dp)
                        .setIconAnimation(R.animator.iconspin)
                        .setTitleColor(R.color.fancyTitle)
                        .setActionColor(R.color.fancyAction)
                        .setMessageColor(R.color.fancyMessage)
                        .setBackgroundColor(R.color.fancyBackground)
                        .setDuration(5000)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .setAction("OPEN SETTINGS", new OnActionClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(getApplicationContext(), "Action Engaged!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });

        Button btnBottomcamera = findViewById(R.id.btn_camera);
        btnBottomcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(MainActivity.this, Cloud.class)
                );


            }
        });

        findViewById(R.id.activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieBar.dismiss(MainActivity.this);
            }
        });




    }
}
