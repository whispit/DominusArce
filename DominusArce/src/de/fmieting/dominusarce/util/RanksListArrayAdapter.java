/**
 * 
 */
package de.fmieting.dominusarce.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.fmieting.dominusarce.R;

/**
 * @author fmieting
 *
 */
public class RanksListArrayAdapter extends ArrayAdapter<RanksListItem>{

    Context context; 
    int layoutResourceId;    
    RanksListItem data[] = null;
    
    public RanksListArrayAdapter(Context context, int layoutResourceId, RanksListItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItem = convertView;
        RanksListItemHolder holder = null;
               
        //Get current item
        if(currentItem == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            currentItem = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new RanksListItemHolder();
            holder.icon = (ImageView)currentItem.findViewById(R.id.icon);
            holder.score = (TextView)currentItem.findViewById(R.id.score);
            holder.name = (TextView)currentItem.findViewById(R.id.name);
            holder.position = (TextView) currentItem.findViewById(R.id.position);
            
            currentItem.setTag(holder);
        } else {
            holder = (RanksListItemHolder)currentItem.getTag();
        }
            
        //Fill item with data
        RanksListItem statsListItem = data[position];
        holder.position.setText(String.format("% 3d.", statsListItem.position));
        holder.score.setText(statsListItem.score);
        if(holder.icon != null) holder.icon.setImageResource(statsListItem.icon);
        holder.name.setText(statsListItem.value);
        if(statsListItem.emphasize == true){
        	holder.name.setTypeface(null, Typeface.BOLD);
        	currentItem.setBackgroundColor(Color.WHITE - 0x30000000);
        } else {
        	holder.name.setTypeface(null, Typeface.NORMAL);
        	currentItem.setBackgroundColor(Color.TRANSPARENT);
        }
        
        return currentItem;
    }
    
    static class RanksListItemHolder
    {
        TextView score;
        ImageView icon;
        TextView name;
        TextView position;
    }
}
