package com.textmessaging.sau.textmessaging.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.adapter.TabLayoutAdapter;
import com.textmessaging.sau.textmessaging.java.MyApplication;
import com.textmessaging.sau.textmessaging.services.ConnectivityReceiver;

import java.net.InetAddress;

public class HomeScreen extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener{
    SharedPreferences pref;
    TabLayout tabLayout;
    ViewPager viewPager;
     RelativeLayout relativeLayout;
    String   message;
    int color;

   /* int [] tabimg={
            R.drawable.chaticon,
            R.drawable.statusicon,
            R.drawable.callicon

    };
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

         relativeLayout = (RelativeLayout) findViewById(R.id.relative);



        //shared pref for Check user already login or first time
        pref = getApplicationContext().getSharedPreferences("logindata", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("value", "1");
        editor.apply();// Storing string

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Chats")/*.setIcon(tabimg[0])*/);
        tabLayout.addTab(tabLayout.newTab().setText("Calls")/*.setIcon(tabimg[1])*/);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabLayoutAdapter adapter = new TabLayoutAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
      //  String message;
        //int color;
        if (isConnected) {
           // message = "Good! Connected to Internet";
            //color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }

    }




    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        // register connection status listener
       MyApplication.getInstance().setConnectivityListener(HomeScreen.this);
      /*  isNetworkConnected();

        if (isNetworkConnected()==true)
        {

        }
        else
        {
            Snackbar snackbar1 = Snackbar.make(relativeLayout, "please connect Internet", Snackbar.LENGTH_SHORT);
            snackbar1.show();
        }*/
    }

/*    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .show();

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
        switch (id) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Intent intent = new Intent(getApplicationContext(),SettingScreen.class);
                startActivity(intent);
              //  Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            /*case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
