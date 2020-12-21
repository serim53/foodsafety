package com.google.sample.cloudvision;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import devlight.io.library.ntb.NavigationTabBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GIGAMOLE on 28.03.2016.
 */
public class VerticalNtbActivity extends Activity {

    String text="nanana";
    int num=3;

    String data;


    ArrayList<String> datanew = new ArrayList<String>();
    ArrayList<String> datanew2 = new ArrayList<String>();
    ArrayList<String> textlist = new ArrayList<String>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_ntb);

        Intent intent = getIntent();
        textlist = intent.getStringArrayListExtra("textlist"); //어레이 리스트 받아옴
        initUI();


    }



    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.right);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 8;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {


                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp, null, false);

                final TextView prdcttext=(TextView) view.findViewById(R.id.prdctname);

               // final ListView txtPage = (ListView) view.findViewById(R.id.listview);
                final TextView profile=(TextView) view.findViewById(R.id.name);
                final TextView profile2=(TextView) view.findViewById(R.id.name2);
                final TextView profile3=(TextView) view.findViewById(R.id.name3);
                final TextView profile4=(TextView) view.findViewById(R.id.name4);
                final TextView profile5=(TextView) view.findViewById(R.id.name5);
                final TextView profile6=(TextView) view.findViewById(R.id.name6);
                final TextView profile7=(TextView) view.findViewById(R.id.name7);
                final TextView profile8=(TextView) view.findViewById(R.id.name8);
                final TextView profile9=(TextView) view.findViewById(R.id.name9);
                final TextView profile10=(TextView) view.findViewById(R.id.name10);



                for(int i=0;i<textlist.size();i++) {
                    String text=textlist.get(i);
                    if (position == i) {
                        new Thread(new Runnable() {

                            @Override

                            public void run() {

                                try {
                                    datanew2= getData2(text); // 하단의 getData 메소드를 통해 데이터를 파싱
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {

                                    @Override

                                    public void run() {
                                        //adapter = new DataAdapter();
                                        //txtPage.setAdapter(adapter);
                                        //setText(data);

                                        profile.setText(datanew2.get(0));
                                        profile2.setText(datanew2.get(1));
                                        profile3.setText(datanew2.get(2));
                                        profile4.setText(datanew2.get(3));
                                        profile5.setText(datanew2.get(4));
                                        profile6.setText(datanew2.get(5));
                                        profile7.setText(datanew2.get(6));
                                        profile8.setText(datanew2.get(7));
                                        profile9.setText(datanew2.get(8));
                                        profile10.setText(datanew2.get(9));



                                        prdcttext.setText(text);

                                    }

                                });

                            }

                        }).start();
                    }
                }
//                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
//                txtPage.setText(String.format("Page #%d", position));

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.yellow_ntb);


        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.left);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        for(int j=0;j<textlist.size();j++){
            String textstr=textlist.get(j);
            if(textstr.length()>=4){
                textstr=textstr.substring(0,4)+"...";}
            Log.d(textstr, textstr);
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24, null),
                            Color.parseColor(colors[j]))
                            //.title(textlist.get(j))
                            .title(textstr)
                            .selectedIcon(getResources().getDrawable(R.drawable.ic_baseline_favorite_24, null))
                            .build()
            );



        }
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }

    ArrayList getData2(String text) throws UnsupportedEncodingException {

        StringBuffer buffer = new StringBuffer();

        int i=0; //1

        String queryUrl="http://openapi.foodsafetykorea.go.kr/api/6d2cd4d6541e469b9e96/I2710/xml/1/397";


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
            //tag= xpp.getName();




            loop:
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:

                        //buffer.append("파싱 시작 단계 \n\n");

                        break;



                    case XmlPullParser.START_TAG:

                        tag= xpp.getName(); // 태그 이름 얻어오기

                        //  if(tag.equals("row id="+i)) ;

                        String str;

                        if(tag.equals("row")){

                            if(i == 0){

                                //buffer.delete(0,buffer.length());
                                datanew.clear();
                            }

                            if (i==1){
                                break loop;
                            }

                        }
                        else if(tag.equals("PRDCT_NM")){

                            //buffer.append("품목명: ");

                            xpp.next();

                            if(text.equals(xpp.getText())){ //text는 이미지에서 받아오는 것 (현재는 가르시니로 고정)
                                i=1;
                            }

                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                            //buffer.append(xpp.getText());
                            datanew.add(str);
                            //prdct_name.add(xpp.getText());

                           // buffer.append("\n"); // 줄바꿈 문자 추가

                        }

                        else if(tag.equals("IFTKN_ATNT_MATR_CN")){

                            //buffer.append("섭취시주의사항 : ");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");
                        }

                        else if(tag.equals("PRIMARY_FNCLTY")){

                            //buffer.append("주된기능성:");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");

                        }

                        else if(tag.equals("DAY_INTK_LOWLIMIT")){

                            //buffer.append("일일섭취량 하한:");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");

                        }

                        else if(tag.equals("DAY_INTK_HIGHLIMIT")){

                            //buffer.append("일일섭취량 상한 :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");

                        }

                        else if(tag.equals("INTK_UNIT")){

                            //buffer.append("단위 :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");

                        }

                        else if(tag.equals("INTK_MEMO")){

                            //buffer.append("REMARK :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                            //buffer.append("\n");

                        }

                        else if(tag.equals("SKLL_IX_IRDNT_RAWMTRL")){

                            //buffer.append("성분명 :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                           // buffer.append("\n");

                        }

                        else if(tag.equals("CRET_DTM")){

                           // buffer.append("최초등록일 :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                           // buffer.append("\n");

                        }

                        else if(tag.equals("LAST_UPDT_DTM")){

                           // buffer.append("최종수정일 :");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                                datanew.add("-");
                            else
                                //buffer.append(xpp.getText());
                                datanew.add(str);
                           // buffer.append("\n");

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
            //buffer.append("파싱 종료 단계 \n");
            e.printStackTrace();

        }


        //buffer.append("파싱 종료datasearch \n");
        //buffer.append(prdct_name);


        //return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    return datanew;
    }

}
