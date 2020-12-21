package com.google.sample.cloudvision;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import android.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class Data extends AppCompatActivity {


    EditText editText;

    TextView textView;

    String data;

    String key="6d2cd4d6541e469b9e96";


    String text="가르시니아캄보지아 추출물";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);



        editText = (EditText)findViewById(R.id.editText);

        textView = (TextView)findViewById(R.id.textView);



    }



    // 버튼을 클릭했을 때 쓰레드를 생성하여 해당 함수를 실행하여 텍스트뷰에 데이터 출력

    public void buttonClicked(View v){

        switch( v.getId() ){

            case R.id.button:

                // 쓰레드를 생성하여 돌리는 구간

                new Thread(new Runnable() {

                    @Override

                    public void run() {

                        try {
                            data= getData(text); // 하단의 getData 메소드를 통해 데이터를 파싱
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {



                            @Override

                            public void run() {

                                textView.setText(data);

                            }

                        });



                    }

                }).start();

                break;

        }

    }





    String getData(String text) throws UnsupportedEncodingException {

        StringBuffer buffer = new StringBuffer();

        String str =  editText.getText().toString();

        String location = URLEncoder.encode(str,"UTF-8");

        int i=0; //1



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
            tag= xpp.getName();







            loop:
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:

                        buffer.append("파싱 시작 단계 \n\n");

                        break;



                    case XmlPullParser.START_TAG:

                        tag= xpp.getName(); // 태그 이름 얻어오기

                        //  if(tag.equals("row id="+i)) ;




                        if(tag.equals("row")){

                            if(i == 0){

                                buffer.delete(0,buffer.length());
                            }

                            if (i==1){
                                break loop;
                            }

                        }
                        else if(tag.equals("PRDCT_NM")){

                            //buffer.append("품목명: ");

                            xpp.next();

                            if(text.equals(xpp.getText())){
                                i=1;
                            }

                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가

                            buffer.append(xpp.getText());

                            buffer.append("\n"); // 줄바꿈 문자 추가

                        }

                        else if(tag.equals("IFTKN_ATNT_MATR_CN")){

                            //buffer.append("섭취시주의사항 : ");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("PRIMARY_FNCLTY")){

                            //buffer.append("주된기능성:");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("DAY_INTK_LOWLIMIT")){

                            //buffer.append("일일섭취량 하한:");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("DAY_INTK_HIGHLIMIT")){

                            //buffer.append("일일섭취량 상한 :");

                            xpp.next();

                            buffer.append(xpp.getText());//

                            buffer.append("\n");

                        }

                        else if(tag.equals("INTK_UNIT")){

                            //buffer.append("단위 :");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("  ,  ");

                        }

                        else if(tag.equals("INTK_MEMO")){

                            //buffer.append("REMARK :");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("SKLL_IX_IRDNT_RAWMTRL")){

                            //buffer.append("성분명 :");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("CRET_DTM")){

                            //buffer.append("최초등록일 :");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }

                        else if(tag.equals("LAST_UPDT_DTM")){

                            //buffer.append("최종수정일 :");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }


                        break;



                    case XmlPullParser.TEXT:

                        break;



                    case XmlPullParser.END_TAG:

                        tag= xpp.getName(); // 태그 이름 얻어오기

                        // if(tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈

                        break;

                }

                eventType= xpp.next();

            }

        } catch (Exception e) {
            buffer.append("파싱 종료 단계 \n");
            e.printStackTrace();

        }


        buffer.append("파싱 종료 \n");
        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환

    }

}