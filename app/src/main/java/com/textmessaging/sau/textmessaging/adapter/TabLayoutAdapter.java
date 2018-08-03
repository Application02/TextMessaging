package com.textmessaging.sau.textmessaging.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.textmessaging.sau.textmessaging.fragment.CallsTab;
import com.textmessaging.sau.textmessaging.fragment.ChatsTab;
import com.textmessaging.sau.textmessaging.fragment.StatusTab;

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
                ChatsTab chatsTab = new ChatsTab();
                return chatsTab;
            case 1:
                StatusTab statusTab = new StatusTab();
                return statusTab;
            case 2:
                CallsTab callsTab = new CallsTab();
                return callsTab;
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