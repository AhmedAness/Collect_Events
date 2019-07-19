package com.example.dt.collect_events.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dt.collect_events.R;
import com.example.dt.collect_events.model.PersonUtils;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<PersonUtils> personUtils;

    public CustomRecyclerAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));

        PersonUtils pu = personUtils.get(position);

        holder.pName.setText(pu.getEventname());
        holder.pJobProfile.setText(pu.getTitle());
        holder.sTime.setText(pu.getStartTime());
        holder.eTime.setText(pu.getEndTime());
//        holder.Container.setBackgroundColor(Colo);
        

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;
        public TextView pJobProfile;
        public TextView sTime;
        public TextView eTime;
        public RelativeLayout Container;


        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.pNametxt);
            pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
            sTime = (TextView) itemView.findViewById(R.id.starttime);
            eTime = (TextView) itemView.findViewById(R.id.endtime);
            Container =  itemView.findViewById(R.id.singleRow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PersonUtils cpu = (PersonUtils) view.getTag();

                    Toast.makeText(view.getContext(), cpu.getEventname()+" is "+ cpu.getTitle(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

}
