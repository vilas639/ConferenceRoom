package com.e.conferenceroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;


import android.content.Context;
import android.content.Intent;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import com.e.conferenceroom.Adapter.NotesAdapter;
import com.e.conferenceroom.Model.Listdata;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeScreen extends AppCompatActivity {




    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Listdata> list =new ArrayList<>();
    String selectedRoom="Room1";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Switch onOffSwitch = (Switch)  findViewById(R.id.on_off_switch);



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(HomeScreen.this);
        recyclerView.setLayoutManager(layoutManager);

        final NotesAdapter notesAdapter=new NotesAdapter(list,this);
        recyclerView.setAdapter(notesAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Notes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Listdata listdata=dataSnapshot1.getValue(Listdata.class);
                    if(listdata.selectroom.equals(selectedRoom))
                    {
                        
                        list.add(listdata);
                    }




                }
                notesAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);

                if (isChecked) {
                    // do something when check is selected
                    selectedRoom = "Room1";
                    list.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                Listdata listdata=dataSnapshot1.getValue(Listdata.class);
                                if(listdata.selectroom.equals(selectedRoom))
                                {
                                    list.add(listdata);
                                }




                            }
                            notesAdapter.notifyDataSetChanged();



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    //do something when unchecked
                    selectedRoom = "Room2";
                    list.clear();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                Listdata listdata=dataSnapshot1.getValue(Listdata.class);
                                if(listdata.selectroom.equals(selectedRoom))
                                {
                                    list.add(listdata);
                                }




                            }
                            notesAdapter.notifyDataSetChanged();



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });



    }


}

