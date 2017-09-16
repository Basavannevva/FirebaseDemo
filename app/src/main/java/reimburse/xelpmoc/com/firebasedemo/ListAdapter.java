package reimburse.xelpmoc.com.firebasedemo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.checked;

/**
 * Created by Irshad on 15/06/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private final firbaseUpadte update;
    Activity activity;
    List<ValueModel>list=new ArrayList<>();


    public ListAdapter(Activity activity, List<ValueModel> state_name_val) {
        this.activity = activity;
        this.list = state_name_val;
        update = (firbaseUpadte) activity;

    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.MyViewHolder holder, final int position) {
     holder.setIsRecyclable(false);
        holder.textVal.setText(list.get(position).values);
        holder.textVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.stateType(list.get(position).id,"Upadate",list.get(position).values);
            }
        });

        holder.textVal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                update.stateType(list.get(position).id,"Delete",list.get(position).values);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textVal;

        public MyViewHolder(View view) {
            super(view);
            textVal = (TextView) view.findViewById(R.id.textVal);

        }
    }


    public interface firbaseUpadte {
        void stateType(String id,String action,String text);

    }
}
