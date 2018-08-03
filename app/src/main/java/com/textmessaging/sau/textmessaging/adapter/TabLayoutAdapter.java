package com.textmessaging.sau.textmessaging.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.textmessaging.sau.textmessaging.fragment.Calls;
import com.textmessaging.sau.textmessaging.fragment.Chats;
import com.textmessaging.sau.textmessaging.fragment.Status;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public TabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Chats chats = new Chats();
                return chats;
            case 1:
                Status status = new Status();
                return status;
            case 2:
                Calls calls = new Calls();
                return calls;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}