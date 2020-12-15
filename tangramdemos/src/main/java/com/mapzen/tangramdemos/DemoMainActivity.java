package com.mapzen.tangramdemos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DemoMainActivity extends AppCompatActivity {

    View.OnClickListener onClickListener = v -> {
        ViewHolder viewHolder = (ViewHolder) v.getTag();
        startActivity(new Intent(DemoMainActivity.this, viewHolder.activityClass));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureListView();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        Class activityClass;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
        }
    }

    private void configureListView() {
        RecyclerView listView = findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public int getItemCount() {
                return DemoDetails.LIST.length;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(DemoMainActivity.this).inflate(R.layout.list_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
                view.setOnClickListener(onClickListener);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                DemoDetails demo = DemoDetails.LIST[position];
                holder.title.setText(demo.getTitleId());
                holder.description.setText(demo.getDetailId());
                holder.activityClass = demo.getActivityClass();
            }
        });
    }
}