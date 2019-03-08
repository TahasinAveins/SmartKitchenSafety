package com.example.smartkitchensafty;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListView extends ArrayAdapter<String> {

    private String[] gas_state;
    private String[] time;
    private Activity context ;

    public CustomListView( Activity context, String[] gas_state,String[] time) {
        super(context, R.layout.layout,gas_state);
        this.context = context;
        this.gas_state=gas_state;
        this.time=time;
    }

    @NonNull
    @Override

    public View getView(int position , @NonNull View convertView, @NonNull ViewGroup parent) {

        View r = convertView;

        ViewHolder viewHolder = null;
        if (r==null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();

            r = layoutInflater.inflate(R.layout.layout,null,true);

            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder)r.getTag();
        }

        viewHolder.textView1.setText(gas_state[position]);
        viewHolder.textView2.setText(time[position]);

        return r;

    }

    class ViewHolder{
        TextView textView1;
        TextView textView2;

        ViewHolder(View v){

            textView1 = (TextView)v.findViewById(R.id.gas_status);
            textView2 = (TextView)v.findViewById(R.id.time_data);
        }
    }
}
