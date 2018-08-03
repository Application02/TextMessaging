package com.textmessaging.sau.textmessaging.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.textmessaging.sau.textmessaging.Activity.ContactList;
import com.textmessaging.sau.textmessaging.R;


public class ChatsTab extends Fragment{
    View view;

    public ChatsTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabchats);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactList.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}