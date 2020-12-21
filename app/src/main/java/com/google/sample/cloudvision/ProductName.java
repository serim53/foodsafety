package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;


public class ProductName extends AppCompatActivity {

    TextView textView;

    String data;

    String key="6d2cd4d6541e469b9e96";

    ArrayList<String> prdct_name = new ArrayList<String>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_productname);

        //textView = (TextView)findViewById(R.id.textView);

        new Thread(new Runnable() {

            @Override

            public void run() {

                try {
                    getData(); // 하단의 getData 메소드를 통해 데이터를 파싱
                    //getData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {



                    @Override

                    public void run() {



                        Intent intent2=getIntent();
                        String message = intent2.getStringExtra("message"); //


                        Intent intent = new Intent (ProductName.this, DataSearch.class);
                        intent.putExtra("prdct_name",prdct_name);
                        intent.putExtra("message",message);
                        startActivity(intent);

                    }

                });
            }

        }).start();
    }





    int getData() throws UnsupportedEncodingException {

        //StringBuffer buffer = new StringBuffer();

        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/6d2cd4d6541e469b9e96/I2710/xml/1/10";


        try {


            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.

            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결



            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();

            XmlPullParser xpp= factory.newPullParser();

            // inputstream 으로부터 xml 입력받기

            xpp.setInput( new InputStreamReader(is, "UTF-8") );


            String tag;


            xpp.next();

            int eventType= xpp.getEventType();


            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:

                        //buffer.append("파싱 시작 단계 \n\n");

                        break;



                    case XmlPullParser.START_TAG:

                        tag= xpp.getName(); // 태그 이름 얻어오기

                            if(tag.equals("PRDCT_NM")){

                            xpp.next();

                            prdct_name.add(xpp.getText());

/*                                buffer.append(xpp.getText());

                                buffer.append("\n");*/

                        }
 else
                                xpp.next();



                        break;



                    case XmlPullParser.TEXT:

                        break;



                    case XmlPullParser.END_TAG:


                        break;

                }

                eventType= xpp.next();

            }


        } catch (Exception e) {
            //buffer.append("파싱 종료 단계 \n");
            e.printStackTrace();

        }

        //buffer.append(prdct_name);

        //return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
        return 0;
    }

}