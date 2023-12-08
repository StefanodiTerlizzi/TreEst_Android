package com.example.treest.ListLines;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treest.Wall.FragmentChooseWall;
import com.example.treest.R;
import com.example.treest.model.Line;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView terminus1;
    TextView terminus2;
    FragmentChooseWall fragmentChooseWall;

    public ViewHolder(@NonNull View itemView, FragmentChooseWall fragmentChooseWall) {
        super(itemView);
        this.title = itemView.findViewById(R.id.tv_title);
        this.terminus1 = itemView.findViewById(R.id.tv_terminus1);
        this.terminus2 = itemView.findViewById(R.id.tv_terminus2);
        this.fragmentChooseWall = fragmentChooseWall;
    }

    public void set(Line line) {
        title.setText(line.getTitle());
        terminus1.setText(line.getTerminus1().getSname());
        terminus1.setOnClickListener(
                v -> {
                    fragmentChooseWall.HandleSelectedWall(line.getTerminus1().getDid(), line);
                }
        );
        terminus2.setText(line.getTerminus2().getSname());
        terminus2.setOnClickListener(
                v -> {
                    fragmentChooseWall.HandleSelectedWall(line.getTerminus2().getDid(), line);
                }
        );
    }
}
