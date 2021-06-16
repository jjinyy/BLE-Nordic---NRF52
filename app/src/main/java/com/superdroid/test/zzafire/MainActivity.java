package com.superdroid.test.zzafire;

import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_csv).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        File exportDir = new File(Environment.getExternalStorageDirectory(),"");
                        if (!exportDir.exists()) {
                            exportDir.mkdirs();
                        }

                        //기간별 데이터 조회를 위해서 year/month , year/month/day hour:min:sec 형태로 저장한다.
                        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.KOREAN);
                        SimpleDateFormat month = new SimpleDateFormat("M", Locale.KOREAN);
                        SimpleDateFormat day = new SimpleDateFormat("dd", Locale.KOREAN);
                        SimpleDateFormat hour = new SimpleDateFormat("HH", Locale.KOREAN);
                        SimpleDateFormat min = new SimpleDateFormat("mm", Locale.KOREAN);
                        SimpleDateFormat sec = new SimpleDateFormat("ss", Locale.KOREAN);

                        //현재 시간을 받아온다.
                        Date currentTime = new Date();

                        String y = year.format(currentTime);
                        String m = month.format(currentTime);
                        String d = day.format(currentTime);
                        String h = hour.format(currentTime);
                        String minute = min.format(currentTime);
                        String s = sec.format(currentTime);


                        String csv_file_name = y + "_" + m + "_" + d + "_" + h + "_" + minute + "_" + s+".csv";

                        Log.d("HaHa",String.valueOf(exportDir));
                        List<DeviceInfo> infos = DeviceInfo.listAll(DeviceInfo.class);
                        File file = new File(exportDir, csv_file_name);
                        Log.d("HaHa","여기까지는 됨!");

                        try {
                            file.createNewFile();
                            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                            for(int i=0;i<infos.size();i++) {

                                String arrStr[] = {infos.get(i).address, infos.get(i).hrdata,
                                        infos.get(i).year,
                                        infos.get(i).month,
                                        infos.get(i).day,
                                        infos.get(i).hour,
                                        infos.get(i).min,
                                        infos.get(i).sec
                                };
                                csvWrite.writeNext(arrStr);
                            }
                            csvWrite.close();
                        } catch (Exception sqlEx) {
                            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                        }

                    }
                }
        );

        //임의로 데이터 넣기
        /*String[] address = new String[] { "00:11:22:33:44:55", "AA:BB:CC:DD:EE:FF", "00:11:22:AA:BB:CC" };
        String year = "2017";
        String month = "5";
        String day = "3";

        for (int i = 0; i < address.length; i++) {
            for (int j = 0; j <= 23; j++) {
                Random random = new Random();
                String hrdata = Integer.toString(random.nextInt(150));

                String hour = "";
                if (j < 10) {
                    hour = "0" + Integer.toString(j);
                } else {
                    hour = Integer.toString(j);
                }

                DeviceInfo info = new DeviceInfo(
                        address[i],
                        hrdata,
                        year,
                        month,
                        day,
                        hour,
                        "01",
                        "00");
                info.save();
            }
        }
        //임의로 데이터 넣기 끝*/
    }

    public void mOnClick_WD(View v){
        Intent intent = new Intent(this, FindWDActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

    }
    public void mOnClick_Data(View v){
        Intent intent = new Intent(this, SeeDataActivity.class);
        startActivity(intent);
    }

    public void onBackPressed(){
        moveTaskToBack(true);
        super.onBackPressed();
    }

}