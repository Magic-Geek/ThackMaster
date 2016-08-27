package com.hit.geek.thackmaster;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by dusz2 on 2016/8/26 0026.
 */
public class SearchActivity extends AppCompatActivity {

    Spinner departSpinner;
    ArrayAdapter<String> departAdapter = null;
    private String departLocation;
    Spinner arriveSpinner;
    ArrayAdapter<String> arriveAdapter = null;
    private String arriveLocation;

    private EditText dateEditText;
    private EditText timeEditText;
    private Button start;

    private int departYear;
    private int departMonth;
    private int departDay;



    public static final String[] departList = {"北京","杭州","西雅图","旧金山","拉斯维加斯","莫斯科","伦敦"};
    public static final String[] arriveList = {"北京","杭州","西雅图","旧金山","拉斯维加斯","莫斯科","伦敦"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("选择出行");
        setSupportActionBar(toolbar);

        //选择出发地
        departSpinner = (Spinner)findViewById(R.id.depart_pinner);
        departAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,departList);//
        departAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//下拉列表风格
        departSpinner.setAdapter(departAdapter);
        departSpinner.setVisibility(View.VISIBLE);

        departSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                departLocation = ((TextView) arg1).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        //选择到达地
        arriveSpinner = (Spinner)findViewById(R.id.arrive_pinner);
        arriveAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arriveList);//
        arriveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//下拉列表风格
        arriveSpinner.setAdapter(arriveAdapter);
        arriveSpinner.setVisibility(View.VISIBLE);

        arriveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                arriveLocation = ((TextView) arg1).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        dateEditText = (EditText)findViewById(R.id.date_edittext);

        final Calendar calendar=Calendar.getInstance();
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        departYear=year;
                        departMonth=monthOfYear+1;
                        departDay=dayOfMonth;

                        dateEditText.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        timeEditText = (EditText)findViewById(R.id.time_edittext);

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
            }
        });

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                intent.putExtra("from",departLocation);
                intent.putExtra("to",arriveLocation);
                intent.putExtra("time",departYear+"-"+departMonth+"-"+departDay);
                startActivity(intent);
            }
        });
    }
}
