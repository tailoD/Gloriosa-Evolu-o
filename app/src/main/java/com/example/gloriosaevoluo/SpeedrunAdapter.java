package com.example.gloriosaevoluo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpeedrunAdapter extends RecyclerView.Adapter<SpeedrunAdapter.ViewHolder> {

    private List<Speedrun> speedruns;

    public SpeedrunAdapter(List<Speedrun> speedruns) {
        this.speedruns = speedruns;
    }

    public void updateList(List<Speedrun> newList) {
        speedruns = newList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla o layout item personalizado
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atributos_speedrun, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Speedrun speedrun = speedruns.get(position);

        holder.gameTextView.setText(speedrun.getGame());
        holder.categoryTextView.setText(speedrun.getCategory());
        holder.namePlayerTextView.setText(speedrun.getNamePlayer());
        holder.timeTextView.setText(speedrun.getTime());
        holder.regionTextView.setText(speedrun.getRegion());
    }

    @Override
    public int getItemCount() {
        return speedruns.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView gameTextView;
        TextView categoryTextView;
        TextView namePlayerTextView;
        TextView timeTextView;
        TextView regionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            gameTextView = itemView.findViewById(R.id.textViewGame);
            categoryTextView = itemView.findViewById(R.id.textViewCategory);
            namePlayerTextView = itemView.findViewById(R.id.textViewPlayer);
            timeTextView = itemView.findViewById(R.id.textViewTime);
            regionTextView = itemView.findViewById(R.id.textViewRegion);
        }
    }
}


