package com.thesanmark.clients.client_384.project_1.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.thesanmark.clients.client_384.project_1.Activities.TripListActivity;
import com.thesanmark.clients.client_384.project_1.Models.TripModel;
import com.thesanmark.clients.R;

/**
 * Created by bryden on 11/8/16.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private TripListActivity mContext;
    private List<TripModel> modelList;
    LayoutInflater layoutInflater;
    public TripAdapter(TripListActivity mContext, List<TripModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
        layoutInflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = null;

        convertView = layoutInflater.inflate(R.layout.trip_list_item, null);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TripModel model = modelList.get(position);
        holder.tv1.setText(model.getID());
        holder.tv2.setText(model.getStart_time());
        holder.tv3.setText(model.getEnd_time());
        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("TripList");
                builder.setMessage("Are you sure to update or delete this trip information?");
                builder.setPositiveButton("UPDATE TRIP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mContext.Update(position);
                    }
                }).setNegativeButton("DELETE TRIP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mContext.Delete(position);
                    }
                }).setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv1, tv2, tv3;
        LinearLayout mainLayout;
        public MyViewHolder(View view)
        {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.textView);
            tv2 = (TextView) view.findViewById(R.id.textView2);
            tv3 = (TextView) view.findViewById(R.id.textView3);
            mainLayout = (LinearLayout)view.findViewById(R.id.layout);
        }
    }
}
