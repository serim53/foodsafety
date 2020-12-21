package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class DataSearch extends AppCompatActivity {

    ArrayList<String> prdct_name;

    TextView textView;

    String data;

    String key="6d2cd4d6541e469b9e96";

    //String input="베타카로틴, 설탕, 세리포리아 락세라타 균사체 배양물(제2018-5호) 스피루리나 소금 단백질"; //cloudvision에서 받아오는 데이터// 세리포리아 락세라타 균사체 배양물(제2018-5호) 베타카로틴

    ArrayList<String> textlist = new ArrayList<String>();// 중복된 품목명

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent=getIntent();

        prdct_name = intent.getStringArrayListExtra("prdct_name"); //품목명 모두 배열로 받아오기

        String input = intent.getStringExtra("message");

        setContentView(R.layout.activity_search);

        textlist= compare(input);

        Intent intent2 = new Intent (DataSearch.this, VerticalNtbActivity.class);
        intent2.putExtra("textlist",textlist);
        startActivity(intent2);

    }

    public ArrayList<String> compare(String input) {
        ArrayList<String> textlist=new ArrayList<String>(); //리턴값이 널일경우 찾을수 있는 항목이 없다고 표시

        int k=0;
        for(String name:prdct_name){
            if(input.contains(name.substring(0,3))){
                textlist.add(name);
                k++;
            }
        }

        return textlist;
    }
}