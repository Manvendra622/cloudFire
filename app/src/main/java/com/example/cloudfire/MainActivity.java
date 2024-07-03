package com.example.cloudfire;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    EditText cityName;
    TextView temperature,weatherState;
    ArrayList<dataList> dataArray;

    EditText dateTime;

    public static MainActivity getInstance() {
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        dateTime=findViewById(R.id.datetime_input);
        dateTime.setInputType(InputType.TYPE_NULL);


        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(dateTime);
            }
        });

        weatherState=(TextView) findViewById(R.id.weatherCondition);
        temperature=(TextView) findViewById(R.id.showdata);
        cityName=(EditText) findViewById(R.id.city);

        Button secondButton=findViewById(R.id.weatherButton);

        dataArray=new ArrayList<>();

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
            }
        });

    }

    private void showDateTimeDialog(EditText dateTime) {
        //I have learned date time dialog addition from Source: https://www.youtube.com/watch?v=XG8OpQ7X-nY
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfday, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfday);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        dateTime.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };
                new TimePickerDialog(MainActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(MainActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent broadcasting=new Intent(MainActivity.this,BroadReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,broadcasting,PendingIntent.FLAG_IMMUTABLE);

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        Toast.makeText(this,"ALARM got set",Toast.LENGTH_SHORT).show();
    }


    public void fetch() {
        //I have learned json data parsing from Source: https://www.youtube.com/watch?v=wcc4z1r-xP4&t=280s
        String keyApi="f6047568425d3cb5aa619a89ca574cc0";
        String txtCity=cityName.getText().toString().trim();
        String urlWeather="https://pro.openweathermap.org/data/2.5/forecast/hourly?q="+txtCity+"&appid="+keyApi;
        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, urlWeather, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray1=response.getJSONArray("list");
                    long description1;
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject mainObject=jsonArray1.getJSONObject(i);
                        description1=mainObject.getLong("dt");
                        JSONObject jsonObject=mainObject.getJSONObject("main");
                        JSONArray jsonArray=mainObject.getJSONArray("weather");

                        dataArray.add(new dataList(description1,jsonObject,jsonArray));
                    }

                    Calendar myCal = Calendar.getInstance();

                    String dateFormat=String.valueOf(dateTime.getText());
                    dateFormat=dateFormat.replaceAll("\\W","");
                    long replacedTime=Long.parseLong(dateFormat);
                    long calendar_year=replacedTime/100000000;
                    long temp=replacedTime%100000000;
                    long calendar_month=temp/1000000;
                    long temp1=temp%1000000;
                    long calendar_day=temp1/10000;
                    long temp2=temp1%10000;
                    long calendar_hour=temp2/100;
                    long calendar_minute= temp2%100;

                    myCal.set((int) calendar_year,(int) (calendar_month-1),(int) calendar_day,(int) calendar_hour,(int) (calendar_minute-1));

                    long myDate=myCal.getTimeInMillis()/1000L;

                    for(int j=0;j< dataArray.size();j++){
                        dataList x=dataArray.get(j);
                        long y=x.getDt();
                        if(y>=myDate){
                            JSONArray weather=x.getWeather();
                            JSONObject main=weather.getJSONObject(0);
                            String description=main.getString("main");
                            JSONObject jsonObject=x.getMain();
                            String temp4=String.valueOf(jsonObject.getDouble("temp"));

                            double getTemp=Double.parseDouble(temp4)-273.00;
                            String setTemp=String.format("%.2f",getTemp);

                            weatherState.setText(description);
                            temperature.setText("Temperature in " +txtCity+ " at that time will be " +setTemp);

                            break;
                        }

                    }

                    setAlarm(myCal.getTimeInMillis());

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);
    }

    public TextView getWeatherState() {
        return weatherState;
    }
}