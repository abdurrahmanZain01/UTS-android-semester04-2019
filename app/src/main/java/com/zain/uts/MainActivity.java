package com.zain.uts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.sql.Ref;

public class MainActivity extends AppCompatActivity {
    String[] daftar;
    ListView ListView1;
    Menu menu;
//    pegaksesan database
    protected Cursor cursor;
    DataHelper dbcenter;
//    diakses di BiodataAct,Update,Lihat
    public static MainActivity ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
                startActivity(intent);
            }
        });

        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
            }

//            pengaksesan dan penampilan data
     public void RefreshList(){
         SQLiteDatabase db = dbcenter.getReadableDatabase();
         cursor = db.rawQuery("select * from biodata", null);
         daftar = new String[cursor.getCount()];
         cursor.moveToFirst();
         for (int cc = 0; cc < cursor.getCount();cc++){
             cursor.moveToPosition(cc);
             daftar[cc] = cursor.getString(1).toString();
         }
         ListView1 = (ListView) findViewById(R.id.listView1);
         ListView1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,daftar));
         ListView1.setSelected(true);
         ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                 final String selection = daftar[arg2];
                 final CharSequence[] dialogitem = {"lihat biodata", "Update Biodata", "Hapus Biodata"};
                 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                 builder.setTitle("Pilihan");
                 builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int item) {
                         switch(item){
                             case 0:
                                 Intent i = new Intent(getApplicationContext(), LihatBioActivity.class);
                                 i.putExtra("nama", selection);
                                 startActivity(i);
                                 break;
                             case 1:
                                 Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                                 in.putExtra("nama", selection);
                                 startActivity(in);
                                 break;
                             case 2:
                                 SQLiteDatabase db = dbcenter.getWritableDatabase();
                                 db.execSQL("delete From biodata where nama = '"+ selection+ "'");
                                 RefreshList();
                                 break;
                         }
                     }
                 });
                 builder.create().show();
             }
         });
         ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
