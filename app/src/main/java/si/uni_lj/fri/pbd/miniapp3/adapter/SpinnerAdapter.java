package si.uni_lj.fri.pbd.miniapp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import si.uni_lj.fri.pbd.miniapp3.R;

public class SpinnerAdapter extends BaseAdapter {
    String ingredients[];
    Context context;

    public SpinnerAdapter(String[] ingredients, Context context){
        this.ingredients = ingredients;
        this.context=context;
    }
    @Override
    public int getCount() {
        return ingredients.length;
    }

    @Override
    public Object getItem(int position) {
        return ingredients[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.text_view_spinner_item);
        tv.setText(ingredients[position]);
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item,null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.text_view_spinner_item);
        tv.setText(ingredients[position]);
        return convertView;
    }
}
