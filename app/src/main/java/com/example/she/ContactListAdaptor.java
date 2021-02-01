package com.example.she;


import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactListAdaptor extends ArrayAdapter<Pair<String,String>> {

    private ArrayList<Pair<String,String>> list;
    ViewHolder viewHolder;

    public ContactListAdaptor(Context context, ArrayList<Pair<String,String>> list) {
        super(context, R.layout.item_contact_list, list );
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (list.size() > 0)
            return list.size();
        else
            return 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contactName = view.findViewById(R.id.contact_name);
            viewHolder.contactNumber = view.findViewById(R.id.contact_number);
            viewHolder.contactNameInitial = view.findViewById(R.id.contact_initial);
            viewHolder.editIv = view.findViewById(R.id.edit);
            viewHolder.deleteIv = view.findViewById(R.id.delete);

            view.setTag(viewHolder);
        }
        else
            {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.contactName.setText(list.get(position).first);

        viewHolder.contactNameInitial.setText(Character.toString(Character.toUpperCase(
                list.get(position).first.charAt(0)
        )));
        viewHolder.contactNumber.setText(list.get(position).second);
        viewHolder.editIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Edit feature coming soon",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Delete feature coming soon",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public class ViewHolder
    {
        TextView contactName;
        TextView contactNumber;
        TextView contactNameInitial;
        ImageView editIv;
        ImageView deleteIv;
    }
}
