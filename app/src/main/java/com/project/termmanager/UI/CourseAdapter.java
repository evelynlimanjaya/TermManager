package com.project.termmanager.UI;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.Entity.Term;
import com.project.termmanager.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);

                    //the .class name is the intended page to send the intent to (maybe go to the new screen?)

                    Intent intent = new Intent(context, CourseEdit.class);
                    //The informations to be sent to next screen
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instName", current.getInstructorName());
                    intent.putExtra("instPhone", current.getInstructorPhone());
                    intent.putExtra("instEmail", current.getInstructorEmail());

                    if(current.getTermID() == -1 && mTerms.size() == 0){
                        intent.putExtra("termID", 1);
                    } else if(current.getTermID() == -1){
                        int newID = mTerms.get(mTerms.size() - 1).getTermID() + 1;
                        intent.putExtra("termID", newID);

                    } else if(current.getTermID() != -1){
                        intent.putExtra("termID", current.getTermID());
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }



    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        } else {
            holder.courseItemView.setText("No course title");
        }
    }

    public void setCourses(List<Course> courses, List<Term> terms){
        mCourses = courses;
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        } else {
            return 0;
        }

    }
}
