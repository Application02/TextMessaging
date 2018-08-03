/*
 * Copyright 2017 L4 Digital LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.textmessaging.sau.textmessaging.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.l4digital.fastscroll.FastScroller;
import com.textmessaging.sau.textmessaging.Activity.ChattingScreen;
import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.pojo.ContactModel;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GetAllContactAdapter extends RecyclerView.Adapter<GetAllContactAdapter.ViewHolder> implements FastScroller.SectionIndexer {
    //ArrayList<ContactModel> contactModel;
    LayoutInflater inflater;
    String Name = "A";
    ViewHolder holder;
    private Context context;

    private ArrayList<ContactModel> contactModel;
    private ArrayList<ContactModel> mFilteredList;

    public GetAllContactAdapter(FragmentActivity context, ArrayList<ContactModel> contactModels) {
        this.contactModel = contactModels;
        this.mFilteredList = contactModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.single_contact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtName.setText(mFilteredList.get(position).getName());
        holder.txtFirstChar.setText((mFilteredList.get(position).getName().toUpperCase().charAt(0) + ""));

        holder.txtFirstChar.setBackgroundColor(getMatColor("600"));
        holder.linearLayoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numbersend =mFilteredList.get(position).getNumber();
                String namesend = mFilteredList.get(position).getName();

                Log.d(TAG, "onClick: " +numbersend );
                Intent intent = new Intent(context, ChattingScreen.class);
                intent.putExtra("number",numbersend);
                intent.putExtra("name",namesend);
                context.startActivity(intent);
              /*  String callInfo = "tel:" + contactModel.get(position).getNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(callInfo));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
              //  context.startActivity(callIntent);

                startActivity(callIntent);*/

            }
        });
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public String getSectionText(int position) {

        return String.valueOf(contactModel.get(position).getName().charAt(0));
    }

    private int getMatColor(String typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    public void updateList(ArrayList<ContactModel> list) {
        contactModel = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFirstChar, mTextView, txtName, txtSetFirst;
        LinearLayout linearLayoutCall;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txtView);
            txtFirstChar = (TextView) itemView.findViewById(R.id.txtFirstCharecter);
            txtName = (TextView) itemView.findViewById(R.id.name);
            linearLayoutCall = (LinearLayout) itemView.findViewById(R.id.linearLayoutCall);
        }

    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = contactModel;
                } else {

                    ArrayList<ContactModel> filteredList = new ArrayList<>();

                    for (ContactModel androidVersion : contactModel) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getNumber().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContactModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
