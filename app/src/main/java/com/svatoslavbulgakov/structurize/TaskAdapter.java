package com.svatoslavbulgakov.structurize;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Task;
import util.DataBaseHandler;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private DataBaseHandler dataBaseHandler;
    private ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.dataBaseHandler = new DataBaseHandler();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_task_layout, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = this.tasks.get(position);
        holder.textViewName.setText(task.getName());
        holder.textViewDate.setText(task.getDate());
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private View itemView;
        private ImageView imageViewCategory;
        private TextView textViewName;
        private TextView textViewCategoryName;
        private TextView textViewDate;

        public TaskViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imageViewCategory = itemView.findViewById(R.id.recycler_item_task_layout_image_view_category_image);
            this.textViewName = itemView.findViewById(R.id.recycler_item_task_layout_textView_name);
            this.textViewCategoryName = itemView.findViewById(R.id.recycler_item_task_layout_image_view_category_name);
            this.textViewDate = itemView.findViewById(R.id.recycler_item_task_layout_text_view_date);
        }
    }
}
