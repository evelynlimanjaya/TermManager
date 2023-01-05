package com.project.termmanager.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.Entity.Term;
import com.project.termmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermEdit extends AppCompatActivity {
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    EditText termTitle;
    String name;
    EditText termStartDate;
    String start;
    EditText termEndDate;
    String end;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;

    int termID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        repo = new Repository(getApplication());
        termID = getIntent().getIntExtra("id", -1);
        Term selectedTerm = repo.getTermById(termID);
        termTitle = findViewById(R.id.editTextTermTitle);
        termStartDate = findViewById(R.id.editTextTermStartDate);
        termEndDate = findViewById(R.id.editTextTermEndDate);
        if(termID != -1){
            name = selectedTerm.getTermTitle();
            termTitle.setText(name);

            start = selectedTerm.getStartDate();
            termStartDate.setText(start);

            end = selectedTerm.getEndDate();
            termEndDate.setText(end);

            System.out.println(termID);
        }
//        if(termID != -1){
//            name = getIntent().getStringExtra("name");
//            termTitle.setText(name);
//
//            start = getIntent().getStringExtra("startDate");
//            termStartDate.setText(start);
//
//            end = getIntent().getStringExtra("endDate");
//            termEndDate.setText(end);
//
//            System.out.println(termID);
//        }

        startDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                updateLabelStart();
            }
        };

        endDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                updateLabelEnd();
            }
        };

        termStartDate.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v){
                 new DatePickerDialog(TermEdit.this, startDP, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
             }
        });

        termEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(TermEdit.this, endDP, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        int newTermID = 0;
        if(termID == -1 && repo.getAllTerms().size() == 0){
            newTermID = 1;
        } else if(termID == -1){
            int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
            newTermID = newID;

        } else if (termID != -1) {
            newTermID = termID;
        }
        for(Course c:repo.getAllCourses()){
            if(c.getTermID() == newTermID){
                filteredCourses.add(c);
            }
        }
        adapter.setCourses(filteredCourses, repo.getAllTerms());

        System.out.println("Term id " + termID);
    }

    private void updateRecyclerView(){

    }

    private void updateLabelStart(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        termStartDate.setText(sdf.format(calendarStart.getTime()));

    }

    private void updateLabelEnd(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        termEndDate.setText(sdf.format(calendarEnd.getTime()));

    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveTermMenu:
                Term term;
                System.out.println(termTitle.getText().toString());
                if(termID == -1 && repo.getAllTerms().size() == 0){
                    term = new Term(1, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
                    repo.insertTerm(term);
                } else if(termID == -1){
                    int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
                    term = new Term(newID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
                    repo.insertTerm(term);
                } else if (termID != -1){
                    term = new Term(termID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
                    repo.updateTerm(term);
                }
                return true;
            case R.id.deleteTermMenu:
                List<Course> coursesList = repo.getAllCourses();
                int coursesCount = 0;
                for(Course c: coursesList){
                    if(c.getTermID() == termID){
                        coursesCount++;
                    }
                }
                System.out.println("Courses count: " + coursesCount);
                if(coursesCount == 0){
                    term = new Term(termID, name, start, end);
                    repo.deleteTerm(term);
                } else {
                    Toast.makeText(TermEdit.this, "Can't delete term with courses", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.refreshTermEdit:
                RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
                final CourseAdapter adapter = new CourseAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Course> filteredCourses = new ArrayList<>();

                int newTermID = 0;
                if(termID == -1 && repo.getAllTerms().size() == 0){
                    newTermID = 1;
                } else if(termID == -1){
                    int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
                    newTermID = newID;

                } else if (termID != -1) {
                    newTermID = termID;
                }
                for(Course c:repo.getAllCourses()){
                    if(c.getTermID() == newTermID){
                        filteredCourses.add(c);
                    }
                }
                adapter.setCourses(filteredCourses, repo.getAllTerms());
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public void saveButton(View view) {
        Term term;
        System.out.println(termTitle.getText().toString());
        if(termID == -1 && repo.getAllTerms().size() == 0){
            term = new Term(1, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.insertTerm(term);
        } else if(termID == -1){
            int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.insertTerm(term);
        } else if (termID != -1){
            term = new Term(termID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.updateTerm(term);
        }
    }

    public void deleteButton(View view) {
        List<Course> coursesList = repo.getAllCourses();
        int coursesCount = 0;
        for(Course c: coursesList){
            if(c.getTermID() == termID){
                coursesCount++;
            }
        }
        System.out.println("Courses count: " + coursesCount);
        if(coursesCount == 0){
            Term term = new Term(termID, name, start, end);
            repo.deleteTerm(term);
        } else {
            Toast.makeText(TermEdit.this, "Can't delete term with courses", Toast.LENGTH_LONG).show();
        }

    }

    public void addCourseButton(View view) {
        Intent intent = new Intent(TermEdit.this, CourseEdit.class);
        if(termID == -1 && repo.getAllTerms().size() == 0){
            intent.putExtra("termID", 1);
        } else if(termID == -1){
            int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
            intent.putExtra("termID", newID);

        } else if(termID != -1){
            intent.putExtra("termID", termID);
        }
        startActivity(intent);
    }
}