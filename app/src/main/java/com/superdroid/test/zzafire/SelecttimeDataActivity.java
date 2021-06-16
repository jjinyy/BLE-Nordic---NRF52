package com.superdroid.test.zzafire;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelecttimeDataActivity extends AppCompatActivity {
    Button btn_date;
    RadioButton btn_date1;
    RadioButton btn_date2;

    TextView text_date1;
    Calendar c = Calendar.getInstance();

    int myear;
    int mmonth;
    int mday;


    ListView listView_up;//리스트뷰 객체
    BleListViewAdapter bleList = null;//리스트 어댑터

    ListView listView_down;
    GraphListViewAdapter graphList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttime_data);

        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date1 = (RadioButton) findViewById(R.id.btn_date1);
        btn_date2 = (RadioButton) findViewById(R.id.btn_date2);
        text_date1 = (TextView) findViewById(R.id.text_date1);


        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);

        this.setListView();
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, SeeDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void setListView() {
        // 기기 address 가져오기


        List<DeviceInfo> info = DeviceInfo.find(DeviceInfo.class, null, null, "address", "address asc", null);

        //Log.d("ttt","ERROR");
        int size = info.size();

        bleList = new BleListViewAdapter(this, size);
        listView_up = (ListView) findViewById(R.id.listView_selecttime);
        listView_up.setAdapter(bleList);

        for (int i = 1; i < size; i++) {
            Log.d("ttt",String.valueOf(info.get(i).address.toString()));
            setDevice(i, info.get(i).address.toString());

                        //Log.d("ttt","error");
        }

        graphList = new GraphListViewAdapter(getApplicationContext(), size);
        listView_down = (ListView) findViewById(R.id.listView_graph2);
        listView_down.setAdapter(graphList);

        // 체크박스 있는 리스트뷰 아이템 클릭 리스너
        listView_up.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bleList.getIsCheck(position)) {      // 체크되있을 경우
                    graphList.setPoint(position, null);
                    graphList.notifyDataSetChanged();
                } else {
                    String address = bleList.getDevice(position);

                    if(btn_date1.isChecked()) {      // "월" 라디오 버튼이 체크
                        // [시작기간] ~ [끝기간]
                        String date1 = text_date1.getText().toString();      // [시작기간] 텍스트뷰 내용 가져오기
                        String[] yearMonth = date1.split("/");            // 텍스트를 "/"를 기준으로 자르기 ex) "2017/5"를 { "2017, "5" }

                  /*
                   * 쿼리
                   * info1 : 해당 년도와 월의 시작일을 알기 위해서 "DAY ASC"로 정렬
                   * info2 : 해당 년도와 월의 마지막일을 알기 위해서 "DAY DESC"로 정렬
                   */
                        List<DeviceInfo> info1 = DeviceInfo.find(DeviceInfo.class,
                                "address=? and year=? and month=?",
                                new String[] { address, yearMonth[0], yearMonth[1] },
                                null,
                                "day asc",
                                "1");
                        List<DeviceInfo> info2 = DeviceInfo.find(DeviceInfo.class,
                                "address=? and year=? and month=?",
                                new String[] { address, yearMonth[0], yearMonth[1] },
                                null,
                                "day desc",
                                "1");

                  /* 예외 처리 */
                        if (info1.size() == 0) {
                            Toast.makeText(getApplicationContext(), "데이터가 없습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int dayStart = Integer.parseInt(info1.get(0).day);
                        int dayEnd = Integer.parseInt(info2.get(0).day);

                  /* 매 일마다 평균 구하고 Point 추가 */
                        ArrayList<Point> points = new ArrayList<Point>();

                        for (int i = dayStart; i <= dayEnd; i++) {
                            String year = yearMonth[0];
                            String month = yearMonth[1];
                            String day = Integer.toString(i);

                            info1.clear();
                            info1 = DeviceInfo.find(DeviceInfo.class,
                                    "address=? and year=? and month=? and day=?",
                                    new String[] {address, year, month, day},
                                    null,
                                    "day",
                                    null);

                            int sum = 0;

                            for (int j = 0; j < info1.size(); j++) {
                                sum += Integer.parseInt(info1.get(j).hrdata);
                            }

                            points.add(new Point(i, (int)(sum / info1.size())));
                        }
                  /* 매 일마다 평균 구하고 Point 추가 끝 */

                        Point[] p = new Point[points.size()];
                        p = points.toArray(p);
                        setPoint(position, p);
                    } else if(btn_date2.isChecked()) {      // "일" 라디오 버튼이 체크
                        String date1 = text_date1.getText().toString();
                        String[] yearMonthDay = date1.split("/");

                        List<DeviceInfo> info1 = DeviceInfo.find(DeviceInfo.class,
                                "address=? and year=? and month=? and day=?",
                                new String[] { address, yearMonthDay[0], yearMonthDay[1], yearMonthDay[2] },
                                null,
                                "hour asc",
                                "1");
                        List<DeviceInfo> info2 = DeviceInfo.find(DeviceInfo.class,
                                "address=? and year=? and month=? and day=?",
                                new String[] { address, yearMonthDay[0], yearMonthDay[1], yearMonthDay[2] },
                                null,
                                "hour desc",
                                "1");

                  /* 예외 처리 */
                        if (info1.size() == 0) {
                            Toast.makeText(getApplicationContext(), "데이터가 없습니다", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int hourStart = Integer.parseInt(info1.get(0).hour);
                        int hourEnd = Integer.parseInt(info2.get(0).hour);

                        ArrayList<Point> points = new ArrayList<Point>();

                        for (int i = hourStart; i <= hourEnd; i++) {
                            String year = yearMonthDay[0];
                            String month = yearMonthDay[1];
                            String day = yearMonthDay[2];
                            String hour = "";
                            if (i < 10) {
                                hour = "0" + Integer.toString(i);
                            } else {
                                hour = Integer.toString(i);
                            }

                            info1.clear();
                            info1 = DeviceInfo.find(DeviceInfo.class,
                                    "address=? and year=? and month=? and day=? and hour=?",
                                    new String[] { address, year, month, day, hour },
                                    null,
                                    "hour",
                                    null);

                            int sum = 0;

                            for (int j = 0; j < info1.size(); j++) {
                                sum += Integer.parseInt(info1.get(j).hrdata);
                            }
                            Log.d("ttt","Hyejinhaha");

                            points.add(new Point(i, (int)(sum / info1.size())));
                            Log.d("ttt","Hyejin");
                        }

                        Point[] p = new Point[points.size()];
                        p = points.toArray(p);
                        setPoint(position, p);
                    }
                }

                bleList.setIsCheck(position);
                bleList.notifyDataSetChanged();
            }
        });
    }

    private void setDevice(int index, String device) {
        bleList.setDevice(index, device);
        bleList.notifyDataSetChanged();
    }

    private void setPoint(int index, Point[] point) {
        graphList.setPoint(index, point);
        graphList.notifyDataSetChanged();
    }

    DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myear = year;
            mmonth = monthOfYear + 1;
            mday = dayOfMonth;

            text_date1.setText(myear + "/" + mmonth + "/" + mday);

        }
    };

    public void disableDayField(DatePickerDialog datePickerDialog) {
        try {
            Field[] f = datePickerDialog.getClass().getDeclaredFields();
            datePickerDialog.getDatePicker().setSpinnersShown(true);

            for (Field dateField : f) {
                if (dateField.getName().equals("mDayPicker") ||
                        dateField.getName().equals("mDaySpinner") ||
                        dateField.getName().equals("mDatePicker")) {
                    dateField.setAccessible(true);

                    DatePicker datePicker = (DatePicker) dateField.get(datePickerDialog);
                    Field datePickerFields[] = dateField.getType().getDeclaredFields();


                    // Lollipop 이후 버전
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                        if (daySpinnerId != 0) {
                            View daySpinner = datePicker.findViewById(daySpinnerId);
                            if(daySpinner != null) {
                                daySpinner.setVisibility(View.GONE);
                            }
                        }
                    }

                    // Lollipop 이전 버전
                    else {
                        for (Field datePickerField : datePickerFields) {
                            if (datePickerField.getName().contains("mDay")) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = new Object();
                                dayPicker = datePickerField.get(datePicker);

                                try {
                                    ((View) dayPicker).setVisibility(View.GONE);
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myear = year;
            mmonth = monthOfYear + 1;

            text_date1.setText(myear + "/" + mmonth);

        }
    };


    public void mOnClick_date1(View v){
        Dialog datepicker = new DatePickerDialog(SelecttimeDataActivity.this,dateSetListener1,myear,mmonth,mday);
        DatePickerDialog datePickerDialog = new DatePickerDialog(SelecttimeDataActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener2, myear, mmonth, mday);
        if(btn_date1.isChecked()==true) {
            disableDayField(datePickerDialog);
            datePickerDialog.show();
        }

        if(btn_date2.isChecked()==true) {
            datepicker.show();
        }
    }
}