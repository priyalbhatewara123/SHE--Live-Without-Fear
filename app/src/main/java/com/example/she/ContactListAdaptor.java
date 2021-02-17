package com.example.she;


import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class ContactListAdaptor extends ArrayAdapter<Pair<String, String>> {

    private ArrayList<Pair<String, String>> list;
    ViewHolder viewHolder;
    ContactItemInterface itemInterface;
    Context context;

    interface ContactItemInterface {
        void editOptionSelected(String phoneNumber, String name);

        void deleteOptionSelected(String phoneNumber);
    }

    public ContactListAdaptor(Context context,
                              ArrayList<Pair<String, String>> list,
                              ContactItemInterface itemInterface) {
        super(context, R.layout.item_contact_list, list);
        this.list = list;
        this.itemInterface = itemInterface;
        this.context = context;
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
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contactName = view.findViewById(R.id.contact_name);
            viewHolder.contactNumber = view.findViewById(R.id.contact_number);
            viewHolder.contactNameInitial = view.findViewById(R.id.contact_initial);
            viewHolder.optionsIv = view.findViewById(R.id.options);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.contactName.setText(list.get(position).first);

        viewHolder.contactNameInitial.setText(Character.toString(Character.toUpperCase(
                list.get(position).first.charAt(0)
        )));
        viewHolder.contactNumber.setText(list.get(position).second);

        viewHolder.optionsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopMenu(v, list.get(position).second, list.get(position).first);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayPopMenu(v.findViewById(R.id.options), list.get(position).second, list.get(position).first);
                return true;
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView contactName;
        TextView contactNumber;
        TextView contactNameInitial;
        ImageView optionsIv;
    }

    void displayPopMenu(View view, final String phoneNumber, final String name) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.contact_list_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item != null) {
                    switch (item.getItemId()) {
                        case R.id.edit: {
                            itemInterface.editOptionSelected(phoneNumber, name);
                        }
                        break;
                        case R.id.delete: {
                            itemInterface.deleteOptionSelected(phoneNumber);
                        }
                        break;
                    }
                }
                return true;
            }
        });
        popupMenu.show();
    }

}
