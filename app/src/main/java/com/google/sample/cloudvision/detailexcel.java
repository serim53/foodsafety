package com.google.sample.cloudvision;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View
        ;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class detailexcel extends AppCompatActivity {

    private ArrayList<ClipData.Item> mItems = new ArrayList<>();

    private RecyclerView itemRv;

    Workbook workbook = new HSSFWorkbook();

    Sheet sheet = workbook.createSheet(); // 새로운 시트 생성

    Row row = sheet.createRow(0); // 새로운 행 생성
    Cell cell;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void saveExcel(){

        String text = "세리포리아 락세라타 균사체 배양물";
        cell = row.createCell(0); // 1번 셀 생성
        cell.setCellValue("품목명"); // 1번 셀 값 입력

        cell = row.createCell(1); // 2번 셀 생성
        cell.setCellValue("기능"); // 2번 셀 값 입

        row = sheet.createRow(398);

        StringBuffer buffer = new StringBuffer();

        int i = 0; //1

        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/6d2cd4d6541e469b9e96/I2710/xml/1/397";


        try {


            URL url = new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.

            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            // inputstream 으로부터 xml 입력받기

            xpp.setInput(new InputStreamReader(is, "UTF-8"));


            String tag;


            xpp.next();

            int eventType = xpp.getEventType();
            //tag= xpp.getName();


            loop:
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:


                        break;


                    case XmlPullParser.START_TAG:

                        tag = xpp.getName();

                        String str;

                        if (tag.equals("row")) {

                            if (i == 1) {
                                break loop;
                            }

                        }
                        else if(tag.equals("PRDCT_NM")){

                            //buffer.append("품목명: ");

                            xpp.next();

                            if(text.equals(xpp.getText())){ //text는 이미지에서 받아오는 것 (현재는 가르시니로 고정)
                                i=1;
                            }

                            str = xpp.getText();

                            if(str == null) {
                                cell = row.createCell(0);
                                cell.setCellValue("-");
                            }
                            else{
                                cell = row.createCell(0);
                                cell.setCellValue(str);
                            }

                        }
                        else if(tag.equals("PRIMARY_FNCLTY")){

                            //buffer.append("주된기능성:");

                            xpp.next();
                            str = xpp.getText();
                            if(str == null)
                            {
                                cell = row.createCell(1);
                                cell.setCellValue("-");
                            }
                            else{
                                cell = row.createCell(1);
                                cell.setCellValue(str);
                            }

                        }

                        break;


                    case XmlPullParser.TEXT:

                        break;


                    case XmlPullParser.END_TAG:

                        tag = xpp.getName(); // 태그 이름 얻어오기

                        // if(tag.equals("row")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈

                        break;

                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            //buffer.append("파싱 종료 단계 \n");
            e.printStackTrace();

        }

        File xlsFile = new File(getExternalFilesDir(null),"detailexcel.xls");

        try{
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os); // 외부 저장소에 엑셀 파일 생성
        }catch (IOException e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),xlsFile.getAbsolutePath()+"에 저장되었습니다",Toast.LENGTH_SHORT).show();

        Uri path = Uri.fromFile(xlsFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/excel");
        shareIntent.putExtra(Intent.EXTRA_STREAM,path);
        startActivity(Intent.createChooser(shareIntent,"엑셀 내보내기"));
    }



}