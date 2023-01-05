package com.project.termmanager.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentEdit.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("name", current.getAssesmentName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("type", current.getType());

                    if(current.getCourseID() == -1 && mCourses.size() == 0){
                        intent.putExtra("courseID", 1);
                    } else if(current.getCourseID() == -1){
                        int newID = mCourses.get(mCourses.size() - 1).getTermID() + 1;
                        intent.putExtra("courseID", newID);

                    } else if(current.getCourseID() != -1){
                        intent.putExtra("courseID", current.getCourseID());
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getAssesmentName();
            holder.assessmentItemView.setText(name);
        } else {
            holder.assessmentItemView.setText("No course title");
        }
    }

    public void setAssessments(List<Assessment> assessments, List<Course> courses){
        mAssessments = assessments;
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        } else {
            return 0;
        }

    }
}
