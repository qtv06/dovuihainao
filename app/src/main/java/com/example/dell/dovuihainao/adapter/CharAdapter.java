package com.example.dell.dovuihainao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.dovuihainao.R;

import java.util.ArrayList;

/**
 * Created by dell on 21/03/2018.
 */

public class CharAdapter extends ArrayAdapter<String> {
    private Context context;
    private int mLayout;
    private ArrayList<String> mList;
    public CharAdapter(@NonNull Context context, int resource,ArrayList<String> list) {
        super(context, resource, list);
        this.context = context;
        this.mLayout = resource;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(mLayout, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvWord = convertView.findViewById(R.id.tvWordAnswer);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String c = mList.get(position);
        viewHolder.tvWord.setText(c + "");
        return convertView;
    }

    private class ViewHolder{
        public TextView tvWord;
    }
}
