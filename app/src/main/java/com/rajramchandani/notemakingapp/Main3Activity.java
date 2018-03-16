package com.rajramchandani.notemakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    static EditText ed;
    int j=0;
    int positionn;
    String value=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        ed=(EditText)findViewById(R.id.editText3);
        Intent i=getIntent();
        positionn=i.getIntExtra("position",0);
        value=i.getStringExtra("val");
       /* j=0;
       ed=(EditText)findViewById(R.id.editText3);
        Intent i=getIntent();

        Log.i("position",Integer.toString(positionn));
        j=1;

        */

       if(Main2Activity.first!=1)
       {
           ed.setText(Main2Activity.notes.get(positionn));
       }
       else
       {
           ed.setText(null);
       }



    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.rajramchandani.notemakingapp", Context.MODE_PRIVATE);

        if(!ed.getText().toString().equals("") && Main2Activity.first==1)
        {
            //String result="";
            Toast.makeText(this,"Note Saved!",Toast.LENGTH_SHORT).show();
            Main2Activity.first=0;
            String data=ed.getText().toString();
            Main2Activity.notes.add(data);
            if(data.length()>10) {
                Main2Activity.track.add(data.substring(0, 10));
            }
            else
            {
                Main2Activity.track.add(data);
            }


            Main2Activity.adapter.notifyDataSetChanged();
            try {
                sharedPreferences.edit().putString("arr",ObjectSerializer.serialize(Main2Activity.notes)).apply();
                sharedPreferences.edit().putString("arrr",ObjectSerializer.serialize(Main2Activity.track)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main2Activity.adapter.notifyDataSetChanged();
            //Toast.makeText(this,j,Toast.LENGTH_SHORT).show();
        }
        else {
                if(!ed.getText().toString().equals("")) {
                    //String result="";
                    Toast.makeText(this,"Note Saved!",Toast.LENGTH_SHORT).show();
                    j = 0;
                    String data = ed.getText().toString();
                    Main2Activity.notes.set(positionn, data);
                   // result=data.substring(0,10);
                    if(data.length()>10) {
                        Main2Activity.track.set(positionn, data.substring(0, 10));
                    }
                    else
                    {
                        Main2Activity.track.set(positionn, data);
                    }
                    Log.i("position", Integer.toString(positionn));
                    Main2Activity.adapter.notifyDataSetChanged();
                    try {
                        sharedPreferences.edit().putString("arr", ObjectSerializer.serialize(Main2Activity.notes)).apply();
                        sharedPreferences.edit().putString("arrr", ObjectSerializer.serialize(Main2Activity.notes)).apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(this,j,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(Main2Activity.notes.get(positionn)).commit();
                    // editor.remove(track.get(position)).commit();
                    //editor.remove(String.valueOf(locations.get(position))).commit();
                    // sharedPreferences.edit().remove(arr.get(position)).apply();
                    //sharedPreferences.edit().remove(latitude.get(position)).apply();
                    //sharedPreferences.edit().remove(longitude.get(position)).apply();
                    //sharedPreferences.edit().remove(String.valueOf(locations.get(position))).apply();
                    // editor.commit();
                    Main2Activity.notes.remove(Main2Activity.notes.get(positionn));
                    Main2Activity.track.remove(Main2Activity.track.get(positionn));
                    Main2Activity.adapter.notifyDataSetChanged();
                }


        }


    }
}
