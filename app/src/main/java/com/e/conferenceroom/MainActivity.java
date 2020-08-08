package com.e.conferenceroom;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.conferenceroom.Model.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener   {


    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    EditText title,desc;
    String titlesend,descsend,selectroom,starttime;
    private DatabaseReference mDatabase;
    RadioGroup radioGroup;
    RadioButton genderradioButton;
    private String format = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Notes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Listdata listdata=dataSnapshot1.getValue(Listdata.class);
                    if(listdata.endtime.equals("12:0"))
                    {
                        desc.setText("13:00");
                        starttime = "13:00";
                    }
                    else  if(listdata.endtime.equals("17:0"))
                    {
                        desc.setText("09:00");
                        starttime = "09:00";
                    }
                    else
                    {
                        desc.setText(listdata.endtime);
                        starttime = listdata.endtime;

                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this,year, month,day);
                datePickerDialog.show();
            }
        });

    }

    public void AddNotes(View view) {
        titlesend=title.getText().toString();
        descsend=desc.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(MainActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
          //  Toast.makeText(MainActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
            selectroom=genderradioButton.getText().toString();
        }

        if(TextUtils.isEmpty(titlesend) || TextUtils.isEmpty(descsend)){
            return;
        }
        AddNotes(titlesend,descsend,selectroom,starttime,descsend);

    }

    private void AddNotes(String titlesend, String descsend,String selectroom,String starttime, String endtime)
    {

        String id=mDatabase.push().getKey();
        Listdata listdata = new Listdata(id,titlesend, descsend,selectroom,starttime,endtime);
        mDatabase.child("Notes").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Notes Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                    }
                });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        myday = c.get(Calendar.DAY_OF_MONTH);
        TimePickerDialog timePickerDialog = new TimePickerDialog( MainActivity.this, MainActivity.this, hour, minute,true);
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

//        if(myHour > 9)
//        {
//            desc.setText(myHour + ":" + myMinute);
//        }
//        else
//        {
//            desc.setText("lunch");
//        }

        String time1 = "09:00:00";
        String time2 = "17:00:00";
        String lunch1 = "12:00:00";
        String lunch2 = "13:00:00";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        try {
            date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            Log.d("tmee",""+date2.getTime());
            Log.d("tmes",""+date1.getTime());

            Log.d("tme",""+difference);

            String hour1 = appendnumber(myHour);
            String myMinute1 = appendnumber(myMinute);
            Log.d("tme",""+hour1+":"+myMinute+":00");

            Date date3 = format.parse(hour1+":"+myMinute+":00");
            Log.d("date3", String.valueOf(date3.getTime()));

            Date date4 = format.parse(lunch1);
            Log.d("date4", String.valueOf(date4.getTime()));

            Date date5 = format.parse(lunch2);
            Log.d("date5", String.valueOf(date5.getTime()));


            if( date3.getTime() > date2.getTime() )
            {
                //afer not change
                Log.d("date3 after", hour1+":"+myMinute+":00");
            }
            else
            {
                //before
                if(date1.getTime() >  date3.getTime()  )
                {
                    Log.d("date3 good ", hour1+":"+myMinute+":00");

                    //after 12 unch time
                }
                else
                {
                    //normal time
                    //9 to 12   and  1 to 5
                    Log.d("date3 good ", hour1+":"+myMinute+":00");
                    if(date3.getTime() > date4.getTime())
                    {
                        if(date5.getTime() > date3.getTime() )
                        {
                            Log.d("date3 he is lunch  ", hour1+":"+myMinute+":00");
                        }
                        else
                        {
                            Log.d("in after work ", hour1+":"+myMinute+":00");
                            desc.setText(myHour + ":" + myMinute);
                        }

                    }
                    else
                    {
                        desc.setText(myHour + ":" + myMinute);
                    }



                   // Log.d("date3 ater 9", hour1+":"+myMinute+":00");

                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }



     public String appendnumber(int num)
     {
         String a= "";
         if (num < 10)
         {      System.out.println("0" + num  );
             a = "0" + num ;

         }
         else
         {
             System.out.println(num);
             a = String.valueOf(num);
         }

         return a;
     }




}
