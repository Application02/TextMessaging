package com.textmessaging.sau.textmessaging.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.l4digital.fastscroll.FastScrollRecyclerView;
import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.adapter.GetAllContactAdapter;
import com.textmessaging.sau.textmessaging.pojo.ContactModel;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    FastScrollRecyclerView recyclerView;
    GetAllContactAdapter getAllContactAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcontactlist);
        toolbar.setTitleTextColor(Color.WHITE);
        //remove default app name
        toolbar.setTitle("");

        //this code for back arrow
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialization();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contactlistmenu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        search(mSearchView);
/*        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/



      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
               // Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
        /*    case R.id.item2:

                //Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;*/
          /*  case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void initialization() {

        contactModelArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setFastScrollEnabled(true);

        getAllContacts();


    }

    private void getAllContacts() {

        Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
           // contactModelArrayList.add(contactModel);
            //  Log.d("name>>", name + "  " + phoneNumber);

            //code for remove duplicate name

            if(contactModelArrayList.size() == 0){
                contactModelArrayList.add(contactModel);
            }
            for(int i=0;i<contactModelArrayList.size();i++){

                if(!contactModelArrayList.get(i).getName().trim().equals(name)){
                    flag = 1;

                }else{
                    flag =0;
                    break;
                }

            }
            if(flag == 1){
                contactModelArrayList.add(contactModel);
            }

            //code over  for remove duplicate name

        }
        phones.close();

        getAllContactAdapter = new GetAllContactAdapter(this, contactModelArrayList);
        recyclerView.setAdapter(getAllContactAdapter);

}

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                getAllContactAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
