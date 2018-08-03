package com.textmessaging.sau.textmessaging.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.scaledrone.lib.RoomListener;
import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.pojo.ContactModel;

import java.util.ArrayList;

public class ChattingScreen extends AppCompatActivity  {

    String number,name;
    TextView txtname;
    private static final String TAG = "ChattingScreen";
    private ArrayList<ContactModel> contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_screen);
        txtname = findViewById(R.id.txtname);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitleTextColor(Color.WHITE);
        //remove default app name
        toolbar.setTitle("");

        //this code for back arrow
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        number=extras.getString("number");
        name = extras.getString("name");

        if (name!=null)
        {
            txtname.setText(name);
        }
        else
        {
            txtname.setText(number);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chattingscreenmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.videocallchatingscreen:
                Toast.makeText(getApplicationContext(), "video Call", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:

                Toast.makeText(getApplicationContext(), "Audio call", Toast.LENGTH_LONG).show();
                return true;
          /*  case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
