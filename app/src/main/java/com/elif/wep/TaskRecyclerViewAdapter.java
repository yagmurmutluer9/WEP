package com.elif.wep;

import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

    ArrayList<Task> tasks;
    private ArrayList<TaskChild> subItemList = new ArrayList<>();
    ChildRecyclerViewAdapter adapter;


    public TaskRecyclerViewAdapter(ArrayList<Task> tasks) {

        this.tasks = tasks;
        adapter = new ChildRecyclerViewAdapter(subItemList);



    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_row, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);

        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());

        // Create sub item view adapter
        ChildRecyclerViewAdapter subItemAdapter = new ChildRecyclerViewAdapter(task.getChildArrayList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.childRecyclerView.getContext());
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setAdapter(subItemAdapter);


        holder.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder taskNameDialog = new AlertDialog.Builder(holder.itemView.getRootView().getContext());
                taskNameDialog.setTitle("Enter Item Name");

                final EditText taskName = new EditText(holder.itemView.getRootView().getContext());

                taskName.setInputType(InputType.TYPE_CLASS_TEXT);
                taskNameDialog.setView(taskName);

                taskNameDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String taskNameUser = taskName.getText().toString();
                        Toast.makeText(holder.itemView.getRootView().getContext(), taskNameUser + " is added", Toast.LENGTH_LONG).show();
                        task.addTaskChild(taskNameUser);
                        adapter.addExercise(new TaskChild (taskNameUser));
                        notifyDataSetChanged();


                    }
                });

                taskNameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                taskNameDialog.show();
            }


        });


    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }


    public  class TaskViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        RecyclerView childRecyclerView;
        private Button addTaskButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            childRecyclerView = itemView.findViewById(R.id.Child_RV);
            addTaskButton = itemView.findViewById(R.id.addItemBtn);

        }

    }
}
