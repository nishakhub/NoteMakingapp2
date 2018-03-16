package com.rajramchandani.notemakingapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    static ListView list;
    static ArrayList<String> notes=new ArrayList<String>();
    static ArrayList<String> track=new ArrayList<String>();
    static ArrayAdapter<String> adapter;
    static int first=0;
    static SharedPreferences sharedPreferences;
    static Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // MenuInflater menuInflater=getMenuInflater();
        getMenuInflater().inflate(R.menu.main_menu,menu);
       // return super.onCreateOptionsMenu(menu);
        return  true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add:
                Intent i=new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(i);
                first=1;
                return true;
            default:
                return false;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=(Toolbar)findViewById(R.id.toolbar3);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        sharedPreferences=this.getSharedPreferences("com.rajramchandani.notemakingapp", Context.MODE_PRIVATE);
        Intent mainIntent=getIntent();
        list=(ListView)findViewById(R.id.listview);
        try {
            notes=(ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("arr",ObjectSerializer.serialize(new ArrayList<String>())));
            track=(ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("arrr",ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, track);



        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Main3Activity.ed.setText(notes.get(position));
                Log.i("positon",Integer.toString(position));
                Intent i=new Intent(Main2Activity.this,Main3Activity.class);
                i.putExtra("val", notes.get(position));
                i.putExtra("position", position);
                startActivity(i);

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);
                    alert.setTitle("Alert");
                    alert.setMessage("Do u want to remove");

                    alert.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.remove(notes.get(position)).commit();
                            editor.remove(track.get(position)).commit();
                            //editor.remove(String.valueOf(locations.get(position))).commit();
                            // sharedPreferences.edit().remove(arr.get(position)).apply();
                            //sharedPreferences.edit().remove(latitude.get(position)).apply();
                            //sharedPreferences.edit().remove(longitude.get(position)).apply();
                            //sharedPreferences.edit().remove(String.valueOf(locations.get(position))).apply();
                            // editor.commit();
                            notes.remove(notes.get(position));
                            track.remove(track.get(position));
                            adapter.notifyDataSetChanged();

                        }
                    });
                    alert.show();



                return true;
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

