package com.google.sample.cloudvision;


//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Navigation extends AppCompatActivity
{

    Button camera,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera=findViewById(R.id.camera);
        data=findViewById(R.id.data);

        // camera.setOnClickListener(this);
        //data.setOnClickListener(this);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Navigation.this, Cloud.class)
                );
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Navigation.this, Data.class)
                );
            }
        });
        // buttonClicked();
    }





/*

    public void onClick( View v) {

        switch (v.getId()) {

            case R.id.camera:
                startActivity(
                        new Intent(MainActivity.this, Cloud.class)
                );
                break;

            case R.id.data:
                startActivity(
                        new Intent(MainActivity.this, Data.class)
                );
                break;

            default:
                break;


        }
    }

*/

}